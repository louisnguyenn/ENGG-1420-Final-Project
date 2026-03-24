import java.util.Scanner;

// old console version of user management, we switched to the GUI but keeping this just in case
public class UserManagementApp {

    public static void runConsole() {

        Scanner scanner = new Scanner(System.in);
        UserRegistry registry = new UserRegistry();

        boolean running = true;

        while (running) {

            System.out.println("\n=== USER MANAGEMENT ===");
            System.out.println("1. Add User");
            System.out.println("2. List Users");
            System.out.println("3. View User Details");
            System.out.println("4. Exit");

            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // clear the leftover newline after the number

            if (choice == 1) {

                // ask for all the info we need
                System.out.print("Enter User ID: ");
                String id = scanner.nextLine();

                System.out.print("Enter Name: ");
                String name = scanner.nextLine();

                System.out.print("Enter Email: ");
                String email = scanner.nextLine();

                System.out.print("Enter Type (student/staff/guest): ");
                String type = scanner.nextLine().toLowerCase();

                // make sure they typed one of the 3 valid types
                if (!type.equals("student") &&
                        !type.equals("staff") &&
                        !type.equals("guest")) {

                    System.out.println("Invalid type. Must be student, staff, or guest.");
                    continue; // go back to the top of the menu
                }

                User user = new User(id, name, email, type);

                // addUser returns false if the id is already taken
                boolean added = registry.addUser(user);

                if (added) {
                    System.out.println("User added successfully.");
                } else {
                    System.out.println("That User ID already exists.");
                }
            }

            else if (choice == 2) {
                // just print everyone in the registry
                registry.listUsers();
            }

            else if (choice == 3) {

                System.out.print("Enter User ID: ");
                String id = scanner.nextLine();

                User found = registry.getUserById(id);

                if (found != null) {
                    System.out.println(found);
                } else {
                    System.out.println("User not found.");
                }
            }

            else if (choice == 4) {
                // user wants to quit, stop the loop
                running = false;
            }

            else {
                System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}