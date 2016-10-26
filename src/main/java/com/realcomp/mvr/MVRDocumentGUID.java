package com.realcomp.mvr;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Utility class to generate the MVR GUID with the form "MVR-{source}-{id}"
 */
public class MVRDocumentGUID{

    public static String generate(@NotNull MVRDocument mvr){
        Objects.requireNonNull(mvr);
        return String.format("%s-%s-%s", "MVR", mvr.getSource(), mvr.getId());
    }

    public static String generate(@NotNull String source, @NotNull String id){
        Objects.requireNonNull(source);
        Objects.requireNonNull(id);
        return String.format("%s-%s-%s", "MVR", source, id);
    }
}
