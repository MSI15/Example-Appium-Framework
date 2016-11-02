package com.sky.sdc.mobile.utilities;

/**
 * Created by msi15 on 01/11/2016.
 */
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class DeviceConfiguration {

    private final static Logger log = Logger.getLogger(DeviceConfiguration.class);

    CommandPrompt cmd = new CommandPrompt();
    Map<String, String> devices = new HashMap<String, String>();

    /**
     * This method start adb server
     */
    public void startADB() throws Exception {
        String output = cmd.runCommand("adb start-server");
        String[] lines = output.split("\n");
        if (lines.length == 1)
            log.info("adb service already started");
        else if (lines[1].equalsIgnoreCase("* daemon started successfully *"))
            log.info("adb service started");
        else if (lines[0].contains("internal or external command")) {
            log.info("adb path not set in system varibale");
            System.exit(0);
        }
    }

    /**
     * This method stop adb server
     */
    public void stopADB() throws Exception {
        cmd.runCommand("adb -s emulator-5554 emu kill");
        cmd.runCommand("adb kill-server");
    }

    /**
     * This method return connected devices
     * @return hashmap of connected devices information
     */
    public Map<String, String> getDevices() throws Exception {

        log.info("Device Conf - getDevices");

        String output = cmd.runCommand("adb devices");
        String[] lines = output.split("\n");

        if (lines.length <= 1) {
            log.info("No Device Connected");
            stopADB();
            System.exit(0); // exit if no connected devices found
        }

        for (int i = 1; i < lines.length; i++) {
            lines[i] = lines[i].replaceAll("\\s+", "");

            if (lines[i].contains("emulator")) {
                lines[i] = lines[i].replaceAll("device", "");
            }

            if (lines[i].contains("device")) {
                lines[i] = lines[i].replaceAll("device", "");
                String deviceID = lines[i];
                String model = cmd.runCommand("adb -s " + deviceID + " shell getprop ro.product.model")
                        .replaceAll("\\s+", "");
                String brand = cmd.runCommand("adb -s " + deviceID + " shell getprop ro.product.brand")
                        .replaceAll("\\s+", "");
                String osVersion = cmd.runCommand("adb -s " + deviceID + " shell getprop ro.build.version.release")
                        .replaceAll("\\s+", "");
                String deviceName = brand + " " + model;

                devices.put("deviceID" + i, deviceID);
                devices.put("deviceName" + i, deviceName);
                devices.put("osVersion" + i, osVersion);

                log.info("Following device is connected");
                log.info(deviceID + " " + deviceName + " " + osVersion + "\n");
            } else if (lines[i].contains("unauthorized")) {
                lines[i] = lines[i].replaceAll("unauthorized", "");
                String deviceID = lines[i];

                log.info("Following device is unauthorized");
                log.info(deviceID + "\n");
            } else if (lines[i].contains("offline")) {
                lines[i] = lines[i].replaceAll("offline", "");
                String deviceID = lines[i];

                log.info("Following device is offline");
                log.info(deviceID + "\n");
            }
        }
        return devices;
    }

    public Map<String, String> waitForEmulatorToGoToDeviceState() throws Exception {

        String output = cmd.runCommand("adb devices");
        String[] lines = output.split("\n");

        for (int i = 1; i < lines.length; i++) {
            lines[i] = lines[i].replaceAll("\\s+", "");

            if (lines[i].contains("emulator")) {

                if (lines[i].contains("offline")) {
                    lines[i] = lines[i].replaceAll("offline", "");

                    waitForEmulatorToGoToDeviceState();
                }

                else if (lines[i].contains("device")) {
                    lines[i] = lines[i].replaceAll("device", "");
                    String deviceID = lines[i];
                    String model = cmd.runCommand("adb -s " + deviceID + " shell getprop ro.product.model")
                            .replaceAll("\\s+", "");
                    String brand = cmd.runCommand("adb -s " + deviceID + " shell getprop ro.product.brand")
                            .replaceAll("\\s+", "");
                    String osVersion = cmd.runCommand("adb -s " + deviceID + " shell getprop ro.build.version.release")
                            .replaceAll("\\s+", "");
                    String deviceName = brand + " " + model;

                    devices.put("deviceID" + i, deviceID);
                    devices.put("deviceName" + i, deviceName);
                    devices.put("osVersion" + i, osVersion);

                    log.info("Following device is connected");
                    log.info(deviceID + " " + deviceName + " " + osVersion + "\n");
                }
            }
        }
        return devices;
    }

    public Map<String, String> getiOSdevice() throws Exception{

        String Output1 = cmd.runCommand("instruments -s devices");
        String[] lines = Output1.split("\n");

        int n = 1;

        for ( int i = 2; i < lines.length; i++ ) {
            if ((lines[i].contains("Apple")) || (lines[i].contains("(Simulator)"))) {
                StringBuffer sBuffer = new StringBuffer(lines[i]);
                sBuffer.delete(0, sBuffer.length());
            } else {
                String Len = lines[i];
                String[] Leng = lines[i].split(" ");
                String UDID = Len.split(" ")[Leng.length-1].replace("[", "").replace("]","").trim();
                String iOSVersion = Len.split(" ")[Leng.length-2].replace("(", "").replace(")","").trim();
                String deviceName = Len.replace(UDID, "").replace(iOSVersion, "").replace("[", "").replace("]","").replace("(", "").replace(")","").trim();

                devices.put("UDID"+n, UDID);
                devices.put("iOSVersion"+n, iOSVersion);
                devices.put("deviceName"+n, deviceName);

                log.info("Device information is : ");
                log.info(deviceName + " " + iOSVersion + " " + UDID + "\n");

                n = n + 1;
            }
        }
        return devices;
    }

}
