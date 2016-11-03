package com.realcomp.mvr.hadoop;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realcomp.mvr.MVRDocument;
import com.realcomp.mvr.MVRTransaction;
import com.realcomp.mvr.TransactionStatus;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

public class MVRReducer extends Reducer<Text,Text,NullWritable,Text>{

    private static final String ATTR_BUILD_DATE = "buildDate";
    private static final Logger logger = Logger.getLogger(MVRReducer.class.getName());

    private final ObjectMapper jackson;
    private final String buildDate;

    private enum Counters{RECORDS_IN, RECORDS_OUT, DELETE_RECORDS_SKIPPED, OLD_RECORDS_SKIPPED};

    public MVRReducer() {
        jackson = new ObjectMapper();
        jackson.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jackson.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        buildDate = df.format(new Date());
    }


    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException{
        List<MVRTransaction> transactions = convertFromHadoopWritable(values);
        int count = transactions.size();
        sortByTransactionDate(transactions);
        transactions = filterDeletions(transactions);
        if (!transactions.isEmpty()) {
            MVRDocument record = new MVRDocument(transactions.get(transactions.size() - 1));
            for (MVRTransaction tx: transactions){
                record.addHistory(tx);
            }
            record.setAttribute(ATTR_BUILD_DATE, buildDate);
            String json = jackson.writeValueAsString(record);
            context.write(NullWritable.get(), new Text(json));
            context.getCounter(Counters.RECORDS_OUT).increment(1);
        }
        else {
            context.getCounter(Counters.DELETE_RECORDS_SKIPPED).increment(count);
        }
    }


    /**
     * If a delete is encountered in the middle of a sorted list of MVRTransactions, remove it and all
     * preceding records.
     *
     * @param sorted sorted by transaction date
     * @return filtered MVRTransaction, still sorted by transaction date.
     */
    private List<MVRTransaction> filterDeletions(List<MVRTransaction> sorted) {
        assert (sorted != null);
        List<MVRTransaction> result = new ArrayList<>();
        Iterator<MVRTransaction> itr = sorted.iterator();
        while (itr.hasNext()){
            MVRTransaction tx = itr.next();
            //if (tx.getTransactionStatus() == TransactionStatus.DELETED && itr.hasNext()){
            if (tx.getTransactionStatus() == TransactionStatus.DELETED){
                //this indicates that the document number is being reused or is a test record
                result.clear();
            }
            else {
                result.add(tx);
            }
        }

        /*
        //if the collection contains only deletes, then filter all transactions data.
        long nonDeleteCount = result.stream().filter(t -> t.getTransactionStatus() != TransactionStatus.DELETED).count();
        if (nonDeleteCount == 0){
            result.clear();
        }
        */

        return result;
    }

    private void sortByTransactionDate(List<MVRTransaction> transactions) {
        Collections.sort(transactions, new OrderByTransactionDate());
    }

    private List<MVRTransaction> convertFromHadoopWritable(Iterable<Text> values) throws IOException {
        List<MVRTransaction> records = new ArrayList<>();
        Iterator<Text> itr = values.iterator();
        while (itr.hasNext()) {
            records.add(jackson.readValue(itr.next().toString(), MVRTransaction.class));
        }
        return records;
    }
}
