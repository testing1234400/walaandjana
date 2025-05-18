Feature: Admin Functionality
  As an administrator
  I want to manage and analyze order history
  So that I can understand customer preferences and business performance

  Background:
    Given an admin user "admin1" with password "secure123"
    And the following order history exists:
      | Customer   | Orders                          |
      | john_doe   | Burger, Fries, Soda             |
      | jane_smith | Pizza, Soda, Salad              |
      | mike_jones | Burger, Soda, Fries, Ice Cream |

  Scenario: View complete order history
    When I view all order history as admin
    Then I should see the complete order history including:
      | Customer   | Orders                          |
      | john_doe   | Burger, Fries, Soda             |
      | jane_smith | Pizza, Soda, Salad              |
      | mike_jones | Burger, Soda, Fries, Ice Cream |

  Scenario: View empty order history
    Given an admin user "admin1" with password "secure123"
    And there are no orders in the history
    When I view all order history as admin
    Then I should see a message indicating no orders found

  Scenario: Analyze popular meals
    When I analyze meal popularity as admin
    Then I should see the following meal frequency:
      | Meal      | Frequency |
      | Soda      | 3         |
      | Burger    | 2         |
      | Fries     | 2         |
      | Pizza     | 1         |
      | Salad     | 1         |
      | Ice Cream | 1         |