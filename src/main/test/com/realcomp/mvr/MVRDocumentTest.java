package com.realcomp.mvr;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class MVRDocumentTest{


    @Test
    public void getLatest() throws Exception{

        MVRDocument doc = new MVRDocument();
        assertFalse(doc.getLatest().isPresent());

        MVRTransaction older = new MVRTransaction();
        older.setTransactionDate("20010804");

        doc.addHistory(older);
        assertEquals(older, doc.getLatest().get());


        MVRTransaction newer = new MVRTransaction();
        newer.setTransactionDate("20170101");
        doc.addHistory(newer);
        assertEquals(newer, doc.getLatest().get());
    }

}