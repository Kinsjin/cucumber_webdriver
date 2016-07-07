package com.cucumber;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
/**
 * Created by dingfan on 2016/7/4.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/feature/"},
        format = {"pretty","html:target/cucumber","json:taget/cucumber.json"},
        glue = {"com.cucumber"}
)
public class RunCukesTest {

}
