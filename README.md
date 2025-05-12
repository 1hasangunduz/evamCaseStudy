# Evam Case Study - Flight Booking Automation Framework

This project is an end-to-end (E2E) test automation framework for flight booking on Kayak, implemented in Java using Selenium WebDriver, Cucumber BDD, and TestNG.

## 🚀 Project Structure

```
evamCaseStudy/
├── src/
│   ├── main/java/
│   │   ├── com/kavak/base
│   │   ├── com/kavak/pages
│   │   ├── com/kavak/utilities
│   │   
│   ├── test/java/
│   │   ├── stepdefs
│   │   ├── tests/E2E
│   │   └── runner
│
├── features/
│   └── flightBooking.feature
│
├── pom.xml
├── log4j2
└── allure-results/
```

## 📦 Tech Stack

* Java 18
* Selenium 4.27.0
* Cucumber 7.14.0
* TestNG 7.7.0
* WebDriverManager 5.4.1
* Allure Reports
* Owner Config

## ✅ How to Run the Tests

### 1. Clone the repository

```bash
git clone https://github.com/1hasangunduz/evamCaseStudy.git
cd evamCaseStudy
```

### 2. Run with Maven

```bash
mvn clean test
```

### 3. Run Tests for TDD

path : suites/SuiteKavakE2E.xml


### 4. Generate Allure Report

Install Allure CLI:

```bash
brew install allure # Mac
choco install allure # Windows
```

Generate & open the report:

```bash
allure serve allure-results
```

> Note: If `brew` or `allure` is not found, make sure to install and add them to your PATH.

## ✨ Features Covered

* Navigate to Kayak homepage
* Select departure and return routes
* Select travel dates
* Add passenger details
* Submit search
* Validate redirections and filters:

  * Cheapest flights
  * Only one transfer
  * Departure after 12:00

## 🧪 BDD Sample (flightBooking.feature)

```gherkin
Feature: Kayak Flight Booking
  Scenario Outline: User books a flight with given passenger details
    Given user navigates to Kayak homepage
    When user selects departure route as "<from>"
    And user selects return route as "<to>"
    And user selects departure date as "<departureDate>"
    And user selects return date as "<returnDate>"
    And user adds <adults> adult, <students> student and <children> child passengers
    And user submits the search
    Then user should be redirected correctly with route "<from>" to "<to>" and dates
    And flights should be shown sorted by price ascending
    And all flights should have only one transfer
    And all departure flights should start after 12:00

    Examples:
      | from | to  | departureDate | returnDate   | adults | students | children |
      | DOH  | NRT | 2025-05-17    | 2025-05-23   | 2      | 1        | 1        |
```

## 🛠 Configuration

Use `configs.properties` or `ConfigFactory` (via OWNER lib) for dynamic control of browser/env.

```properties
browser=chrome
environment=prod
```

## ❗Troubleshooting

* **WebDriver session error**: Ensure `Driver.quit()` is not called too early
* **Element click intercepted**: Add waits or scroll into view before clicking
* **Allure not found**: Ensure `brew install allure` or manual installation is complete

## 👨‍💻 Maintainer

* [Hasan Gunduz](hasangunduz1010@gmail.com)

---

Feel free to fork or raise issues if you encounter any problems.
Happy Testing!
