package com.pageObject;

import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.server.SeleniumDriverResourceHandler;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
    Actions actions;
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
        this.actions = new Actions(driver);
        actions.keyDown(Keys.CONTROL).sendKeys(Keys.F5).perform();
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
    public void scrollToBottom(){
        ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,document.body.scrollHeight)");
        Log.info("Scroll to the bottom of the page..................................................");
    }
    public void scrollTo(int x, int y){
        ((JavascriptExecutor)driver).executeScript("window.scrollTo("+ x +","+ y + ")");
        Log.info("Scroll to the element location...............................................");
    }
    public void scrollBy(int x, int y){
        ((JavascriptExecutor)driver).executeScript("window.scrollBy("+ x +","+ y + ")");
        Log.info("Scroll by " + x + " pixel and " + y + " pixel in Horizontal and Vertical directory");
    }
    public void scrollToView(WebElement ele){
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",ele);
        //the same as arguments[0].scrollInoview(true)
        Log.info("Scroll and make the " + ele + " to view.......................................");
    }
    public String getInnerHtmlCode(WebElement e){
        String contents = (String)executeJSReturn("return arguments[0].innerHTML;", e);
        Log.info("Get the html code for this webelement:"+contents);
        highLight(e);
        return contents;
    }
    public boolean waitProcessBarNotAppeared(WebElement e){
        int waitcount = 0;
        boolean isDisplayed = false;
        while (e.isDisplayed()) {
            waitcount = waitcount + 1;
            isDisplayed = e.isDisplayed();
            Log.info("Waitting for the object displayed status-the load object displayed status is:"
                    + isDisplayed);
            sleepSeconds(3);
            if (waitcount == 5) {
                Log.error("Waitting for the object displayed status-the object cannot show in the page:"
                        + e.getTagName() + ",exit the identify the object ");
                break;
            }

        }
        return isDisplayed;
    }
    public void clickWithJS(WebElement e){
        if(driver instanceof JavascriptExecutor && e.isEnabled()&& e.isDisplayed()){
            ((JavascriptExecutor)driver).executeScript("argument[0].click();",e);
        }
        highLight(e);
    }
    public void highLight(WebElement ele){
        if(driver instanceof JavascriptExecutor) {
            for(int i=0; i<2; i++){;
                JavascriptExecutor je = (JavascriptExecutor) driver;
                je.executeScript("arguments[0].setAttribute('style', arguments[1]);", ele, "color: yellow; border: 2px solid yellow;");//亮
                je.executeScript("arguments[0].setAttribute('style', arguments[1]);", ele, ""); //暗
            }
        }
    }
    public void highLight(WebDriver driver, WebElement ele) {
        for (int i = 0; i < 2; i++) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", ele, "color: yellow; border: 2px solid yellow;");//亮
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", ele, ""); //暗
        }
        Log.info("High light the element...........................................................");
    }
    public boolean waitForObjectDisplay(final String xpathExpression){
        boolean findobject=false;
        WebDriverWait wait=new WebDriverWait(driver, 120);
        try{
            wait.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    Log.info("Enter the waitForObjectDisplay method to wait for the object displayed in the page ");
                    return (driver.findElement(By.xpath(xpathExpression)).isDisplayed());
                }
            });
            findobject=true;
        }
        catch(TimeoutException te){
            Log.info("throw expection ,cannot find the web element:"+te.getMessage());
            Log.info("the time out is 120 ,we cannot find this webelment:"+xpathExpression);
//            Assert.fail("Cannot find this web element in the page:"+xpathExpression);
        }

        return findobject;
    }
    public void modifyElement(){}
    public boolean alertClickOK(int seconds){
        boolean isclicked=false;
        WebDriverWait wait=new WebDriverWait(driver, seconds);
        try{
            Alert alert=wait.until(ExpectedConditions.alertIsPresent());
//            driver.switchTo().alert();
            Log.info("Pop up Alert text is:"+alert.getText());
            alert.accept();
            isclicked=true;
        }catch(NoAlertPresentException e){
            Log.info("the Alert didn't pop up currently:"+e.getMessage());
        }catch(TimeoutException e){
            Log.error("Time out we cannot find this OK button:"+seconds);
        }
        return isclicked;
    }
    public boolean alertClickCancel(int seconds){
        boolean isclicked=false;
        WebDriverWait wait=new WebDriverWait(driver, seconds);
        try{
            Alert alert=wait.until(ExpectedConditions.alertIsPresent());
//            driver.switchTo().alert();
            Log.info("Pop up Alert text is:"+alert.getText());
            alert.dismiss();
            isclicked=true;
        }catch(NoAlertPresentException e){
            Log.info("the alert didn't pop up currently:"+e.getMessage());
        }
        catch(TimeoutException e){
            Log.error("Time out we cannot find this Cancel button:"+seconds);
        }

        return isclicked;
    }
    public void getPageTittle(){}
    public void uploadFile(){}
    public void downloadFile(){}
    public void sleepSeconds(int seconds){
        Log.info("Begin to sleep current step for " + seconds
                + " seconds........");
        //You need to be in a synchronized block in order for Object.wait() to work.

        //Also, I recommend looking at the concurrency packages instead of the old school threading packages. They are safer and way easier to work with.
        //synchronized (driver)
        //    {
        //    driver.wait(seconds * 1000);
        //    }
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            Log.error("Sleep current step met an error:"+e.getMessage());
        }
    }
    public void waitForAjaxPresent(int timeoutInSeconds){
        if (driver instanceof JavascriptExecutor) {
            final long currentbowserstate=(Long)executeJS("return return jQuery.active;");
            Log.info("Current ajax active code  is:"+ currentbowserstate);
            WebDriverWait wdw=new WebDriverWait(driver, timeoutInSeconds);
            ExpectedCondition<Boolean> ec=new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    // TODO Auto-generated method stub
                    long newpagestate=(Long) executeJS("return return jQuery.active;");
                    Log.debug("the new ajax active code is:"+newpagestate);
                    return (newpagestate==0L);
                }
            };

            boolean loaded=wdw.until(ec);
            Log.debug("finally the ajax had been loaded status is:"+loaded);
        }
        else{
            Log.error("Web driver: " + driver + " cannot execute javascript");
        }

    }
    public void dragPageElement(WebElement ele,int x,int y){
        this.actions = new Actions(driver);
        for(int i=0;i<x; i++){
            actions.dragAndDropBy(ele,0,10).build().perform();
        }
        Log.info("Drag and drop element in Horizontal directory by" + x*10 + "pixel");
        for(int i=0;i<y; i++){
            actions.dragAndDropBy(ele,10,0).build().perform();
            Log.info("Drag and drop element in Vertical directory by" + x*10 + "pixel");
        }
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
