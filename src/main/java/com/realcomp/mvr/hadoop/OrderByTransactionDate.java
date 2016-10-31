package com.realcomp.mvr.hadoop;

import com.realcomp.mvr.MVRTransaction;

import java.util.Comparator;

/**
 * Orders MVRTransaction by their transaction date.
 *
 *
 */
public class OrderByTransactionDate implements Comparator<MVRTransaction> {


    @Override
    public int compare(MVRTransaction a, MVRTransaction b){
        String txDateA = a.getTransactionDate();
        String txDateB = b.getTransactionDate();

        if (txDateA == null){
            throw new IllegalArgumentException("no transaction date found in [" + a.toString() + "]");
        }
        else if (txDateB == null){
            throw new IllegalArgumentException("no transaction date found in [" + b.toString() + "]");
        }
        else{
            return txDateA.compareTo(txDateB);
        }
    }

}
