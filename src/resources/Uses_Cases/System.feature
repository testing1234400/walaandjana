Feature: System Automated Inventory and Ordering Behavior
  As a system,
  I want to manage inventory automatically,
  So that kitchen operations can run efficiently without delays.

  Background:
    Given the system is monitoring ingredient stock levels in real-time

  Scenario: System suggests restocking when stock is low
    Given the stock of "Tomatoes" is low
    When the kitchen manager checks the inventory
    Then the system should suggest restocking "Tomatoes"
    And notify the kitchen manager with the suggestion

  Scenario: System auto-generates purchase order when stock is critically low
    Given the stock of "Tomatoes" is critically low
    When the system checks stock levels
    Then it should automatically generate a purchase order for "Tomatoes"
    And notify the supplier and kitchen manager

  Scenario: System avoids duplicate orders for ongoing restocking
    Given a purchase order for "Tomatoes" is already pending
    And the stock of "Tomatoes" is still low
    When the system checks stock levels
    Then it should not generate a new purchase order
    And notify the kitchen manager about the existing pending order

  Scenario: System prioritizes critical ingredients
    Given the stock of "Olive Oil" is critically low
    And "Olive Oil" is marked as high-priority
    When the system checks stock levels
    Then it should immediately generate a high-priority purchase order
    And escalate the alert to the kitchen manager

####################
  Scenario: Validating a custom meal with valid ingredients
    Given a customer profile with dietary preference "Vegan"
    When the customer selects the ingredients "Tofu, Lettuce, Tomato"
    Then the custom meal should be validated successfully

  Scenario: Validating a custom meal with incompatible ingredients
    Given a customer profile with dietary preference "Vegan"
    When the customer selects the ingredients "Beef, Cheese, Lettuce"
    Then the system should show an error indicating dietary mismatch

  Scenario: Validating a custom meal with unavailable ingredients
    Given a customer profile with dietary preference "Vegetarian"
    When the customer selects the ingredients "Dragon Fruit"
    Then the system should show an error indicating no valid ingredients


    #############################
