Feature: Notifications and Alerts

  Scenario Outline: Notify chefs of scheduled cooking tasks
    Given a chef has upcoming meal preparations
    And the notification details:
      | Chef Name  | Task               | Notification Sent |
      | <ChefName> | <Task>             | <Notification>    |
    When their schedule updates
    Then the system should send them a notification

    Examples:
      | ChefName  | Task              | Notification |
      | Chef Mike | Prepare Sushi     | Yes          |
      | Chef Emma | Bake Birthday Cake| Yes          |


  Scenario Outline: Receive an alert for ingredient substitution
    Given a chef is preparing a meal
    And the alert details:
      | Chef Name | Meal           | Substituted Ingredient | Alert Received |
      | <ChefName>| <Meal>         | <Substitution>         | <Alert>        |
    When an ingredient substitution is applied
    Then the system should send an alert to the chef for approval or adjustment

    Examples:
      | ChefName | Meal          | Substitution           | Alert |
      | Chef Alan| Vegan Burger  | Tofu for Tempeh        | Yes   |
      | Chef Nina| Pasta Alfredo | Cashew Cream for Dairy | Yes   |