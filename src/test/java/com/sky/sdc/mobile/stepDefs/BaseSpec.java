package com.sky.sdc.mobile.stepDefs;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by msi15 on 01/11/2016.
 */

public abstract class BaseSpec {

    public static AppiumDriver<WebElement> driver;

    public BaseSpec() throws Exception {
        this.driver = Hooks.beforeHookfunction();
    }

}