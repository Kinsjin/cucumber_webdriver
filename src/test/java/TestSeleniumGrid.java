import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dingfan on 2016/7/7
 */
public class TestSeleniumGrid {
    WebDriver driver;
    public static String baseUrl = "http://www.sogou.com/";
    public static String nodeUrl = "http://192.168.99.56:6666/wd/hub";
    @Test
    public void testSogouSearch(){
        System.out.println("WebDriver has been setup!");
        driver.get(baseUrl + "/");
        System.out.println("sogo");
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);}
        driver.findElement(By.id("query")).sendKeys("1231");
        System.out.println("123132");
        driver.findElement(By.id("stb")).click();
//        (new WebDriverWait(driver,10)).until(new ExpectedCondition<Boolean>() {
//            @Override
//            public Boolean apply(WebDriver d){
//                return  d.findElement(By.id("s_footer")).getText().contains("光荣之路");
//            }
//        });
//        Assert.assertTrue(driver.getPageSource().contains("光荣之路"));
    }
    @Before
    public void beforeMethod()throws MalformedURLException{
        System.setProperty("webdriver.ie.driver","C:\\IEDriverServer.exe");
        DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
//        capability.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "http://www.baidu.com/");
        capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
        capability.setCapability("ignoreZoomSetting",true);
        capability.setBrowserName("internetExplorer");
        capability.setPlatform(Platform.WINDOWS);
        driver = new InternetExplorerDriver(capability);
//        DesiredCapabilities capability = DesiredCapabilities.chrome();
//        capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
//        capability.setCapability("ignoreZoomSetting",true);
//        capability.setBrowserName("chrome");
//        capability.setPlatform(Platform.WINDOWS);

//        DesiredCapabilities capability = new DesiredCapabilities().firefox();
//        capability.setBrowserName("firefox");
//        capability.setPlatform(Platform.WINDOWS);
//        driver = new RemoteWebDriver(new URL(nodeUrl),capability);
    }

    @After
    public void afterMethod(){
        driver.quit();
    }
}
