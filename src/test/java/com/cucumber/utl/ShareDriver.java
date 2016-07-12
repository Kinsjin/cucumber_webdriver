package com.cucumber.utl;


import com.cucumber.config.ConfigManager;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ShareDriver extends EventFiringWebDriver {
    private static WebDriver REAL_DRIVER = null;
    private static String browser,proxyServer,hubUrl;
    private static final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            REAL_DRIVER.close();
        }
    };

    static {
        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
        ConfigManager config = new ConfigManager();
        browser = config.get("browser");
        proxyServer = config.get("proxy");
        hubUrl = config.get("hubUrl");
        //Common setting
        DesiredCapabilities capability = new DesiredCapabilities();
        capability.setCapability("javascriptEnabled",true);
        capability.setCapability("cssSelectorsEnabled",true);
        capability.setCapability("takesScreenshot",true);
        capability.setCapability("ACCEPT_SSL_CERTS",true);
        capability.setBrowserName(browser);
        Log.info("Webdriver common setting..........................Finished");
        //proxy setting
        if(!proxyServer.equals("")){
            capability.setCapability(CapabilityType.PROXY,new Proxy().setHttpProxy(proxyServer));
            capability.setCapability(CapabilityType.PROXY,new Proxy().setNoProxy("localhost"));
            Log.info("Proxy setting.................................Done!");
        }
        //Broser setting
        if(hubUrl==null||hubUrl.isEmpty()){
            Log.info("Local server testing........................................");
            if(browser==null||browser.isEmpty()){
                Log.info("Using IEserverdriver as the default driver...............");
                System.setProperty("webdriver.ie.driver","src\\test\\resources\\webDriver\\IEDriverServer.exe");
                capability = new DesiredCapabilities().internetExplorer();
                capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                capability.setCapability("ignoreProtectedModeSettings",true);
                capability.setCapability("enablePersistentHover", false);
                capability.setCapability("ignoreZoomSetting",true);
                capability.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "http://www.baidu.com/");
                REAL_DRIVER = new InternetExplorerDriver(capability);
                Log.info("Start the IE driver now...................................");
            }else if (browser.trim().equalsIgnoreCase("firefox")){
//                ProfilesIni allProfiles = new ProfilesIni();
//                FirefoxProfile firefoxProfile = allProfiles.getProfile("default");
                FirefoxProfile firefoxProfile = new FirefoxProfile();
                firefoxProfile.setPreference("browser.startup.homepage", "www.baidu.com");
                capability.setCapability(FirefoxDriver.PROFILE,firefoxProfile);
                REAL_DRIVER = new FirefoxDriver(capability);
                Log.debug("Start the firefox driver now............................... ");
            }else if(browser.trim().equalsIgnoreCase("chrome")){
                System.setProperty("webdriver.chrome.driver","src\\test\\resources\\webDriver\\chromedriver.exe");
                REAL_DRIVER = new ChromeDriver(capability);
            }else {
                Log.info("Cannot find the needed webdriver.............................");
            }
        }else {
            Log.info("Remote server testing............................................");
            if ("firefox".equalsIgnoreCase(browser)) {
                capability = DesiredCapabilities.firefox();
                Log.info("Using firefox................................................");
            } else if ("chrome".equalsIgnoreCase(browser)){
                capability = DesiredCapabilities.chrome();
                Log.info("Using chrome.................................................");
            }else {
                capability = DesiredCapabilities.internetExplorer();
                capability = new DesiredCapabilities().internetExplorer();
                capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                capability.setCapability("ignoreProtectedModeSettings",true);
                capability.setCapability("enablePersistentHover", false);
                capability.setCapability("ignoreZoomSetting",true);
                capability.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "http://www.baidu.com/");
            }
            try {
                REAL_DRIVER = new RemoteWebDriver(new URL(hubUrl),capability);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
//            REAL_DRIVER = new RemoteWebDriver(capability);
//            Capabilities actualCapabilities = ((RemoteWebDriver) REAL_DRIVER).getCapabilities();
        }
    }

    public ShareDriver() {
        super(REAL_DRIVER);
        REAL_DRIVER.manage().window().maximize();
        REAL_DRIVER.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @Override
    public void close() {
        if (Thread.currentThread() != CLOSE_THREAD) {
            throw new UnsupportedOperationException("You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
        }
        super.close();
    }

    @Before
    public void deleteAllCookies() {
        REAL_DRIVER.manage().deleteAllCookies();
    }

    @After
    public void embedScreenshot(Scenario scenario) {
        try {
            byte[] screenshot = getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");

        } catch (WebDriverException somePlatformsDontSupportScreenshots) {
            System.err.println(somePlatformsDontSupportScreenshots.getMessage());
        }
    }
}