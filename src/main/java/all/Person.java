package all;

public class Person {


        protected String userName;
        private String pass;
        private String role;

        public Person(String userName, String pass, String role) {
            this.userName = userName;
            this.pass = pass;
            this.role = role;
        }

        public String getUserName() {
            return userName;
        }

        public String getPass() {
            return pass;
        }

        public String getRole() {
            return role;
        }

    }
