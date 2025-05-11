package com.kavak.base;

import com.kavak.utilities.Log;
import com.kavak.utilities.ReusableMethods;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class BasePage extends ReusableMethods {

    /**
     * This method is used to accept cookies.
     * If it is not displayed, it will print a message on the console.
     * If it is displayed, it will click on the 'Kabul Et' button.
     */
    @Step("Accept Cookies")
    public void acceptCookies() {
        waitForPageToLoad();
        Log.pass("Accept cookies.");
        var acceptCookiesButton = Driver.getDriver().findElement(By.xpath("//*[contains(@class, 'RxNS-button-content') and normalize-space(.)='Tümünü kabul et']"));
        if (isDisplayElement(acceptCookiesButton)) {
            clickElement(acceptCookiesButton, "Clicked on the 'Accept Cookies' button.");
        } else {
            Log.pass("The 'Accept Cookies' button is not displayed.");
        }
    }


}
