package com.pageObject;

import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.server.SeleniumDriverResourceHandler;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.Log;

import java.awt.*;
import java.io.*;
import java.util.Set;

/**
 * Created by dingfan on 2016/7/13.
 */
public abstract class PageObject {
    public WebDriver driver = null;
    public PageObject(WebDriver driver){
        this.driver = driver;
    }
    /**
     * wait for the web page to full loading correctly ,it will wait for 3 minutes to load the page ,
     * if the page not loading in 3 minutes ;it will throw error;
     */
    public boolean pageFullyLoaded(){
        final String currentPageState = (String)executeJS("document.readyState;");
        Log.info("Current browser state is:"+currentPageState);
        WebDriverWait wait = new WebDriverWait(driver, 180);
        ExpectedCondition<Boolean> ec=new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                // TODO Auto-generated method stub
                String newpagestate=(String) executeJS("return document.readyState;");
                Log.debug("the new page state is:"+newpagestate);
                return (newpagestate.equals("complete"));
            }
        };
        boolean loaded = wait.until(ec);
        Log.debug("finally the load is loading with completed status is:"+loaded);
        return true;
    }
    public void openPage(String URL){
        Log.info("Open the page:" + URL);
        driver.get(URL);
    }
    public void pageNavigateRefresh(){
        Log.info("Refresh the page........................................................");
        driver.navigate().refresh();
    }
    public void pageNavigateToRefresh(){
        Log.info("Refresh the page........................................................");
        driver.navigate().to(driver.getCurrentUrl());
    }
    public void pageGetRefresh(){
        Log.info("Refresh the page........................................................");
        driver.get(driver.getCurrentUrl());
    }
    public void sendKeyToRefresh(WebElement E){
        Log.info("Press F5................................................................");
        E.sendKeys(Keys.F5);
    }
    public void sendKeyAssicToRefresh(WebElement E){
        Log.info("Press Assic F5................................................................");
        E.sendKeys("\uE035");
    }
    public void forceToRefresh(){
        Log.info("Force to refresh..............................................................");
        Actions action = new Actions(driver);
        action.keyDown(Keys.CONTROL).sendKeys(Keys.F5).perform();
    }
    public void executJStoRefresh(){
        Log.info("Refresh page....................................................................");
        ((JavascriptExecutor)driver).executeScript("history.go(0)");
    }
    public void pageGoBack(){
        Log.info("Go back page....................................................................");
        ((JavascriptExecutor)driver).executeScript("history.go(-1)");
    }
    public void pageGoForward(){
        Log.info("Go page forward.................................................................");
        ((JavascriptExecutor)driver).executeScript("history.go(1)");
    }
    public void getCurrentURL(){
        Log.info("Get the current UR: " + driver.getCurrentUrl());
        driver.getCurrentUrl();
    }
    public void switchWindow(String pageTittle) throws AWTException {
        String winHandle = driver.getWindowHandle();
        Robot robote = new Robot();
        Set<String> allWindowsHandles = driver.getWindowHandles();
        if(!allWindowsHandles.isEmpty()){
            for(String windowHandle:allWindowsHandles){
                try {
                    if (driver.switchTo().window(windowHandle).getTitle().equalsIgnoreCase(pageTittle))
                        winHandle = driver.getWindowHandle();
                }catch(NoSuchWindowException e) {
                    Log.error("Do not find the" + pageTittle + "window..........................");
                    e.printStackTrace();
                }
            }
        }
        driver.switchTo().window(winHandle);
        Log.info("Current page title is: " + driver.getTitle());
    }
    public void scrollToView(){

    }
    public void innerHtmlCode(){}
    public void performanceLoadingTime(){}
    public void waitProcessBarNotAppeared(){}
    public void clickWithJS(){}
    public void highLight(){}
    public void waitForObjectDisplay(){}
    public void modifyElement(){}
    public void alertClickOK(){}
    public void alertClickCancel(){}
    public void getPageTittle(){}
    public void uploadFile(){}
    public void downloadFile(){}
    public void sleepSecond(){}
    public void waitForAjaxPresent(){}
    public void dragPageElement(WebElement ele){
    }
    public long getPageLoadTime() {
        long pageloadtime = 0;
        long pagestarttime = 0;
        long pageendtime = 0;
        Object startobject = executeJSReturn("return window.performance.timing.navigationStart;");
        Object endobject=executeJSReturn("return window.performance.timing.loadEventEnd;");
        if(startobject instanceof Long){
            pagestarttime=(Long) startobject;
            Log.debug("the page navigate start time is:"+pagestarttime);
        }
        if(startobject instanceof Double){
            Double tempvalue=(Double) startobject;
            pagestarttime=new Double(tempvalue).longValue();
            Log.debug("the page navigate start time is:"+pagestarttime);
        }
        if(endobject instanceof Long){
            pageendtime=((Long) endobject);
            Log.debug("the page end time is:"+pageendtime);
        }
        if(endobject instanceof Double){
            double tempvalue=(Double) endobject;
            pageendtime=new Double(tempvalue).longValue();
            Log.debug("the page end time is:"+pageendtime);
        }

        pageloadtime=(pageendtime-pagestarttime)/1000;
        Log.info("Get current page loading time is:"+pageloadtime);
        return pageloadtime;
    }

    /**
     * executeJS:(execute the java script in this page).

     * @author huchan
//     * @param driver -- the web driver's instance
     * @param script  --the java script we need to execute
     * @since JDK 1.6
     */
    public Object executeJS(String script) {
        Log.info("Run the javascript from page ,the java script is:"
                + script);
        JavascriptExecutor je = (JavascriptExecutor) driver;
        return je.executeScript(script);

    }
    public void executeJS(String script,WebElement e) {
        Log.info("Run the javascript from page ,the java script is:"
                + script);
        //highLight(e);
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript(script,e);

    }
    public Object executeJSReturn(String script,WebElement e) {
        Log.info("Run the javascript from page ,the java script is:"
                + script);
        //highLight(e);
        JavascriptExecutor je = (JavascriptExecutor) driver;
        Object object=je.executeScript(script,e);
        return object;

    }
    public Object executeJSReturn(String script) {
        Log.info("Run the javascript from page ,the java script is:"
                + script);
        JavascriptExecutor je = (JavascriptExecutor) driver;
        Object object=je.executeScript(script);
        return object;
    }
}
