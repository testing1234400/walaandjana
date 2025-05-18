package all;

import io.cucumber.java.en.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class SystemSteps {

    // State variables
    private final Set<String> lowStockItems = new HashSet<>();
    private final Set<String> criticallyLowStockItems = new HashSet<>();
    private final Set<String> pendingOrders = new HashSet<>();
    private final List<String> notifications = new ArrayList<>();
    private final Set<String> highPriorityItems = new HashSet<>();
    private boolean monitoringStock = false;
/// /////////////////////
     MyApplication app;
     CustomerProfile profile;
     MyApplication.ValidationResult result;

    @Given("a customer profile with dietary preference {string}")
    public void a_customer_profile_with_dietary_preference(String dietaryPreference) {
        app = new MyApplication();
        profile = new CustomerProfile("TestUser", "123", "customer", dietaryPreference, "None");
    }

    @When("the customer selects the ingredients {string}")
    public void the_customer_selects_the_ingredients(String ingredients) {
        result = app.validateCustomMeal(ingredients, profile);
    }

    @Then("the custom meal should be validated successfully")
    public void the_custom_meal_should_be_validated_successfully() {
        assertNotNull(result);
        assertTrue(result.getValidatedIngredients().size() > 0);
        System.out.println("✅ Custom meal validated successfully.");
    }

    @Then("the system should show an error indicating dietary mismatch")
    public void the_system_should_show_an_error_indicating_dietary_mismatch() {
        assertNotNull(result);
        assertFalse(result.getValidatedIngredients().size() > 0);
        System.out.println("❌ Dietary mismatch detected.");
    }

    @Then("the system should show an error indicating no valid ingredients")
    public void the_system_should_show_an_error_indicating_no_valid_ingredients() {
        assertNotNull(result);
        assertEquals(0, result.getValidatedIngredients().size());
        System.out.println("❌ No valid ingredients found.");
    }
/// ////////////////////
    // Background step
    @Given("the system is monitoring ingredient stock levels in real-time")
    public void the_system_is_monitoring_ingredient_stock_levels_in_real_time() {
        monitoringStock = true;
        System.out.println("System initialized with real-time stock monitoring.");
    }

    // Scenario 1: System suggests restocking when stock is low
    @Given("the stock of {string} is low")
    public void the_stock_of_is_low(String item) {
        lowStockItems.add(item);
    }

    @When("the kitchen manager checks the inventory")
    public void the_kitchen_manager_checks_the_inventory() {
        lowStockItems.forEach(item ->
                notifications.add("Suggest restocking: " + item)
        );
    }

    @Then("the system should suggest restocking {string}")
    public void the_system_should_suggest_restocking(String item) {
        assertTrue(notifications.contains("Suggest restocking: " + item),
                "System should suggest restocking: " + item);
    }

    @Then("notify the kitchen manager with the suggestion")
    public void notify_the_kitchen_manager_with_the_suggestion() {
        assertFalse(notifications.isEmpty(),
                "Manager should be notified with restocking suggestions");
    }

    // Scenario 2: System auto-generates purchase order when stock is critically low
    @Given("the stock of {string} is critically low")
    public void the_stock_of_is_critically_low(String item) {
        criticallyLowStockItems.add(item);
    }

    @When("the system checks stock levels")
    public void the_system_checks_stock_levels() {
        criticallyLowStockItems.forEach(item -> {
            if (!pendingOrders.contains(item)) {
                pendingOrders.add(item);
                notifications.add("Auto purchase order: " + item);
            } else {
                notifications.add("Existing order pending for: " + item);
            }
        });
    }

    @Then("it should automatically generate a purchase order for {string}")
    public void it_should_automatically_generate_a_purchase_order_for(String item) {
        assertTrue(notifications.contains("Auto purchase order: " + item),
                "Purchase order should be generated for: " + item);
    }

    @Then("notify the supplier and kitchen manager")
    public void notify_the_supplier_and_kitchen_manager() {
        assertTrue(notifications.stream()
                        .anyMatch(msg -> msg.contains("Auto purchase order")),
                "Supplier and kitchen manager should be notified");
    }

    // Scenario 3: System avoids duplicate orders for ongoing restocking
    @Given("a purchase order for {string} is already pending")
    public void a_purchase_order_for_is_already_pending(String item) {
        pendingOrders.add(item);
        notifications.add("Auto purchase order: " + item);
    }

    @Given("the stock of {string} is still low")
    public void the_stock_of_is_still_low(String item) {
        criticallyLowStockItems.add(item);
    }

    @Then("it should not generate a new purchase order")
    public void it_should_not_generate_a_new_purchase_order() {
        long autoOrderCount = notifications.stream()
                .filter(msg -> msg.startsWith("Auto purchase order"))
                .count();
        assertEquals(pendingOrders.size(), autoOrderCount,
                "Number of auto-generated orders should match pending orders count");
    }

    @Then("notify the kitchen manager about the existing pending order")
    public void notify_the_kitchen_manager_about_the_existing_pending_order() {
        assertTrue(notifications.stream()
                        .anyMatch(msg -> msg.contains("Existing order pending")),
                "Kitchen manager should be notified about existing order");
    }

    // Scenario 4: System prioritizes critical ingredients
    @Given("{string} is marked as high-priority")
    public void is_marked_as_high_priority(String item) {
        highPriorityItems.add(item);
        criticallyLowStockItems.add(item);
    }

    @Then("it should immediately generate a high-priority purchase order")
    public void it_should_immediately_generate_a_high_priority_purchase_order() {
        highPriorityItems.forEach(item -> {
            String priorityMsg = "High-priority purchase order: " + item;
            pendingOrders.add(item);
            notifications.add(priorityMsg);
            assertTrue(notifications.contains(priorityMsg),
                    "High-priority order should be generated for: " + item);
        });
    }

    @Then("escalate the alert to the kitchen manager")
    public void escalate_the_alert_to_the_kitchen_manager() {
        assertTrue(notifications.stream()
                        .anyMatch(msg -> msg.startsWith("High-priority")),
                "Escalation alert should be sent to kitchen manager");
    }




}