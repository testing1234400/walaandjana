
Feature: billing and notification

Scenario Outline: Customer receives an invoice
Given the customer "<customer>" is logged in
And a customer "<customer>" has placed orders
When the system generates an invoice with details:
| Customer Name | Ordered Meal      |
| <customer>    | <meal>            |
Then the customer should receive the invoice

Examples:
| Customer | Meal                  |
| Alice    | Vegan Bowl            |
| Bob      | Quinoa Avocado Bowl   |

Scenario Outline: Administrator generates financial report
Given the administrator is logged in
And the system has processed invoices for customers:
| Customer Name | Total Amount |
| <customer>    | <amount>     |
When the administrator generates a financial report
Then the system should display the financial report

Examples:
| Customer | Amount |
| Alice    | 20.00  |
| Bob      | 10.00  |

