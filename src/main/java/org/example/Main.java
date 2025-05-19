package org.example;

import all.*;

import java.util.List;
import java.util.*;
public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        MyApplication app = new MyApplication();

        while (true) { // Infinite loop
            System.out.println("╔═══════════════════════════════════════════════════════════════════════╗");
            System.out.println("║        Welcome to the Special Cook Project Management System          ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════════╝");

            // Login Process
            System.out.println("╔═══════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                               Login page hi                             ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════════╝");

            System.out.print("👤 Enter username: ");
            String username = scanner.nextLine();
            System.out.print("🔑 Enter password: ");
            String password = scanner.nextLine();

            app.setUsernameAndPassAndPassFromSystem(username, password);

            if (!app.getValidation()) {
                System.out.println("❌ Invalid username or password.");
                continue; // Restart the loop
            }


            String role = app.getLoggedInUserRole();

            if (role.equalsIgnoreCase("chef")) {
                chefMenu(app, scanner, username);
            } else if (role.equalsIgnoreCase("manager")) {
                kitchenManagerMenu(app, scanner);

            } else if (role.equalsIgnoreCase("customer")) {
                customerMenu(app,scanner,username);

            } else {
                System.out.println("❌ Unknown role. Access denied.");
            }
        }
    }



   private static void chefMenu(MyApplication app, Scanner scanner, String username) {
   //     System.out.println("\n👨‍🍳 Chef Menu 👨‍🍳");
       while (true) {
       // Login Process
       System.out.println("╔═══════════════════════════════════════════════════════════════════════╗");
       System.out.println("                           👨‍🍳 Chef Menu 👨‍🍳                               ");
       System.out.println("╚═══════════════════════════════════════════════════════════════════════╝");


            System.out.println("1️⃣ View Assigned Tasks");
            System.out.println("2️⃣ Update Task Status");
            System.out.println("3️⃣ Logout");
            System.out.println("4️⃣ View Custom Meal Requests");
            System.out.println("5️⃣ View Ingredient Availability");
            System.out.println("6️⃣ Suggest Ingredient Substitutions");
            System.out.println("7️⃣ View Customer Preferences");
            System.out.println("8️⃣ View Past Orders");
            System.out.println("9️⃣ View Meal Plan Suggestions");

            System.out.print("\n👨‍🍳 Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> app.viewChefTasks(username);
                case 2 -> {
                    System.out.print("✏️ Enter task number to mark as complete: ");
                    int taskIndex = scanner.nextInt();
                    scanner.nextLine();
                    app.completeChefTask(username, taskIndex);
                }
                case 3 -> {
                    System.out.println("👋 Logging out...");
                    return;
                }
                case 4 -> app.viewCustomMealRequests(username);
                case 5 -> app.viewIngredientAvailability(username);
                case 6 -> app.suggestIngredientSubstitutions(username);
               case 7 -> app.viewCustomerPreferences(username);
                case 8 -> app.viewPastOrders(username);
                case 9 -> app.viewMealPlanSuggestions(username);
                default -> System.out.println("❌ Invalid option. Try again.");
            }
        }
    }

    private static void kitchenManagerMenu(MyApplication app, Scanner scanner) {
        System.out.println("\n🍽️ Kitchen Manager Menu 🍽️");
        // Login Process
        System.out.println("╔═══════════════════════════════════════════════════════════════════════╗");
        System.out.println("                      🍽️ Kitchen Manager Menu 🍽️                        ");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════╝");


        while (true) {
            System.out.println("1️⃣ View Inventory");
            System.out.println("2️⃣ Add Ingredient");
            System.out.println("3️⃣ Use Ingredient");
            System.out.println("4️⃣ Restock Ingredient");
            System.out.println("5️⃣ Assign Task to Chef");
            System.out.println("6️⃣ View Chef Tasks");
            System.out.println("7️⃣ Logout");

            System.out.print("🍽️ Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
            //    case 1 -> app.showInventory();
                case 2 -> {
                    System.out.print("📝 Ingredient Name: ");
                    String name = scanner.nextLine();
                    System.out.print("📦 Quantity: ");
                    int qty = scanner.nextInt();
                    System.out.print("⚠️ Threshold: ");
                    int threshold = scanner.nextInt();
                    System.out.print("📝 Ingredient alternative: ");
                    String alternative1 = scanner.nextLine();
                    scanner.nextLine();
                    app.ingredients.add(new Ingredient(name,qty,threshold,app.findalternative(alternative1)));

                }
                case 3 -> {
                    System.out.print("📝 Ingredient Name: ");
                    String name = scanner.nextLine();
                    System.out.print("📉 Quantity to use: ");
                    int qty = scanner.nextInt();
                    scanner.nextLine();
                    app.useIngredient(name, qty);
                }
                case 4 -> {
                    System.out.print("📝 Ingredient Name: ");
                    String name = scanner.nextLine();
                    System.out.print("📈 Quantity to restock: ");
                    int qty = scanner.nextInt();
                    scanner.nextLine();
                    app.restockIngredient(name, qty);
                }
                case 5 -> {
                    // Fetch and display the available chefs
                    List<chef> availableChefs = app.getChefs(); // Assuming this returns a list of chef objects
                    if (availableChefs.isEmpty()) {
                        System.out.println("❌ No available chefs.");
                        break;
                    }

                    // Display the available chefs by name
                    System.out.print("👨‍🍳 Available Chefs: ");
                    availableChefs.forEach(chef -> System.out.print(chef.getUserName() + " ")); // Assuming the chef class has a getName() method
                    System.out.println();

                    String chefName = "";
                    boolean validChef = false;

                    // Get chef name and validate input
                    while (!validChef) {
                        System.out.print("👨‍🍳 Enter Chef Name: ");
                        chefName = scanner.nextLine();

                        // Check if the entered chef name exists in the list
                        String finalChefName1 = chefName;
                        validChef = availableChefs.stream()
                                .anyMatch(chef -> chef.getUserName().equalsIgnoreCase(finalChefName1));

                        if (!validChef) {
                            System.out.println("❌ Chef not found. Please try again.");
                        }
                    }

                    // Get task description
                    System.out.print("📝 Task Description: ");
                    String task = scanner.nextLine();

                    // Find the selected chef by name
                    String finalChefName = chefName;
                    chef selectedChef = availableChefs.stream()
                            .filter(chef -> chef.getUserName().equalsIgnoreCase(finalChefName))
                            .findFirst()
                            .orElse(null);

                    // Assign task to the selected chef
                    if (selectedChef != null) {
                        app.assignTaskToChef(String.valueOf(selectedChef), task);
                        System.out.println("✅ Task successfully assigned to " + selectedChef.getUserName());
                    } else {
                        System.out.println("❌ Error: Chef not found during task assignment.");
                    }
                }

                case 6 -> {
                    // Assuming you want to print tasks for all chefs
                    List<chef> allChefs = app.getChefs(); // Get the list of all chefs (assuming app.getChefs() returns a list of chefs)

                    if (allChefs.isEmpty()) {
                        System.out.println("❌ No chefs available.");
                    } else {
                        for (int i = 0; i < allChefs.size(); i++) {
                            chef ch = allChefs.get(i);
                            System.out.println("👨‍🍳 Tasks for Chef: " + ch.getUserName());
                            app.viewChefTasks(String.valueOf(i)); // Call viewChefTasks with the chef index (or unique identifier)
                            System.out.println(); // Add a blank line for better readability between chefs
                        }
                    }
                }

                case 7 -> {
                    System.out.println("👋 Logging out...");
                    return;
                }
                default -> System.out.println("❌ Invalid option. Try again.");
            }
        }
    }

    private static void customerMenu(MyApplication app, Scanner scanner, String username) {
        System.out.println("\n👤 Customer Dashboard 👤");
        CustomerProfile customer = app.getProfileByName(username);
        if (customer == null) {
            System.out.println("❌ Customer profile not found. Logging out...");
            return;
        }

        double totalPrice = 0.0;

        while (true) {
            System.out.println("1️⃣ Set Dietary Preferences and Allergies");
            System.out.println("2️⃣ View Past Orders and Reorder");
            System.out.println("3️⃣ View Suggested Meals and Reorder");
            System.out.println("4️⃣ Customize Meal");
            System.out.println("5️⃣ View Pending Orders");
            System.out.println("6️⃣ View Upcoming Meal Notifications");
            System.out.println("7️⃣ Logout");
            System.out.printf("💰 Total Price: $%.2f%n", totalPrice);

            System.out.print("👤 Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.println("🍽️ Set Your Dietary Preferences and Allergies:");
                    // List of dietary preferences
                    String[] dietaryOptions = {"Vegetarian", "Vegan", "High Protein", "Low Carb", "None"};
                    System.out.println("Dietary Preferences:");
                    for (int i = 0; i < dietaryOptions.length; i++) {
                        System.out.println(" " + (i + 1) + ". " + dietaryOptions[i]);
                    }
                    System.out.print("📝 Choose a dietary preference (1-" + dietaryOptions.length + "): ");
                    int dietChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (dietChoice < 1 || dietChoice > dietaryOptions.length) {
                        System.out.println("❌ Invalid selection. Keeping current preference.");
                    } else {
                        String selectedDiet = dietaryOptions[dietChoice - 1];
                        customer.setDietaryPreference(selectedDiet);
                        System.out.println("✅ Dietary Preference set to: " + selectedDiet);
                    }

                    // List of allergies
                    String[] allergyOptions = {"Nuts", "Dairy", "Gluten", "Eggs", "None"};
                    System.out.println("Allergies:");
                    for (int i = 0; i < allergyOptions.length; i++) {
                        System.out.println(" " + (i + 1) + ". " + allergyOptions[i]);
                    }
                    System.out.print("📝 Choose an allergy (1-" + allergyOptions.length + "): ");
                    int allergyChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (allergyChoice < 1 || allergyChoice > allergyOptions.length) {
                        System.out.println("❌ Invalid selection. Keeping current allergy.");
                    } else {
                        String selectedAllergy = allergyOptions[allergyChoice - 1];
                        customer.setAllergy(selectedAllergy);
                        System.out.println("✅ Allergy set to: " + selectedAllergy);
                    }
                }
                case 2 -> {
                    System.out.println("📜 Your Past Orders:");
                    List<order> orders = app.getCustomerOrderHistory(customer);
                    if (orders.isEmpty()) {
                        System.out.println(" - No past orders found.");
                    } else {
                        while (true) {
                            for (int i = 0; i < orders.size(); i++) {
                                order ord = orders.get(i);
                                double mealPrice = app.calculateMealPrice(ord.getMeal().getIngredients());
                                System.out.printf(" %d. %s - $%.2f%n", (i + 1), ord.getMeal().getName(), mealPrice);
                            }
                            System.out.println(" 0. Return to Menu");
                            System.out.print("📝 Enter the number of the meal to reorder (0 to exit): ");
                            int mealIndex = scanner.nextInt();
                            scanner.nextLine();
                            if (mealIndex == 0) break;
                            if (mealIndex < 1 || mealIndex > orders.size()) {
                                System.out.println("❌ Invalid selection.");
                                continue;
                            }
                            order selectedOrder = orders.get(mealIndex - 1);
                            meal selectedMeal = selectedOrder.getMeal();
                            app.addToPendingOrders(customer, selectedMeal);
                            double mealPrice = app.calculateMealPrice(selectedMeal.getIngredients());
                            totalPrice += mealPrice;
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(new Date());
                            cal.add(Calendar.DATE, 1); // Set delivery for tomorrow
                            app.sendDeliveryReminder(customer, selectedMeal.getName(), cal.getTime());
                            System.out.println("✅ '" + selectedMeal.getName() + "' added to pending orders.");
                        }
                    }
                }
                case 3 -> {
                    System.out.println("🍽️ Suggested Meals (based on your preferences and allergies):");
                    List<meal> suggestedMeals = app.getFilteredSuggestedMeals(customer);
                    if (suggestedMeals.isEmpty()) {
                        System.out.println(" - No suggested meals available.");
                    } else {
                        while (true) {
                            for (int i = 0; i < suggestedMeals.size(); i++) {
                                meal m = suggestedMeals.get(i);
                                double mealPrice = app.calculateMealPrice(m.getIngredients());
                                System.out.printf(" %d. %s (%s) - $%.2f%n", (i + 1), m.getName(), m.getDietaryCategory(), mealPrice);
                            }
                            System.out.println(" 0. Return to Menu");
                            System.out.print("📝 Enter the number of the meal to reorder (0 to exit): ");
                            int mealIndex = scanner.nextInt();
                            scanner.nextLine();
                            if (mealIndex == 0) break;
                            if (mealIndex < 1 || mealIndex > suggestedMeals.size()) {
                                System.out.println("❌ Invalid selection.");
                                continue;
                            }
                            meal selectedMeal = suggestedMeals.get(mealIndex - 1);
                            app.addToPendingOrders(customer, selectedMeal);
                            double mealPrice = app.calculateMealPrice(selectedMeal.getIngredients());
                            totalPrice += mealPrice;
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(new Date());
                            cal.add(Calendar.DATE, 1); // Set delivery for tomorrow
                            app.sendDeliveryReminder(customer, selectedMeal.getName(), cal.getTime());
                            System.out.println("✅ '" + selectedMeal.getName() + "' added to pending orders.");
                        }
                    }
                }
                case 4 -> {
                    System.out.println("🍲 Customize Your Meal:");
                    System.out.println("📋 Available Ingredients:");
                    List<Ingredient> availableIngredients = app.getIngredients();
                    for (int i = 0; i < availableIngredients.size(); i++) {
                        Ingredient ing = availableIngredients.get(i);
                        System.out.println(" " + (i + 1) + ". " + ing.getName() + " (Qty: " + ing.getQuantity() + ")");
                    }
                    System.out.println("📝 Enter ingredient numbers (one per line, type 'done' to finish):");
                    List<Ingredient> selectedIngredients = new ArrayList<>();
                    while (true) {
                        String input = scanner.nextLine();
                        if (input.equalsIgnoreCase("done")) break;
                        try {
                            int index = Integer.parseInt(input) - 1;
                            if (index >= 0 && index < availableIngredients.size()) {
                                selectedIngredients.add(availableIngredients.get(index));
                            } else {
                                System.out.println("❌ Invalid ingredient number.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("❌ Please enter a valid number.");
                        }
                    }
                    if (selectedIngredients.isEmpty()) {
                        System.out.println("❌ No ingredients selected.");
                        break;
                    }
                    MyApplication.ValidationResult result = app.validateIngredients(selectedIngredients, customer);
                    List<Ingredient> validatedIngredients = result.getValidatedIngredients();
                    if (!result.getSubstitutions().isEmpty()) {
                        System.out.println("⚠️ Substitutions suggested:");
                        for (MyApplication.ValidationResult.Substitution sub : result.getSubstitutions()) {
                            System.out.println(" - " + sub.original.getName() + " (out of stock/allergen) → " + sub.substitute.getName());
                        }
                    }
                    meal customMeal = new meal("Custom Meal for " + customer.getUserName(), validatedIngredients, customer.getDietaryPreference());
                    app.addToPendingOrders(customer, customMeal);
                    double mealPrice = app.calculateMealPrice(customMeal.getIngredients());
                    totalPrice += mealPrice;
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    cal.add(Calendar.DATE, 1); // Set delivery for tomorrow
                    app.sendDeliveryReminder(customer, customMeal.getName(), cal.getTime());
                    System.out.println("✅ '" + customMeal.getName() + "' added to pending orders.");
                }
                case 5 -> {
                    System.out.println("⏳ Pending Orders:");
                    List<order> pending = app.getPendingOrdersForCustomer(customer);
                    if (pending.isEmpty()) {
                        System.out.println(" - No pending orders.");
                    } else {
                        double pendingTotal = 0.0;
                        for (order o : pending) {
                            double mealPrice = app.calculateMealPrice(o.getMeal().getIngredients());
                            boolean hasSubstitution = o.getMeal().getIngredients().stream()
                                    .anyMatch(ing -> ing.getAlternative() != null && ing.getQuantity() < ing.getThreshold());
                            System.out.printf(" - %s - $%.2f %s%n", o.getMeal().getName(), mealPrice,
                                    hasSubstitution ? "(Awaiting chef approval due to substitution)" : "");
                            pendingTotal += mealPrice;
                        }
                        System.out.printf("💰 Pending Total Price: $%.2f%n", pendingTotal);
                    }
                }
                case 6 -> {
                    System.out.println("🔔 Upcoming Meal Notifications:");
                    List<String> notifications = app.getNotificationLog();
                    if (notifications.isEmpty()) {
                        System.out.println(" - No upcoming meal notifications.");
                    } else {
                        notifications.forEach(note -> System.out.println(" " + note));
                    }
                }
                case 7 -> {
                    System.out.println("👋 Logging out...");
                    app.logoutUser();
                    System.out.println("🔓 Successfully logged out.");
                    return;
                }
                default -> System.out.println("❌ Invalid option. Try again.");
            }
        }
    }


}