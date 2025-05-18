Feature: Customer


  Scenario Outline: tore dietary preferences and allergies
    Given the customer "<Customer>" is logged in
    And a customer wants to input their dietary preferences
    When the preference details:
      | Customer Name | Dietary Preference | Allergy  |
      | <Customer>   | <Preference>       | <Allergy> |
    Then the system should store their preferences
    #And ensure meals do not contain restricted ingredients
    And the system should only show meals matching their dietary needs


    Examples:
      | Customer | Preference  | Allergy  |
      | Alice    | Vegetarian  | Nuts     |
      | Mark     | Vegan       | Dairy    |


  Scenario Outline: Track past orders and meal plans
    Given the customer "<Customer>" is logged in
    And  order history details:
      | Customer Name | Last Ordered Meal      |
      | <Customer>    | <LastMeal>             |
    When they access their order history
    Then the system should display their past meal orders

    Examples:
      | Customer | LastMeal               |
      | ALice  | Grilled Chicken Salad  |
      | ALice   | Fruit Bowl             |
      | Alice    | Lentil Soup            |
      | Mark    | Gluten-Free Pasta      |
      | Mark     | Protein Shake          |



  Scenario Outline: Customer reorders a past meal
    Given the customer "<Customer>" is logged in
    And order history details:
      | Customer Name | Last Ordered Meal |
      | <Customer>    | <Meal>            |
    When the customer chooses to reorder "<Meal>"
    Then the system should confirm the reorder

    Examples:
      | Customer | Meal                  |
      | ALice  | Grilled Chicken Salad   |
      | ALice   | Fruit Bowl             |


  Scenario Outline: Create custom meal requests
    Given the customer "<Customer>" is logged in
    And a customer wants to customize their meal
    And the customization details:
      | Customer Name | Selected Ingredients    |
      | <Customer>    | <Ingredients>           |
    When they place a custom order
    Then the system should validate the ingredient selection
    And ensure it meets dietary restrictions

    Examples:
      | Customer | Ingredients              |
      | Emily    | Quinoa, Avocado, Spinach |
      | Jake     | Tofu, Rice, Broccoli     |



  Scenario Outline: Suggest ingredient substitutions
    Given the customer "<Customer>" is logged in
    And a customer selects an unavailable ingredient
    And substitution details:
      | Customer Name | Original Ingredient | Suggested Substitute | Decision  |
      | <Customer>    | <Original>          | <Substitute>        | <Decision> |
    When they receive the suggested substitution
    Then they should approve or reject the change

    Examples:
      | Customer | Original    | Substitute  | Decision  |
      | Alice    | Almond Milk |OatMilk    | Approved  |
      | Mark    | Sugar       | OatMilk      | Rejected   |


  Scenario: Send notification for upcoming meal
    Given a customer has an upcoming meal delivery
    When the system sends a reminder notification
    Then the customer should receive the notification