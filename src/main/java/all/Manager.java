package all;

import java.util.HashMap;

public class Manager extends Person {

    static HashMap<String, Ingredient> ingredients = new HashMap<>();

    public Manager(String userName, String pass, String role) {
        super(userName, pass, role);
    }

    public static void addIngredient(String name, int quantity, int threshold ,Ingredient m) {
        ingredients.put(name.toLowerCase(), new Ingredient(name, quantity, threshold,m));
    }

    public static void useIngredient(String name, int amount) {
        Ingredient ing = ingredients.get(name.toLowerCase());
        if (ing != null) {
            ing.reduceQuantity(amount);
        } else {
            System.out.println("❌ Ingredient not found: " + name);
        }
    }

    public static void restockIngredient(String name, int amount) {
        Ingredient ing = ingredients.get(name.toLowerCase());
        if (ing != null) {
            ing.restock(amount);
        } else {
            System.out.println("❌ Ingredient not found: " + name);
        }
    }

    public static void showInventory() {
        System.out.println("📦 Current Inventory:");
        for (Ingredient ing : ingredients.values()) {
            System.out.println(" - " + ing.getName() + ": " + ing.getQuantity());
        }
    }

    public boolean isValid() {
        return userName != null  ;
    }
}
