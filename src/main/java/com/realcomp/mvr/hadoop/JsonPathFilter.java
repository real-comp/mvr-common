package com.realcomp.mvr.hadoop;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

public class JsonPathFilter implements PathFilter{

    public static final JsonPathFilter INSTANCE = new JsonPathFilter();


    @Override
    public boolean accept(Path path){
        return path.getName().endsWith(".json") || path.getName().endsWith(".json.gz");
    }
}
