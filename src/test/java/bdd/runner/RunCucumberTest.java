package bdd.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"bdd/stepdefs", "bdd/hooks"},
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        monochrome = true
)

public class RunCucumberTest extends AbstractTestNGCucumberTests {
}
