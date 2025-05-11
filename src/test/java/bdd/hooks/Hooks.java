package bdd.hooks;

import com.kavak.base.Driver;
import com.kavak.data.Configs;
import com.kavak.pages.homepage.KavakHomePage;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.testng.annotations.BeforeMethod;

public class Hooks {

    @Before
    public void setUp() {
        var browser = Configs.getConfigs().browser();
        Driver.setDriver(browser != null ? browser : "chrome");
    }

    @After
    public void tearDown() {
        if (Driver.getDriver() != null) {
            Driver.getDriver().quit();
        }
        Driver.unloadDriver();
    }
}
