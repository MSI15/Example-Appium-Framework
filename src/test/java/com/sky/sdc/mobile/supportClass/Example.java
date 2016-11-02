package com.sky.sdc.mobile.supportClass;

import com.sky.sdc.mobile.utilities.AbstractMethods;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by msi15 on 01/11/2016.
 */
public class Example extends AbstractMethods {

    AppiumDriver<?>driver;

    @iOSFindBy(id = "Settings")
    public WebElement settingsScreenTitle;

    public Example(AppiumDriver<?> driver2) {
        super(driver2);
        this.driver = driver2;

        PageFactory.initElements(new AppiumFieldDecorator(driver),this);
    }

    public boolean isSettingsScreenDisplayed(){
        return settingsScreenTitle.isDisplayed();
    }
}
