import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class UserManagementView {

    private VBox root;

    public UserManagementView(MainApp app, UserRegistry registry) {
        root = new VBox();
        root.setSpacing(10);
        root.setStyle("-fx-padding: 15");

        // Title label at the top
        Label title = new Label("USER MANAGEMENT");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");

        // -- Add New User section --
        Label formTitle = new Label("Add New User:");
        formTitle.setStyle("-fx-font-weight: bold");

        TextField idField    = new TextField();
        TextField nameField  = new TextField();
        TextField emailField = new TextField();

        idField.setPromptText("User ID");
        nameField.setPromptText("Name");
        emailField.setPromptText("Email");

        // Dropdown for user type
        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("student", "staff", "guest");
        typeCombo.setPromptText("Select User Type");

        Label formStatus = new Label(); // shows success or error message

        Button createUserBtn = new Button("CREATE USER");
        createUserBtn.setOnAction(e -> {

            // Make sure all fields are filled in
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
            User user = new User(
                    idField.getText().trim(),
                    nameField.getText().trim(),
                    emailField.getText().trim(),
                    typeCombo.getValue());

            boolean added = registry.addUser(user);

            if (added) {
                formStatus.setText("User added: " + idField.getText().trim());
                // Clear the form fields
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

        // -- View User Details section --
        Label detailTitle = new Label("View User Details:");
        detailTitle.setStyle("-fx-font-weight: bold");

        TextField searchIdField = new TextField();
        searchIdField.setPromptText("Enter User ID");

        Label detailResult = new Label();
        detailResult.setWrapText(true); // wrap long text

        Button viewBtn = new Button("VIEW USER");
        viewBtn.setOnAction(e -> {

            // Make sure a user ID was entered
            if (searchIdField.getText().trim().isEmpty()) {
                detailResult.setText("Please enter a User ID.");
                return;
            }

            User found = registry.getUserById(searchIdField.getText().trim());
            if (found != null) {
                detailResult.setText(found.toString());
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
        root.getChildren().addAll(
                title,
                new Separator(),
                formTitle, idField, nameField, emailField, typeCombo, createUserBtn, formStatus,
                new Separator(),
                detailTitle, searchIdField, viewBtn, detailResult,
                new Separator(),
                new Label("All Users:")
        );

        // Render the user list, then add the back button at the very bottom
        refreshUserList(root, registry);
        root.getChildren().add(backBtn);
    }

    // Rebuilds the user list at the bottom of the view
    private void refreshUserList(VBox root, UserRegistry registry) {

        // Remove all old user row labels
        ArrayList<javafx.scene.Node> toRemove = new ArrayList<>();
        for (javafx.scene.Node node : root.getChildren()) {
            if (node instanceof Label && "userRow".equals(node.getId())) {
                toRemove.add(node);
            }
        }
        root.getChildren().removeAll(toRemove);

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
        if (users.isEmpty()) {
            Label none = new Label("No users registered yet.");
            none.setId("userRow");
            root.getChildren().add(none);
        } else {
            for (User u : users) {
                Label row = new Label(u.toString());
                row.setId("userRow");
                root.getChildren().add(row);
            }
        }

        // Re-add back button at the very bottom
        if (backBtn != null) {
            root.getChildren().add(backBtn);
        }
    }

    public Parent getView() {
        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        return scroll;
    }
}
