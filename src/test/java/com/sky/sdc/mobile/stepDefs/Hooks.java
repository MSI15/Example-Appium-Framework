package com.sky.sdc.mobile.stepDefs;

import com.sky.sdc.mobile.utilities.CreateDriver;
import com.sky.sdc.mobile.utilities.Settings;
import cucumber.api.java.Before;
import io.appium.java_client.AppiumDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

/**
 * Created by msi15 on 01/11/2016.
 */
public class Hooks {

    public static CreateDriver driver = new CreateDriver();
    public static AppiumDriver<WebElement> DRIVER;
    private final static Logger log = Logger.getLogger(Hooks.class);

    @Before
    public static AppiumDriver<WebElement> beforeHookfunction() throws Exception{

        if(Settings.device.equalsIgnoreCase("android")) {
            if (CreateDriver.ANDROID_DRIVER == null) {
                log.info("Driver is null. Creating android driver");
                driver.setUp(Settings.device);
            }
            DRIVER = CreateDriver.ANDROID_DRIVER;
        }
        else if(Settings.device.equalsIgnoreCase("ios")) {
            if(CreateDriver.iOS_DRIVER == null) {
                log.info("Driver is null. Creating iOS driver");
                driver.setUp(Settings.device);
            }
            DRIVER = CreateDriver.iOS_DRIVER;
        }
        return DRIVER;
    }
}