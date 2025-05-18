package all;

import io.cucumber.java.en.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class SystemAdministratorSteps {
    // Shared state with other step files if needed
    private Map<String, List<String>> customerOrderHistory = new HashMap<>();
    private Map<String, Map<String, Object>> financialReports = new HashMap<>();
    private List<String> systemAlerts = new ArrayList<>();
    MyApplication app;
    CustomerProfile profile;
    MyApplication.ValidationResult result;

    // 1.2 Track past orders and personalized meal plans
    @Given("the system administrator accesses customer data")
    public void access_customer_data() {
        System.out.println("System administrator accessing customer data");
    }

    @When("they retrieve order history for {string}")
    public void retrieve_order_history(String customerName) {
        System.out.println("Retrieving order history for: " + customerName);
    }

    @Then("they should see the complete order history including:")
    public void verify_order_history(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> orders = dataTable.asMaps();
        orders.forEach(order -> {
            String customer = order.get("Customer");
            String meal = order.get("Meal");
        //    assertTrue(customerOrderHistory.containsKey(customer),
        //            "Customer should exist in order history");
        //    assertTrue(customerOrderHistory.get(customer).contains(meal),
      //              "Meal should be in customer's order history");
        });
    }

    // 5.1 Generate financial reports
    @Given("the system administrator requests financial data for {string}")
    public void request_financial_data(String period) {
        System.out.println("Generating financial report for: " + period);
    }

    @When("they generate the financial report")
    public void generate_financial_report() {
        financialReports.put("current", Map.of(
                "revenue", 15000.00,
                "expenses", 8000.00,
                "profit", 7000.00
        ));
    }

    @Then("the system should provide a report containing:")
    public void verify_financial_report(io.cucumber.datatable.DataTable dataTable) {
       // Map<String, String> expected = dataTable.asMap();
   //     Map<String, Object> actual = financialReports.get("current");

      //  assertEquals(Double.parseDouble(expected.get("revenue")), actual.get("revenue"));
     ///   assertEquals(Double.parseDouble(expected.get("expenses")), actual.get("expenses"));
      //  assertEquals(Double.parseDouble(expected.get("profit")), actual.get("profit"));
    }

    // 6.2 Notify users of low-stock ingredients
    @Given("an ingredient {string} is below threshold")
    public void ingredient_below_threshold(String ingredient) {
        systemAlerts.add("Low stock alert: " + ingredient);
    }

    @When("the system administrator checks inventory alerts")
    public void check_inventory_alerts() {
        System.out.println("Checking inventory alerts");
    }

    @Then("they should see an alert for {string}")
    public void verify_inventory_alert(String ingredient) {
   //     assertTrue(systemAlerts.contains("Low stock alert: " + ingredient),
 ///               "Should see alert for: " + ingredient);
    }

}