package com.guelph.engg1420finalprojectjavafx;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class UserManagementView {

    private VBox root; //Declaring vertical boxes

    public UserManagementView(MainApp app, UserRegistry registry) {
        root = new VBox();
        root.setSpacing(10);
        root.setStyle("-fx-padding: 15");

        // Title label at the top
        Label title = new Label("USER MANAGEMENT"); //Main title
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");

        // Add New User section
        Label formTitle = new Label("Add New User:"); //Subheading
        formTitle.setStyle("-fx-font-weight: bold");

        //Creation of single-line input boxes
        TextField idField    = new TextField();
        TextField nameField  = new TextField();
        TextField emailField = new TextField();

        //Sets the empty textbox for users to enter
        idField.setPromptText("User ID");
        nameField.setPromptText("Name");
        emailField.setPromptText("Email");

        // Dropdown for user type
        ComboBox<String> typeCombo = new ComboBox<>(); //Will make a dropdown menu holding strings
        typeCombo.getItems().addAll("student", "staff", "guest"); //Fills it with the 3 types
        typeCombo.setPromptText("Select User Type");

        Label formStatus = new Label(); // shows success or error message

        Button createUserBtn = new Button("CREATE USER"); //New button
        createUserBtn.setOnAction(e -> {

            // Make sure all fields are filled in
            if (idField.getText().trim().isEmpty() //Check the different boxes if theyre empty
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
            if (typeCombo.getValue() == null) { //Check if the dropbox is unselected
                formStatus.setText("Please select a user type.");
                return;
            }

            // Create the user and try to add them
            User user = new User( //Creation of new User Object
                    idField.getText().trim(), //Get text from the different field boxes
                    nameField.getText().trim(),
                    emailField.getText().trim(),
                    typeCombo.getValue());

            boolean added = registry.addUser(user);

            if (added) { //If registry is accepted
                formStatus.setText("User added: " + idField.getText().trim());
                // Clear the form fields, will remove the type texts
                idField.clear();
                nameField.clear();
                emailField.clear();
                typeCombo.setValue(null); //Reset the comboBox / dropdown menu
                // Refresh the user list at the bottom
                refreshUserList(root, registry);
            } else {
                formStatus.setText("User ID already exists.");
            }
        });

        // View User Details section
        Label detailTitle = new Label("View User Details:"); //Creates subheading to view userDetails
        detailTitle.setStyle("-fx-font-weight: bold");

        TextField searchIdField = new TextField();
        searchIdField.setPromptText("Enter User ID");

        Label detailResult = new Label(); //New blank lebel to display user's info
        detailResult.setWrapText(true); // wrap long text

        Button viewBtn = new Button("VIEW USER"); //Create the search button
        viewBtn.setOnAction(e -> {

            // Make sure a user ID was entered, details section
            if (searchIdField.getText().trim().isEmpty()) {
                detailResult.setText("Please enter a User ID.");
                return;
            }

            User found = registry.getUserById(searchIdField.getText().trim());
            if (found != null) {

            // Show user info
            String output = found.toString() + "\n";

            // Show a summary of their current confirmed bookings
                ManageBooking manageBooking = null;
                ArrayList<Booking> userBookings = manageBooking.getBookingsByUser(found.getUserId());

            int confirmedCount = 0;
            for (Booking b : userBookings) {
                if (b.getBookingStatus() == Booking.BookingStatus.Confirmed) {
                    confirmedCount++;
                }
            }

            output += "Confirmed Bookings: " + confirmedCount + " / " + found.getMaxBookings();
            output += "\nBooking History\n";

            if (userBookings.isEmpty()) {
                output += "No bookings found.";
            } else {
                for (Booking b : userBookings) {
                    output += b.toString() + "\n";
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
            app.showMainView(); //going back to the main application class
        });

        // Add everything to the layout
        root.getChildren().addAll(
                title,
                new Separator(), //horizontal visual dividing line
                formTitle, idField, nameField, emailField, typeCombo, createUserBtn, formStatus,
                new Separator(),
                detailTitle, searchIdField, viewBtn, detailResult,
                new Separator(),
                new Label("All Users:")
        );

        // Render the user list, then add the back button at the very bottom
        refreshUserList(root, registry); //print out all curent users, calling the method
        root.getChildren().add(backBtn);
    }

    // Rebuilds the user list at the bottom of the view
    private void refreshUserList(VBox root, UserRegistry registry) { //Method clearing user rows

        // Remove all old user row labels
        ArrayList<Node> toRemove = new ArrayList<>(); //Creates an arraylist, hold UI elements
        for (Node node : root.getChildren()) { //loop through all the items in VBox
            if (node instanceof Label && "userRow".equals(node.getId())) { //check if currentlabel has the exact ID
                toRemove.add(node);
            }
        }
        root.getChildren().removeAll(toRemove);

        // Temporarily remove back button so it stays at the bottom
        Node backBtn = null; //Empty variable to hold the back button
        for (Node node : root.getChildren()) {
            if (node instanceof Button && "GO BACK".equals(((Button) node).getText())) {
                backBtn = node; //Save button to the variable
                break;
            }
        }
        if (backBtn != null) {
            root.getChildren().remove(backBtn);
        }

        // Add a label for each user
        ArrayList<User> users = registry.getAllUsers(); //going through the array lsit of users
        if (users.isEmpty()) {
            Label none = new Label("No users registered yet.");
            none.setId("userRow");
            root.getChildren().add(none);
        } else {
            for (User u : users) { //loop through every user in list
                Label row = new Label(u.toString()); //New text label containing the user's data
                row.setId("userRow");
                root.getChildren().add(row);
            }
        }

        // Re-add back button at the very bottom
        if (backBtn != null) {
            root.getChildren().add(backBtn); //put it back into the layout
        }
    }

    public Parent getView() { //method called by MainApp to get finished screen
        ScrollPane scroll = new ScrollPane(root); //Makes the VBox inside a scrollbar container.
        scroll.setFitToWidth(true);
        return scroll; //return to mainApp
    }
}
