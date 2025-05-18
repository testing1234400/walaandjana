package all;

import java.util.HashMap;
import java.util.Map;

public class Supplier {
    private String name;
    private Map<Ingredient, Double> ingredientPrices; // Ingredient object → price

    public Supplier(String name) {
        this.name = name;
        this.ingredientPrices = new HashMap<>();
    }

    public void setPrice(Ingredient ingredient, double price) {
        ingredientPrices.put(ingredient, price);
    }

    public double getPrice(Ingredient ingredient) {
        return ingredientPrices.getOrDefault(ingredient, -1.0);
    }

    public String getName() {
        return name;
    }

    public void addIngredientPrice(Ingredient flour, double v) {
        setPrice(flour, v);
    }
}
