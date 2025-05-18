package all;

import all.Admin;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import static org.junit.Assert.*;

public class AdminStep {
    private Admin admin;
    private Map<String, List<String>> orderHistory;
    private String consoleOutput;
    private Map<String, Integer> actualMealFrequency;

    @Given("an admin user {string} with password {string}")
    public void an_admin_user_with_password(String username, String password) {
        admin = new Admin(username, password);
    }

    @Given("the following order history exists:")
    public void the_following_order_history_exists(io.cucumber.datatable.DataTable dataTable) {
        orderHistory = new HashMap<>();
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : rows) {
            String customer = row.get("Customer");
            String[] orders = row.get("Orders").split(",\\s*");
            orderHistory.put(customer, Arrays.asList(orders));
        }
    }

    @Given("there are no orders in the history")
    public void there_are_no_orders_in_the_history() {
        orderHistory = new HashMap<>();
    }

    @When("I view all order history as admin")
    public void i_view_all_order_history_as_admin() {
        // Redirect System.out to capture console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out;
        System.setOut(printStream);

        admin.viewAllOrderHistory(orderHistory);

        System.out.flush();
        System.setOut(originalOut);
        consoleOutput = outputStream.toString();
    }
    @When("I analyze meal popularity as admin")
    public void i_analyze_meal_popularity_as_admin() {
        // Redirect System.out to capture console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out;
        System.setOut(printStream);

        admin.analyzePopularMeals(orderHistory);

        System.out.flush();
        System.setOut(originalOut);
        consoleOutput = outputStream.toString();

        // Parse the actual meal frequency from console output
        actualMealFrequency = new HashMap<>();
        String[] lines = consoleOutput.split("\n");
        for (String line : lines) {
            if (line.contains(":") && line.contains("orders")) {
                try {
                    String[] parts = line.split(":");
                    String meal = parts[0].replace(" - ", "").trim();
                    String frequencyPart = parts[1].replace("orders", "").trim();
                    if (!frequencyPart.isEmpty()) {
                        int frequency = Integer.parseInt(frequencyPart);
                        actualMealFrequency.put(meal, frequency);
                    }
                } catch (NumberFormatException e) {
                    // Skip lines that don't have valid numbers
                    continue;
                }
            }
        }
    }

    @Then("I should see the complete order history including:")
    public void i_should_see_the_complete_order_history_including(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> expected = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : expected) {
            String customer = row.get("Customer");
            String expectedOrders = row.get("Orders");

            assertTrue(consoleOutput.contains("👤 " + customer + ":"));
            for (String order : expectedOrders.split(",\\s*")) {
                assertTrue(consoleOutput.contains("• " + order));
            }
        }
    }

    @Then("I should see a message indicating no orders found")
    public void i_should_see_a_message_indicating_no_orders_found() {
        assertTrue(consoleOutput.contains("⚠️ No orders found."));
    }

    @Then("I should see the following meal frequency:")
    public void i_should_see_the_following_meal_frequency(io.cucumber.datatable.DataTable dataTable) {
        Map<String, Integer> expected = new HashMap<>();
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : rows) {
            expected.put(row.get("Meal"), Integer.parseInt(row.get("Frequency")));
        }

        assertEquals(expected.size(), actualMealFrequency.size());
        for (Map.Entry<String, Integer> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), actualMealFrequency.get(entry.getKey()));
        }
    }
}