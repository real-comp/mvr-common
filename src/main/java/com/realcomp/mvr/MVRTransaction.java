package com.realcomp.mvr;

import com.realcomp.address.Address;
import com.realcomp.address.RawAddress;
import com.realcomp.names.Name;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;

public class MVRTransaction implements Comparable<MVRTransaction>{

    private static final Pattern DATE_PATTERN = Pattern.compile("^[0-9]{8}$"); //YYYYMMDD
    private static final Pattern MONTH_PATTERN = Pattern.compile("^[0-9]{2}$"); //MM
    private static final Pattern YEAR_PATTERN = Pattern.compile("^[0-9]{4}$"); //YYYY

    @NotNull
    private String id;

    /**
     * The US state this transaction originated from.
     */
    @NotNull
    private String state;

    /**
     * The source of this transaction
     */
    @NotNull
    private String source;

    /**
     * This is the date this transaction was created. This could be the data date of the raw data.
     */
    @NotNull
    private String transactionDate;


    @NotNull
    private DocumentType type;

    @NotNull
    private TransactionStatus transactionStatus;

    private String titleIssueDate;
    private BondedTitleType bondedTitleType = BondedTitleType.NONE;


    private String plate;
    private String registrationClassCode;
    private String registrationCounty;
    private String registrationEffectiveDate;
    private String registrationExpMonth;
    private String registrationExpYear;
    private boolean registrationInvalid;

    private Vehicle vehicle;
    private RawAddress rawVehicleLocation;
    private Address vehicleLocation;
    private String salePrice;
    private String saleDate;



    private boolean stolen;
    private boolean exempt;
    private boolean governmentOwned;
    private boolean lemonLaw;

    //Indicates that a salvage title or other title was
    // previously issued for a motor vehicle which has been
    // declared a total loss due to flood damage.
    private boolean floodDamage;

    private boolean inspectionWaived;
    private boolean junk;

    /**
     * Indicates that a salvage title or other title has
     * previously been issued on a vehicle that has, at
     * some time, been a vehicle with a history of a
     * previously issued salvaged title.
     */
    private boolean reconditioned;

    /**
     * Indicates that some type of physical modification was made to the vehicle.
     * Example: 	Vehicle was changed from one type of vehicle to another.
     * Example: 	Vehicle has had a new frame installed and the identification number of the frame acts as the VIN.
     * Example: 	Vehicle with a VEHMODLYR prior to 1956 has had a motor change.
     * Example: 	Vehicle was assembled from three component parts (motor, frame and body).
     */
    private boolean reconstructed;
    private boolean titleRevoked;
    private boolean surrenderedTitle;
    private String surrenderedTitleDate;
    private boolean safetySuspension;
    private boolean plateSeized;
    private boolean stickerSeized;
    private boolean heavyUseTax;

    @NotNull
    private List<Owner> owners;

    /**
     * This field contains the name of the person to which a Registration Renewal Form is sent.  It may
     * or may not be the same name as the Owner.
     *
     * For Example: The owner of a vehicle may be "Troy Waters"
     * who allows his daughter to use the vehicle while
     * attending college.  The RenewalName could then be "Linda Waters" who would receive the
     * Registration Renewal Form.
     */
    private String rawRenewalName;
    private Name renewalName;
    private RawAddress renewalRawAddress;
    private Address renewalAddress;

    @NotNull
    private List<LienHolder> lienHolders;
    private boolean additionalLienHolders;

    private Map<String, String> attributes;

    public MVRTransaction(){
        id = "";
        state = "";
        transactionDate = "00000000";
        source = "";
        type = DocumentType.UNKNOWN;
        transactionStatus = TransactionStatus.UNKNOWN;
        attributes = new HashMap<>();
        owners = new ArrayList<>();
        lienHolders = new ArrayList<>();
    }

    @NotNull
    public String getId(){
        return id;
    }

    public void setId(@NotNull String id){
        Objects.requireNonNull(id);
        this.id = id;
    }

    @NotNull
    public String getState(){
        return state;
    }

    public void setState(@NotNull String state){
        Objects.requireNonNull(state);
        this.state = state;
    }

    @NotNull
    public String getSource(){
        return source;
    }

    public void setSource(@NotNull String source){
        Objects.requireNonNull(source);
        this.source = source;
    }

    @NotNull
    public String getTransactionDate(){
        return transactionDate;
    }

    public void setTransactionDate(@NotNull String transactionDate){
        Objects.requireNonNull(transactionDate);
        if (!DATE_PATTERN.matcher(transactionDate).matches()){
            throw new IllegalArgumentException(
                    "transactionDate [" + transactionDate + "] does not match pattern YYYYMMDD");
        }
        this.transactionDate = transactionDate;
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


    public String getTitleIssueDate(){
        return titleIssueDate;
    }

    public void setTitleIssueDate(String titleIssueDate){
        if (titleIssueDate != null && !DATE_PATTERN.matcher(titleIssueDate).matches()){
            throw new IllegalArgumentException(
                    "titleIssueDate [" + titleIssueDate + "] does not match pattern YYYYMMDD");
        }
        this.titleIssueDate = titleIssueDate;
    }

    public BondedTitleType getBondedTitleType(){
        return bondedTitleType;
    }

    public void setBondedTitleType(BondedTitleType bondedTitleType){
        this.bondedTitleType = bondedTitleType;
    }

    public String getPlate(){
        return plate;
    }

    public void setPlate(String plate){
        this.plate = plate;
    }

    public String getRegistrationClassCode(){
        return registrationClassCode;
    }

    public void setRegistrationClassCode(String registrationClassCode){
        this.registrationClassCode = registrationClassCode;
    }

    public String getRegistrationCounty(){
        return registrationCounty;
    }

    public void setRegistrationCounty(String registrationCounty){
        this.registrationCounty = registrationCounty;
    }

    public String getRegistrationEffectiveDate(){
        return registrationEffectiveDate;
    }

    public void setRegistrationEffectiveDate(String registrationEffectiveDate){
        if (registrationEffectiveDate != null && !DATE_PATTERN.matcher(registrationEffectiveDate).matches()){
            throw new IllegalArgumentException(
                    "registrationEffectiveDate [" + registrationEffectiveDate + "] does not match pattern YYYYMMDD");
        }
        this.registrationEffectiveDate = registrationEffectiveDate;
    }


    public String getRegistrationExpMonth(){
        return registrationExpMonth;
    }

    public void setRegistrationExpMonth(String registrationExpMonth){
        if (registrationExpMonth != null && !MONTH_PATTERN.matcher(registrationExpMonth).matches()){
            throw new IllegalArgumentException(
                    "registrationExpMonth [" + registrationExpMonth + "] does not match pattern MM");
        }
        this.registrationExpMonth = registrationExpMonth;
    }

    public String getRegistrationExpYear(){
        return registrationExpYear;
    }

    public void setRegistrationExpYear(String registrationExpYear){
        if (registrationExpYear != null && !YEAR_PATTERN.matcher(registrationExpYear).matches()){
            throw new IllegalArgumentException(
                    "registrationExpYear [" + registrationExpYear + "] does not match pattern YYYY");
        }
        this.registrationExpYear = registrationExpYear;
    }

    public boolean isRegistrationInvalid(){
        return registrationInvalid;
    }

    public void setRegistrationInvalid(boolean registrationInvalid){
        this.registrationInvalid = registrationInvalid;
    }


    public String getSalePrice(){
        return salePrice;
    }

    public void setSalePrice(String salePrice){
        this.salePrice = salePrice;
    }

    public String getSaleDate(){
        return saleDate;
    }

    public void setSaleDate(String saleDate){
        if (saleDate != null && !DATE_PATTERN.matcher(saleDate).matches()){
            throw new IllegalArgumentException(
                    "saleDate [" + saleDate + "] does not match pattern YYYYMMDD");
        }
        this.saleDate = saleDate;
    }

    public boolean isStolen(){
        return stolen;
    }

    public void setStolen(boolean stolen){
        this.stolen = stolen;
    }

    public boolean isExempt(){
        return exempt;
    }

    public void setExempt(boolean exempt){
        this.exempt = exempt;
    }

    public boolean isGovernmentOwned(){
        return governmentOwned;
    }

    public void setGovernmentOwned(boolean governmentOwned){
        this.governmentOwned = governmentOwned;
    }

    public boolean isLemonLaw(){
        return lemonLaw;
    }

    public void setLemonLaw(boolean lemonLaw){
        this.lemonLaw = lemonLaw;
    }

    public boolean isFloodDamage(){
        return floodDamage;
    }

    public void setFloodDamage(boolean floodDamage){
        this.floodDamage = floodDamage;
    }

    public boolean isInspectionWaived(){
        return inspectionWaived;
    }

    public void setInspectionWaived(boolean inspectionWaived){
        this.inspectionWaived = inspectionWaived;
    }

    public boolean isJunk(){
        return junk;
    }

    public void setJunk(boolean junk){
        this.junk = junk;
    }

    public boolean isReconditioned(){
        return reconditioned;
    }

    public void setReconditioned(boolean reconditioned){
        this.reconditioned = reconditioned;
    }

    public boolean isReconstructed(){
        return reconstructed;
    }

    public void setReconstructed(boolean reconstructed){
        this.reconstructed = reconstructed;
    }

    public boolean isTitleRevoked(){
        return titleRevoked;
    }

    public void setTitleRevoked(boolean titleRevoked){
        this.titleRevoked = titleRevoked;
    }

    public boolean isSurrenderedTitle(){
        return surrenderedTitle;
    }

    public void setSurrenderedTitle(boolean surrenderedTitle){
        this.surrenderedTitle = surrenderedTitle;
    }

    public String getSurrenderedTitleDate(){
        return surrenderedTitleDate;
    }

    public void setSurrenderedTitleDate(String surrenderedTitleDate){
        if (surrenderedTitleDate != null && !DATE_PATTERN.matcher(surrenderedTitleDate).matches()){
            throw new IllegalArgumentException(
                    "surrenderedTitleDate [" + surrenderedTitleDate + "] does not match pattern YYYYMMDD");
        }
        this.surrenderedTitleDate = surrenderedTitleDate;
    }

    public boolean isSafetySuspension(){
        return safetySuspension;
    }

    public void setSafetySuspension(boolean safetySuspension){
        this.safetySuspension = safetySuspension;
    }

    public boolean isPlateSeized(){
        return plateSeized;
    }

    public void setPlateSeized(boolean plateSeized){
        this.plateSeized = plateSeized;
    }

    public boolean isStickerSeized(){
        return stickerSeized;
    }

    public void setStickerSeized(boolean stickerSeized){
        this.stickerSeized = stickerSeized;
    }

    public boolean isHeavyUseTax(){
        return heavyUseTax;
    }

    public void setHeavyUseTax(boolean heavyUseTax){
        this.heavyUseTax = heavyUseTax;
    }


    public RawAddress getRawVehicleLocation(){
        return rawVehicleLocation;
    }

    public void setRawVehicleLocation(RawAddress rawVehicleLocation){
        this.rawVehicleLocation = rawVehicleLocation;
    }

    public Address getVehicleLocation(){
        return vehicleLocation;
    }

    public void setVehicleLocation(Address vehicleLocation){
        this.vehicleLocation = vehicleLocation;
    }

    public String getRawRenewalName(){
        return rawRenewalName;
    }

    public void setRawRenewalName(String rawRenewalName){
        this.rawRenewalName = rawRenewalName;
    }

    public Name getRenewalName(){
        return renewalName;
    }

    public void setRenewalName(Name renewalName){
        this.renewalName = renewalName;
    }

    public RawAddress getRenewalRawAddress(){
        return renewalRawAddress;
    }

    public void setRenewalRawAddress(RawAddress renewalRawAddress){
        this.renewalRawAddress = renewalRawAddress;
    }

    public Address getRenewalAddress(){
        return renewalAddress;
    }

    public void setRenewalAddress(Address renewalAddress){
        this.renewalAddress = renewalAddress;
    }

    @NotNull
    public List<LienHolder> getLienHolders(){
        return lienHolders;
    }

    public void setLienHolders(@NotNull List<LienHolder> lienHolders){
        Objects.requireNonNull(lienHolders);
        this.lienHolders = lienHolders;
    }

    public void addLienHolder(@NotNull LienHolder lienHolder){
        Objects.requireNonNull(lienHolder);
        this.lienHolders.add(lienHolder);
    }

    public boolean isAdditionalLienHolders(){
        return additionalLienHolders;
    }

    public void setAdditionalLienHolders(boolean additionalLienHolders){
        this.additionalLienHolders = additionalLienHolders;
    }

    public Vehicle getVehicle(){
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle){
        this.vehicle = vehicle;
    }

    @NotNull
    public List<Owner> getOwners(){
        return owners;
    }

    public void setOwners(@NotNull List<Owner> owners){
        Objects.requireNonNull(owners);
        this.owners = owners;
    }

    public void addOwner(@NotNull Owner owner){
        Objects.requireNonNull(owner);
        owners.add(owner);
    }

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


    @Override
    public String toString(){
        return "MVRTransaction{" +
                "type=" + type +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NotNull MVRTransaction other){
        return transactionDate.compareTo(other.transactionDate);
    }

}
