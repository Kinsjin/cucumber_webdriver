package com.pageObject;

import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import util.Log;

import java.io.IOException;

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
    public void verifyPageElements(String pagename) throws IOException{
        Log.info("\n" + Strings.repeat("*",20) + pagename + Strings.repeat("*",20));
        long pageloadingtime = getPageLoadTime();
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
            logger.debug("the page navigate start time is:"+pagestarttime);
        }
        if(endobject instanceof Long){
            pageendtime=((Long) endobject);
            logger.debug("the page end time is:"+pageendtime);
        }
        if(endobject instanceof Double){
            double tempvalue=(Double) endobject;
            pageendtime=new Double(tempvalue).longValue();
            logger.debug("the page end time is:"+pageendtime);
        }

        pageloadtime=(pageendtime-pagestarttime)/1000;
        logger.info("Get current page loading time is:"+pageloadtime);

        return pageloadtime;
    }

    /**
     * executeJS:(execute the java script in this page).

     * @author huchan
     * @param driver -- the web driver's instance
     * @param script  --the java script we need to execute
     * @since JDK 1.6
     */
    public Object executeJS(String script) {
        logger.info("Run the javascript from page ,the java script is:"
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
