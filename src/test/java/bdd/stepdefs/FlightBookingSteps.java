package bdd.stepdefs;

import com.kavak.data.GetData;
import com.kavak.pages.homepage.KavakHomePage;
import io.cucumber.java.en.*;
import java.time.LocalDate;

public class FlightBookingSteps {

    KavakHomePage homePage = new KavakHomePage();

    LocalDate departureDate;
    LocalDate returnDate;
    String from, to;

    @Given("user navigates to Kayak homepage")
    public void userNavigatesToHomepage() {
        homePage.navigateToKayak();
    }

    @When("user selects departure route as {string}")
    public void userSelectsDeparture(String departure) {
        from = departure;
        homePage.selectDepartureRouteBdd("Departure", departure);
    }

    @When("user selects return route as {string}")
    public void userSelectsReturn(String returnTo) {
        to = returnTo;
        homePage.selectReturnRoute("Return", returnTo);
    }

    @When("user selects departure date as {string}")
    public void userSelectsDepartureDate(String date) {
        departureDate = LocalDate.parse(date);
        homePage.selectDepartureDate(departureDate);
    }

    @When("user selects return date as {string}")
    public void userSelectsReturnDate(String date) {
        returnDate = LocalDate.parse(date);
        homePage.selectReturnDate(returnDate);
    }

    @When("user adds {int} adult, {int} student and {int} child passengers")
    public void userAddsPassengers(int adult, int student, int child) {
        homePage
                .addPassenger(GetData.PassengerType.ADULT, adult)
                .addPassenger(GetData.PassengerType.STUDENT, student)
                .addPassenger(GetData.PassengerType.CHILD, child);
    }

    @When("user submits the search")
    public void userSubmitsSearch() {
        homePage.clickSubmitButton();
    }

    @Then("user should be redirected correctly with route {string} to {string} and dates")
    public void checkRedirection(String from, String to) {
        homePage.checkRedirections(from, to, departureDate, returnDate);
    }

    @Then("flights should be shown sorted by price ascending")
    public void checkPricesSorted() {
        homePage.validatePricesAreSortedAscending();
    }

    @Then("all flights should have only one transfer")
    public void checkOnlyOneTransfer() {
        homePage.validateOnlyOneTransferFilterApplied();
    }

    @Then("all departure flights should start after 12:00")
    public void checkDepartureAfterNoon() {
        homePage.validateDepartureFilterAfterNoon();
    }
}
