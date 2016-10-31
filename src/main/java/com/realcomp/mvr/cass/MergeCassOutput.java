package com.realcomp.mvr.cass;

import com.realcomp.accuzip.util.JsonFileWriter;
import com.realcomp.address.Address;
import com.realcomp.lockstep.OrderedLockstep;
import com.realcomp.mvr.MVRTransaction;
import com.realcomp.mvr.MVRTransactionJsonReader;
import com.realcomp.mvr.MVRTransactionReader;
import com.realcomp.mvr.Owner;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MergeCassOutput extends OrderedLockstep<MVRTransaction, Address>{

    private static final Logger logger = Logger.getLogger(MergeCassOutput.class.getName());

    private JsonFileWriter<MVRTransaction> writer;

    public MergeCassOutput(@NotNull MVRTransactionReader mvrTransactions, @NotNull AddressReader cassOutput)
            throws IOException{
        super(mvrTransactions, cassOutput);
        writer = new JsonFileWriter<>();
    }

    public void open(@NotNull OutputStream out) throws IOException{
        Objects.requireNonNull(out);
        writer.open(out);
    }

    @Override
    public Object getInputKey(MVRTransaction input){
        return input.getId();
    }

    @Override
    public Object getUpdateKey(Address match){
        //The Address id will be the MVRTransactionId + "-" + suffix.
        // (e.g., "129484848844-owner", "129484848844-leinHolder-0")
        String id = match.getId();
        int pos = id.indexOf("-");
        return pos < 0 ? id : id.substring(0, pos);
    }

    @Override
    public void emit(List<MVRTransaction> inputs, List<Address> updates) throws IOException{


        if (inputs.size() == 2 && inputs.get(0).get

        if (inputs.size() != 1){
            throw new IOException(
                    String.format(
                            "Sanity check failed: There were [%d] MVRTransactions and [%d] Addresses for [%s]",
                            inputs.size(), updates.size(), inputs.get(0).getId()));
        }

        MVRTransaction tx = inputs.get(0);
        for (Address address: updates){
            saveAddress(tx, address);
        }
        writer.write(tx);
    }

    private void saveAddress(@NotNull MVRTransaction tx, @NotNull Address address) throws IOException{
        Objects.requireNonNull(tx);
        Objects.requireNonNull(address);

        String suffix = getIdSuffix(address);
        if (suffix.equals("owner")){
            for (Owner owner: tx.getOwners()){
                owner.setAddress(new Address(address));
            }
        }
        else if (suffix.startsWith("lienHolder-")){
            int index = Integer.parseInt(suffix.substring(suffix.indexOf("-") + 1));
            tx.getLienHolders().get(index).setAddress(address);
        }
        else if (suffix.equals("location")){
            tx.setVehicleLocation(address);
        }
        else if (suffix.equals("renewal")){
            tx.setRenewalAddress(address);
        }
        else{
            throw new IOException("Unhandled Address id [" + address.getId() + "]");
        }
    }

    private String getIdSuffix(Address address){
        String id = address.getId();
        int pos = id.indexOf("-");
        if (pos <= 0){
            throw new IllegalArgumentException("Address id [" + address.getId() + "] is not in the expected format.");
        }
        return id.substring(pos + 1);
    }

    private void LeinHolderAddress(MVRTransaction tx, Address address){

    }

    public long getCount(){
        return writer.getCount();
    }

    @Override
    public void close() throws Exception{
        writer.close();
    }


    public static void main(String[] args) throws Exception{

        OptionParser parser = new OptionParser();
        OptionSpec<String> in = parser.accepts("in", "json encoded MVRTransaction objects (default: STDIN)")
                .withRequiredArg().describedAs("file");
        OptionSpec<String> cass = parser.accepts("cass", "json encoded Address objects")
                .withRequiredArg().describedAs("file").required();
        OptionSpec<String> out = parser.accepts("out", "json encoded MVRTransaction objects with standardized Addresses (default: STDOUT)")
                .withRequiredArg().describedAs("file");

        OptionSpec help = parser.acceptsAll(Arrays.asList("?", "help"), "show help");

        int result = 1;

        OptionSet options = parser.parse(args);
        if (options.has(help)){
            parser.printHelpOn(System.err);
        }
        else{
            try (MVRTransactionJsonReader original = new MVRTransactionJsonReader(options.has(in) ? new FileInputStream(options.valueOf(in)) : System.in);
                 AddressReader cassOutput = new AddressReader(new FileInputStream(options.valueOf(cass)));
                 MergeCassOutput merge = new MergeCassOutput(original, cassOutput)){

                merge.open(options.has(out) ? new FileOutputStream(options.valueOf(out)) : System.out);
                merge.execute();
                logger.info("Input: " + merge.getInputCount());
                logger.info("Matched: " + merge.getInputMatchCount());
                logger.info("Output: " + merge.getCount());
                result = 0;
            }
            catch (IOException ex){
                logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        System.exit(result);
    }

}
