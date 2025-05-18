Feature: Manager Inventory Management
  As a kitchen manager
  I want to manage ingredient inventory
  So that I can maintain optimal stock levels

  Background:
    Given a manager "chef_john" with password "secure123" and role "manager"
    And the following ingredients exist:
      | Name      | Quantity | Threshold | Alternative |
      | Tomato    | 20       | 5         | None        |
      | Onion     | 15       | 3         | Shallot     |
      | Chicken   | 10       | 2         | None        |

  Scenario: Add new ingredient to inventory
    When I add ingredient "Garlic" with quantity 30 and threshold 10
    Then ingredient "Garlic" should exist in inventory with quantity 30

  Scenario: Use existing ingredient
    When I use 5 "Tomato" from inventory
    Then "Tomato" quantity should be 15
    And I should see inventory updated message

  Scenario: Use non-existent ingredient
    When I use 3 "Potato" from inventory
    Then I should see "Ingredient not found" error message

  Scenario: Restock existing ingredient
    When I restock "Onion" with 10 more
    Then "Onion" quantity should be 25
    And I should see restock confirmation message

  Scenario: Restock non-existent ingredient
    When I restock "Beef" with 5 more
    Then I should see "Ingredient not found" error message

  Scenario: View current inventory
    When I view the inventory
    Then I should see all ingredients with their quantities:
      | Name      | Quantity |
      | Tomato    | 20       |
      | Onion     | 15       |
      | Chicken   | 10       |

    #################
  Scenario: Successfully assign a task to a chef with matching expertise
    Given there are chefs with expertise "Italian Cuisine" and "Vegan"
    When I assign a task "Prepare Salad" to a chef with expertise "Italian Cuisine"
    Then the task should be assigned to a chef with "Italian Cuisine"

  Scenario: No chef available with matching expertise
    Given there are chefs with expertise "Grilling" and "Baking"
    When I assign a task "Sushi Preparation" to a chef with expertise "Japanese"
    Then the system should display "❌ No chef available with expertise: Japanese"


    #######################