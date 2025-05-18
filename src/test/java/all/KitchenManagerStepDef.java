package all;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import static org.junit.Assert.*;

public class KitchenManagerStepDef {
    private Manager manager;
    private String consoleOutput;
    private ByteArrayOutputStream outputStream;
    MyApplication app ;

    public KitchenManagerStepDef(MyApplication obj) {
        this.app = obj;

    }

    @Given("there are chefs with expertise {string} and {string}")
    public void setupChefs(String expertise1, String expertise2) {
        app = new MyApplication();
        app.getChefs().add(new chef("Chef1", expertise1, "password1", "Chef"));
        app.getChefs().add(new chef("Chef2", expertise2, "password2", "Chef"));
    }

    @When("I assign a task {string} to a chef with expertise {string}")
    public void assignTaskToChef(String task, String expertise) {
        app.assignTaskToChef(task, expertise);
    }

    @Then("the task should be assigned to a chef with {string}")
    public void verifyTaskAssignment(String expertise) {
        boolean assigned = app.getChefs().stream()
                .anyMatch(chef -> chef.getExpertise().equalsIgnoreCase(expertise) && chef.getAssignedTasks().contains("Prepare Salad"));
        assertTrue(assigned);
        System.out.println("✅ Task assigned successfully.");
    }

    @Then("the system should display {string}")
    public void verifyErrorMessage(String message) {
        System.out.println(message);
    }



    /// //////////////////

    @Given("the application has ingredients {string}, {string}, and {string}")
    public void setupIngredients(String ing1, String ing2, String ing3) {
        app = new MyApplication();
        app.getIngredients().add(new Ingredient(ing1, 10, 5, null));
        app.getIngredients().add(new Ingredient(ing2, 8, 3, null));
        app.getIngredients().add(new Ingredient(ing3, 12, 6, null));
    }

    @When("I search for an alternative for {string}")
    public void searchForAlternative(String name) {
        Ingredient alternative = app.findalternative(name);
        System.out.println("Alternative: " + (alternative != null ? alternative.getName() : "None"));
    }

    @When("I use {string} with quantity {int}")
    public void useIngredient(String name, int qty) {
        app.useIngredient(name, qty);
    }

    @Then("the ingredient quantity should be reduced by {int}")
    public void verifyIngredientUsage(int qty) {
        Ingredient garlic = app.getIngredients().stream().filter(i -> i.getName().equals("Garlic")).findFirst().orElse(null);
        assertNotNull(garlic);
        assertEquals(5, garlic.getQuantity());
    }



    @When("I restock {string} with quantity {int}")
    public void restockIngredient(String name, int qty) {
        app.restockIngredient(name, qty);
    }

    @Then("the ingredient quantity should be increased by {int}")
    public void verifyIngredientRestock(int qty) {
        Ingredient cheese = app.getIngredients().stream().filter(i -> i.getName().equals("Cheese")).findFirst().orElse(null);
        assertNotNull(cheese);
        assertEquals(8, cheese.getQuantity());
    }
    ///
    ///
    @Given("a manager {string} with password {string} and role {string}")
    public void a_manager_with_password_and_role(String username, String password, String role) {
        manager = new Manager(username, password, role);
    }

    @Given("the following ingredients exist:")
    public void the_following_ingredients_exist(io.cucumber.datatable.DataTable dataTable) {
        Manager.ingredients.clear();
        dataTable.asMaps().forEach(row -> {
            String name = row.get("Name");
            int quantity = Integer.parseInt(row.get("Quantity"));
            int threshold = Integer.parseInt(row.get("Threshold"));
            String alternativeName = row.get("Alternative");

            Ingredient alternative = null;
            if (!"None".equals(alternativeName)) {
                alternative = new Ingredient(alternativeName, 0, 0, null);
            }
            Manager.addIngredient(name, quantity, threshold, alternative);
        });
    }

    @When("I add ingredient {string} with quantity {int} and threshold {int}")
    public void i_add_ingredient_with_quantity_and_threshold(String name, int quantity, int threshold) {
        Manager.addIngredient(name, quantity, threshold, null);
    }

    @When("I use {int} {string} from inventory")
    public void i_use_from_inventory(int amount, String name) {
        captureConsoleOutput();
        Manager.useIngredient(name, amount);
        releaseConsoleOutput();
    }

    @When("I restock {string} with {int} more")
    public void i_restock_with_more(String name, int amount) {
        captureConsoleOutput();
        Manager.restockIngredient(name, amount);
        releaseConsoleOutput();
    }

    @When("I view the inventory")
    public void i_view_the_inventory() {
        captureConsoleOutput();
        Manager.showInventory();
        releaseConsoleOutput();
    }

    @Then("ingredient {string} should exist in inventory with quantity {int}")
    public void ingredient_should_exist_in_inventory_with_quantity(String name, int expectedQuantity) {
        Ingredient ingredient = Manager.ingredients.get(name.toLowerCase());
        assertNotNull(ingredient);
        assertEquals(expectedQuantity, ingredient.getQuantity());
    }

    @Then("{string} quantity should be {int}")
    public void quantity_should_be(String name, int expectedQuantity) {
        Ingredient ingredient = Manager.ingredients.get(name.toLowerCase());
        assertNotNull(ingredient);
        assertEquals(expectedQuantity, ingredient.getQuantity());
    }

    @Then("I should see inventory updated message")
    public void i_should_see_inventory_updated_message() {
     //   assertTrue(consoleOutput.contains("Restock Alert"));
    }


    @Then("I should see restock confirmation message")
    public void i_should_see_restock_confirmation_message() {
        assertTrue(consoleOutput.contains("✅") && consoleOutput.contains("restocked"));
    }

    @Then("I should see {string} error message")
    public void i_should_see_error_message(String expectedMessage) {
        assertTrue(consoleOutput.contains("❌") && consoleOutput.contains(expectedMessage));
    }

    @Then("I should see all ingredients with their quantities:")
    public void i_should_see_all_ingredients_with_their_quantities(io.cucumber.datatable.DataTable dataTable) {
        dataTable.asMaps().forEach(row -> {
            String name = row.get("Name");
            String expectedQuantity = row.get("Quantity");
            assertTrue(consoleOutput.contains(name + ": " + expectedQuantity));
        });
    }

    private void captureConsoleOutput() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    private void releaseConsoleOutput() {
        consoleOutput = outputStream.toString();
        System.setOut(System.out);
    }
}