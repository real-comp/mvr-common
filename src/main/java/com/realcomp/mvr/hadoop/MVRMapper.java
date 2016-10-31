package com.realcomp.mvr.hadoop;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realcomp.mvr.MVRTransaction;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Builds the MVRDocument  data from the Real-Comp source data.
 *
 * This is a basic Hadoop M/R job and does not rely on (many) realcomp-data-hadoop utilities.
 */
public class MVRMapper extends Mapper<LongWritable,Text,Text,Text>{

    private static final Logger logger = Logger.getLogger(MVRMapper.class.getName());

    private ObjectMapper jackson;
    private Map<String,String> attributes;

    private enum Counters{RECORDS_IN};

    public MVRMapper() {
        jackson = new ObjectMapper();
        jackson.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jackson.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        attributes = new HashMap<>();
    }

    @Override
    protected void setup(Context context) throws IOException, InterruptedException{
        super.setup(context);
        Iterator<Map.Entry<String, String>> itr = context.getConfiguration().iterator();
        while (itr.hasNext()){
            Map.Entry<String,String> entry = itr.next();
            if (entry.getKey().startsWith(MVRBuild.ATTRIBUTE_PREFIX)){
                attributes.put(entry.getKey().substring(MVRBuild.ATTRIBUTE_PREFIX.length()), entry.getValue());
            }
        }
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
        MVRTransaction tx = jackson.readValue(value.toString(), MVRTransaction.class);
        for (Map.Entry<String,String> entry: attributes.entrySet()){
            tx.setAttribute(entry.getKey(), entry.getValue());
        }
        context.getCounter(Counters.RECORDS_IN).increment(1);
        context.write(new Text(tx.getId()), new Text(jackson.writeValueAsString(tx)));
    }
}
