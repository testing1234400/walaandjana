package all;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;
import org.junit.Assert;

import java.util.Date;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class BillingStepDef {

    private MyApplication obj;
    private CustomerProfile currentCustomer;
    private String invoice;
    private String financialReport;

    public BillingStepDef(MyApplication iobj) {
        this.obj = iobj;
    }



    @Given("a customer {string} has placed orders")
    public void customerHasPlacedOrders(String customerName) {
        currentCustomer = obj.getProfileByName(customerName);
        if (currentCustomer == null) {
            currentCustomer = new CustomerProfile(customerName, "pass", "customer", "Vegan", "None");
            obj.addCustomer(currentCustomer);
        }
        obj.addMealToOrderHistory(currentCustomer, "Vegan Bowl");
        obj.addMealToOrderHistory(currentCustomer, "Quinoa Avocado Bowl");
        System.out.println("Orders placed for " + customerName);
    }

    @When("the system generates an invoice with details:")
    public void generateInvoiceWithDetails(DataTable dataTable) {
        Map<String, String> details = dataTable.asMaps().get(0);
        String customerName = details.get("Customer Name");
        String meal = details.get("Ordered Meal");
        currentCustomer = obj.getProfileByName(customerName);
        assert currentCustomer != null;
        // Ensure the meal is in order history for this customer
        obj.addMealToOrderHistory(currentCustomer, meal);
        invoice = obj.generateInvoice(currentCustomer);
        System.out.println("Generated invoice:\n" + invoice);
    }

    @Then("the customer should receive the invoice")
    public void verifyCustomerReceivesInvoice() {
        Assert.assertNotNull(invoice);
        Assert.assertTrue(invoice.contains("Invoice for") && invoice.contains("Total"));
        System.out.println("✅ Invoice received by " + currentCustomer.getUserName());
    }

    @Given("the administrator is logged in")
    public void administratorIsLoggedIn() {
       // obj.loggedInUser = new Manager("admin", "admin", "administrator"); // Simulate admin login
        System.out.println("✅ Administrator logged in");
    }

    @Given("the system has processed invoices for customers:")
    public void systemHasProcessedInvoices(DataTable dataTable) {
        for (Map<String, String> row : dataTable.asMaps()) {
            String customerName = row.get("Customer Name");
            //double amount = parseDouble(row.get("Total Amount"));
            CustomerProfile customer = obj.getProfileByName(customerName);
            if (customer == null) {
                customer = new CustomerProfile(customerName, "pass", "customer", "Vegan", "None");
                obj.addCustomer(customer);
            }
            // Simulate order to generate invoice
            obj.addMealToOrderHistory(customer, "Vegan Bowl");
            obj.generateInvoice(customer);
            //obj.customerInvoices.put(customer, amount); // Override with test amount
           // obj.totalRevenue += amount;
        }
        System.out.println("All customer invoices processed");
    }

    @When("the administrator generates a financial report")
    public void generateFinancialReport() {
        financialReport = obj.generateFinancialReport();
        System.out.println("Generated financial report:\n" + financialReport);
    }

    @Then("the system should display the financial report")
    public void verifyFinancialReport() {
        Assert.assertNotNull(financialReport);
        Assert.assertTrue(financialReport.contains("Total Revenue") && financialReport.contains("Customer Invoices"));
        System.out.println("✅ Financial report displayed");
    }
}