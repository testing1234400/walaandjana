package all;

import java.util.logging.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class MyApplication {



    ///////////////log in/////////////////////////////

    protected static final List<chef> chefs = new ArrayList<>(); // array of ches
    protected static List<Manager> managers = new ArrayList<>(); // array of managers
    public static  List<Ingredient> ingredients = new ArrayList<>(); // array of ingredients
    public static List<Supplier> suppliers = new ArrayList<>();
    private static List<CustomerProfile> customers = new ArrayList<>();// array of suppliers
    private static final List<order> pendingOrders = new ArrayList<>();
     private  static final Map<CustomerProfile, List<order>> orderHistory = new HashMap<>();

    private static final List<meal> meals=new ArrayList<>();
    private final List<String> notificationLog = new ArrayList<>();


    // Existing notification logs

    // Existing invoice and financial data
    private final Map<CustomerProfile, Double> customerInvoices = new HashMap<>();
    private double totalRevenue = 0.0;

    // New: Track current login state and user type
    private String currentUserRole;

    private String message;
    private boolean validation;

   private static Person loggedInUser;
    private boolean isLoggedIn;
    private static final String ACTION_1 = "Prepare Salad";
    private static final String ACTION_2 = "Cook Steak";
    private static final String ACTION_3 = "customer";
    private static final String ACTION_4 = "Vegetarian";
    private static final String ACTION_5 = "Vegan";
    private static final String ACTION_6 = "High Protein";
    private static final String ACTION_7 = "Chicken";
    private static final String ACTION_8 = "✅ Customer added: ";
    private static final String ACTION_9 = "❌ Invalid customer object.";

    Logger logger = Logger.getLogger(getClass().getName());


    static {
        // Create a chef for testing
        chef alice = new chef("Alice", "Italian Cuisine", "password123", "Chef");
        alice.assignTask1(ACTION_1);
        alice.assignTask1(ACTION_2);
        chefs.add(alice);
    }

    public MyApplication() {


        CustomerProfile alice = new CustomerProfile("Alice", "123", ACTION_3, ACTION_4, "Nuts");
        CustomerProfile mark  = new CustomerProfile("Mark", "1234", ACTION_3, ACTION_5, "Dairy");
        CustomerProfile emily = new CustomerProfile("Emily", "1234", ACTION_3, ACTION_4, "None");
        CustomerProfile tom   = new CustomerProfile("Tom", "1234", ACTION_3, "Low Carb", "Gluten");
        CustomerProfile jake  = new CustomerProfile("Jake", "1234", ACTION_3, ACTION_6, "Eggs");





            customers.add(alice);
            customers.add(mark);
            customers.add(emily);
            customers.add(tom);
            customers.add(jake);





        chefs.add(new chef("chef1", "grilling", "chef1pass", "chef"));
        chefs.add(new chef("chef2", ACTION_5, "chef2pass", "chef"));
        chefs.add(new chef("chef3", "baking", "chef3pass", "chef"));

        managers.add(new Manager("manager1", "manager1pass", ACTION_7));
        managers.add(new Manager("manager2", "manager2pass", ACTION_7));
        managers.add(new Manager("manager3", "manager3pass", ACTION_7));

        chefs.get(0).assignTask1(ACTION_1);
        chefs.get(0).assignTask1(ACTION_2);
        chefs.get(1).assignTask1(ACTION_1);
        chefs.get(1).assignTask1(ACTION_2);
        // 🥦 Mock ingredients
        Ingredient tomato = new Ingredient("Tomato", 20, 10, new Ingredient("Red Pepper", 10, 5, null));
        Ingredient cheese = new Ingredient("Cheese", 5, 8, new Ingredient("Vegan Cheese", 10, 5, null));
        Ingredient lettuce = new Ingredient("Lettuce", 2, 5, new Ingredient("Spinach", 10, 5, null));
        Ingredient onion = new Ingredient("Onion", 15, 10, new Ingredient("Leek", 7, 4, null));
        Ingredient garlic = new Ingredient("Garlic", 7, 5, null);
        Ingredient beef = new Ingredient("Beef", 3, 6, new Ingredient("Tofu", 15, 5, null));
        Ingredient chicken = new Ingredient(ACTION_7, 12, 8, new Ingredient("Soy Chunks", 10, 5, null));
        Ingredient flour = new Ingredient("Flour", 25, 15, new Ingredient("Oat Flour", 10, 5, null));
        Ingredient sugar = new Ingredient("Sugar", 18, 10, new Ingredient("Stevia", 8, 3, null));
        Ingredient salt = new Ingredient("Salt", 20, 10, null);
        Ingredient tofu = new Ingredient("Tofu", 15, 5, new Ingredient("Tempeh", 10, 5, null));

        ingredients.add(tomato);  ingredients.add(cheese);  ingredients.add(lettuce);  ingredients.add(onion);
        ingredients.add(garlic);  ingredients.add(beef);  ingredients.add(chicken);  ingredients.add(flour);
        ingredients.add(sugar);  ingredients.add(tofu);  ingredients.add(salt);
        Ingredient OatMilk =new Ingredient("Oat Milk", 10, 5, null);

        // Ingredients for Vegan, Vegetarian, and High Protein diets, with alternatives
        Ingredient spinach = new Ingredient("Spinach", 15, 5, new Ingredient("Kale", 10, 5, null));
        Ingredient avocado = new Ingredient("Avocado", 8, 3, new Ingredient("Hummus", 10, 5, null));
        Ingredient quinoa = new Ingredient("Quinoa", 20, 10, new Ingredient("Brown Rice", 15, 5, null));
        Ingredient chickpeas = new Ingredient("Chickpeas", 25, 10, new Ingredient("Lentils", 20, 10, null));
        Ingredient almondMilk = new Ingredient("Almond Milk", 12, 5, OatMilk);
        ingredients.add(OatMilk);

        Ingredient stevia = new Ingredient("Stevia", 18, 5, new Ingredient("Maple Syrup", 10, 5, null));
        Ingredient broccoli = new Ingredient("Broccoli", 20, 8, new Ingredient("Cauliflower", 15, 5, null));
        Ingredient oliveOil = new Ingredient("Olive Oil", 30, 10, new Ingredient("Coconut Oil", 15, 5, null));
        ingredients.add(spinach); ingredients.add(avocado); ingredients.add(quinoa); ingredients.add(chickpeas);
        ingredients.add(almondMilk); ingredients.add(stevia); ingredients.add(broccoli); ingredients.add(oliveOil);



        meal veganBowl = new meal("Vegan Bowl", List.of(tofu, lettuce, tomato), ACTION_5);
        meal beefBurger = new meal("Beef Burger", List.of(beef, onion, lettuce, salt), ACTION_6);
        meal cheesyGarlicBread = new meal("Cheesy Garlic Bread", List.of(flour, cheese, garlic), ACTION_4);
        meal chickenWrap = new meal("Chicken Wrap", List.of(chicken, tomato, lettuce, onion), ACTION_6);
        meal sweetBites = new meal("Sweet Bites", List.of(sugar, flour), ACTION_4);
        meal proteinDelight = new meal("Protein Delight", List.of(beef, chicken, garlic), ACTION_6);
        meal greenSalad = new meal("Green Salad", List.of(lettuce, tomato, onion), ACTION_5);
        meal classicToast = new meal("Classic Toast", List.of(flour, salt), ACTION_4);
        meal dietSmoothie = new meal("Diet Smoothie", List.of(sugar, salt, tomato), ACTION_5);

        meals.add(veganBowl);
        meals.add(beefBurger);
        meals.add(cheesyGarlicBread);
        meals.add(chickenWrap);
        meals.add(sweetBites);
        meals.add(proteinDelight);
        meals.add(greenSalad);
        meals.add(classicToast);
        meals.add(dietSmoothie);


        meal fruitBowl = new meal("Fruit Bowl", List.of(tomato, sugar), ACTION_5); // Simplified ingredients
        meal lentilSoup = new meal("Lentil Soup", List.of(onion, tomato), ACTION_5);
        meal glutenFreePasta = new meal("Gluten-Free Pasta", List.of(tofu, tomato), ACTION_5);
        meal proteinShake = new meal("Protein Shake", List.of(chicken, sugar), ACTION_6);

        // Vegan meals
        meal quinoaAvocadoBowl = new meal("Quinoa Avocado Bowl", List.of(quinoa, avocado, spinach, oliveOil), ACTION_5);
        meal chickpeaStirFry = new meal("Chickpea Stir Fry", List.of(chickpeas, broccoli, garlic, oliveOil), ACTION_5);

// Vegetarian meals
        meal spinachSmoothie = new meal("Spinach Smoothie", List.of(spinach, almondMilk, stevia), ACTION_4);

// High Protein meals
        meal tofuQuinoaSalad = new meal("Tofu Quinoa Salad", List.of(tofu, quinoa, broccoli, oliveOil), ACTION_6);

        meals.add(fruitBowl);
        meals.add(lentilSoup);
        meals.add(glutenFreePasta);
        meals.add(proteinShake);
        meal grilledChickenSalad = new meal("Grilled Chicken Salad", List.of(chicken, lettuce, tomato), ACTION_6);
        meals.add(grilledChickenSalad);
        meals.add(quinoaAvocadoBowl);
        meals.add(spinachSmoothie);
        meals.add(chickpeaStirFry);
        meals.add(tofuQuinoaSalad);




        // 🚚 Mock suppliers
        Supplier supplier1 = new Supplier("FreshFoods");
        supplier1.addIngredientPrice(tomato, 2.0);
        supplier1.addIngredientPrice(cheese, 5.5);
        supplier1.addIngredientPrice(garlic, 1.0);
        supplier1.addIngredientPrice(beef, 10.0);

        Supplier supplier2 = new Supplier("GreenHarvest");
        supplier2.addIngredientPrice(lettuce, 1.2);
        supplier2.addIngredientPrice(onion, 1.8);
        supplier2.addIngredientPrice(chicken,6.5);

        Supplier supplier3 = new Supplier("DailyEssentials");
        supplier3.addIngredientPrice(flour,0.9);
        supplier3.addIngredientPrice(sugar, 1.1);
        supplier3.addIngredientPrice(salt, 0.5);

        suppliers.add(supplier1);
        suppliers.add(supplier2);
        suppliers.add(supplier3);




        Admin admin = new Admin("admin1", "adminpass");


        isLoggedIn = false;

    }



    public void setUsernameAndPassAndPassFromSystem(String name, String pass) {
        // Reset state
        validation = false;
        message = "";

        // Handle empty input cases upfront
        if (name == null || name.trim().isEmpty()) {
            message = "Username cannot be empty";
            return;
        }
        if (pass == null || pass.trim().isEmpty()) {
            message = "Password cannot be empty";
            return;
        }

        // Combine all users into a single iteration
        List<Person> allUsers = new ArrayList<>();
        allUsers.addAll(chefs);
        allUsers.addAll(managers);
        allUsers.addAll(customers);

        for (Person user : allUsers) {
            if (isValidUser( user, name, pass)) {
                validation = true;
                return; // Exit after successful authentication
            }
        }

        message = "User not found";
    }

    private boolean isValidUser(Person user, String name, String pass) {
        if (user instanceof chef && ( user).getUserName().equals(name)) {
            if (( user).getPass().equals(pass)) {

                message = "Chef Found";
                return true;
            } else {
                message = "Incorrect password";
                return false;
            }
        } else if (user instanceof Manager && ( user).getUserName().equals(name)) {
            if (( user).getPass().equals(pass)) {

                message = "Manager Found";
                return true;
            } else {
                message = "Incorrect password";
                return false;
            }
        } else if (user instanceof CustomerProfile && ( user).getUserName().equals(name)) {
            if (( user).getPass().equals(pass)) {

                message = "Customer Found";
                return true;
            } else {
                message = "Incorrect password";
                return false;
            }
        }
        return false;
    }

    public String getLoggedInUserRole() {
        return (loggedInUser != null) ? loggedInUser.getRole() : null;
    }

    public boolean getValidation() {
        return validation;
    }

    public void iAmNotInSystem(MyApplication obj) {
        validation = false;
        //loggedInUser = null;
    }

    public String getMessage() {
        return message;
    }

////////////////////////////////////////////////////////////////////////

    public boolean isCustomer() {

        return loggedInUser != null && ACTION_3.equalsIgnoreCase(loggedInUser.getRole());
    }

    public void loginByNameOnly(String name) {

            for (CustomerProfile c : customers) {
                if (c.getUserName().equalsIgnoreCase(name)) {
                    loggedInUser = c;
                    System.out.println("🔐 User logged in by name: " + name);
                    return;
                }
            }
        logger.info("❌ No customer found with name: " + name);
        }


    public List<CustomerProfile> getCustomerProfiles() {
        return customers;
    }

    public void addCustomer(CustomerProfile c) {
        if (c != null && c.isValid()) {
            customers.add(c);
            logger.info(ACTION_8 + c.getUserName());
        } else {
            logger.info(ACTION_9);
        }
    }

    public void addChef(chef c) {
        if (c != null && c.isValid()) {
            chefs.add(c);
            logger.info(ACTION_8 + c.getUserName());
        } else {
            logger.info(ACTION_9);
        }
    }
    public void addManager(Manager c) {
        if (c != null && c.isValid()) {
            managers.add(c);
            logger.info(ACTION_8 + c.getUserName());
        } else {
            logger.info(ACTION_9);
        }
    }

    public CustomerProfile getProfileByName(String name) {
        for (CustomerProfile profile : customers) {
            if (profile.getUserName().equalsIgnoreCase(name)) {
                return profile;
            }
        }
        return null;
    }




    public meal getMealByName(String mealName) {
        for (meal m : meals) {
            if (m.getName().equalsIgnoreCase(mealName)) {
                return m;
            }
        }
        return null;
    }




/////////////////// kitchen manager ////////////////////

    public static void assignTaskToChef(String task, String requiredExpertise) {
        chef bestChef = null;

        for (chef chef : chefs) {
            if (chef.getExpertise().equalsIgnoreCase(requiredExpertise)) {
                if (bestChef == null || chef.getTaskCount() < bestChef.getTaskCount()) {
                    bestChef = chef;
                }
            }
        }

        if (bestChef != null) {
            bestChef.assignTask(task);
        } else {
            System.out.println("❌ No chef available with expertise: " + requiredExpertise);
        }
    }

///////////////////////////////////////////////////////////////
public static String viewAssignedTasksForChef(String chefName) {
    for (chef chef : chefs) {
        if (chef.getUserName().equalsIgnoreCase(chefName)) {
            StringBuilder tasks = new StringBuilder();
            for (String task : chef.getAssignedTasks()) {
                tasks.append(task).append("\n");
            }
            if (tasks.length() > 0) {
                return tasks.toString(); // Return the list of tasks
            } else {
                return "❌ No tasks assigned."; // Return message if no tasks are assigned
            }
        }
    }
    return "❌ Chef not found."; // Return message if chef is not found
}

    ////////////////////////////////////////////////////////////////////////////
    private Set<String> unavailableIngredients = Set.of("Peanuts", "Shellfish", "Bacon");



//    public boolean validateCustomMeal(String selectedIngredients, CustomerProfile profile) {
//
//        String[] selected = ingredients.Split(",\\s*");
//        for (String ing : selected) {
//            // 1. Check allergy
//            if (profile.getAllergy().equalsIgnoreCase(ing)) {
//                System.out.println("❌ Ingredient conflicts with allergy: " + ing);
//                return false;
//            }
//            // 2. Check stock
//            if (unavailableIngredients.contains(ing)) {
//           System.out.println("⚠️ Ingredient unavailable: " + ing);
//                return false;
//            }
//        }
//        return true;
//
//
//    }


    public double getPriceForIngredient(Ingredient ingredient) {
        for (Supplier supplier : suppliers) {
            double price = supplier.getPrice(ingredient);
            if (price >= 0) {
                return price;
            }
        }
        logger.info("⚠️ No supplier found for ingredient: " + ingredient.getName());
        return 0.0;  // or -1.0 if you want to flag it as an error
    }

    public double calculateMealPrice(List<Ingredient> ingredients) {
        double price = 0.0;
        for (Ingredient ing : ingredients) {

         //   price += ingredientPrices.getOrDefault(ing, 0.0); // assuming ingredientPrices is a Map
        }
        return price;
    }



    private void alertChef(CustomerProfile customer, Ingredient original, Ingredient substitute) {
        System.out.printf("👨‍🍳 Chef Alert: %s's order substituted %s with %s.%n", customer.getUserName(), original.getName(), substitute.getName());

    }

    public void showAllAvailableMeals(CustomerProfile customer) {
        if (meals.isEmpty()) {
            logger.info("❌ No meals available.");
            return;
        }

        logger.info("\n🍽️ Meals safe for " + customer.getUserName() + " (Allergy: " + customer.getAllergy() + "):");

        int count = 0;
        for (int i = 0; i < meals.size(); i++) {
            meal meal = meals.get(i);

            // ✅ Skip meals that contain allergens
            if (meal.containsAllergen(customer.getAllergy())) {
                continue;
            }

            double price = calculateMealPrice(meal.getIngredients());
            System.out.printf("%d. %s - $%.2f\n", ++count, meal.getName(), price);

            logger.info("   Ingredients: ");
            for (int j = 0; j < meal.getIngredients().size(); j++) {
                Ingredient ing = meal.getIngredients().get(j);
                logger.info(ing.getName());
                if (j < meal.getIngredients().size() - 1) logger.info(", ");
            }
            System.out.println(); // new line
        }

        if (count == 0) {
            logger.info("⚠️ No meals match your allergy restrictions.");
        }
    }





    // Corrected method
    public static void viewChefTasks(String chefName) {
        for (chef chef : chefs) {
            if (chef.getUserName().equalsIgnoreCase(chefName)) {
                System.out.println("📋 Tasks for " + chef.getUserName() + ":");
                for (String task : chef.getAssignedTasks()) {
                    System.out.println(" - " + task);
                }
                return;
            }
        }
        System.out.println("❌ Chef not found.");
    }


    public void completeChefTask(String username, int taskIndex) {
        chef ch = chefs.get(Integer.parseInt(username));
        if (ch != null) {
            List<String> tasks = ch.getAssignedTasks();
            if (taskIndex > 0 && taskIndex <= tasks.size()) {
                String completedTask = tasks.remove(taskIndex - 1);
                System.out.println("✅ Task completed: " + completedTask);
            } else {
                System.out.println("❌ Invalid task number.");
            }
        }
    }


    public List<chef> getChefs() {
        return chefs;
    }

    //public void setChefs(List<chef> chefs) {
     //   this.chefs = chefs;
   // }


    public Ingredient findalternative (String name ) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equals(name)) {
                    return ingredient;
                } else {
                    message = "Incorrect password";
                    return null;
                }
    }
        return null;
}

    public void useIngredient(String name, int qty) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equals(name)) {
                ingredient.reduceQuantity(qty);
            } else {
                message = "Ingredient not found";
            }
        }

    }

    public void restockIngredient(String name, int qty) {       for (Ingredient ingredient : ingredients) {
        if (ingredient.getName().equals(name)) {
            ingredient.increaseQuantity(qty);
        } else {
            message = "Ingredient not found";
        }
    }
    }
    //////////////////////////////////////////////////////


    public void displayCustomerDietaryInfo(CustomerProfile customer) {
        if (customer == null) {
            logger.info("❌ Customer not found.");
            return;
        }

        logger.info("📋 Dietary Profile for " + customer.getUserName() + ":");
        logger.info("   • Preference: " + customer.getDietaryPreference());
        logger.info("   • Allergy   : " + customer.getAllergy());
    }

    public List<Ingredient> getIngredients() { return ingredients;
    }





/// ////////////////////يا ولاء ليش معرفة ليست هون
    public static class ValidationResult {
        private final List<Ingredient> validatedIngredients;
        private final List<Substitution> substitutions;

        public static class Substitution {
            public final Ingredient original;
            public final Ingredient substitute;
            public final String reason;

            public Substitution(Ingredient original, Ingredient substitute, String reason) {
                this.original = original;
                this.substitute = substitute;
                this.reason = reason;
            }
        }

        public ValidationResult(List<Ingredient> validatedIngredients, List<Substitution> substitutions) {
            this.validatedIngredients = validatedIngredients;
            this.substitutions = substitutions;
        }

        public List<Ingredient> getValidatedIngredients() {
            return validatedIngredients;
        }

        public List<Substitution> getSubstitutions() {
            return substitutions;
        }
    }

    public ValidationResult validateIngredients(List<Ingredient> selected, CustomerProfile customer) {
        List<Ingredient> finalList = new ArrayList<>();
        List<ValidationResult.Substitution> substitutions = new ArrayList<>();

        for (Ingredient ing : selected) {
            boolean unavailable = ing.getQuantity() < ing.getThreshold();
            boolean allergic = ing.getName().equalsIgnoreCase(customer.getAllergy());

            if (unavailable || allergic) {
                if (ing.getAlternative() != null) {
                    String reason = allergic ? "allergen" : "out of stock";
                    substitutions.add(new ValidationResult.Substitution(ing, ing.getAlternative(), reason));
                    alertChef(customer, ing, ing.getAlternative());
                    finalList.add(ing.getAlternative());
                } else {
                    System.out.printf("❌ No substitute available for '%s'. Removing it.%n", ing.getName());
                }
            } else {
                finalList.add(ing);
            }
        }

        return new ValidationResult(finalList, substitutions);
    }

    // New: Validate a custom meal based on a comma-separated ingredient string
    public ValidationResult validateCustomMeal(String ingredientList, CustomerProfile profile) {
        List<Ingredient> selectedIngredients = Arrays.stream(ingredientList.split(",\\s*"))
                .map(this::findIngredient)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (selectedIngredients.isEmpty()) {
            logger.info("❌ No valid ingredients found in: " + ingredientList);
            return new ValidationResult(List.of(), List.of());
        }

        // Validate dietary preference (e.g., ensure all ingredients are compatible)
        boolean dietaryMismatch = selectedIngredients.stream()
                .anyMatch(ing -> !isIngredientCompatibleWithDietaryPreference(ing, profile.getDietaryPreference()));
        if (dietaryMismatch) {
            logger.info("❌ Ingredients do not match dietary preference: " + profile.getDietaryPreference());
            return new ValidationResult(List.of(), List.of());
        }

        // Use existing validateIngredients for allergy and stock checks
        return validateIngredients(selectedIngredients, profile);
    }

    // Helper for validateCustomMeal
    private Ingredient findIngredient(String name) {
        return ingredients.stream()
                .filter(ing -> ing.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    // Helper for dietary preference check (simplified, can be expanded)
    private boolean isIngredientCompatibleWithDietaryPreference(Ingredient ingredient, String dietaryPreference) {
        // Example logic: reject non-vegan ingredients for vegan preference
        if (dietaryPreference.equalsIgnoreCase(ACTION_5)) {
            return !List.of("Beef", ACTION_7, "Cheese").contains(ingredient.getName());
        } else if (dietaryPreference.equalsIgnoreCase(ACTION_4)) {
            return !List.of("Beef", ACTION_7).contains(ingredient.getName());
        }
        return true; // Default: assume compatible for other preferences
    }

    // New: Handle ingredient substitution with customer approval
    public boolean handleIngredientSubstitution(Ingredient original, Ingredient substitute, CustomerProfile customer, boolean approved) {
        if (!approved) {
            System.out.printf("❌ Customer %s rejected substitution: %s -> %s%n", customer.getUserName(), original.getName(), substitute.getName());
            return false;
        }

        // Simulate updating the order with the substitute
        System.out.printf("✅ Customer %s approved substitution: %s -> %s%n", customer.getUserName(), original.getName(), substitute.getName());
        // Could update pendingOrders or create a new order with the substitute
        return true;
    }

    // New: Get order history for a specific customer
    public List<order> getCustomerOrderHistory(CustomerProfile customer) {
        return orderHistory.getOrDefault(customer, new ArrayList<>());
    }

    // New: Send a notification (mock implementation)
    public void sendNotification(CustomerProfile customer, String message) {
        String notification = String.format("Notification to %s: %s", customer.getUserName(), message);
        notificationLog.add(notification);
        logger.info("📩 " + notification);
    }

    // Helper: Check if a notification was sent (for testing)
    public boolean hasNotificationForCustomer(CustomerProfile customer, String message) {
        String expected = String.format("Notification to %s: %s", customer.getUserName(), message);
        return notificationLog.contains(expected);
    }

    // Existing methods (unchanged or partially shown for context)
    public void addMealToOrderHistory(CustomerProfile customer, String mealName) {
        meal matchedMeal = meals.stream()
                .filter(m -> m.getName().equalsIgnoreCase(mealName))
                .findFirst()
                .orElse(null);

        if (matchedMeal == null) {
            logger.info("⚠️ Meal not found: " + mealName);
            return;
        }

        orderHistory.putIfAbsent(customer, new ArrayList<>());
        orderHistory.get(customer).add(new order(customer, matchedMeal));
        System.out.printf("✅ Order added to %s's history: %s\n", customer.getUserName(), mealName);
    }

    public List<meal> getFilteredSuggestedMeals(CustomerProfile profile) {
        return meals.stream()
                .filter(m -> !m.containsAllergen(profile.getAllergy()))
                .filter(m -> m.getDietaryCategory().equalsIgnoreCase(profile.getDietaryPreference()))
                .collect(Collectors.toList());
    }

    public void addToPendingOrders(CustomerProfile customer, meal meal) {
        pendingOrders.add(new order(customer, meal));
        logger.info("⚠️ Order added to pending list. Please confirm it before submission.");
    }

    public List<order> getPendingOrdersForCustomer(CustomerProfile customer) {
        return pendingOrders.stream()
                .filter(order -> order.getCustomer().equals(customer))
                .collect(Collectors.toList());
    }
    public List<meal> getMeals() {
        return new ArrayList<>(meals); // Return a copy to prevent external modification
    }

    // Method to view ingredient availability
    public void viewIngredientAvailability(String username) {
        logger.info("\n🌿 Ingredient Availability:");
        for (Ingredient ingredient : ingredients) {
            logger.info("🔹 " + ingredient.getName() + ": " + ingredient.getQuantity() + " units");
        }
    }


/// //////////////////////////////////////////////////////
    // Method to suggest ingredient substitutions
    public void suggestIngredientSubstitutions(String username) {
        logger.info("\n🔄 Ingredient Substitutions:");
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getAlternative() != null) {
                logger.info("🔹 " + ingredient.getName() + " ➡️ " + ingredient.getAlternative().getName());
            }
        }
    }

    // Method to view customer preferences
    public void viewCustomerPreferences(String username) {
        logger.info("\n👥 Customer Preferences:");
        for (CustomerProfile customer : customers) {
            logger.info("🔹 " + customer.getUserName() + " prefers: " + customer.getDietaryPreference());
        }
    }
    // Method to view custom meal requests
    public void viewCustomMealRequests(String username) {
        logger.info("\n🍽️ Custom Meal Requests:");
        for (CustomerProfile customer : customers) {
            System.out.println("🔹 " + customer.getUserName() + " requested a custom meal with preferences: " + customer.getDietaryPreference());
        }
    }
    // Method to view past orders
    public void viewPastOrders(String username) {
        logger.info("\n📜 Past Orders:");
        if (orderHistory.containsKey(username)) {
            for (order ord : orderHistory.get(username)) {
                logger.info("🔹 " + ord.toString());
            }
        } else {
            logger.info("❌ No past orders found.");
        }
    }

    // Method to view meal plan suggestions
    public void viewMealPlanSuggestions(String username) {
        logger.info("\n🍽️ Meal Plan Suggestions:");
        for (meal meal : meals) {
            System.out.println("🔹 " + meal.getName() + " (" + meal.getDietaryCategory() + ")");
        }
    }



    public String generateInvoice(CustomerProfile customer) {
        List<order> customerOrders = orderHistory.getOrDefault(customer, new ArrayList<>());
        double total = 0.0;
        StringBuilder invoice = new StringBuilder();
        invoice.append("Invoice for ").append(customer.getUserName()).append(" (Date: ")
                .append(new Date()).append(")\n");
        invoice.append("--------------------------------\n");
        for (order o : customerOrders) {
            double mealCost = 10.0; // Placeholder cost per meal
            total += mealCost;
            invoice.append("- ").append(o.getMeal().getName()).append(": $").append(mealCost).append("\n");
        }
        invoice.append("Total: $").append(String.format("%.2f", total)).append("\n");
        customerInvoices.put(customer, total);
        totalRevenue += total;
        return invoice.toString();
    }

    // Existing: Generate financial report
    public String generateFinancialReport() {
        StringBuilder report = new StringBuilder();
        report.append("Financial Report (Date: ").append(new Date()).append(")\n");
        report.append("--------------------------------\n");
        report.append("Total Revenue: $").append(String.format("%.2f", totalRevenue)).append("\n");
        report.append("Customer Invoices:\n");
        for (Map.Entry<CustomerProfile, Double> entry : customerInvoices.entrySet()) {
            report.append("- ").append(entry.getKey().getUserName()).append(": $")
                    .append(String.format("%.2f", entry.getValue())).append("\n");
        }
        return report.toString();
    }

    // Existing: Send reminder to customer for upcoming delivery
    public void sendDeliveryReminder(CustomerProfile customer, String mealName, Date deliveryDate) {
        String message = String.format("Reminder: Your %s delivery is scheduled for %s. Be prepared!",
                mealName, deliveryDate);
        notificationLog.add(message);
        logger.info("📩 " + message + " to " + customer.getUserName());
    }


    // New: Login method to set the logged-in user and role
    public void loginUser(Person user, String role) {
        this.loggedInUser = user;
        this.currentUserRole = role;
        this.isLoggedIn = true;
        System.out.println("🔐 User " + user.getUserName() + " logged in as " + role);
    }
//
    // New: Logout method to clear the logged-in state
    public void logoutUser() {
        this.loggedInUser = null;
        this.currentUserRole = null;
        this.isLoggedIn = false;
        System.out.println("🔓 User logged out");
    }
//
    // New: Get the currently logged-in user
    public Person getLoggedInUser() {
        return loggedInUser;
    }
//
//    // New: Get the current user role
    public String getCurrentUserRole() {
        return currentUserRole;
    }


    public List<String> getNotificationLog() {
        return new ArrayList<>(notificationLog);
    }








}