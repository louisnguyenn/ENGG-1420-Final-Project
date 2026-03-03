public class User {

    private String userId;
    private String name;
    private String email;
    private String userType; // student, staff, or guest

    // Constructor to create a new user
    public User(String userId, String name, String email, String userType) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.userType = userType.toLowerCase(); // make it lowercase for consistency
    }

    // Getter methods so other classes can access private variables

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUserType() {
        return userType;
    }

    // This method returns booking limit based on user type, will use to assist booking class
    // We use if statements instead of subclasses to keep it simple
    public int getMaxBookings() {
        if (userType.equals("student")) return 3;
        if (userType.equals("staff")) return 5;
        if (userType.equals("guest")) return 1;
        return 0; // default if type is invalid
    }

    // toString helps us print the user nicely
    @Override
    public String toString() {
        return "ID: " + userId
                + ", Name: " + name
                + ", Email: " + email
                + ", Type: " + userType
                + ", Max Bookings: " + getMaxBookings();
    }
}