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

    @NotNull
    private String state;

    @NotNull
    private String source;

    @NotNull
    private String transactionDate;

    @NotNull
    private DocumentType type;

    @NotNull
    private TransactionStatus transactionStatus;
    private String transactionCode;
    private String titleIssueDate; //TTLISSUEDATE
    private BondedTitleType bondedTitleType = BondedTitleType.NONE;

    private String vin; //VIN
    private String plate; //REGPLTNO
    private String registrationClassCode;
    private String registrationCounty; //RESCOMPTCNTYNO (fips)
    private String registrationEffectiveDate; //REGEFFDATE
    private String registrationExpMonth; //REGEXPMO
    private String registrationExpYear; //REGEXPYR
    private boolean registrationInvalid;


    //vehicle/trailer characteristics
    private String bodyTypeCode;
    private String vehicleClassCode;
    private String make;
    private String model;
    private String modelYear;
    private String colorPrimary;
    private String colorSecondary;
    private String vehicleTonage;
    private String bodyVin;
    private int length; //The length of travel trailers that are less than forty body feet in length (excluding the hitch).
    private int emptyWeight;
    private int grossWeight;
    private FuelType fuelType;
    private boolean fixedEquipment; //Indicates that the vehicle has permanently mounted equipment that covers over 2/3 of the vehicle's bed.
    private TrailerType trailerType;

    // Indicates that a "Park Model" trailer must have a
    // permit from the Central Permit Office to move this
    // trailer.  Dimensions are in excess of 8'6" wide or 45' long.
    private boolean permitRequired;
    private String odometerBrand;
    private String odometerReading;

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

    private RawAddress rawVehicleLocation;
    private Address vehicleLocation;

    @NotNull
    private List<Owner> owners;

    /**
     * This field contains the name of the person to which
     * the Texas Registration Renewal Form is sent.  It may
     * or may not be the same name as the OWNRTTLNAME.
     * <p>
     * Example: 	The OWNRTTLNAME of a vehicle may be "Troy Waters"
     * who allows his daughter to use the vehicle while
     * attending college.  The RECPNTNAME could then be
     * "Linda Waters" who would receive the Texas
     * Registration Renewal Form.
     */
    private String rawRenewalName;
    private Name renewalName;
    private RawAddress renewalRawAddress;
    private Address renewalAddress;

    @NotNull
    private List<LienHolder> lienHolders;
    private boolean additionalLienHolders; //ADDLLIENRECRDINDI

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

    public String getTransactionCode(){
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode){
        this.transactionCode = transactionCode;
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

    public String getVin(){
        return vin;
    }

    public void setVin(String vin){
        this.vin = vin;
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

    public String getBodyTypeCode(){
        return bodyTypeCode;
    }

    public void setBodyTypeCode(String bodyTypeCode){
        this.bodyTypeCode = bodyTypeCode;
    }

    public String getVehicleClassCode(){
        return vehicleClassCode;
    }

    public void setVehicleClassCode(String vehicleClassCode){
        this.vehicleClassCode = vehicleClassCode;
    }

    public String getMake(){
        return make;
    }

    public void setMake(String make){
        this.make = make;
    }

    public String getModel(){
        return model;
    }

    public void setModel(String model){
        this.model = model;
    }

    public String getModelYear(){
        return modelYear;
    }

    public void setModelYear(String modelYear){
        if (modelYear != null && !YEAR_PATTERN.matcher(modelYear).matches()){
            throw new IllegalArgumentException(
                    "modelYear [" + modelYear + "] does not match pattern YYYY");
        }
        this.modelYear = modelYear;
    }

    public String getColorPrimary(){
        return colorPrimary;
    }

    public void setColorPrimary(String colorPrimary){
        this.colorPrimary = colorPrimary;
    }

    public String getColorSecondary(){
        return colorSecondary;
    }

    public void setColorSecondary(String colorSecondary){
        this.colorSecondary = colorSecondary;
    }

    public String getVehicleTonage(){
        return vehicleTonage;
    }

    public void setVehicleTonage(String vehicleTonage){
        this.vehicleTonage = vehicleTonage;
    }

    public String getBodyVin(){
        return bodyVin;
    }

    public void setBodyVin(String bodyVin){
        this.bodyVin = bodyVin;
    }

    public int getLength(){
        return length;
    }

    public void setLength(int length){
        this.length = length;
    }

    public int getEmptyWeight(){
        return emptyWeight;
    }

    public void setEmptyWeight(int emptyWeight){
        this.emptyWeight = emptyWeight;
    }

    public int getGrossWeight(){
        return grossWeight;
    }

    public void setGrossWeight(int grossWeight){
        this.grossWeight = grossWeight;
    }

    public FuelType getFuelType(){
        return fuelType;
    }

    public void setFuelType(FuelType fuelType){
        this.fuelType = fuelType;
    }

    public boolean isFixedEquipment(){
        return fixedEquipment;
    }

    public void setFixedEquipment(boolean fixedEquipment){
        this.fixedEquipment = fixedEquipment;
    }

    public TrailerType getTrailerType(){
        return trailerType;
    }

    public void setTrailerType(TrailerType trailerType){
        this.trailerType = trailerType;
    }

    public boolean isPermitRequired(){
        return permitRequired;
    }

    public void setPermitRequired(boolean permitRequired){
        this.permitRequired = permitRequired;
    }

    public String getOdometerBrand(){
        return odometerBrand;
    }

    public void setOdometerBrand(String odometerBrand){
        this.odometerBrand = odometerBrand;
    }

    public String getOdometerReading(){
        return odometerReading;
    }

    public void setOdometerReading(String odometerReading){
        this.odometerReading = odometerReading;
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

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (!(o instanceof MVRTransaction)){
            return false;
        }

        MVRTransaction that = (MVRTransaction) o;

        if (registrationInvalid != that.registrationInvalid){
            return false;
        }
        if (length != that.length){
            return false;
        }
        if (emptyWeight != that.emptyWeight){
            return false;
        }
        if (grossWeight != that.grossWeight){
            return false;
        }
        if (fixedEquipment != that.fixedEquipment){
            return false;
        }
        if (permitRequired != that.permitRequired){
            return false;
        }
        if (stolen != that.stolen){
            return false;
        }
        if (exempt != that.exempt){
            return false;
        }
        if (governmentOwned != that.governmentOwned){
            return false;
        }
        if (lemonLaw != that.lemonLaw){
            return false;
        }
        if (floodDamage != that.floodDamage){
            return false;
        }
        if (inspectionWaived != that.inspectionWaived){
            return false;
        }
        if (junk != that.junk){
            return false;
        }
        if (reconditioned != that.reconditioned){
            return false;
        }
        if (reconstructed != that.reconstructed){
            return false;
        }
        if (titleRevoked != that.titleRevoked){
            return false;
        }
        if (surrenderedTitle != that.surrenderedTitle){
            return false;
        }
        if (safetySuspension != that.safetySuspension){
            return false;
        }
        if (plateSeized != that.plateSeized){
            return false;
        }
        if (stickerSeized != that.stickerSeized){
            return false;
        }
        if (heavyUseTax != that.heavyUseTax){
            return false;
        }
        if (additionalLienHolders != that.additionalLienHolders){
            return false;
        }
        if (!id.equals(that.id)){
            return false;
        }
        if (!state.equals(that.state)){
            return false;
        }
        if (!source.equals(that.source)){
            return false;
        }
        if (!transactionDate.equals(that.transactionDate)){
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
        if (titleIssueDate != null ? !titleIssueDate.equals(that.titleIssueDate) : that.titleIssueDate != null){
            return false;
        }
        if (bondedTitleType != that.bondedTitleType){
            return false;
        }
        if (vin != null ? !vin.equals(that.vin) : that.vin != null){
            return false;
        }
        if (plate != null ? !plate.equals(that.plate) : that.plate != null){
            return false;
        }
        if (registrationClassCode != null ? !registrationClassCode.equals(that.registrationClassCode) : that.registrationClassCode != null){
            return false;
        }
        if (registrationCounty != null ? !registrationCounty.equals(that.registrationCounty) : that.registrationCounty != null){
            return false;
        }
        if (registrationEffectiveDate != null ? !registrationEffectiveDate.equals(that.registrationEffectiveDate) : that.registrationEffectiveDate != null){
            return false;
        }
        if (registrationExpMonth != null ? !registrationExpMonth.equals(that.registrationExpMonth) : that.registrationExpMonth != null){
            return false;
        }
        if (registrationExpYear != null ? !registrationExpYear.equals(that.registrationExpYear) : that.registrationExpYear != null){
            return false;
        }
        if (bodyTypeCode != null ? !bodyTypeCode.equals(that.bodyTypeCode) : that.bodyTypeCode != null){
            return false;
        }
        if (vehicleClassCode != null ? !vehicleClassCode.equals(that.vehicleClassCode) : that.vehicleClassCode != null){
            return false;
        }
        if (make != null ? !make.equals(that.make) : that.make != null){
            return false;
        }
        if (model != null ? !model.equals(that.model) : that.model != null){
            return false;
        }
        if (modelYear != null ? !modelYear.equals(that.modelYear) : that.modelYear != null){
            return false;
        }
        if (colorPrimary != null ? !colorPrimary.equals(that.colorPrimary) : that.colorPrimary != null){
            return false;
        }
        if (colorSecondary != null ? !colorSecondary.equals(that.colorSecondary) : that.colorSecondary != null){
            return false;
        }
        if (vehicleTonage != null ? !vehicleTonage.equals(that.vehicleTonage) : that.vehicleTonage != null){
            return false;
        }
        if (bodyVin != null ? !bodyVin.equals(that.bodyVin) : that.bodyVin != null){
            return false;
        }
        if (fuelType != that.fuelType){
            return false;
        }
        if (trailerType != that.trailerType){
            return false;
        }
        if (odometerBrand != null ? !odometerBrand.equals(that.odometerBrand) : that.odometerBrand != null){
            return false;
        }
        if (odometerReading != null ? !odometerReading.equals(that.odometerReading) : that.odometerReading != null){
            return false;
        }
        if (salePrice != null ? !salePrice.equals(that.salePrice) : that.salePrice != null){
            return false;
        }
        if (saleDate != null ? !saleDate.equals(that.saleDate) : that.saleDate != null){
            return false;
        }
        if (surrenderedTitleDate != null ? !surrenderedTitleDate.equals(that.surrenderedTitleDate) : that.surrenderedTitleDate != null){
            return false;
        }
        if (rawVehicleLocation != null ? !rawVehicleLocation.equals(that.rawVehicleLocation) : that.rawVehicleLocation != null){
            return false;
        }
        if (vehicleLocation != null ? !vehicleLocation.equals(that.vehicleLocation) : that.vehicleLocation != null){
            return false;
        }
        if (!owners.equals(that.owners)){
            return false;
        }
        if (rawRenewalName != null ? !rawRenewalName.equals(that.rawRenewalName) : that.rawRenewalName != null){
            return false;
        }
        if (renewalName != null ? !renewalName.equals(that.renewalName) : that.renewalName != null){
            return false;
        }
        if (renewalRawAddress != null ? !renewalRawAddress.equals(that.renewalRawAddress) : that.renewalRawAddress != null){
            return false;
        }
        if (renewalAddress != null ? !renewalAddress.equals(that.renewalAddress) : that.renewalAddress != null){
            return false;
        }
        if (!lienHolders.equals(that.lienHolders)){
            return false;
        }
        return attributes != null ? attributes.equals(that.attributes) : that.attributes == null;

    }

    @Override
    public int hashCode(){
        int result = id.hashCode();
        result = 31 * result + state.hashCode();
        result = 31 * result + source.hashCode();
        result = 31 * result + transactionDate.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + transactionStatus.hashCode();
        result = 31 * result + (transactionCode != null ? transactionCode.hashCode() : 0);
        result = 31 * result + (titleIssueDate != null ? titleIssueDate.hashCode() : 0);
        result = 31 * result + (bondedTitleType != null ? bondedTitleType.hashCode() : 0);
        result = 31 * result + (vin != null ? vin.hashCode() : 0);
        result = 31 * result + (plate != null ? plate.hashCode() : 0);
        result = 31 * result + (registrationClassCode != null ? registrationClassCode.hashCode() : 0);
        result = 31 * result + (registrationCounty != null ? registrationCounty.hashCode() : 0);
        result = 31 * result + (registrationEffectiveDate != null ? registrationEffectiveDate.hashCode() : 0);
        result = 31 * result + (registrationExpMonth != null ? registrationExpMonth.hashCode() : 0);
        result = 31 * result + (registrationExpYear != null ? registrationExpYear.hashCode() : 0);
        result = 31 * result + (registrationInvalid ? 1 : 0);
        result = 31 * result + (bodyTypeCode != null ? bodyTypeCode.hashCode() : 0);
        result = 31 * result + (vehicleClassCode != null ? vehicleClassCode.hashCode() : 0);
        result = 31 * result + (make != null ? make.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (modelYear != null ? modelYear.hashCode() : 0);
        result = 31 * result + (colorPrimary != null ? colorPrimary.hashCode() : 0);
        result = 31 * result + (colorSecondary != null ? colorSecondary.hashCode() : 0);
        result = 31 * result + (vehicleTonage != null ? vehicleTonage.hashCode() : 0);
        result = 31 * result + (bodyVin != null ? bodyVin.hashCode() : 0);
        result = 31 * result + length;
        result = 31 * result + emptyWeight;
        result = 31 * result + grossWeight;
        result = 31 * result + (fuelType != null ? fuelType.hashCode() : 0);
        result = 31 * result + (fixedEquipment ? 1 : 0);
        result = 31 * result + (trailerType != null ? trailerType.hashCode() : 0);
        result = 31 * result + (permitRequired ? 1 : 0);
        result = 31 * result + (odometerBrand != null ? odometerBrand.hashCode() : 0);
        result = 31 * result + (odometerReading != null ? odometerReading.hashCode() : 0);
        result = 31 * result + (salePrice != null ? salePrice.hashCode() : 0);
        result = 31 * result + (saleDate != null ? saleDate.hashCode() : 0);
        result = 31 * result + (stolen ? 1 : 0);
        result = 31 * result + (exempt ? 1 : 0);
        result = 31 * result + (governmentOwned ? 1 : 0);
        result = 31 * result + (lemonLaw ? 1 : 0);
        result = 31 * result + (floodDamage ? 1 : 0);
        result = 31 * result + (inspectionWaived ? 1 : 0);
        result = 31 * result + (junk ? 1 : 0);
        result = 31 * result + (reconditioned ? 1 : 0);
        result = 31 * result + (reconstructed ? 1 : 0);
        result = 31 * result + (titleRevoked ? 1 : 0);
        result = 31 * result + (surrenderedTitle ? 1 : 0);
        result = 31 * result + (surrenderedTitleDate != null ? surrenderedTitleDate.hashCode() : 0);
        result = 31 * result + (safetySuspension ? 1 : 0);
        result = 31 * result + (plateSeized ? 1 : 0);
        result = 31 * result + (stickerSeized ? 1 : 0);
        result = 31 * result + (heavyUseTax ? 1 : 0);
        result = 31 * result + (rawVehicleLocation != null ? rawVehicleLocation.hashCode() : 0);
        result = 31 * result + (vehicleLocation != null ? vehicleLocation.hashCode() : 0);
        result = 31 * result + owners.hashCode();
        result = 31 * result + (rawRenewalName != null ? rawRenewalName.hashCode() : 0);
        result = 31 * result + (renewalName != null ? renewalName.hashCode() : 0);
        result = 31 * result + (renewalRawAddress != null ? renewalRawAddress.hashCode() : 0);
        result = 31 * result + (renewalAddress != null ? renewalAddress.hashCode() : 0);
        result = 31 * result + lienHolders.hashCode();
        result = 31 * result + (additionalLienHolders ? 1 : 0);
        result = 31 * result + (attributes != null ? attributes.hashCode() : 0);
        return result;
    }
}
