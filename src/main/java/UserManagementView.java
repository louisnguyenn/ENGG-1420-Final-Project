package com.guelph.engg1420finalprojectjavafx;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class UserManagementView {

    private VBox root;

    public UserManagementView(MainApp app, UserController controller) {
        root = new VBox();
        root.setSpacing(15);

        Label title = new Label("USER MANAGEMENT");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");

        HBox buttonLayout = new HBox();
        buttonLayout.setSpacing(4);
        Button createUserBtn = new Button("CREATE USER");
        createUserBtn.setOnAction(e-> {
            app.showUserFormView(null);
        });
        Button backBtn = new Button("GO BACK");
        backBtn.setOnAction(e -> {
            app.showMainView();
        });

        buttonLayout.getChildren().addAll(backBtn, createUserBtn);

        for(User u: controller.getUserList()) {
            Button b = new Button(u.getName());
            b.setOnAction(e -> {
                app.showUserFormView(u);
            });
            root.getChildren().add(b);
        }

        root.getChildren().addAll(title, buttonLayout);
    }


    public Parent getView() {
        return root;
    }
}
