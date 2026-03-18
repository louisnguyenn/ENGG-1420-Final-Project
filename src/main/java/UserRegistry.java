import java.util.ArrayList;

public class UserRegistry {

    private ArrayList<User> users; // this stores all users

    // Constructor creates an empty list
    public UserRegistry() {
        users = new ArrayList<>();
    }

    // Adds a new user
    // Returns false if userId already exists
    public boolean addUser(User newUser) {

        // Check if user already exists
        if (getUserById(newUser.getUserId()) != null) {
            return false; // duplicate ID
        }

        users.add(newUser); // add to list
        return true;
    }

    // Finds and returns a user by ID
    // If not found, returns null
    public User getUserById(String userId) {

        // Loop through the list to find matching ID
        for (User u : users) {
            if (u.getUserId().equals(userId)) {
                return u;
            }
        }

        return null; // user not found
    }

    // Prints all users
    public void listUsers() {

        if (users.size() == 0) {
            System.out.println("No users found.");
            return;
        }

        // Loop through and print each user
        for (User u : users) {
            System.out.println(u);
        }
    }

    // Returns all users as a list (used by GUI)
    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}