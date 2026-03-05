package com.guelph.engg1420finalprojectjavafx;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class ShowMainView {
    // VBox store elements up and down

    // HBox Storing them left to right
    private VBox root;

    public ShowMainView(MainApp app) {
        root = new VBox();
        root.setSpacing(15);

        Label title = new Label("CAMPUS EVENT BOOKING SYSTEM");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");

        //Creation of horizontal box, where users will see the options in a column
        HBox buttonLayout = new HBox();
        buttonLayout.setSpacing(4);
        Button userManBtn = new Button("USER MANAGEMENT");
        userManBtn.setOnAction(e -> {
            app.showManageUserView();
        });

        Button bookingManBtn = new Button("BOOKING MANAGEMENT");

        Button eventManBtn = new Button("EVENT MANAGEMENT");
        eventManBtn.setOnAction(e->{
            app.showEventsManagementView();
        });

        Button waitlistManBtn = new Button("WAITLIST MANAGEMENT");

        buttonLayout.getChildren().addAll(userManBtn, bookingManBtn, eventManBtn, waitlistManBtn);
        //Storing the buttons into the layout

        root.getChildren().addAll(title, buttonLayout);
        //Added title and button layout to the VBox
    }

    public Parent getView() {
        return root;
    }

}
