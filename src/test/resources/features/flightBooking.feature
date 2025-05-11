Feature: Flight Booking on Kayak

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
      | from | to  | departureDate | returnDate  | adults | students | children |
      | DOH  | NRT | 2025-05-17    | 2025-05-23  | 2      | 1        | 1        |
