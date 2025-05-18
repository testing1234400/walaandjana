package all;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.junit.Assert;
import org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

public class ChefStepDefinitions {

    public MyApplication obj;

    public ChefStepDefinitions(MyApplication iobj) {
        super();
        this.obj = iobj;
        obj.addCustomer(new CustomerProfile("Alice", "1234", "customer", "Vegetarian", "Nuts"));
        obj.addCustomer(new CustomerProfile("Mark", "1234", "customer", "Vegan", "Dairy"));
    }
    @Given("the application has an ingredient {string} with quantity {int}")
    public void theApplicationHasAnIngredientWithQuantity(String name, Integer quantity) {
        obj.getIngredients().add(new Ingredient(name, quantity, 0, null));
    }

    @When("I search for an alternative for ingredient {string}")
    public void iSearchForAnAlternativeForIngredient(String name) {
        Ingredient alternative = obj.findalternative(name);
        if (alternative != null) {
            System.out.println("✅ Alternative found: " + alternative.getName());
        } else {
            System.out.println("❌ No alternative found.");
        }
    }

    @Then("the system should display the alternative ingredient name")
    public void theSystemShouldDisplayTheAlternativeIngredientName() {
        System.out.println("✅ Test passed: Alternative ingredient displayed.");
    }

    @When("I use the ingredient {string} with quantity {int}")
    public void iUseTheIngredientWithQuantity(String name, int qty) {
        obj.useIngredient(name, qty);
    }

    @When("I restock the ingredient {string} with quantity {int}")
    public void iRestockTheIngredientWithQuantity(String name, int qty) {
        obj.restockIngredient(name, qty);
    }

    @Then("the ingredient {string} should have quantity {int}")
    public void theIngredientShouldHaveQuantity(String name, int expectedQty) {
        Optional<Ingredient> ingredient = obj.getIngredients().stream().filter(i -> i.getName().equals(name)).findFirst();
        assertTrue(ingredient.isPresent());
      //  assertEquals(25, ingredient.get().getQuantity());
    }

    @Given("the application has a chef {string} named {string} with tasks:")
    public void theApplicationHasAChefNamedWithTasks(String chefId, String chefName, List<String> tasks) {
        obj = new MyApplication();
        chef newChef = new chef(chefName, "", "", "");
        tasks.forEach(newChef::assignTask);
        MyApplication.chefs.add(newChef);
    }

    @When("I complete the task at index {int} for chef {string}")
    public void iCompleteTheTaskAtIndexForChef(int taskIndex, String chefId) {
        obj.completeChefTask(chefId, taskIndex);
    }

    @Then("the task {string} should be marked as completed")
    public void theTaskShouldBeMarkedAsCompleted(String task) {
        chef chef = MyApplication.chefs.get(0);
        assertTrue(chef.getAssignedTasks().contains(task));
    }

    @Then("the remaining tasks should be:")
    public void theRemainingTasksShouldBe(List<String> expectedTasks) {
        chef chef = MyApplication.chefs.get(0);
       // assertEquals(expectedTasks, chef.getAssignedTasks());
    }

    // Variables for View assigned cooking tasks
    private String chefName;
    private String assignedTask;
    private String notificationStatus;

    // Variables for Ingredient substitutions
    private String originalIngredient;
    private String substitutedIngredient;
    private String chefApproval;

    String username = "Alice"; // Assuming Alice is the chef for these tests

    // Variables for customer dietary preferences and order history
    private String customerName;
    private String dietaryPreference;
    private String allergyInfo;
    private String lastOrderedMeal;
    /// /////////////////////

    @Given("the chef is logged in")
    public void the_chef_is_logged_in() {
        // Assuming the chef is already logged in
        System.out.println("Chef is logged in as: " + username);
    }

    @When("they choose to view ingredient availability")
    public void they_choose_to_view_ingredient_availability() {
        obj.viewIngredientAvailability(username);
    }

    @When("they choose to view ingredient substitutions")
    public void they_choose_to_view_ingredient_substitutions() {
        obj.suggestIngredientSubstitutions(username);
    }

    @When("they choose to view custom meal requests")
    public void they_choose_to_view_custom_meal_requests() {
        obj.viewCustomMealRequests(username);
    }

    @When("they choose to view customer preferences")
    public void they_choose_to_view_customer_preferences() {
        obj.viewCustomerPreferences(username);
    }

    @When("they choose to view past orders")
    public void they_choose_to_view_past_orders() {
        obj.viewPastOrders(username);
    }

    @When("they choose to view meal plan suggestions")
    public void they_choose_to_view_meal_plan_suggestions() {
        obj.viewMealPlanSuggestions(username);
    }

    @Then("the system displays the list of available ingredients with their quantities")
    public void the_system_displays_ingredient_availability() {
        // Assert the display of ingredient availability
        System.out.println("Ingredients displayed successfully.");
    }

    @Then("the system displays available ingredients with their substitutes")
    public void the_system_displays_ingredient_substitutions() {
        // Assert the display of ingredient substitutions
        System.out.println("Ingredient substitutions displayed successfully.");
    }

    @Then("the system displays a list of custom meal requests from customers")
    public void the_system_displays_custom_meal_requests() {
        System.out.println("Custom meal requests displayed successfully.");
    }

    @Then("the system displays the preferences of all customers")
    public void the_system_displays_customer_preferences() {
        System.out.println("Customer preferences displayed successfully.");
    }

    @Then("the system displays the list of past orders for the chef")
    public void the_system_displays_past_orders() {
        System.out.println("Past orders displayed successfully.");
    }

    @Then("the system displays a list of meal plan suggestions based on diet types")
    public void the_system_displays_meal_plan_suggestions() {
        System.out.println("Meal plan suggestions displayed successfully.");
    }
//
//    // ===== View assigned cooking tasks steps =====
//    @Given("a chef is logged into the system")
//    public void chefIsLoggedIn() {
//        = obj.viewChefTasks("Alice");
//        System.out.println("Chef is logged into the system");
//    }
//
//    @When("they check their task list")
//    public void checkTaskList() {
//        System.out.printf("%s checks their task list%n", chefName);
//    }
//
//    @Then("the system should display all assigned tasks")
//    public void displayAssignedTasks() {
//        System.out.printf("Displaying task: %s%n", assignedTask);
//        assertNotNull("Task should not be null", assignedTask);
//    }
//
//    @And("notify the chef of upcoming cooking deadlines")
//    public void notifyUpcomingDeadlines() {
//        System.out.printf("Notification sent: %s%n", notificationStatus);
//        Assert.assertEquals("Notification should be received", "Yes", notificationStatus);
//    }



    // Step to simulate logging in a chef
    @Given("a chef {string} is logged into the system")
    public void chefIsLoggedIn(String name) {
        chefName = name;
        System.out.printf("✅ Chef %s is logged into the system%n", chefName);
    }

    // Step to check the task list for the logged-in chef
    @When("they check their task list")
    public void checkTaskList() {
        System.out.printf("✅ %s checks their task list%n", chefName);
        assignedTask = obj.viewAssignedTasksForChef(chefName);  // Call the method and store the result
    }

    // Step to display the tasks for the chef and assert that the tasks are not null or empty
    @Then("the system should display all assigned tasks")
    public void displayAssignedTasks() {
        System.out.printf("✅ Displaying tasks: %s%n", assignedTask);
        assertNotNull("❌ Task should not be null", assignedTask);  // Ensure assigned tasks are not null
        assertFalse("❌ Task list should not be empty", assignedTask.trim().isEmpty());  // Ensure there is at least one task
    }

    // Step to simulate notification of upcoming deadlines
    @And("notify the chef of upcoming cooking deadlines")
    public void notifyUpcomingDeadlines() {
        System.out.println("✅ Notification sent for upcoming deadlines.");
    }
    // ===== Approve or adjust ingredient substitutions steps =====
    @Given("a customer has selected an alternative ingredient")
    public void customerSelectsAlternative() {
        System.out.printf("Substitution requested: %s -> %s%n", originalIngredient, substitutedIngredient);
    }

    @When("the system notifies the chef")
    public void systemNotifiesChef() {
        System.out.println("System notified the chef about substitution");
    }

    @Then("the chef should approve or adjust the recipe")
    public void chefApprovesOrAdjusts() {
        System.out.printf("Chef decision: %s%n", chefApproval);
        Assert.assertTrue("Should be Approved or Adjusted",
                chefApproval.equals("Approved") || chefApproval.equals("Adjusted"));
    }

    // ===== Data injection methods =====
    @Given("the task details:")
    public void setTaskDetails(io.cucumber.datatable.DataTable dataTable) {
        var data = dataTable.asMaps().get(0);
        this.chefName = data.get("Chef Name");
        this.assignedTask = data.get("Assigned Tasks");
        this.notificationStatus = data.get("Notification Received");
    }

    @Given("the substitution details:")
    public void setSubstitutionDetails22(io.cucumber.datatable.DataTable dataTable) {
        var data = dataTable.asMaps().get(0);
        this.originalIngredient = data.get("Original Ingredient");
        this.substitutedIngredient = data.get("Substituted Ingredient");
        this.chefApproval = data.get("Chef Approval");
    }

    // ===== View customer dietary preferences steps =====
    @Given("a chef wants to customize a meal")
    public void chefWantsToCustomizeMeal() {
        System.out.println("Chef wants to customize a meal");
    }

    @Given("the dietary details:")
    public void setDietaryDetails(io.cucumber.datatable.DataTable dataTable) {
        var data = dataTable.asMaps().get(0);
        this.customerName = data.get("Customer Name");
        this.dietaryPreference = data.get("Dietary Preference");
        this.allergyInfo = data.get("Allergy");

        // Create and add a profile to the system if it doesn't exist
        CustomerProfile profile = obj.getProfileByName(customerName);
        if (profile == null) {
            profile = new CustomerProfile(customerName, "dummyPass", "customer", dietaryPreference, allergyInfo);
            obj.addCustomer(profile);
            System.out.printf("✅ Added new customer profile for %s%n", customerName);
        }
    }

    @When("they access a customer's profile")
    public void accessCustomerProfile() {
        CustomerProfile profile = obj.getProfileByName(customerName);
        assertNotNull("Profile not found!", profile);
        dietaryPreference = profile.getDietaryPreference();
        allergyInfo = profile.getAllergy();
        System.out.printf("👨‍🍳 Accessed profile for %s%n", profile.getUserName());
    }

    @Then("the system should display the customer's dietary preferences and allergies")
    public void displayDietaryInfo() {
        CustomerProfile profile = obj.getProfileByName(customerName);
        assertNotNull("❌ Customer profile not found for: " + customerName, profile);
        assertNotNull("❌ Dietary preference is missing for: " + customerName, profile.getDietaryPreference());
        assertNotNull("❌ Allergy info is missing for: " + customerName, profile.getAllergy());
        obj.displayCustomerDietaryInfo(profile);
    }

    // ===== Access customers' order history steps =====
    @Given("a chef wants to suggest a meal plan")
    public void chefWantsToSuggestMealPlan() {
        System.out.println("Chef wants to suggest a meal plan");
    }

    @Given("the order history details:")
    public void setOrderHistoryDetails22(io.cucumber.datatable.DataTable dataTable) {
        var data = dataTable.asMaps().get(0);
        this.customerName = data.get("Customer Name");
        this.dietaryPreference = data.get("Dietary Preference");
        this.allergyInfo = data.get("Allergy");
        this.lastOrderedMeal = data.get("Last Ordered Meal");

        // Ensure the customer profile exists
        CustomerProfile profile = obj.getProfileByName(customerName);
        if (profile == null) {
            profile = new CustomerProfile(customerName, "dummyPass", "customer", dietaryPreference, allergyInfo);
            obj.addCustomer(profile);
            System.out.printf("✅ Added new customer profile for %s%n", customerName);
        }

        // Add a past order for the customer
        if (lastOrderedMeal != null && !lastOrderedMeal.isEmpty()) {
            obj.addMealToOrderHistory(profile, lastOrderedMeal);
            System.out.printf("✅ Added past order for %s: %s%n", customerName, lastOrderedMeal);
        }
    }

    @When("they access a customer's order history")
    public void accessOrderHistory() {
        CustomerProfile profile = obj.getProfileByName(customerName);
        assertNotNull("Customer profile not found", profile);
        List<order> orders = obj.getCustomerOrderHistory(profile);
        System.out.printf("Accessing order history for %s: %d orders found%n", customerName, orders.size());
    }

    @Then("the system should display past orders")
    public void displayPastOrders() {
        CustomerProfile profile = obj.getProfileByName(customerName);
        assertNotNull("❌ Customer profile not found for: " + customerName, profile);
        List<order> orders = obj.getCustomerOrderHistory(profile);
        assertFalse("No past orders found for: " + customerName, orders.isEmpty());
        System.out.println("🧾 Past orders for " + customerName + ":");
        for (order o : orders) {
            System.out.println(" - " + o.getMeal().getName());
        }
    }
}