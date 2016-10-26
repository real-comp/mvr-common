package com.realcomp.mvr.cass;

import com.realcomp.accuzip.util.JsonFileReader;
import com.realcomp.accuzip.util.JsonFileWriter;
import com.realcomp.address.RawAddress;
import com.realcomp.mvr.LienHolder;
import com.realcomp.mvr.MVRTransaction;
import com.realcomp.mvr.Owner;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The MVRTransaction has many addresses to standardize:
 * <ol>
 *     <li>ownership addresses</li>
 *     <li>renewal address</li>
 *     <li>lien holder addresses</li>
 *     <li>vehicle location addresses</li>
 * </ol>
 */
public class CassInputFileWriter{

    private static final Logger logger = Logger.getLogger(CassInputFileWriter.class.getName());

    long skipCount = 0;

    public long write(JsonFileReader<MVRTransaction> reader, JsonFileWriter<RawAddress> writer) throws IOException{
        skipCount = 0;
        MVRTransaction tx = reader.read();
        while (tx != null){
            Collection<RawAddress> raw = filterRawAddresses(getAllRawAddresses(tx));
            for (RawAddress entry: raw){
                writer.write(entry);
            }

            if (raw.isEmpty()){
                skipCount++;
            }

            tx = reader.read();
        }
        return writer.getCount();
    }

    public long getSkipCount(){
        return skipCount;
    }



    private Collection<RawAddress> filterRawAddresses(Collection<RawAddress> raw){
        Map<String,RawAddress> filtered = new HashMap<>();
        for (RawAddress entry: raw){
          filtered.put(entry.getId(), entry);
        }
        return filtered.values();
    }


    private List<RawAddress> getAllRawAddresses(MVRTransaction tx) throws IOException{
        List<RawAddress> addresses = new ArrayList<>();

        RawAddress renewal = tx.getRenewalRawAddress();
        if (renewal != null && !renewal.getAddressLines().isEmpty()){
            if (renewal.getId() == null || renewal.getId().isEmpty()){
                throw new IOException("No id found in RawAddress for renewal in MVRTransaction [" + tx.getId() + "]");
            }
            addresses.add(renewal);
        }


        RawAddress location = tx.getRawVehicleLocation();
        if (location != null && !location.getAddressLines().isEmpty()){
            if (location.getId() == null || location.getId().isEmpty()){
                throw new IOException("No id found in RawAddress for rawVehicleLocation in MVRTransaction [" + tx.getId() + "]");
            }
            addresses.add(location);
        }

        for (Owner owner: tx.getOwners()){
            RawAddress raw = owner.getRawAddress();
            if (raw != null && !raw.getAddressLines().isEmpty()){
                if (raw.getId() == null || raw.getId().isEmpty()){
                    throw new IOException("No id found in RawAddress for owner in MVRTransaction [" + tx.getId() + "]");
                }
                addresses.add(raw);
            }
        }

        for (LienHolder lienHolder: tx.getLienHolders()){
            RawAddress raw = lienHolder.getRawAddress();
            if (raw != null && !raw.getAddressLines().isEmpty()){
                if (raw.getId() == null || raw.getId().isEmpty()){
                    throw new IOException("No id found in RawAddress for lienHolder in MVRTransaction [" + tx.getId() + "]");
                }
                addresses.add(raw);
            }
        }

        return addresses;
    }


    public static void main(String[] args) throws Exception{

        OptionParser parser = new OptionParser();
        OptionSpec<String> in = parser.accepts("in", "json encoded MVRTransaction objects (default: STDIN)")
                .withRequiredArg().describedAs("file");
        OptionSpec<String> out = parser.accepts("out", "json encoded RawAddress objects (default: STDOUT)")
                .withRequiredArg().describedAs("file");

        OptionSpec help = parser.acceptsAll(Arrays.asList("?", "help"), "show help");

        int result = 1;

        OptionSet options = parser.parse(args);
        if (options.has(help)){
            parser.printHelpOn(System.err);
        }
        else{
            InputStream inputStream = options.has(in) ? new FileInputStream(options.valueOf(in)) : System.in;
            OutputStream outputStream = options.has(out) ? new FileOutputStream(options.valueOf(out)) : System.out;

            try (JsonFileReader<MVRTransaction> reader = new JsonFileReader<>(MVRTransaction.class);
                 JsonFileWriter<RawAddress> writer = new JsonFileWriter()){

                reader.open(inputStream);
                writer.open(outputStream);
                CassInputFileWriter cassWriter = new CassInputFileWriter();
                long count = cassWriter.write(reader, writer);
                logger.info(count + " RawAddresses written.");
                logger.info(cassWriter.getSkipCount() + " records with no RawAddress skipped.");
                result = 0;
            }
            catch (IOException ex){
                logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        System.exit(result);
    }



}
