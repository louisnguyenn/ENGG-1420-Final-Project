package com.guelph.engg1420finalprojectjavafx;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Collection;

public class EventsManagementView {

    private VBox root;

    public EventsManagementView (MainApp app, EventController controller) {
        root = new VBox(); //made a new object of our "root"

        Label title = new Label("EVENTS MANAGEMENT");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold"); //Optional, just style

        HBox buttonLayout = new HBox(); //Initiating a new Horizontal box. (row)
        buttonLayout.setSpacing(4); //Set the spacing between the boxes

        Button createEventBtn = new Button ("CREATE EVENT");
        createEventBtn.setOnAction(e -> {
            app.showEventFormView(null); // enable createEvent view, allowing user to choose the button,
        });

        //Back button function
        Button backBtn = new Button ("GO BACK");
        backBtn.setOnAction(e -> {
            app.showMainView(); //When user wants to go back to menu
        });

        buttonLayout.getChildren().addAll(backBtn, createEventBtn); //to store backBtn and createEventBtn in the button layout
        root.getChildren().addAll(title, buttonLayout); //to label title and entire buttonLayout

        // include the edit page opn a button for each event
        for(Event ev: controller.getEventList()) {
            Button b = new Button(ev.getTitle() + " " + ev.getStatus());
            b.setOnAction(e -> {
                app.showEventFormView(ev);
            });
            root.getChildren().add(b);
        }
        //Using a for loop in order to cycle through the events
        //When we select the events, we will see eventFormView where the user creates an event
    }
    public Parent getView() { return root;}
}
