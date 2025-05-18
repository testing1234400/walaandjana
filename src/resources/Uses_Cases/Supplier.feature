Feature: Manage ingredient prices by the supplier

  Scenario: Set price for an ingredient
    Given a supplier named "Supplier1"
    And an ingredient named "Flour" with quantity 50 and threshold 10
    When the supplier sets the price of "Flour" to 2.5
    Then the price of "Flour" should be 2.5

  Scenario: Get price of an ingredient
    Given a supplier named "Supplier2"
    And an ingredient named "Sugar" with quantity 30 and threshold 5
    When the supplier sets the price of "Sugar" to 3.0
    Then the price of "Sugar" should be 3.0

  Scenario: Add price for an ingredient and update it
    Given a supplier named "Supplier3"
    And an ingredient named "Salt" with quantity 20 and threshold 5
    When the supplier adds the price of "Salt" with price 1.0
    And the supplier updates the price of "Salt" to 1.5
    Then the price of "Salt" should be 1.5

