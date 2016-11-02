package com.sky.sdc.mobile.stepDefs;

import com.sky.sdc.mobile.supportClass.Example;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import io.appium.java_client.AppiumDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.springframework.util.Assert;

/**
 * Created by msi15 on 01/11/2016.
 */
public class ExampleStepDef extends BaseSpec{

    AppiumDriver<WebElement> DRIVER;
    Example ex;

    private final static Logger log = Logger.getLogger(ExampleStepDef.class);

    public ExampleStepDef() throws Exception {
    }

    @Before
    public void setUp(){
        DRIVER = BaseSpec.driver;
        ex = new Example(DRIVER);
    }

    @Given("^I am on the device settings screen$")
    public void i_on_the_device_settings() throws Throwable {
        Assert.isTrue(ex.isSettingsScreenDisplayed(), "Is settings screen displayed?");
    }

    @After
    public void close(){
        DRIVER.quit();
    }
}
