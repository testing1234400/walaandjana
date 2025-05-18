package all;

public class order {


        private CustomerProfile customer;
        private meal meal;
       // public double price;
        public order(CustomerProfile customer, meal meal) {
            this.customer = customer;
            this.meal = meal;

        }

        public CustomerProfile getCustomer() {
            return customer;
        }

        public meal getMeal() {
            return meal;
        }


        //public double getPrice() {
       // return price;
        //}


    @Override
        public String toString() {
            return "🧑 " + customer.getUserName() + " ordered 🍽 " + meal.getName();
        }


}
