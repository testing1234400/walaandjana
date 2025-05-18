package all;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class loginsteps {
    public String Role;
    public String name;
    public String pass;


    MyApplication obj;
    public loginsteps(MyApplication obj2) {
        super();
        this.obj = obj2;
        obj.addCustomer(new CustomerProfile("wala", "wala123", "customer", "vegan","garlic"));
        obj.addChef(new chef("chef1", "grilling", "chef1pass", "chef"));
        obj.addManager(new Manager( "user88", "abc123", "kitchenManager"));

    }


    @Given("the system is ready for login")
    public void the_system_is_ready_for_login() {

        System.out.println("WELCOME TO THE APP");
        obj.iAmNotInSystem(obj);




    }
    @When("the user enters username {string} and password {string}")
    public void the_user_enters_username_and_password(String username, String password) {

        obj.setUsernameAndPassAndPassFromSystem(username, password);
    }

    @Then("login should succeed for {string}")
    public void login_should_succeed_for(String username) {
        assertTrue( obj.getValidation());
      //  assertEquals("Logged in username mismatch", username, obj.getLoggedInUserRole().getRole());
    }

    @Then("the user should be redirected to the {string} dashboard")
    public void the_user_should_be_redirected_to_the_dashboard(String expectedRole) {
        assertEquals("User role mismatch", expectedRole, obj.getLoggedInUserRole());

    }



    @When("the user enters username {string} and invalid password {string}")
    public void the_user_enters_username_and_invalid_password(String string, String string2) {
        this.name=string;
        this.pass=string2;

        obj.setUsernameAndPassAndPassFromSystem(name,pass);


    }



    @When("the user enters unkown username {string} and password {string}")
    public void the_user_enters_unkown_username_and_password(String string, String string2) {
        this.name=string;
        this.pass=string2;

        obj.setUsernameAndPassAndPassFromSystem(name,pass);

    }


    @When("the user enters empty username {string} and password {string}")
    public void the_user_enters_empty_username_and_password(String string, String string2) {
        this.name=string;
        this.pass=string2;

        obj.setUsernameAndPassAndPassFromSystem(name,pass);
    }



    @When("the user enters username {string} and empty password {string}")
    public void the_user_enters_username_and_empty_password(String string, String string2) {
        this.name=string;
        this.pass=string2;

        obj.setUsernameAndPassAndPassFromSystem(name,pass);
    }

    @When("the user enters empty username {string} and empty password {string}")
    public void the_user_enters_empty_username_and_empty_password(String string, String string2) {
        this.name=string;
        this.pass=string2;

        obj.setUsernameAndPassAndPassFromSystem(name,pass);
    }

    @Then("login should fail with message {string}")
    public void login_should_fail_with_message(String expectedMessage) {

        assertFalse("Login should fail", obj.getValidation());
        assertEquals(expectedMessage, obj.getMessage());
    }

}
