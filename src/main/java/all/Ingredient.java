package all;

public class Ingredient {
    private String name;
    private int quantity;
    private int threshold; // when quantity drops below this, suggest restock

    private Ingredient alternative;
    public Ingredient(String name, int quantity, int threshold,Ingredient alternative) {
        this.name = name;
        this.alternative = alternative;
        this.quantity = quantity;
        this.threshold = threshold;
    }
///


    public boolean isLowStock() {
        return quantity < threshold;
    }


    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public int getThreshold() { return threshold; }

    public void reduceQuantity(int amount) {
        this.quantity -= amount;
        if (this.quantity < threshold) {
            System.out.println("⚠️ Restock Alert: " + name + " is low (" + quantity + " left)");
        }
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;
        Ingredient that = (Ingredient) o;
        return name.equalsIgnoreCase(that.name); // compare by name only
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
    public void restock(int amount) {
        this.quantity += amount;
        System.out.println("✅ " + name + " restocked. New quantity: " + quantity);
    }

    public Ingredient getAlternative( ) {
        return alternative;
    }



}