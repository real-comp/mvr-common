package com.realcomp.mvr;

import com.realcomp.lockstep.ObjectReader;

import java.io.IOException;

public interface MVRTransactionReader extends AutoCloseable, ObjectReader<MVRTransaction>{

    MVRTransaction read() throws IOException;

    void close() throws IOException;
}
