package com.realcomp.mvr.hadoop;

import com.realcomp.data.DataSetID;
import com.realcomp.data.hadoop.fs.Paths;
import com.realcomp.data.hadoop.fs.PathsFactory;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Builds the TX DriversLicense data from the Real-Comp source data.
 *
 * This is a basic Hadoop M/R job and does not rely on many realcomp-data-hadoop utilities.
 *
 */
public class MVRBuild extends Configured implements Tool{

    private static final Logger logger = Logger.getLogger(MVRBuild.class.getName());

    public static final String ATTRIBUTE_PREFIX = "_attr_";
    private DateFormat df = new SimpleDateFormat("yyyyMMdd");

    protected List<Path> inputPaths;
    protected Path outputPath;
    protected String jobName;
    protected boolean slowStartReducers = true;
    protected int numReduceTasks = 50;
    protected boolean compressMapOutput = true;
    protected Map<String,String> attributes;


    public MVRBuild() {
        this.inputPaths = new ArrayList<>();
        attributes = new HashMap<>();
    }


    public void addInputPath(@NotNull Path inputPath){
        Objects.requireNonNull(inputPath);
        this.inputPaths.add(inputPath);
    }

    /**
     * @return the path that will contain the output
     */
    public Path getOutputPath() {
        return outputPath;
    }

    /**
     * @param path the Hadoop path that contains the output data.
     */
    public void setOutputPath(Path path) {
        this.outputPath = path;
    }



    /**
     * @return all the attributes that should be passed the the Hadoop job context and
     * made available to the reducers.
     */
    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        if (attributes == null)
            throw new IllegalArgumentException("attributes is null");

        this.attributes = attributes;
    }

    public String setAttribute(String name, String value){
        return attributes.put(name, value);
    }

    public String getAttribute(String name){
        return attributes.get(name);
    }

    /**
     * Adds all attributes to the configuration using the ATTRIBUTE_PREFIX
     * to distinguish themselves from other Hadoop properties in the job context.
     * @param conf
     * @throws IOException
     */
    protected void applyAttributes(Configuration conf){
        for (Map.Entry<String,String> entry: attributes.entrySet()){
            conf.set(entry.getKey(), entry.getValue());
        }
    }


    @NotNull
    public List<Path> computeInputPaths(Configuration conf) throws IOException{

        List<Path> result = new ArrayList<>();
        Paths paths = PathsFactory.build(conf);
        for (Path inputPath: inputPaths){
            result.addAll(paths.list(inputPath, JsonPathFilter.INSTANCE, true));
        }

        //TODO: temporarily disabling date checks.
       // ensureAllDataIsAvailable(result);
        return result;
    }

    private void ensureAllDataIsAvailable(@NotNull List<Path> paths) throws IOException{
        Objects.requireNonNull(paths);

        if (paths.isEmpty()){
            throw new IOException("No input paths found.");
        }

        try{
            List<String> dates = getRecentDataDates(paths);
            Collections.sort(dates);
            Calendar expected = Calendar.getInstance();
            Calendar actual = Calendar.getInstance();

            expected.setTime(df.parse(dates.remove(0)));
            //each date should increase by 7 days.
            for (String date: dates){
                expected.add(Calendar.DAY_OF_YEAR, 7);
                actual.setTime(df.parse(date));
                if (!isWithinOneDay(expected, actual)){
                    throw new IOException("Missing a weekly MVR file!  Expected: "  + df.format(expected.getTime()) + " Actual: " + df.format(actual.getTime()));
                }
                expected.setTime(actual.getTime());
            }
        }
        catch (ParseException ex){
            Logger.getLogger(MVRBuild.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private boolean isWithinOneDay(Calendar expected, Calendar actual){
        boolean result = false;
        if (expected.get(Calendar.YEAR) == actual.get(Calendar.YEAR)){
            if (expected.get(Calendar.MONTH) == actual.get(Calendar.MONTH)){
                int diff = Math.abs(expected.get(Calendar.DAY_OF_MONTH) - actual.get(Calendar.DAY_OF_MONTH));
                if (diff <= 1){
                    result = true;
                }
            }
        }
        return result;
    }

    private List<String> getRecentDataDates(List<Path> paths) throws ParseException{
        List<String> dates = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        for (Path path: paths){
            DataSetID id = DataSetID.parse(path.toString());
            String date = id.getVersion();
            c.setTime(df.parse(date));
            if (c.get(Calendar.YEAR) >= 2015){
                dates.add(date);
            }
        }
        return dates;
    }


    @Override
    public int run(String[] strings) throws Exception{

        if (inputPaths.isEmpty())
            throw new IllegalStateException("input path(s) not set");
        if (outputPath == null)
            throw new IllegalStateException("output path not set");

        Configuration conf = getConf();
        conf.setBoolean("mapreduce.map.output.compress", true);
        if (slowStartReducers){
            conf.set("mapreduce.reduce.slowstart.completed.maps",".90");
        }
        conf.setBoolean("mapreduce.map.output.compress", compressMapOutput);
        conf.setBoolean("mapreduce.map.speculative", false);
        conf.setBoolean("mapreduce.reduce.speculative", false);

        applyAttributes(conf);

        Class mapper = MVRMapper.class;
        Class reducer = MVRReducer.class;

        Job job = Job.getInstance(conf);
        job.setJobName(jobName == null ? mapper.getName() : jobName);
        job.setJarByClass(MVRBuild.class);
        job.setMapperClass(mapper);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setMapSpeculativeExecution(false);
        job.setReduceSpeculativeExecution(false);
        job.setReducerClass(reducer);
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);
        job.setNumReduceTasks(numReduceTasks);

        for (Path path: computeInputPaths(conf)){
            logger.log(Level.INFO, "adding path: {0}", path);
            MultipleInputs.addInputPath(job, path, TextInputFormat.class);
        }

        SequenceFileOutputFormat.setOutputPath(job, outputPath);
        SequenceFileOutputFormat.setCompressOutput(job, true);
        SequenceFileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);
        job.waitForCompletion(true);
        return job.isSuccessful() ? 0 : 1;
    }


    public static void main(String[] args) throws Exception {
        String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
        OptionParser parser = new OptionParser();
        OptionSpec<String> in = parser.acceptsAll(Arrays.asList(new String[]{"i","in"}))
                .withRequiredArg().describedAs("HDFS/S3 input path")
                .defaultsTo("s3://rc-data-raw/mvr/tx/rc/");
        OptionSpec<String> out = parser.acceptsAll(Arrays.asList(new String[]{"o","out"}))
                .withRequiredArg().describedAs("HDFS/S3 ouptput path")
                .defaultsTo("s3://rc-build-output/mvr/tx/rc/" + today);
        OptionSpec<String> attr = parser.acceptsAll(Arrays.asList(new String[]{"a","attr"}))
                .withRequiredArg()
                .describedAs("attribute(s) set in every Record (i.e., valueDescription:2011 Certified)");
        OptionSpec help = parser.acceptsAll(Arrays.asList("?", "help"), "show help");

        int result = 1;
        OptionSet options = parser.parse(args);
        if (options.has(help)){
            parser.printHelpOn(System.err);
        }
        else{
            MVRBuild mvrBuild = new MVRBuild();
            mvrBuild.addInputPath(new Path(options.valueOf(in)));
            mvrBuild.setOutputPath(new Path(options.valueOf(out)));
            for (String attribute : options.valuesOf(attr)) {
                int pos = attribute.indexOf(":");
                mvrBuild.setAttribute(attribute.substring(0, pos), attribute.substring(pos + 1));
            }
            result = ToolRunner.run(mvrBuild, args);
        }
        System.exit(result);
    }
}
