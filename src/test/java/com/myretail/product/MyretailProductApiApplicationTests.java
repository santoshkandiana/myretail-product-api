package com.myretail.product;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/test/resources/feature"},
		plugin = {"pretty"},
		glue={"src/test/com.myretail.product.stepdef"},
		monochrome = true
)
public class MyretailProductApiApplicationTests {


}
