package all;

import java.util.List;

public class meal {
    private String name;
    private List<Ingredient> ingredients;
    private String dietaryCategory; // NEW field e.g., "Vegan", "Low Carb"

    public meal(String name, List<Ingredient> ingredients, String dietaryCategory) {
        this.name = name;
        this.ingredients = ingredients;
        this.dietaryCategory = dietaryCategory;
    }

    public String getName() { return name; }
    public List<Ingredient> getIngredients() { return ingredients; }
    public String getDietaryCategory() { return dietaryCategory; }

    public boolean containsAllergen(String allergen) {
        return ingredients.stream()
                .anyMatch(ing -> ing.getName().equalsIgnoreCase(allergen));
    }

   // public boolean matchesDietaryPreference(String preference) {
  //      return dietaryCategory.equalsIgnoreCase(preference);
   // }

    @Override
    public String toString() {
        return name + " (" + dietaryCategory + ")";
    }

}


