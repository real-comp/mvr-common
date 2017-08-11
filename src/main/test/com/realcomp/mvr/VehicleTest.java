package com.realcomp.mvr;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VehicleTest{


    @Test
    public void testCopyConstructor() throws Exception{

        Vehicle vehicle = new Vehicle();
        vehicle.setVin("1234");
        vehicle.setBodyTypeCode("BT");
        vehicle.setAttribute("attr", "val");
        vehicle.setBodyVin("484884");
        vehicle.setEmptyWeight(1);
        vehicle.setFixedEquipment(false);
        vehicle.setFuelType(FuelType.DIESEL);
        vehicle.setGrossWeight(2);
        vehicle.setLength(49);
        vehicle.setMake("make");
        vehicle.setModel("model");
        vehicle.setModelYear("1999");
        vehicle.setOdometerBrand("odo");
        vehicle.setOdometerReading("94949");
        vehicle.setPrimaryColor(Color.RED);
        vehicle.setSecondaryColor(Color.BLACK);
        vehicle.setTonage(1.1f);
        vehicle.setTrailerType(TrailerType.FULL);


        Vehicle copy = new Vehicle(vehicle);
        assertTrue(copy.equals(vehicle));

        copy.setModelYear("1970");
        assertFalse(copy.equals(vehicle));

    }

}