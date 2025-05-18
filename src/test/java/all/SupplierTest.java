package all;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class SupplierTest {


        private Ingredient ingredient;
        private Supplier supplier;
        private double price;


//////////////////////////////

        @Given("a supplier named {string}")
        public void givenASupplierNamed(String name) {
            supplier = new Supplier(name);
        }

        @Given("an ingredient named {string} with quantity {int} and threshold {int}")
        public void givenAnIngredientNamedWithQuantityAndThreshold(String name, int quantity, int threshold) {
            ingredient = new Ingredient(name, quantity, threshold, null);
        }

        @When("the supplier sets the price of {string} to {double}")
        public void whenTheSupplierSetsThePriceOfTo(String ingredientName, double price) {
            supplier.setPrice(ingredient, price);
            this.price = price;
        }

        @Then("the price of {string} should be {double}")
        public void thenThePriceOfShouldBe(String ingredientName, double expectedPrice) {
            double actualPrice = supplier.getPrice(ingredient);
            assertEquals("The price of the ingredient should match", expectedPrice, actualPrice, 0.001);
        }

        @When("the supplier adds the price of {string} with price {double}")
        public void whenTheSupplierAddsThePriceOfWithPrice(String ingredientName, double price) {
            supplier.addIngredientPrice(ingredient, price);
            this.price = price;
        }

        @Then("the price of {string} should be updated to {double}")
        public void thenThePriceOfShouldBeUpdatedTo(String ingredientName, double expectedPrice) {
            double actualPrice = supplier.getPrice(ingredient);
            assertEquals("The price of the ingredient should be updated", expectedPrice, actualPrice, 0.001);
        }
    @When("the supplier updates the price of {string} to {double}")
    public void theSupplierUpdatesThePriceOfTo(String ingredientName, double price) {
        // Assuming ingredient is already created, set the new price for the ingredient
        supplier.setPrice(ingredient, price);  // Update price using the setPrice method
        this.price = price;  // Store the updated price for verification in the next step
    }

}
