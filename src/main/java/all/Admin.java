package all;

import java.util.*;
import java.util.logging.Logger;

public class Admin extends Person {
    Logger logger = Logger.getLogger(getClass().getName());

    public Admin(String userName, String pass) {
        super(userName, pass, "admin");
    }
///////
    /////
    ////////
    ////
    // Access full order history
    public void viewAllOrderHistory(Map<String, List<String>> orderHistory) {
        logger.info("📋 Full Order History:");
        if (orderHistory.isEmpty()) {
            logger.info("⚠️ No orders found.");
            return;
        }

        for (Map.Entry<String, List<String>> entry : orderHistory.entrySet()) {
            String customer = entry.getKey();
            List<String> orders = entry.getValue();
            logger.info("👤 " + customer + ":");
            for (String meal : orders) {
                logger.info("   • " + meal);
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

        logger.info("📊 Meal Popularity:");
        mealFrequency.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .forEach(entry -> logger.info(" - " + entry.getKey() + ": " + entry.getValue() + " orders"));
    }
}
