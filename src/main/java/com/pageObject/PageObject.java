package com.pageObject;

import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.server.SeleniumDriverResourceHandler;
import util.Log;

import java.io.*;

/**
 * Created by dingfan on 2016/7/13.
 */
public abstract class PageObject {
    public WebDriver driver = null;
    public static Logger logger = Logger.getLogger(PageObject.class);

    public static String status;
    public static String comments;
    public static String comment_executionstart = null;
    public static String comment_login_credential = null;
    public static String comment_paf_buildnumber = null;
    public static String comment_paf_url = null;

    public PageObject(WebDriver driver){
        this.driver = driver;
    }
    public void pageFullyLoaded(){

    }
    public void openPage(){}
    public void pageRefresh(){}
    public void getCurrentURL(){}
    public void switchWindow(){}
    public void scrollToView(){}
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
