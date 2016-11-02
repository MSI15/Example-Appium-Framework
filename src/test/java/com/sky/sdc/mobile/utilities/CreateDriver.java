package com.sky.sdc.mobile.utilities;

/**
 * Created by msi15 on 01/11/2016.
 */
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CreateDriver {

    private final static Logger log = Logger.getLogger(CreateDriver.class);

    public static AndroidDriver<WebElement> ANDROID_DRIVER;
    public static IOSDriver<WebElement> iOS_DRIVER;
    public static WebDriverWait driverWait;
    static Map<String, String> devices = new HashMap<String, String>();
    public static int deviceCount;
    public String deviceId, deviceName, osVersion, UDID, iOSVersion;
    static DeviceConfiguration deviceConf = new DeviceConfiguration();
    static AppiumManager appiumMan = new AppiumManager();
    static CommandPrompt cmd = new CommandPrompt();
    AndroidEmulator emu = new AndroidEmulator();
    Ports ap = new Ports();
    String testPlatform, port, chromePort, bootstrapPort;

    public void setUp(String device) throws Exception {

        port = ap.getPort();
        chromePort = ap.getPort();
        bootstrapPort = ap.getPort();
        appiumMan.startAppium(port, chromePort, bootstrapPort);

        if (device.equalsIgnoreCase("ios")) {
            startIOSDriver();
        }
        if (device.equalsIgnoreCase("android")) {
            deviceConf.startADB();
            startAndroidDriver();
        }
    }

    public  AndroidDriver<WebElement> startAndroidDriver() throws Exception {
        URL serverAddress = new URL("http://127.0.0.1:"+port+"/wd/hub");
        DesiredCapabilities capabilities = getAndroidDesiredCapabilities();
        ANDROID_DRIVER = new AndroidDriver<WebElement>(serverAddress, capabilities);
        return ANDROID_DRIVER;
    }

    public  DesiredCapabilities getAndroidDesiredCapabilities() throws Exception {
        // create appium driver instance
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium-version", "1.0");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("app", Settings.applicationPath);
        capabilities.setCapability("appPackage", Settings.applicationPackage);
        capabilities.setCapability("fullReset", true); // uninstall app every time

        if ((Settings.device.equalsIgnoreCase("android")) && ( Settings.androidEmulator.equalsIgnoreCase("default"))) {
            emu.startDefaultEmulator();
            String deviceName = emu.getDefaultEmulator();
            log.info("Emulator Name : " + deviceName);
            capabilities.setCapability(CapabilityType.VERSION, "6.0");
            capabilities.setCapability("deviceName", deviceName);
        } else if ((Settings.device.equalsIgnoreCase("android")) && (Settings.androidEmulator.equalsIgnoreCase("no"))) {

            devices = deviceConf.getDevices();
            deviceCount = devices.size() / 3;
            if (deviceCount == 0) {
                log.info("No device connected");
                System.exit(0);
            } else {
                log.info("Total number of devices connected : " + deviceCount);
                for (int deviceNumber = 1; deviceNumber <= deviceCount; deviceNumber++) {
                    this.deviceId = devices.get("deviceID" + deviceNumber);
                    this.deviceName = devices.get("deviceName" + deviceNumber);
                    this.osVersion = devices.get("osVersion" + deviceNumber);
                    log.info("Device ID : " + deviceId);
                    log.info("Device Name : " + deviceName);
                    log.info("OS Version : " + osVersion);

                    if (osVersion.startsWith("6")) {
                        capabilities.setCapability("autoAcceptAlerts", true);
                    }

                }
            }
            capabilities.setCapability(CapabilityType.VERSION, osVersion);
            capabilities.setCapability("deviceName", deviceName);
            capabilities.setCapability("udid", deviceId);
        } else if ((Settings.device.equals("android")) && (Settings.androidEmulator.isEmpty() == false)) {
            emu.startEmulator(Settings.androidEmulator);
            Thread.sleep(2000);
            deviceConf.waitForEmulatorToGoToDeviceState();
            capabilities.setCapability(CapabilityType.VERSION, Settings.emulatorVersion);
            capabilities.setCapability("deviceName", Settings.androidEmulator);
        }

        return capabilities;
    }

    public AndroidDriver<WebElement> getAndroidWebDriver() {
        return ANDROID_DRIVER;
    }

    public IOSDriver<WebElement> startIOSDriver() throws Exception {
        URL serverAddress = new URL("http://127.0.0.1:"+port+"/wd/hub");
        DesiredCapabilities capabilities = getIosDesiredCapabilities();
        iOS_DRIVER = new IOSDriver<WebElement>(serverAddress, capabilities);
        return iOS_DRIVER;
    }

    public DesiredCapabilities getIosDesiredCapabilities() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "ios");
        capabilities.setCapability("command-timeout", 300);
        capabilities.setCapability("app", Settings.applicationPath);
        capabilities.setCapability("bundleId", Settings.applicationPackage);
        capabilities.setCapability("autoDismissAlerts", true);

        if ( !Settings.iOSSimulator.equalsIgnoreCase("no") ) {
            log.info("Launching simulator");
            log.info(Settings.iOSSimulator);
            log.info(Settings.iOSSimulatorVersion);

            capabilities.setCapability(CapabilityType.VERSION, Settings.iOSSimulatorVersion);
            capabilities.setCapability("deviceName", Settings.iOSSimulator);
        }
        else {
            devices = deviceConf.getiOSdevice();  //@return hashmap of connected devices information
            deviceCount = devices.size()/3;

            if( deviceCount == 0 ) {
                log.info("No device is connected");
                System.exit(0);
            }
            else {
                log.info("Total number of devices connected : " + deviceCount);
                for ( int device_number = 1; device_number<=deviceCount; device_number++ ) {
                    String deviceadd = devices.get("deviceName" + device_number);
                    String UDIDadd = devices.get("UDID" + device_number);
                    String iOSVersionadd = devices.get("iOSVersion" + device_number);

                    if (Settings.iOSDevice.equalsIgnoreCase("no")){
                        log.info("Launching default device :" + (Settings.defaultiOSDevice));
                        this.deviceName = Settings.defaultiOSDevice;
                        this.UDID = Settings.UDID;
                        this.iOSVersion = Settings.defaultiOSVersion;
                    } else if (deviceadd.equalsIgnoreCase(Settings.iOSDevice)){
                        log.info("Launching set iOS device :" + deviceadd);
                        this.deviceName = deviceadd;
                        this.UDID = UDIDadd;
                        this.iOSVersion = iOSVersionadd;
                        break;
                    } else if (Settings.iOSDevice.isEmpty()){
                        log.info("Launching a device from the list:" );
                        this.deviceName = deviceadd;
                        this.UDID = UDIDadd;
                        this.iOSVersion = iOSVersionadd;
                        break;
                    }

                    if(this.deviceName.isEmpty() || this.UDID.isEmpty() || this.iOSVersion.isEmpty()){
                        log.info("No device has been found with the stated device name");
                    }
                }
                capabilities.setCapability(CapabilityType.VERSION, iOSVersion);
                capabilities.setCapability("deviceName", deviceName);
                capabilities.setCapability("udid", UDID);
                capabilities.setCapability("noReset", true);
                log.info(iOSVersion);
                log.info(deviceName);
                log.info(UDID);
            }
        }
        return capabilities;
    }

    public IOSDriver<WebElement> getIOSWebDriver() {
        return iOS_DRIVER;
    }

    public void tearDown() throws Exception {
        if (ANDROID_DRIVER != null) {
            ANDROID_DRIVER.quit();
            deviceConf.stopADB();
            cmd.stopServer();
        }
        if (iOS_DRIVER != null) {
            iOS_DRIVER.quit();
            cmd.stopServer();
        }
    }

}
