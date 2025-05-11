package com.kavak.pages.homepage;

import com.kavak.base.BasePage;
import com.kavak.base.Driver;
import com.kavak.data.GetData;
import com.kavak.utilities.Log;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class KavakHomePage extends BasePage {

    @FindBy(css = ".c_neb-item-button")
    WebElement clearButtonDepartureInputBox;

    @FindBy(css = "[class*='airport-code']")
    List<WebElement> textRowAirportCode;

    @FindBy(css = "[class='vPgG-name-container']")
    List<WebElement> rowAirportName;

    @FindBy(css = "[placeholder='Nereden?']")
    WebElement inputDepartureDestination;

    @FindBy(css = "[placeholder='Nereye?']")
    WebElement inputReturnDestination;

    @FindBy(css = "[aria-label*='Gidiş tarihi']")
    WebElement openCalender;

    @FindBy(css = ".c9HET")
    WebElement submitButton;

    @FindBy(css = "div.Qk4D-filter-head [aria-label='KAYAK üzerinden rezerve edin']")
    WebElement bookViaKavakArea;

    @FindBy(id = "valueSetFilter-vertical-whisky-whisky-label")
    WebElement activeBookViaKavakAreaButton;

    @FindBy(css = "[class='Fxw9-result-item-container']")
    List<WebElement> resultItems;

    @FindBy(css = "[class='ULvh-button show-more-button']")
    WebElement showMoreButton;

    @FindBy(css = "[aria-label='En ucuz']")
    WebElement buttonBestPrices;

    @FindBy(xpath = "(//*[@aria-label='Sadece 1 aktarma için sonuçları göster'])[1]")
    WebElement onlyOneTransfer;


    /*===============================================================================================================================*/

    public KavakHomePage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    /*===============================================================================================================================*/
    // Constants
    PassengerSelectorComponent selector = new PassengerSelectorComponent();
    public static final String Flight_URL_PARAM = "giris";
    public static final String REGISTER_URL_PARAM = "uyelik";
    public static final String Flight_PAGE_NOT_OPENED = "The Flight page is not opened.";
    public static final String GENDER_SELECTED = "Gender selected: ";

    /* ==================================================================================================================== */

    @Step("Select departure route: {departureRoute}")
    public KavakHomePage selectDepartureRoute(String routeName, String departureRoute) {
        try {

            clickElement(clearButtonDepartureInputBox, routeName + " Route Box base route closed.");
            Log.pass("Mevcut kalkış konumu kapatıldı: " + routeName);

            clickElement(inputDepartureDestination, routeName + " Route Box");

            waitMs(200);
            sendKeysCharacters(inputDepartureDestination, departureRoute);
            waitMs(200);
            Log.pass("Kalkış rotası girildi: " + departureRoute);

            selectMatchingAirportFromList(textRowAirportCode, departureRoute);

        } catch (Exception e) {
            Log.fail("Kalkış rotası seçimi sırasında hata oluştu: " + e.getMessage());
            throw e;
        }

        return this;
    }

    @Step("Select departure route: {returnRoute}")
    public KavakHomePage selectReturnRoute(String routeName, String returnRoute) {
        try {

            clickElement(inputReturnDestination, routeName + " Route Box");

            waitMs(200);

            sendKeysCharacters(inputReturnDestination, returnRoute);
            waitMs(200);
            Log.pass("Dönüş rotası girildi: " + returnRoute);

            selectMatchingAirportFromList(textRowAirportCode, returnRoute);

        } catch (Exception e) {
            Log.fail("Dönüş rotası seçimi sırasında hata oluştu: " + e.getMessage());
            throw e;
        }

        return this;
    }

    @Step("Select matching airport for keyword: {routeKeyword}")
    public void selectMatchingAirportFromList(List<WebElement> airportList, String routeKeyword) {
        if (airportList == null || airportList.isEmpty()) {
            Log.fail("Havalimanı listesi boş geldi.");
            throw new NoSuchElementException("Havalimanı listesi boş.");
        }
        waitMs(1000);
        for (int i = 0; i < airportList.size(); i++) {
            var text = getTextElement(airportList.get(i));
            if (text.contains(routeKeyword)) {
                clickWithJS(rowAirportName.get(i), "Eşleşen havalimanı seçildi: " + text);
                return;
            }
        }
        Log.fail("Girilen rota için eşleşen havalimanı bulunamadı: " + routeKeyword);
        throw new AssertionError("Uygun havalimanı bulunamadı: " + routeKeyword);
    }

    @Step("Select departure date: {departureDate}")
    public KavakHomePage selectDepartureDate(LocalDate departureDate) {
        openCalender();
        String departureCss = GetData.getKayakDateSelector(departureDate);
        WebElement departureDay = Driver.getDriver().findElement(By.cssSelector(departureCss));
        waitClickAbleByOfElement(departureDay, 3);
        departureDay.click();
        return this;
    }

    @Step("Select return date: {returnDate}")
    public KavakHomePage selectReturnDate(LocalDate returnDate) {
        String returnCss = GetData.getKayakDateSelector(returnDate);
        WebElement returnDay = Driver.getDriver().findElement(By.cssSelector(returnCss));
        waitClickAbleByOfElement(returnDay, 3);
        returnDay.click();
        return this;
    }

    @Step("Open calendar widget")
    private void openCalender() {
        waitClickAbleByOfElement(openCalender, 3);
        clickElement(openCalender, "Gidiş tarihi box");

    }

    @Step("Set passenger type '{passengerType}' to count: {targetCount}")
    public KavakHomePage addPassenger(GetData.PassengerType passengerType, int targetCount) {
        selector.setPassengerCount(passengerType.getLabel(), targetCount);
        return this;
    }

    @Step("Click Submit button.")
    public KavakHomePage clickSubmitButton() {
        clickElement(submitButton, "Clicked Submit button.");
        return this;
    }

    @Step("Click Quick Filter: {filter}")
    public KavakHomePage applyQuickFilter(GetData.QuickFilter filter) {
        String label = filter.getLabel();
        WebElement quickFilterBtn = Driver.getDriver().findElement(By.cssSelector("[aria-label='" + label + "']"));
        scrollToElementBlockCenter(quickFilterBtn, "Scroll to Quick Filter: " + label);
        waitMs(300);
        clickElement(quickFilterBtn, "Clicked filter: " + label);
        return this;
    }

    @Step("Click Transfer Filter for {count} stop(s)")
    public KavakHomePage applyTransferFilter(GetData.TransferFilter filter) {

        String css = "[aria-label='Sadece " + filter.getValue() + " aktarma için sonuçları göster'][class*='hYzH-only-filter']";
        WebElement transferFilterBtn = Driver.getDriver().findElement(By.cssSelector(css));
        scrollToElementBlockCenter(transferFilterBtn,"Scroll to Transfer Filter: " + filter.getValue());
        waitMs(200);
        hoverElement(transferFilterBtn,2);
        clickElement(transferFilterBtn, "Clicked transfer filter: " + filter.getValue() + " aktarma");
        waitMs(500);
        return this;
    }

    @Step("Slide departure filter start time to 12:00.")
    public KavakHomePage slideDepartureStartTimeToNoon() {
        WebDriver driver = Driver.getDriver();
        WebElement sliderHandle = driver.findElement(By.cssSelector("span[aria-label='Gidiş 1']"));
        Actions actions = new Actions(driver);

        int moveBy = 0;

        while (true) {
            String timeText = sliderHandle.getAttribute("aria-valuetext");
            Log.pass("Current slider time: " + timeText);

            Pattern pattern = Pattern.compile("(\\d{2})\\.(\\d{2})");
            Matcher matcher = pattern.matcher(timeText);

            if (matcher.find()) {
                int hour = Integer.parseInt(matcher.group(1));
                int minute = Integer.parseInt(matcher.group(2));

                if (hour > 12 || (hour == 12 && minute >= 0)) {
                    Log.pass("Departure filter correctly set to 12:00 or later ➜ " + timeText);
                    break;
                }
            }

            actions.clickAndHold(sliderHandle)
                    .moveByOffset(1, 0)
                    .release()
                    .perform();

            waitMs(60);
            moveBy++;

            if (moveBy > 300) {
                throw new RuntimeException("Slider couldn't be moved to 12:00. Last seen: " + timeText);
            }
        }
        return this;
    }



    @Step("Check redirection URL for from: {from}, to: {to}, dates: {departure} - {returning}")
    public KavakHomePage checkRedirections(String from, String to, LocalDate departure, LocalDate returning) {
        String actualUrl = Driver.getDriver().getCurrentUrl();
        String expectedUrlPart = String.format(
                "/flights/%s-%s/%s/%s",
                from,
                to,
                formatDate(departure),
                formatDate(returning)
        );

        Assert.assertTrue(actualUrl.contains(expectedUrlPart),
                "Beklenen URL bulunamadı.\nExpected Part: " + expectedUrlPart + "\nActual: " + actualUrl);

        return this;
    }

    private String formatDate(LocalDate date) {
        return date.toString();
    }

    @Step("open Book Via Kavak")
    public KavakHomePage openBookViaKavak() {
        scrollToElementBlockCenter(bookViaKavakArea);
        waitMs(200);
        clickElement(bookViaKavakArea, "KAYAK üzerinden rezerve edin");
        waitMs(200);
        clickElement(activeBookViaKavakAreaButton, "Active Book Kayak.");
        return this;
    }

    @Step("Click 'Show more' until at least 50 flights are listed")
    public KavakHomePage showMoreFlightsItems() {
        int maxAttempts = 10;
        int attempt = 0;

        while (resultItems.size() < 50 && attempt < maxAttempts) {
            try {
                scrollToElementBlockCenter(showMoreButton, "Scrolling to 'Show more' button");
                waitClickAbleByOfElement(showMoreButton, 5);
                clickElement(showMoreButton, "Clicked 'Show more' button");
                waitMs(1500);
            } catch (Exception e) {
                Log.fail("Show more button could not be clicked or is no longer visible.");
                break;
            }

            attempt++;
            Log.pass("Current flight count: " + resultItems.size());
        }

        if (resultItems.size() < 50) {
            Log.fail("50 uçuşa ulaşılamadı, sadece " + resultItems.size() + " uçuş listelendi.");
        }

        return this;
    }

    @Step("Validate prices are sorted in ascending order.")
    public KavakHomePage validatePricesAreSortedAscending() {
        List<WebElement> priceElements = Driver.getDriver().findElements(By.cssSelector(".e2GB-price-text"));

        List<Double> prices = priceElements.stream()
                .map(WebElement::getText)
                .map(text -> text.replaceAll("[^\\d,]", "").replace(",", "."))
                .map(Double::parseDouble)
                .collect(Collectors.toList());

        Log.pass("Prices found: " + prices);

        boolean sorted = IntStream.range(0, prices.size() - 1)
                .allMatch(i -> prices.get(i) <= prices.get(i + 1));

        Assert.assertTrue(sorted, "Prices are not sorted in ascending order ➜ " + prices);
        Log.pass("Prices are correctly sorted in ascending order.");

        return this;
    }

    //  FOR CUCUMBER
    @Step("Navigate to Kayak homepage")
    public KavakHomePage navigateToKayak() {
        Driver.getDriver().get("https://www.kayak.com.tr/");
        Log.pass("Navigated to Kayak homepage.");
        return this;
    }

    @Step("Validate all flights have only one transfer")
    public KavakHomePage validateOnlyOneTransferFilterApplied() {
        List<WebElement> transferBadges = Driver.getDriver().findElements(By.cssSelector(".VY2U-transfer-tag")); // örnek class
        for (WebElement badge : transferBadges) {
            String text = badge.getText().toLowerCase(Locale.ROOT);
            if (!text.contains("1 aktarma")) {
                Log.fail("More than one transfer found: " + text);
                throw new AssertionError("Flight found with more than one transfer.");
            }
            Log.pass("Valid transfer found: " + text);
        }
        return this;
    }

    @Step("Validate all departure flights are after 12:00")
    public KavakHomePage validateDepartureFilterAfterNoon() {
        List<WebElement> departureTimes = Driver.getDriver().findElements(By.cssSelector(".k8uH-time-start")); // örnek class

        for (WebElement time : departureTimes) {
            String timeText = time.getText().trim(); // e.g. "12:45"
            String[] parts = timeText.split(":");
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);

            if (hour < 12) {
                Log.fail("Departure filter starts before 12:00 ➜ Found: " + timeText);
                throw new AssertionError("Departure time is before 12:00 ➜ " + timeText);
            }
            Log.pass("Valid departure time: " + timeText);
        }
        return this;
    }

    @Step("Select departure route: {departureRoute}")
    public KavakHomePage selectDepartureRouteBdd(String routeName, String departureRoute) {
        try {
            waitMs(3000);
            acceptCookies();
            waitMs(2000);

            clickElement(clearButtonDepartureInputBox, routeName + " Route Box base route closed.");
            Log.pass("Mevcut kalkış konumu kapatıldı: " + routeName);

            clickElement(inputDepartureDestination, routeName + " Route Box");

            waitMs(200);
            sendKeysCharacters(inputDepartureDestination, departureRoute);
            waitMs(200);
            Log.pass("Kalkış rotası girildi: " + departureRoute);

            selectMatchingAirportFromList(textRowAirportCode, departureRoute);

        } catch (Exception e) {
            Log.fail("Kalkış rotası seçimi sırasında hata oluştu: " + e.getMessage());
            throw e;
        }

        return this;
    }

}
