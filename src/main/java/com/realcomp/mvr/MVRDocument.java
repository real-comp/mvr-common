package com.realcomp.mvr;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MVRDocument{

    @NotNull
    private String guid;

    @NotNull
    private String source;

    @NotNull
    private String state;

    @NotNull
    private String id;

    @NotNull
    private DocumentType type;

    @NotNull
    private TransactionStatus transactionStatus;

    @NotNull
    private TreeSet<MVRTransaction> history;

    @NotNull
    private Map<String,String> attributes;

    public MVRDocument(){
        id = "";
        source = "";
        state = "";
        history = new TreeSet<>();
        attributes = new HashMap<>();
        type = DocumentType.UNKNOWN;
        transactionStatus = TransactionStatus.UNKNOWN;
        guid = MVRDocumentGUID.generate(this);
    }


    public MVRDocument(@NotNull MVRTransaction latest){
        Objects.requireNonNull(latest);
        id = latest.getId();
        source = latest.getSource();
        state = latest.getState();
        history = new TreeSet<>();
        attributes = new HashMap<>();
        type = latest.getType();
        transactionStatus = latest.getTransactionStatus();
        guid = MVRDocumentGUID.generate(this);
        history = new TreeSet<>();
        attributes = new HashMap<>();
        attributes.putAll(latest.getAttributes());
        guid = MVRDocumentGUID.generate(this);
    }


    @NotNull
    public String getGuid(){
        return guid;
    }

    public void setGuid(@NotNull String guid){
        Objects.requireNonNull(guid);
        this.guid = guid;
    }

    @NotNull
    public String getId(){
        return id;
    }

    public void setId(@NotNull String id){
        Objects.requireNonNull(id);
        this.id = id;
        guid = MVRDocumentGUID.generate(this);
    }

    @NotNull
    public String getSource(){
        return source;
    }

    public void setSource(@NotNull String source){
        Objects.requireNonNull(source);
        this.source = source;
        guid = MVRDocumentGUID.generate(this);
    }

    @NotNull
    public String getState(){
        return state;
    }

    public void setState(@NotNull String state){
        this.state = state;
    }


    @NotNull
    public DocumentType getType(){
        return type;
    }

    public void setType(@NotNull DocumentType type){
        Objects.requireNonNull(type);
        this.type = type;
    }

    @NotNull
    public TransactionStatus getTransactionStatus(){
        return transactionStatus;
    }

    public void setTransactionStatus(@NotNull TransactionStatus transactionStatus){
        Objects.requireNonNull(transactionStatus);
        this.transactionStatus = transactionStatus;
    }

    @NotNull
    public Map<String, String> getAttributes(){
        return attributes;
    }


    public void setAttributes(@NotNull Map<String, String> attributes){
        Objects.requireNonNull(attributes);
        this.attributes = attributes;
    }

    public String setAttribute(@NotNull String key, String value){
        Objects.requireNonNull(key);
        return attributes.put(key, value);
    }

    @NotNull
    public Set<MVRTransaction> getHistory(){
        return history;
    }

    public void setHistory(@NotNull Set<MVRTransaction> history){
        Objects.requireNonNull(history);
        this.history = new TreeSet<>();
        this.history.addAll(history);
    }

    public void addHistory(@NotNull MVRTransaction tx){
        Objects.requireNonNull(tx);
        this.history.add(tx);
    }

    public MVRTransaction getLatest(){
        return history.isEmpty() ? null : history.last();
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (!(o instanceof MVRDocument)){
            return false;
        }

        MVRDocument that = (MVRDocument) o;

        if (!guid.equals(that.guid)){
            return false;
        }
        if (!source.equals(that.source)){
            return false;
        }
        if (!state.equals(that.state)){
            return false;
        }
        if (!id.equals(that.id)){
            return false;
        }
        if (type != that.type){
            return false;
        }
        if (transactionStatus != that.transactionStatus){
            return false;
        }
        if (!history.equals(that.history)){
            return false;
        }
        return attributes.equals(that.attributes);

    }

    @Override
    public int hashCode(){
        int result = guid.hashCode();
        result = 31 * result + source.hashCode();
        result = 31 * result + state.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + transactionStatus.hashCode();
        result = 31 * result + history.hashCode();
        result = 31 * result + attributes.hashCode();
        return result;
    }
}
