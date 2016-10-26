package com.realcomp.mvr;

import com.realcomp.address.Address;
import com.realcomp.address.RawAddress;
import com.realcomp.names.Name;

import java.util.regex.Pattern;

public class LienHolder{


    private static final Pattern DATE_PATTERN = Pattern.compile("^[0-9]{8}$"); //YYYYMMDD


    private String id;
    private String rawName;
    private Name name;
    private RawAddress rawAddress;
    private Address address;
    private String lienDate;
    private String lienCountry;

    public LienHolder(){
    }

    public LienHolder(LienHolder copy){
        id = copy.getId();
        rawName = copy.getRawName();
        rawAddress = copy.getRawAddress() == null ? null : new RawAddress(copy.getRawAddress());
        address = copy.getAddress() == null ? null : new Address(copy.getAddress());
        name = copy.getName() == null ? null : new Name(copy.getName());
        lienDate = copy.getLienDate();
        lienCountry = copy.getLienCountry();
    }


    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
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

    public String getLienDate(){
        return lienDate;
    }

    public void setLienDate(String lienDate){
        if (lienDate != null && !DATE_PATTERN.matcher(lienDate).matches()){
            throw new IllegalArgumentException(
                    "lienDate [" + lienDate + "] does not match pattern YYYYMMDD");
        }
        this.lienDate = lienDate;
    }

    public String getLienCountry(){
        return lienCountry;
    }

    public void setLienCountry(String lienCountry){
        this.lienCountry = lienCountry;
    }


    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (!(o instanceof LienHolder)){
            return false;
        }

        LienHolder that = (LienHolder) o;

        if (id != null ? !id.equals(that.id) : that.id != null){
            return false;
        }
        if (rawName != null ? !rawName.equals(that.rawName) : that.rawName != null){
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null){
            return false;
        }
        if (rawAddress != null ? !rawAddress.equals(that.rawAddress) : that.rawAddress != null){
            return false;
        }
        if (address != null ? !address.equals(that.address) : that.address != null){
            return false;
        }
        if (lienDate != null ? !lienDate.equals(that.lienDate) : that.lienDate != null){
            return false;
        }
        return lienCountry != null ? lienCountry.equals(that.lienCountry) : that.lienCountry == null;

    }

    @Override
    public int hashCode(){
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (rawName != null ? rawName.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (rawAddress != null ? rawAddress.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (lienDate != null ? lienDate.hashCode() : 0);
        result = 31 * result + (lienCountry != null ? lienCountry.hashCode() : 0);
        return result;
    }
}
