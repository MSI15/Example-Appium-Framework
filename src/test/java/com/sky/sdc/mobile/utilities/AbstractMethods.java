package com.sky.sdc.mobile.utilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by msi15 on 01/11/2016.
 */

public abstract class AbstractMethods {

 final static Logger log = Logger.getLogger(AbstractMethods.class);

    public AppiumDriver<?> driver;
    Dimension size;

    public AbstractMethods(AppiumDriver<?> driver2) {
        this.driver = driver2;
    }

    /**
     * Method to return to
     * previous screen
     * @return
     */
    public String goBack() {
        driver.navigate().back();
        return driver.getContext();
    }

    /**
     * Method to wait
     * @param waitFor
     */
    protected void waitFor(long waitFor) {
        try {
            Thread.sleep(waitFor);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void launchApp() {
        driver.launchApp();
    }

    public void closeApp() {
        driver.closeApp();
    }

    public void installApp(String appPath){
        driver.installApp(appPath);
    }

    protected boolean isAppInstalled(String bundleID){
        return driver.isAppInstalled(bundleID);
    }

    public void removeApp(String bundleID){
        driver.removeApp(bundleID);
    }

    public void hideKeyboard(){
        driver.hideKeyboard();
    }

    public void runInbackground(int duration) {
        driver.runAppInBackground(duration);
    }

    public void rotateScreen(ScreenOrientation orientation) {
        driver.rotate(orientation);
    }

    /**
     * Method to verify
     * whether an element
     * is present or not
     * @param by
     * @return
     */
    public boolean isElementPresent(By by) {
        try {
            if(driver.findElement(by) != null){
                return true;
            }
        } catch (NoSuchElementException e) {
            log.info("Element with identifier " + by + " does not exist!");
            return false;
        } catch (Exception e){
            log.info("Element with identifier " + by + " does not exist!");
            return false;
        }
        return false;
    }

    /**
     * Method for an
     * element to be
     * visible
     * @param element
     */
    public void waitForElement(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Method to get
     * a list element
     * using a locator
     * @param by
     * @return
     */
    public List<WebElement> getWebElementList(By by) {
        List<WebElement> elementList = (List<WebElement>) driver.findElements(by);
        return elementList;
    }

    public void swipeElement(WebElement element, String direction, int duration) {
        MobileElement e = (MobileElement) element;
        if(direction.equalsIgnoreCase("LEFT")) {
            e.swipe(SwipeElementDirection.LEFT, duration);
        } else if (direction.equalsIgnoreCase("RIGHT")){
            e.swipe(SwipeElementDirection.RIGHT, duration);
        } else if (direction.equalsIgnoreCase("DOWN")){
            e.swipe(SwipeElementDirection.DOWN, duration);
        } else if (direction.equalsIgnoreCase("UP")){
            e.swipe(SwipeElementDirection.UP, duration);
        }

    }

    public void scrollToElement(String elementStr) {
        // calculate the screen size and get the coords for the swipe
        Dimension screenSize = driver.manage().window().getSize();
        int screenWidth = screenSize.getWidth() / 2;
        int screenHight = screenSize.getHeight() - 20;
        // this swipes down the screen until the given element is visible
        if (!driver.findElement(By.xpath(elementStr)).isDisplayed()) {
            do {
                driver.swipe(screenWidth, screenHight, screenWidth, screenHight - 600, 1000);
            } while (!driver.findElement(By.xpath(elementStr)).isDisplayed());
        }
    }

    /**
     * Horizontal swipe Pass : Right2Left to swipe from right to left Pass :
     * Left2Right to swipe from left to right
     */
    public void swipingHorizontal(String direction) throws InterruptedException {
        // Get the size of screen.
        size = driver.manage().window().getSize();

        int startx = (int) (size.width*0.99);
        int endx = (int) (size.width*0.01);
        int starty = size.height / 2;
        // Swipe from Right to Left.
        if (direction.contentEquals("Right2Left")) {
            driver.swipe(startx, starty, endx, starty, 500);
        } else if (direction.contentEquals("Left2Right")) {
            driver.swipe(endx, starty, startx, starty, 500);
        }
    }

    /**
     * Vertical swipe Pass : Bottom2Top to swipe upwards Pass : Top2Bottom to
     * swipe downwards
     */
    public void swipingVertical(String direction) throws InterruptedException {
        // Get the size of screen.
        size = driver.manage().window().getSize();

        int starty = (int) (size.height * 0.20);
        int endy = (int) (size.height * 0.50);
        int startx = size.width/2;
        // Swipe from Bottom to Top.
        if (direction.contentEquals("Top2Bottom")) {
            driver.swipe(startx, starty, startx, endy, 500);
        } else if (direction.contentEquals("Bottom2Top")) {
            driver.swipe(startx, endy, startx, starty, 500);
        }
        //waitFor(2000);
    }

    /**
     * Method used to generate screenshots
     * Throws IO Exception
     * Created by msi15
     */
    public void takeScreenshot( ) throws IOException {
        File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String path = "./reports/target/screenshots/" + source.getName();
        FileUtils.copyFile(source, new File(path));
    }

    /**
     * Method to read from a file
     * @param fileName
     * @return
     * @throws IOException
     */

    public String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }
    /**
     * Method to scroll to an Element
     * using a path relative to the scrollView
     * @param relativepathToElement
     */

    public void scrollToAnElementToBeVisible(String relativepathToElement){
        JavascriptExecutor executor = driver;
        executor.executeScript("target.frontMostApp().mainWindow().scrollViews()[0]."+ relativepathToElement +".scrollToVisible();");
    }

    /**
     * Method to determine device
     * type i.e. whether device is
     * ipad or iphone
     * @return deviceType
     */
    public String deviceType(){
        String deviceType;
        size = driver.manage().window().getSize();
        if((size.getHeight()>800)&&(size.getWidth()>500)){
            return deviceType = "iPad";
        } else {
            return deviceType = "iPhone";
        }
    }

    /**
     * Method to
     * Zoom and pinch
     * @param element
     */
    public void zoomNPinch(WebElement element){
        driver.zoom(element);
        driver.pinch(element);
    }

    /**
     * Method to find an element
     * based on xpath
     */
    public WebElement returnElementByXpath(String xpath){
        WebElement element;
        element = driver.findElement(By.xpath(xpath));
        return element;
    }

}


