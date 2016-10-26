package com.realcomp.mvr;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * All the physical characteristics of a Vehicle or Trailer
 */
public class Vehicle{

    private static final Pattern YEAR_PATTERN = Pattern.compile("^[0-9]{4}$"); //YYYY

    private String vin;
    private String bodyTypeCode;
    private String vehicleClassCode;
    private String make;
    private String model;
    private String modelYear;
    private String colorPrimary;
    private String colorSecondary;
    private String vehicleTonage;
    private String bodyVin;
    private int length;
    private int emptyWeight;
    private int grossWeight;
    private FuelType fuelType;

    /**
     * Indicates that the vehicle has permanently mounted equipment that covers over 2/3 of the vehicle's bed.
     */
    private boolean fixedEquipment;
    private TrailerType trailerType;

    private String odometerBrand;
    private String odometerReading;
    private Map<String, String> attributes;

    public Vehicle(){
        attributes = new HashMap<>();
    }

    public String getVin(){
        return vin;
    }

    public void setVin(String vin){
        this.vin = vin;
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
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (!(o instanceof Vehicle)){
            return false;
        }

        Vehicle vehicle = (Vehicle) o;

        if (length != vehicle.length){
            return false;
        }
        if (emptyWeight != vehicle.emptyWeight){
            return false;
        }
        if (grossWeight != vehicle.grossWeight){
            return false;
        }
        if (fixedEquipment != vehicle.fixedEquipment){
            return false;
        }
        if (vin != null ? !vin.equals(vehicle.vin) : vehicle.vin != null){
            return false;
        }
        if (bodyTypeCode != null ? !bodyTypeCode.equals(vehicle.bodyTypeCode) : vehicle.bodyTypeCode != null){
            return false;
        }
        if (vehicleClassCode != null ? !vehicleClassCode.equals(vehicle.vehicleClassCode) : vehicle.vehicleClassCode != null){
            return false;
        }
        if (make != null ? !make.equals(vehicle.make) : vehicle.make != null){
            return false;
        }
        if (model != null ? !model.equals(vehicle.model) : vehicle.model != null){
            return false;
        }
        if (modelYear != null ? !modelYear.equals(vehicle.modelYear) : vehicle.modelYear != null){
            return false;
        }
        if (colorPrimary != null ? !colorPrimary.equals(vehicle.colorPrimary) : vehicle.colorPrimary != null){
            return false;
        }
        if (colorSecondary != null ? !colorSecondary.equals(vehicle.colorSecondary) : vehicle.colorSecondary != null){
            return false;
        }
        if (vehicleTonage != null ? !vehicleTonage.equals(vehicle.vehicleTonage) : vehicle.vehicleTonage != null){
            return false;
        }
        if (bodyVin != null ? !bodyVin.equals(vehicle.bodyVin) : vehicle.bodyVin != null){
            return false;
        }
        if (fuelType != vehicle.fuelType){
            return false;
        }
        if (trailerType != vehicle.trailerType){
            return false;
        }
        if (odometerBrand != null ? !odometerBrand.equals(vehicle.odometerBrand) : vehicle.odometerBrand != null){
            return false;
        }
        if (odometerReading != null ? !odometerReading.equals(vehicle.odometerReading) : vehicle.odometerReading != null){
            return false;
        }
        return attributes != null ? attributes.equals(vehicle.attributes) : vehicle.attributes == null;

    }

    @Override
    public int hashCode(){
        int result = vin != null ? vin.hashCode() : 0;
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
        result = 31 * result + (odometerBrand != null ? odometerBrand.hashCode() : 0);
        result = 31 * result + (odometerReading != null ? odometerReading.hashCode() : 0);
        result = 31 * result + (attributes != null ? attributes.hashCode() : 0);
        return result;
    }
}
