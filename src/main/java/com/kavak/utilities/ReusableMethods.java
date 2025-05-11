package com.kavak.utilities;


import com.kavak.base.BaseTest;
import com.kavak.base.Driver;
import com.kavak.data.Configs;

import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;


@Listeners(AllureTestNg.class)
public class ReusableMethods extends BaseTest {
    public final Configs config = Configs.getConfigs();

    private final Logger logger = LogManager.getLogger(ReusableMethods.class);
    public final SecureRandom random = new SecureRandom();

    /*=======================================================================================*/
    public static final int DEFAULT_WAIT_TIME = 3;
    /*=======================================================================================*/

    public WebElement waitVisibleByLocator(WebElement element) {
        return waitVisibleByLocator(element, DEFAULT_WAIT_TIME);
    }

    @Step("Waits {time} seconds for the locator to become visible")
    public WebElement waitVisibleByLocator(WebElement element, int time) {
        try {
            new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(time)).until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            logger.error("Web element is not visible!");
        }
        return element;
    }

    /*=======================================================================================*/
    public String getCurrentURL() {
        var currentUrl = Driver.getDriver().getCurrentUrl();
        Log.pass("Current URL: " + currentUrl);
        return currentUrl;
    }
    /*=======================================================================================*/

    @Step("Navigate to back")
    public void navigateToBack() {
        Driver.getDriver().navigate().back();
        waitForPageToLoad(3);

    }
    /*=======================================================================================*/
    @Step("Hover on element")
    public void hoverElement(WebElement webElement, int second) {
        Actions action = new Actions(Driver.getDriver());
        action.moveToElement(webElement).pause(Duration.ofSeconds(second)).perform();

    }

    /*=======================================================================================*/

    public String getTextElement(WebElement element) {
        return waitVisibleByLocator(element).getText();
    }

    /*=======================================================================================*/

    public String getTextOfElement(WebElement elem) {

        String text = null;
        try {
            text = elem.getText();
            Log.pass(text);
        } catch (Exception exception) {
            logger.fatal("Error while getting text of element: ", exception);
        }
        return text;
    }

    /*=======================================================================================*/
    public boolean isDisplayElement(WebElement webElement) {
        WebElement element = waitVisibleByLocator(webElement);
        return element.isDisplayed();
    }
    /*=======================================================================================*/

    public String getDomPropertyElement(WebElement webElement, String attribute) {
        WebElement element = waitVisibleByLocator(webElement);
        return element.getDomProperty(attribute);
    }


    /*=======================================================================================*/

    public void clickElement(WebElement webElement, String message) {
        waitVisibleByLocator(webElement);
        scrollToElementBlockCenter(webElement);
        waitClickAbleByOfElement(webElement).click();
        Log.pass(message);
    }

    /*=======================================================================================*/

    @Step("Click With JS")
    public void clickWithJS(WebElement element, String message) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", element);
        Log.pass(message);
    }

    /*=======================================================================================*/
    @Step("Scroll To Element Block Center")
    public void scrollToElementBlockCenter(WebElement element, String whereToScroll) {
        if (Driver.getDriver() == null) {
            Log.warning("Driver is null, skipping scroll for element: " + whereToScroll);
            return;
        }
        try {
            JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            Log.pass("The object where your scroll operation was successful :  " + whereToScroll);
        } catch (Exception e) {
            logger.fatal("Error while scrolling to the element : ", e);
        }

    }

    /*=======================================================================================*/
    @Step("Scroll To Element Block Center")
    public void scrollToElementBlockCenter(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        } catch (Exception e) {
            logger.fatal("Error while scrolling to the element : ", e);
        }

    }

    /*=======================================================================================*/

    @Step("Close The Main Window Outside")
    public static void closeTheMainWindowOutside() {
        Set<String> windowHandles = Driver.getDriver().getWindowHandles();
        String activeWindowHandle = Driver.getDriver().getWindowHandle();
        for (String windowHandle : windowHandles) {
            if (!windowHandle.equals(activeWindowHandle)) {
                Driver.getDriver().switchTo().window(windowHandle);
                Driver.getDriver().close();
            }
        }
        Driver.getDriver().switchTo().window(activeWindowHandle);
    }


    /*=======================================================================================*/

    /**
     * Driver tipinin mobile olup olmadigini kontrol edecek
     *
     * @return eğer mobile ise true, değilse false dönecek,
     */
    public boolean isMobile() {
        return Driver.getDriverType().equals("iPhone X");
    }

    /*=======================================================================================*/


    @Step("Navigate to URL")
    public void navigateToUrl() {
        var env = config.env();
        String baseUrl;
        try {
            baseUrl = config.kavakBaseUrl();
            if (baseUrl == null || baseUrl.isEmpty()) {
                Log.warning("Invalid or missing base URL for environment: " + env);
                return;
            }

            Driver.getDriver().get(baseUrl);
            Driver.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
            Log.pass("Application launched! URL: " + baseUrl);
            waitMs(3000);
            closeTheMainWindowOutside();
        } catch (Exception e) {
            Log.error("Error navigating to URL: " + e.getMessage());
        }
    }

    /*=======================================================================================*/


    public void sendKeysCharacters(WebElement inputElement, String text) {
        for (int i = 0; i < text.length(); i++) {
            String character = String.valueOf(text.charAt(i));
            waitMs(200);
            inputElement.sendKeys(character);
        }
    }
    /*=======================================================================================*/

    public LocalDate selectLocalDate(int departureYear, int departureMonth, int departureDay) {
        return LocalDate.of(departureYear, departureMonth, departureDay);
    }
    /*=======================================================================================*/

}