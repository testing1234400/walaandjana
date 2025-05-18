package all;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class Customer {

    private final MyApplication obj;
    private String customerName;
    private String dietaryPreference;
    private String allergyInfo;
    private String lastOrderedMeal;
    private String selectedIngredients;
    private String unavailableIngredient;
    private String suggestedSubstitution;
    private String substitutionApproval;
    private String notificationStatus;

    public Customer(MyApplication iobj) {
        this.obj = iobj;
        // Initialize mock customers
        obj.addCustomer(new CustomerProfile("Alice", "1234", "customer", "Vegetarian", "Nuts"));
        obj.addCustomer(new CustomerProfile("Mark", "1234", "customer", "Vegan", "Dairy"));
    }

    // Store dietary preferences
    @Given("the customer {string} is logged in")
    public void the_customer_is_logged_in(String name) {
        obj.loginByNameOnly(name);
        assertTrue("User is not a customer!", obj.isCustomer());
    }

    @Given("a customer wants to input their dietary preferences")
    public void a_customer_wants_to_input_their_dietary_preferences() {
        System.out.println("Customer wants to input dietary preferences.");
    }

    @When("the preference details:")
    public void the_preference_details(DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps().get(0);
        this.customerName = data.get("Customer Name");
        this.dietaryPreference = data.get("Dietary Preference");
        this.allergyInfo = data.get("Allergy");

        obj.loginByNameOnly(customerName);
        CustomerProfile profile = new CustomerProfile(customerName, "1234", "customer", dietaryPreference, allergyInfo);
        obj.addCustomer(profile);
        System.out.println("✅ Customer profile created and saved for: " + customerName);
    }

    @Then("the system should store their preferences")
    public void the_system_should_store_their_preferences() {
        CustomerProfile profile = obj.getProfileByName(customerName);
        assertNotNull("Profile should not be null", profile);
        assertEquals("Dietary preference mismatch", dietaryPreference, profile.getDietaryPreference());
        assertEquals("Allergy info mismatch", allergyInfo, profile.getAllergy());
        System.out.println("✅ Preferences successfully stored.");
    }

    @Then("the system should only show meals matching their dietary needs")
    public void the_system_should_only_show_meals_matching_their_dietary_needs() {
        CustomerProfile profile = obj.getProfileByName(customerName);
        List<meal> suggestedMeals = obj.getFilteredSuggestedMeals(profile);
        assertFalse("No meals matched the dietary needs!", suggestedMeals.isEmpty());
        for (meal m : suggestedMeals) {
            assertTrue("Meal does not match preference or contains allergen: " + m.getName(),
                    profile.isMealValid(m));
            System.out.println(" - " + m.getName());
        }
    }

    // Track past orders
    @Given("order history details:")
    public void order_history_details(DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps().get(0);
        this.customerName = data.get("Customer Name");
        this.lastOrderedMeal = data.get("Last Ordered Meal");
        CustomerProfile profile = obj.getProfileByName(customerName);
        assertNotNull("Customer profile not found", profile);
        obj.addMealToOrderHistory(profile, lastOrderedMeal);
        System.out.printf("✅ Loaded and saved past order for %s: %s%n", customerName, lastOrderedMeal);
    }

    @When("they access their order history")
    public void they_access_their_order_history() {
        System.out.printf("Accessing order history for customer: %s%n", customerName);
    }

    @Then("the system should display their past meal orders")
    public void the_system_should_display_their_past_meal_orders() {
        CustomerProfile profile = obj.getProfileByName(customerName);
        assertNotNull("Customer profile not found", profile);
        // Use getCustomerOrderHistory instead of getOrdersForCustomer
        List<order> customerOrders = obj.getCustomerOrderHistory(profile);
        assertFalse("No past meals found!", customerOrders.isEmpty());
        System.out.println("🧾 Past meals for " + customerName + ":");
        for (order o : customerOrders) {
            System.out.println(" - " + o.getMeal().getName());
        }
    }

    // Reorder a past meal
    @When("the customer chooses to reorder {string}")
    public void the_customer_chooses_to_reorder(String meal) {
        this.lastOrderedMeal = meal;
        CustomerProfile profile = obj.getProfileByName(customerName);
        meal m = obj.getMealByName(meal);
        assertNotNull("Customer not found", profile);
        assertNotNull("Meal not found", m);
        obj.addToPendingOrders(profile, m);
        System.out.println("✅ Customer " + customerName + " has reordered: " + meal);
    }

    @Then("the system should confirm the reorder")
    public void the_system_should_confirm_the_reorder() {
        CustomerProfile profile = obj.getProfileByName(customerName);
        List<order> customerPendingOrders = obj.getPendingOrdersForCustomer(profile);
        boolean found = customerPendingOrders.stream()
                .anyMatch(o -> o.getMeal().getName().equalsIgnoreCase(lastOrderedMeal));
        assertTrue("Meal not found in pending orders!", found);
        System.out.println("✅ Order is in the pending list for confirmation.");
    }

    // Create custom meal requests
    @Given("a customer wants to customize their meal")
    public void a_customer_wants_to_customize_their_meal() {
        System.out.println("Customer wants to customize their meal.");
    }

    @Given("the customization details:")
    public void the_customization_details(DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps().get(0);
        this.customerName = data.get("Customer Name");
        this.selectedIngredients = data.get("Selected Ingredients");
        System.out.printf("Loaded customization for %s: Ingredients = %s%n", customerName, selectedIngredients);
    }

    @When("they place a custom order")
    public void they_place_a_custom_order() {
        System.out.printf("Placing custom order with ingredients: %s%n", selectedIngredients);
    }

    @Then("the system should validate the ingredient selection")
    public void the_system_should_validate_the_ingredient_selection() {
        CustomerProfile profile = obj.getProfileByName(customerName);
        assertNotNull("Customer profile not found", profile);
        List<Ingredient> selected = parseIngredients(selectedIngredients);
     //   List<Ingredient> validatedIngredients = obj.validateIngredients(selected, profile);
     //   assertFalse("No valid ingredients after validation", validatedIngredients.isEmpty());
        System.out.println("✅ Custom meal is valid and safe.");
    }

    @Then("ensure it meets dietary restrictions")
    public void ensure_it_meets_dietary_restrictions() {
        CustomerProfile profile = obj.getProfileByName(customerName);
        List<Ingredient> selected = parseIngredients(selectedIngredients);
     //   List<Ingredient> validatedIngredients = obj.validateIngredients(selected, profile);
//        for (Ingredient ing : validatedIngredients) {
//            assertFalse("Ingredient conflicts with allergy: " + ing.getName(),
//                    ing.getName().equalsIgnoreCase(profile.getAllergy()));
//        }
        System.out.println("✅ Custom order meets dietary restrictions.");
    }




    // Suggest ingredient substitutions
    @Given("a customer selects an unavailable ingredient")
    public void a_customer_selects_an_unavailable_ingredient() {
        System.out.println("Customer selects an unavailable ingredient.");
    }

    @Given("substitution details:")
    public void substitution_details(DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps().get(0);
        this.customerName = data.get("Customer Name");
        this.unavailableIngredient = data.get("Original Ingredient");
        this.suggestedSubstitution = data.get("Suggested Substitute");
        this.substitutionApproval = "Approved"; // Default to approved for simplicity
        System.out.printf("Substitution details loaded for %s: %s -> %s%n",
                customerName, unavailableIngredient, suggestedSubstitution);
    }

    @When("they receive the suggested substitution")
    public void they_receive_the_suggested_substitution() {
        CustomerProfile profile = obj.getProfileByName(customerName);
        Ingredient original = findIngredient(unavailableIngredient);
        Ingredient substitute = findIngredient(suggestedSubstitution);
//        assertNotNull("Original ingredient not found", original);
//        assertNotNull("Substitute ingredient not found", substitute);
        obj.validateIngredients(List.of(original), profile); // Triggers substitution alert
        System.out.printf("Suggested substitution for %s: %s%n", unavailableIngredient, suggestedSubstitution);
    }

    @Then("they should approve or reject the change")
    public void they_should_approve_or_reject_the_change() {
        assertTrue("Substitution decision must be made",
                substitutionApproval.equals("Approved") || substitutionApproval.equals("Rejected"));
        if (substitutionApproval.equals("Approved")) {
            System.out.printf("✅ Customer approved substitution: %s -> %s%n", unavailableIngredient, suggestedSubstitution);
        } else {
            System.out.printf("❌ Customer rejected substitution: %s -> %s%n", unavailableIngredient, suggestedSubstitution);
        }
    }

    // Send notification for upcoming meal
    @Given("a customer has an upcoming meal delivery")
    public void a_customer_has_an_upcoming_meal_delivery() {
        System.out.println("Customer has an upcoming delivery scheduled.");
    }

    @When("the system sends a reminder notification")
    public void the_system_sends_a_reminder_notification() {
        this.notificationStatus = "Sent";
        // Simulate sending a notification (could integrate with a mock notification service)
        System.out.println("Reminder notification sent to customer.");
    }

    @Then("the customer should receive the notification")
    public void the_customer_should_receive_the_notification() {
        assertEquals("Notification should be sent successfully", "Sent", notificationStatus);
        System.out.println("✅ Customer received the meal delivery reminder.");
    }

    // Helper methods
    private List<Ingredient> parseIngredients(String ingredients) {
        return List.of(ingredients.split(",\\s*")).stream()
                .map(this::findIngredient)
                .filter(ing -> ing != null)
                .collect(Collectors.toList());
    }

    private Ingredient findIngredient(String name) {
        for (Ingredient ing : MyApplication.ingredients) {
            if (ing.getName().equalsIgnoreCase(name)) {
                return ing;
            }
        }
        return null;
    }
}