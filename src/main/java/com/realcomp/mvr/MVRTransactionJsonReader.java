package com.realcomp.mvr;

import com.realcomp.accuzip.util.JsonFileReader;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


public class MVRTransactionJsonReader implements MVRTransactionReader{

    private JsonFileReader<MVRTransaction> reader;

    public MVRTransactionJsonReader(@NotNull InputStream in) throws IOException{
        Objects.requireNonNull(in);
        reader = new JsonFileReader<>(MVRTransaction.class);
        reader.open(in);
    }

    @Override
    public MVRTransaction read() throws IOException{
        return reader.read();
    }

    @Override
    public void close() throws IOException{
        reader.close();
    }
}
