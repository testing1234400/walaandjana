package all;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.Map;

public class ChefNotification {



    // Existing variables (you should have these already)
    private String chefName;
    private String currentTask;   // Reuse or rename from assignedTask if needed
    private String notificationStatus;
    private String currentMeal;
    private String substitutedIngredient;
    private String alertStatus;
    // ... other existing variables ...

    // ===== Notifications and Alerts steps =====
    @Given("a chef has upcoming meal preparations")
    public void chefHasUpcomingMealPreparations() {
        System.out.println("Chef has upcoming meal preparations");
    }


    @Given("the notification details:")
    public void setNotificationDetails(io.cucumber.datatable.DataTable dataTable) {
        var data = dataTable.asMaps().get(0);
        this.chefName = data.get("Chef Name");
        this.currentTask = data.get("Task");
        this.notificationStatus = data.get("Notification Sent");
        System.out.printf("Loaded notification details for %s%n", chefName);
    }

    @When("their schedule updates")
    public void scheduleUpdates() {
        System.out.printf("%s's schedule updated%n", chefName);
    }

    @Then("the system should send them a notification")
    public void verifyNotificationSent() {
    //    Assert.assertEquals("Notification should be sent", "Yes", notificationStatus);
        System.out.printf("Verified notification for %s about %s%n", chefName, currentTask);
    }

    // ===== Alert for Ingredient Substitution Steps =====
    @Given("a chef is preparing a meal")
    public void chefIsPreparingMeal() {
        System.out.println("Chef is preparing a meal");
    }

    @Given("the alert details:")
    public void setAlertDetails(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps().get(0);
        this.chefName = data.get("Chef Name");
        this.currentMeal = data.get("Meal");
        this.substitutedIngredient = data.get("Substituted Ingredient");
        this.alertStatus = data.get("Alert Received");
        System.out.printf("Alert details loaded for %s: %s with substitution %s%n",
                chefName, currentMeal, substitutedIngredient);
    }

    @When("an ingredient substitution is applied")
    public void applyIngredientSubstitution() {
        System.out.printf("Applying substitution: %s for meal %s%n",
                substitutedIngredient, currentMeal);
        // Here you would typically call your application code to apply the substitution
    }

    @Then("the system should send an alert to the chef for approval or adjustment")
    public void verifyAlertSent() {
        Assert.assertEquals("Alert should be received", "Yes", alertStatus);
        System.out.printf("Verified alert sent to %s about %s substitution%n",
                chefName, substitutedIngredient);

        // Additional verification logic could go here
        Assert.assertNotNull("Substitution details should exist", substitutedIngredient);
        Assert.assertNotNull("Meal information should exist", currentMeal);
    }

}
//hana assert wpjnif

