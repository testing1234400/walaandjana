Feature: Login functionality for users

  Background:
    Given the system is ready for login

  Scenario Outline: Successful login for existing users
    When the user enters username "<username>" and password "<password>"
    Then login should succeed for "<username>"
    And the user should be redirected to the "<role>" dashboard

    Examples:
      | username | password  | role           |
      | wala     | wala123   | customer       |
      | chef1    | chef1pass  | chef          |
      | user88   | abc123    | kitchenManager |

  Scenario Outline: Failed login due to wrong password
    When the user enters username "<username>" and invalid password "<password>"
    Then login should fail with message "Incorrect password"

    Examples:
      | username | password   |
      | wala     | wrong123   |
      | chef1    | badpass    |
      | user88   | 123abc     |

  Scenario Outline: Failed login due to unknown username
    When the user enters unkown username "<username>" and password "<password>"
    Then login should fail with message "User not found"

    Examples:
      | username | password  |
      | notexist | any123    |
      | fakeuser | passme    |

  Scenario Outline: Failed login due to empty username
    When the user enters empty username "<username>" and password "<password>"
    Then login should fail with message "Username cannot be empty"

    Examples:
      | username | password |
      |          | wala123  |

  Scenario Outline: Failed login due to empty password
    When the user enters username "<username>" and empty password "<password>"
    Then login should fail with message "Password cannot be empty"

    Examples:
      | username | password |
      | wala     |          |

  Scenario Outline: Failed login due to both fields being empty
    When the user enters empty username "<username>" and empty password "<password>"
    Then login should fail with message "Username and password cannot be empty"

    Examples:
      | username | password |
      |          |          |
