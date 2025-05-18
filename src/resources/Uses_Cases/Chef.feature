Feature: Chef Task Management

  Scenario: View assigned tasks for a chef
    Given a chef "Alice" is logged into the system
    When they check their task list
    Then the system should display all assigned tasks
    And notify the chef of upcoming cooking deadlines

  Scenario Outline: Approve or adjust ingredient substitutions
    Given a customer has selected an alternative ingredient
    And the substitution details:
      | Customer Name | Original Ingredient | Substituted Ingredient | Chef Approval |
      | <Customer>    | <Original>          | <Substituted>          | <Approval>    |
    When the system notifies the chef
    Then the chef should approve or adjust the recipe

    Examples:
      | Customer | Original    | Substituted | Approval  |
      | Alice    | Almond Milk | Soy Milk    | Approved  |
      | Bob      | Sugar       | Honey       | Adjusted  |


  Scenario Outline: View customer dietary preferences
    Given a chef wants to customize a meal
    And the dietary details:
      | Customer Name | Dietary Preference | Allergy |
      | <Customer>    | <Preference>       | <Allergy> |
    When they access a customer's profile
    Then the system should display the customer's dietary preferences and allergies

    Examples:
      | Customer | Preference  | Allergy  |
      | Alice    | Vegetarian  | Nuts     |
      | Mark     | Vegan       | Dairy    |

  Scenario Outline: Access customers' order history
    Given a chef wants to suggest a meal plan
    And the order history details:
      | Customer Name | Last Ordered Meal       |
      | <Customer>    | <LastMeal>             |
    When they access a customer's order history
    Then the system should display past orders

    Examples:
      | Customer | LastMeal               |
      | ALice    | Grilled Chicken Salad  |
      | Mark      | Gluten-Free Pasta      |

##########################################

  Scenario: Viewing Ingredient Availability
    Given the chef is logged in
    When they choose to view ingredient availability
    Then the system displays the list of available ingredients with their quantities

  Scenario: Suggesting Ingredient Substitutions
    Given the chef is logged in
    When they choose to view ingredient substitutions
    Then the system displays available ingredients with their substitutes

  Scenario: Viewing Custom Meal Requests
    Given the chef is logged in
    When they choose to view custom meal requests
    Then the system displays a list of custom meal requests from customers

  Scenario: Viewing Customer Preferences
    Given the chef is logged in
    When they choose to view customer preferences
    Then the system displays the preferences of all customers

  Scenario: Viewing Past Orders
    Given the chef is logged in
    When they choose to view past orders
    Then the system displays the list of past orders for the chef

  Scenario: Viewing Meal Plan Suggestions
    Given the chef is logged in
    When they choose to view meal plan suggestions
    Then the system displays a list of meal plan suggestions based on diet types

#
#Feature: Notifications and Alerts
#
#  Scenario Outline: Notify chefs of scheduled cooking tasks
#    Given a chef has upcoming meal preparations
#    And the notification details:
#      | Chef Name  | Task               | Notification Sent |
#      | <ChefName> | <Task>             | <Notification>    |
#    When their schedule updates
#    Then the system should send them a notification
#
#    Examples:
#      | ChefName  | Task              | Notification |
#      | Chef Mike | Prepare Sushi     | Yes          |
#      | Chef Emma | Bake Birthday Cake| Yes          |

#  Scenario Outline: Receive an alert for ingredient substitution
#    Given a chef is preparing a meal
#    And the alert details:
#      | Chef Name | Meal           | Substituted Ingredient | Alert Received |
#      | <ChefName>| <Meal>         | <Substitution>         | <Alert>        |
#    When an ingredient substitution is applied
#    Then the system should send an alert to the chef for approval or adjustment
#
#    Examples:
#      | ChefName | Meal          | Substitution           | Alert |
#      | Chef Alan| Vegan Burger  | Tofu for Tempeh        | Yes   |
#      | Chef Nina| Pasta Alfredo | Cashew Cream for Dairy | Yes   |
  Scenario: Searching for an alternative ingredient
    Given the application has an ingredient "Garlic" with quantity 10
    When I search for an alternative for ingredient "Garlic"
    Then the system should display the alternative ingredient name

  Scenario: Using an existing ingredient
    Given the application has an ingredient "Cheese" with quantity 5
    When I use the ingredient "Cheese" with quantity 2
    Then the ingredient "Cheese" should have quantity 3

  Scenario: Restocking an existing ingredient
    Given the application has an ingredient "Tomato" with quantity 3
    When I restock the ingredient "Tomato" with quantity 5
    Then the ingredient "Tomato" should have quantity 8

  Scenario: Completing a chef's task
    Given the application has a chef "1" named "Chef John" with tasks:
      | Prepare Salad |
      | Cook Steak    |
    When I complete the task at index 1 for chef "1"
    Then the task "Prepare Salad" should be marked as completed
    And the remaining tasks should be:
      | Cook Steak |