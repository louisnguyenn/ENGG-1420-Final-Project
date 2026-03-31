package com.guelph.engg1420finalprojectjavafx;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
/*
Rough notes: Combined both userController and userManagement view. I
Implemented logic code and the UI elements in this class

 */



public class UserManagementView {

    private VBox root; //Declaring main vertical container holding UI element

    public UserManagementView(MainApp app, UserRegistry registry, ManageBooking manageBooking) {
        root = new VBox();
        root.setSpacing(10);
        root.setStyle("-fx-padding: 15");

        // Title label at the top
        Label title = new Label("USER MANAGEMENT"); //Main page header
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");

        // Add New User section
        Label formTitle = new Label("Add New User:"); //Subheading for user
        formTitle.setStyle("-fx-font-weight: bold");

        //Input box for the following
        TextField idField    = new TextField();
        TextField nameField  = new TextField();
        TextField emailField = new TextField();

        //Sets a placeholder text for the following
        idField.setPromptText("User ID");
        nameField.setPromptText("Name");
        emailField.setPromptText("Email");

        // Dropdown for user type, use comboBox for each
        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("student", "staff", "guest");
        typeCombo.setPromptText("Select User Type");


        Label formStatus = new Label(); // shows success or error message

        Button createUserBtn = new Button("CREATE USER");
        createUserBtn.setOnAction(e -> {

            // Make sure all fields are filled in using if statement
            if (idField.getText().trim().isEmpty()
                    || nameField.getText().trim().isEmpty()
                    || emailField.getText().trim().isEmpty()) {
                formStatus.setText("All fields are required.");
                return;
            }

            // Make sure email contains @ and a dot
            if (!emailField.getText().trim().contains("@")
                    || !emailField.getText().trim().contains(".")) {
                formStatus.setText("Please enter a valid email address.");
                return;
            }

            // Make sure a type was selected
            if (typeCombo.getValue() == null) {
                formStatus.setText("Please select a user type.");
                return;
            }

            // Create the user and try to add them
            User user = new User( //Construct a brand new user object , and grab details
                    idField.getText().trim(),
                    nameField.getText().trim(),
                    emailField.getText().trim(),
                    typeCombo.getValue());

            boolean added = registry.addUser(user); //Added userobject into the registry/database

            if (added) { //Check if its successfully added to the database and no duplication
                formStatus.setText("User added: " + idField.getText().trim());
                // Clear the form fields, removing text from fields
                idField.clear();
                nameField.clear();
                emailField.clear();
                typeCombo.setValue(null);
                // Refresh the user list at the bottom
                refreshUserList(root, registry);
            } else {
                formStatus.setText("User ID already exists.");
            }
        });

        // View User Details section
        Label detailTitle = new Label("View User Details:"); //Creates subheading in search tool
        detailTitle.setStyle("-fx-font-weight: bold");

        TextField searchIdField = new TextField(); //Creates a new textbox, allowing users to type ID they want to search
        searchIdField.setPromptText("Enter User ID"); //Set a textbox for user input

        Label detailResult = new Label();
        detailResult.setWrapText(true); // wrap long text

        Button viewBtn = new Button("VIEW USER");
        viewBtn.setOnAction(e -> {

            // Make sure a user ID was entered
            if (searchIdField.getText().trim().isEmpty()) {
                detailResult.setText("Please enter a User ID.");
                return;
            }

            User found = registry.getUserById(searchIdField.getText().trim()); //Finds the ID in the database
            if (found != null) { //Returns a matching user object if found

                // Show user info, prints out the string in array
                String output = found.toString() + "\n";

                // Show a summary of their current confirmed bookings
                ArrayList<Booking> userBookings = manageBooking.getBookingsByUser(found.getUserId());

                int confirmedCount = 0;
                for (Booking b : userBookings) { //To loop through the system for the user and check if its confirmed
                    if (b.getBookingStatus() == Booking.BookingStatus.Confirmed) {
                        confirmedCount++; //+1 if confirmed
                    }
                }

                output += "Confirmed Bookings: " + confirmedCount + " / " + found.getMaxBookings();
                output += "\nBooking History \n";

                if (userBookings.isEmpty()) { //Check if user has zero bookings
                    output += "No bookings found.";
                } else {
                    for (Booking b : userBookings) {
                        output += b.toString() + "\n"; //add text details to display the string
                    }
                }

                detailResult.setText(output.trim());

            } else {
                detailResult.setText("User not found.");
            }
        });

        // Back button returns to main menu
        Button backBtn = new Button("GO BACK");
        backBtn.setOnAction(e -> {
            app.showMainView();
        });

        // Add everything to the layout
        root.getChildren().addAll( //Puts UI components into the main VBox container
                title,
                new Separator(), //Place line to separate
                formTitle, idField, nameField, emailField, typeCombo, createUserBtn, formStatus,
                new Separator(),
                detailTitle, searchIdField, viewBtn, detailResult,
                new Separator(),
                new Label("All Users:")
        );

        // Render the user list, then add the back button at the very bottom
        refreshUserList(root, registry); //Draw current users in the bottom of the VBox
        root.getChildren().add(backBtn);
    }

    // Rebuilds the user list at the bottom of the view
    private void refreshUserList(VBox root, UserRegistry registry) {

        // Remove all old user row labels
        ArrayList<javafx.scene.Node> toRemove = new ArrayList<>(); //Store elements in an arrayList  that we want to delete, goes through each element in VBox
        for (javafx.scene.Node node : root.getChildren()) {
            if (node instanceof Label && "userRow".equals(node.getId())) { //Identify the labels
                toRemove.add(node); //Gets the array list "delete" arrayList
            }
        }
        root.getChildren().removeAll(toRemove);  //Delete elements from VBox

        // Temporarily remove back button so it stays at the bottom
        javafx.scene.Node backBtn = null;
        for (javafx.scene.Node node : root.getChildren()) {
            if (node instanceof Button && "GO BACK".equals(((Button) node).getText())) {
                backBtn = node;
                break;
            }
        }
        if (backBtn != null) {
            root.getChildren().remove(backBtn);
        }

        // Add a label for each user
        ArrayList<User> users = registry.getAllUsers();
        if (users.isEmpty()) { //Check if the database is empty
            Label none = new Label("No users registered yet."); //Placeholder label
            none.setId("userRow");
            root.getChildren().add(none);
        } else {
            for (User u : users) {
                Label row = new Label(u.toString()); //Creates text label with user's details
                row.setId("userRow");
                root.getChildren().add(row);
            }
        }

        // Re-add back button at the very bottom
        if (backBtn != null) {
            root.getChildren().add(backBtn);
        }
    }

    public Parent getView() { //public method called by MainApp when it wants to display this screen
        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        return scroll;
    }
}
