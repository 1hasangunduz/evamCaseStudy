package tests.E2E;

import com.kavak.base.BasePage;
import com.kavak.data.GetData;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


import static com.kavak.utilities.StepInit.KAVAK_HOME_PAGE;

@Epic("Flight Tests")
@Feature("Flight Functionality")
@Story("Checking Flight functionality in different ways")
public class KavakHomePageTests extends BasePage {


    @BeforeMethod
    @Parameters(value = {"browser", "environment"})
    public void beforeMethod() {
        setupTest();
        navigateToUrl();
        acceptCookies();
    }

    @Test(description = "You are going to make a booking from “Doha (DOH)” to “Narita (NRT)” airports between 28.04.2025 - 04.05.2025.")
    public void testDateSelectionKayak() {
        var departure = selectLocalDate(2025, 5, 17);
        var returning = selectLocalDate(2025, 5, 23);
        String from = "DOH";
        String to = "NRT";
        int adults = 2;
        int students = 1;
        int children = 1;

        KAVAK_HOME_PAGE
                .selectDepartureRoute("Departure", from)
                .selectReturnRoute("Return", to)
                .selectDepartureDate(departure)
                .selectReturnDate(returning)
                .addPassenger(GetData.PassengerType.ADULT, adults)
                .addPassenger(GetData.PassengerType.STUDENT, students)
                .addPassenger(GetData.PassengerType.CHILD, children)
                .clickSubmitButton()
                .checkRedirections(from, to, departure, returning)
                .openBookViaKavak()
                .showMoreFlightsItems()
                .applyQuickFilter(GetData.QuickFilter.CHEAPEST)
                .applyTransferFilter(GetData.TransferFilter.ONE)
                .slideDepartureStartTimeToNoon()
                .validatePricesAreSortedAscending();

    }


}
