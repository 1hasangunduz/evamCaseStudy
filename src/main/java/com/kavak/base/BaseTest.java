package com.kavak.base;

import com.kavak.data.Configs;
import com.kavak.listener.Listener;
import com.kavak.utilities.Log;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;

import java.time.Duration;

@Listeners({Listener.class})
public class BaseTest implements WaitConditions {

    private static final int DEFAULT_TIMEOUT = 40;

    @Step("Setup Test")
    public static void setupTest() {
        var browser = Configs.getConfigs().browser();
        var selectedBrowser = (browser == null || browser.isEmpty()) ? "chrome" : browser;
        Driver.setDriver(selectedBrowser);
        Log.pass("Browser initialized: " + selectedBrowser);
        Log.pass("Window size: " + Driver.getDriver().manage().window().getSize());
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            WebDriver driver = Driver.getDriver();
            if (driver != null) {
                driver.quit();
                Log.pass("Driver quit successfully, and session ended.");
            }
        } catch (Exception e) {
            Log.error("An error occurred during driver cleanup: " + e.getMessage());
        } finally {
            Driver.unloadDriver();
        }
    }

}
