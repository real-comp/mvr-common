package com.realcomp.mvr.cass;

import com.realcomp.accuzip.util.JsonFileReader;
import com.realcomp.address.Address;
import com.realcomp.lockstep.ObjectReader;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * An ObjectReader for Lockstepping MVR address standardization
 */
class AddressReader implements ObjectReader<Address>{

    private JsonFileReader<Address> reader;

    public AddressReader(@NotNull InputStream in) throws IOException{
        Objects.requireNonNull(in);
        reader = new JsonFileReader<>(Address.class);
        reader.open(in);
    }

    @Override
    public Address read() throws IOException{
        return reader.read();
    }

    @Override
    public void close() throws IOException{
        reader.close();
    }
}
