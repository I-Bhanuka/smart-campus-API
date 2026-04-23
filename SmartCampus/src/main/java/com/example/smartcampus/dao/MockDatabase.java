/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.smartcampus.dao;

import com.example.smartcampus.model.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Isith Bhanuka
 */
public class MockDatabase {
    public static final List<Room>          ROOM     = new ArrayList<>();
    public static final List<Sensor>        SENSOR  = new ArrayList<>();
    public static final List<SensorReading> READINGS = new ArrayList<>();

    static {
        // Rooms
        Room r1 = new Room("R1", "Computer Lab", 30);
        r1.getSensorIds().add("S1");
        r1.getSensorIds().add("S2");

        Room r2 = new Room("R2", "Lecture Hall A", 100);
        r2.getSensorIds().add("S3");
        r2.getSensorIds().add("S4");

        Room r3 = new Room("R3", "Library", 50);
        r3.getSensorIds().add("S5");

        Room r4 = new Room("R4", "Staff Room", 20);
        // No sensors yet, sensorIds list stays empty

        ROOM.add(r1);
        ROOM.add(r2);
        ROOM.add(r3);
        ROOM.add(r4);

        // Sensors 
        SENSOR.add(new Sensor("S1", "Temperature", "ACTIVE", 22.5, "R1"));
        SENSOR.add(new Sensor("S2", "Humidity", "ACTIVE", 60.0, "R1"));
        SENSOR.add(new Sensor("S3", "Temperature", "ACTIVE", 24.0, "R2"));
        SENSOR.add(new Sensor("S4", "Motion", "OFFLINE", 0.0, "R2"));
        SENSOR.add(new Sensor("S5", "CO2", "MAINTENANCE", 412.0, "R3"));

        // Readings
        READINGS.add(new SensorReading("RD1", "S1", 1700000001L, 22.5));
        READINGS.add(new SensorReading("RD2", "S1", 1700000002L, 23.1));
        READINGS.add(new SensorReading("RD3", "S2", 1700000003L, 60.0));
        READINGS.add(new SensorReading("RD4", "S2", 1700000004L, 62.3));
        READINGS.add(new SensorReading("RD5", "S3", 1700000005L, 24.0));
        READINGS.add(new SensorReading("RD6", "S5", 1700000006L, 412.0));
    }
}
