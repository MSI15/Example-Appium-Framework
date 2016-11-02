package com.sky.sdc.mobile.utilities;

/**
 * Created by msi15 on 01/11/2016.
 */
public class Settings {

    public static final String applicationPath;
    public static final String device;
    public static final String applicationPackage;
    public static final String launchActivity;
    public static final String emulatorVersion;
    public static final String androidEmulator;
    public static final String iOSDevice;
    public static final String defaultiOSDevice;
    public static final String iOSSimulator;
    public static final String iOSSimulatorVersion;
    public static final String testDeviceName;
    public static final String testDeviceVersion;
    public static final String deviceID;;
    public static final String UDID;
    public static final String defaultiOSVersion;

    static {

        //Android properties
        applicationPath = System.getProperty("applicationPath");
        androidEmulator = System.getProperty("androidEmulator");
        device = System.getProperty("device");
        applicationPackage = System.getProperty("applicationPackage");
        launchActivity = System.getProperty("launchActivity");
        emulatorVersion = System.getProperty("emulatorVersion");
        testDeviceName = System.getProperty("testDeviceName");
        testDeviceVersion = System.getProperty("testDeviceVersion");
        deviceID = System.getProperty("deviceId");

        //iOS properties
        iOSDevice = System.getProperty("iOSDevice");
        defaultiOSDevice = System.getProperty("defaultiOSDevice");
        UDID = System.getProperty("UDID");
        defaultiOSVersion = System.getProperty("defaultiOSVersion");
        iOSSimulator = System.getProperty("iOSSimulator");
        iOSSimulatorVersion = System.getProperty("iOSSimulatorVersion");
    }
}
