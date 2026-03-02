import java.util.Scanner;

public class UserManagementApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        UserRegistry registry = new UserRegistry();

        boolean running = true; // controls the menu loop

        while (running) {

            System.out.println("\n=== USER MANAGEMENT ===");
            System.out.println("1. Add User");
            System.out.println("2. List Users");
            System.out.println("3. View User Details");
            System.out.println("4. Exit");

            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {

                // Get user info from input
                System.out.print("Enter User ID: ");
                String id = scanner.nextLine();

                System.out.print("Enter Name: ");
                String name = scanner.nextLine();

                System.out.print("Enter Email: ");
                String email = scanner.nextLine();

                System.out.print("Enter Type (student/staff/guest): ");
                String type = scanner.nextLine().toLowerCase();

                // Check if type is valid
                if (!type.equals("student") &&
                        !type.equals("staff") &&
                        !type.equals("guest")) {

                    System.out.println("Invalid type. Must be student, staff, or guest.");
                    continue; // skip and go back to menu
                }

                User user = new User(id, name, email, type);

                // Try adding user
                boolean added = registry.addUser(user);

                if (added) {
                    System.out.println("User added successfully.");
                } else {
                    System.out.println("That User ID already exists.");
                }
            }

            else if (choice == 2) {
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
                running = false; // stop the loop
            }

            else {
                System.out.println("Invalid option.");
            }
        }

        scanner.close(); // close scanner at end
    }
}