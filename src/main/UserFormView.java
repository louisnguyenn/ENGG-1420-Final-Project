package com.guelph.engg1420finalprojectjavafx;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class UserFormView {

    private VBox root = new VBox();
    public UserFormView(MainApp app, UserController controller, User currUser) {
        root = new VBox();
        root.setSpacing(15);

        boolean isCreateMode = currUser == null;

        //Creating new title labels for user
        Label title = new Label();
        if (isCreateMode) {
            title.setText("Create new User");
        } else {
            title.setText("Edit User");
        }

        // Create text fields
        TextField nameField = new TextField();
        nameField.setPromptText("Enter Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Set email");

        TextField userTypeField = new TextField();
        userTypeField.setPromptText("Set user type");

        if (!isCreateMode) {
            nameField.setText(currUser.getName());
            emailField.setText(currUser.getEmail());
            userTypeField.setText(currUser.getUserType());
        }

        Button finishBtn = new Button(); //Creation of a button (Create)
        if (isCreateMode) {
            finishBtn.setText("CREATE USER");
            finishBtn.setOnAction(e -> {
                controller.addUser(
                    nameField.getText(),
                    emailField.getText(),
                    userTypeField.getText());
                app.showManageUserView(); //Display UserManagement after everything is inputted
            });
        } else {
            finishBtn.setText("UPDATE USER");
            finishBtn.setOnAction(e -> {
                controller.editUser(
                    currUser.getUserId(),
                    nameField.getText(),
                    emailField.getText(),
                    userTypeField.getText()
                );
            });
            app.showManageUserView();
        }

        Button backBtn = new Button("GO BACK");
        backBtn.setOnAction(e -> {
            app.showMainView();
        });

        root.getChildren().addAll(title, nameField, emailField, userTypeField, finishBtn, backBtn);
    }

    public Parent getView() {
        return root;
    }
}
