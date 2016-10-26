package com.realcomp.mvr;

import com.realcomp.address.Address;
import com.realcomp.address.RawAddress;
import com.realcomp.names.Name;

public class Owner{


    private String rawName;
    private Name name;
    private RawAddress rawAddress;
    private Address address;
    private OwnerEvidenceType ownerEvidenceType;
    private String country;

    public Owner(){
    }

    public Owner(Owner copy){
        rawName = copy.getRawName();
        rawAddress = copy.getRawAddress() == null ? null : new RawAddress(copy.getRawAddress());
        address = copy.getAddress() == null ? null : new Address(copy.getAddress());
        name = copy.getName() == null ? null : new Name(copy.getName());
        ownerEvidenceType = copy.getOwnerEvidenceType();
    }

    public String getRawName(){
        return rawName;
    }

    public void setRawName(String rawName){
        this.rawName = rawName;
    }

    public Name getName(){
        return name;
    }

    public void setName(Name name){
        this.name = name;
    }

    public RawAddress getRawAddress(){
        return rawAddress;
    }

    public void setRawAddress(RawAddress rawAddress){
        this.rawAddress = rawAddress;
    }

    public Address getAddress(){
        return address;
    }

    public void setAddress(Address address){
        this.address = address;
    }

    public String getCountry(){
        return country;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public OwnerEvidenceType getOwnerEvidenceType(){
        return ownerEvidenceType;
    }

    public void setOwnerEvidenceType(OwnerEvidenceType ownerEvidenceType){
        this.ownerEvidenceType = ownerEvidenceType;
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (!(o instanceof Owner)){
            return false;
        }

        Owner owner = (Owner) o;

        if (rawName != null ? !rawName.equals(owner.rawName) : owner.rawName != null){
            return false;
        }
        if (name != null ? !name.equals(owner.name) : owner.name != null){
            return false;
        }
        if (rawAddress != null ? !rawAddress.equals(owner.rawAddress) : owner.rawAddress != null){
            return false;
        }
        if (address != null ? !address.equals(owner.address) : owner.address != null){
            return false;
        }
        if (ownerEvidenceType != owner.ownerEvidenceType){
            return false;
        }
        return country != null ? country.equals(owner.country) : owner.country == null;

    }

    @Override
    public int hashCode(){
        int result = rawName != null ? rawName.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (rawAddress != null ? rawAddress.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (ownerEvidenceType != null ? ownerEvidenceType.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }
}
