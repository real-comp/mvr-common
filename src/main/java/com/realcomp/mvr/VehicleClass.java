package com.realcomp.mvr;

/**
 * This follows the NHTSA Definitions of Motor Vehicle Type Classifications
 */
public enum VehicleClass{

    /**
     * A motor vehicle with motive power, except a low-speed vehicle, multipurpose
     * passenger vehicle, motorcycle, or trailer, designed for carrying 10 persons or less.
     */
    CAR,

    /**
     * A motor vehicle with motive power, except a low-speed vehicle or trailer, designed
     * to carry 10 persons or less which is constructed either on a truck chassis or with
     * special features for occasional off-road operation.
     */
    MPV,

    /**
     * A motor vehicle with motive power, except a trailer, designed primarily for the
     * transportation of property or special purpose equipment.
     */
    TRUCK,

    /**
     * A motor vehicle with motive power, except a trailer, designed for carrying more
     * than 10 persons.
     */
    BUS,

    /**
     * A motor vehicle with motive power having a seat or saddle for the use of the rider
     * and designed to travel on not more than three wheels in contact with the ground.
     */
    MOTORCYCLE,

    /**
     * A motorcycle with a motor that produces 5 brake horsepower or less.
     */
    MOPED,

    /**
     * A motor vehicle with or without motive power, designed for carrying persons or
     * property and for being drawn by another motor vehicle.
     */
    TRAILER,

    /**
     * A motor vehicle, that is 4-wheeled, whose speed attainable in 1 mile (1.6 km) is
     * more than 20 miles per hour (32 kilometers per hour) and not more than 25 miles
     * per hour (40 kilometers per hour) on a paved level surface, and whose GVWR is
     * less than 3,000 pounds (1,361 kilograms).
     */
    LOW_SPEED,

    /**
     * A motor vehicle without motive power designed to be drawn by another motor
     * vehicle and attached to the towing vehicle by means of a reach or pole, or by
     * being boomed or otherwise secured to the towing vehicle, for transporting long or
     * irregularly shaped loads such as poles, pipes, or structural members capable
     * generally of sustaining themselves as beams between the supporting connections.
     */
    POLE_TRAILER,

    OTHER,


    UNKNOWN
}
