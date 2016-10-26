package com.realcomp.mvr;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;

public class MVRDocument{

    private static final Pattern DATE_PATTERN = Pattern.compile("^[0-9]{8}$"); //YYYYMMDD
    private static final Pattern MONTH_PATTERN = Pattern.compile("^[0-9]{2}$"); //MM
    private static final Pattern YEAR_PATTERN = Pattern.compile("^[0-9]{4}$"); //YYYY

    @NotNull
    private String guid;

    @NotNull
    private String source;

    @NotNull
    private String state;

    @NotNull
    private String id;

    private String latestIssueDate;
    private String originalIssueDate;

    @NotNull
    private DocumentType type;

    @NotNull
    private TransactionStatus transactionStatus;

    private String transactionCode;
    private String vin;

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
        latestIssueDate = latest.getTitleIssueDate();
        history = new TreeSet<>();
        attributes = new HashMap<>();
        attributes.putAll(latest.getAttributes());
        transactionCode = latest.getTransactionCode();
        vin = latest.getVin();
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

    public String getLatestIssueDate(){
        return latestIssueDate;
    }

    public void setLatestIssueDate(String latestIssueDate){
        if (latestIssueDate != null && !DATE_PATTERN.matcher(latestIssueDate).matches()){
            throw new IllegalArgumentException(
                    "latestIssueDate [" + latestIssueDate + "] does not match pattern YYYYMMDD");
        }
        this.latestIssueDate = latestIssueDate;
    }

    public String getOriginalIssueDate(){
        return originalIssueDate;
    }

    public void setOriginalIssueDate(String originalIssueDate){
        if (originalIssueDate != null && !DATE_PATTERN.matcher(originalIssueDate).matches()){
            throw new IllegalArgumentException(
                    "issueDate [" + originalIssueDate + "] does not match pattern YYYYMMDD");
        }
        this.originalIssueDate = originalIssueDate;
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

    public String getTransactionCode(){
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode){
        this.transactionCode = transactionCode;
    }

    public String getVin(){
        return vin;
    }

    public void setVin(String vin){
        this.vin = vin;
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
        if (latestIssueDate != null ? !latestIssueDate.equals(that.latestIssueDate) : that.latestIssueDate != null){
            return false;
        }
        if (originalIssueDate != null ? !originalIssueDate.equals(that.originalIssueDate) : that.originalIssueDate != null){
            return false;
        }
        if (type != that.type){
            return false;
        }
        if (transactionStatus != that.transactionStatus){
            return false;
        }
        if (transactionCode != null ? !transactionCode.equals(that.transactionCode) : that.transactionCode != null){
            return false;
        }
        if (vin != null ? !vin.equals(that.vin) : that.vin != null){
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
        result = 31 * result + (latestIssueDate != null ? latestIssueDate.hashCode() : 0);
        result = 31 * result + (originalIssueDate != null ? originalIssueDate.hashCode() : 0);
        result = 31 * result + type.hashCode();
        result = 31 * result + transactionStatus.hashCode();
        result = 31 * result + (transactionCode != null ? transactionCode.hashCode() : 0);
        result = 31 * result + (vin != null ? vin.hashCode() : 0);
        result = 31 * result + history.hashCode();
        result = 31 * result + attributes.hashCode();
        return result;
    }
}
