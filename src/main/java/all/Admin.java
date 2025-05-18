package all;

import java.util.*;

public class Admin extends Person {

    public Admin(String userName, String pass) {
        super(userName, pass, "admin");
    }
///////
    /////
    ////////
    // Access full order history
    public void viewAllOrderHistory(Map<String, List<String>> orderHistory) {
        System.out.println("📋 Full Order History:");
        if (orderHistory.isEmpty()) {
            System.out.println("⚠️ No orders found.");
            return;
        }

        for (Map.Entry<String, List<String>> entry : orderHistory.entrySet()) {
            String customer = entry.getKey();
            List<String> orders = entry.getValue();
            System.out.println("👤 " + customer + ":");
            for (String meal : orders) {
                System.out.println("   • " + meal);
            }
        }
    }

    // Get most frequent meals ordered
    public void analyzePopularMeals(Map<String, List<String>> orderHistory) {
        Map<String, Integer> mealFrequency = new HashMap<>();

        for (List<String> meals : orderHistory.values()) {
            for (String meal : meals) {
                mealFrequency.put(meal, mealFrequency.getOrDefault(meal, 0) + 1);
            }
        }

        System.out.println("📊 Meal Popularity:");
        mealFrequency.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .forEach(entry -> System.out.println(" - " + entry.getKey() + ": " + entry.getValue() + " orders"));
    }
}
