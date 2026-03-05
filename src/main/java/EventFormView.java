package com.guelph.engg1420finalprojectjavafx;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class EventFormView {
    private VBox root = new VBox();

    /*
    If the Event object is null, treat this like a create form
    If the event is not null, then treat it like an edit/update
     */
    public EventFormView(MainApp app, EventController controller, Event currEvent) { //We set parameters, the mainapp(main stage), the event controller and the current events

        root = new VBox(); //Creating a new vertical box
        root.setSpacing(15);

        boolean isCreateMode = currEvent == null;

        Label title = new Label();
        if (isCreateMode) {
            title.setText("Create new Event");
        } else {
            title.setText("Edit Event " + currEvent.getEventId());
        }

        // Create the text fields
        TextField titleField = new TextField();
        TextField locField = new TextField();
        TextField capField = new TextField();

        ComboBox<Event.Status> statusComboBox = new ComboBox<>(); //Creating a comboBox, a dropdown menu
        statusComboBox.getItems().addAll(Event.Status.values()); //Get the items (enum vals) and we add

        // Set prompt for user
        titleField.setPromptText("Enter Title: ");
        locField.setPromptText("Enter Location: ");
        capField.setPromptText("Enter Capacity: ");

        if (!isCreateMode) { //When not in create mode, we will access the currentEvent and set it
            titleField.setText(currEvent.getTitle());
            locField.setText(currEvent.getLocation());
            capField.setText(currEvent.getCapacity() + ""); // cap is an int but needs to be a string to be displayed
            statusComboBox.setValue(currEvent.getStatus());
        }

        root.getChildren().addAll(title, titleField, locField, capField); //to access the root(VBox), and access the list, inputting elements

        Button finishBtn = new Button();
        if (isCreateMode) {
            finishBtn.setText("CREATE");
            finishBtn.setOnAction(e-> {
                controller.addEvent( //Call the controller and add event
                    titleField.getText(), //User will input the following text
                    locField.getText(),
                    capField.getText());
                app.showEventsManagementView(); // redirect to the events management page
            });
        } else {
            root.getChildren().add(statusComboBox); // only add if in edit mode
            finishBtn.setText("UPDATE EVENT");
            finishBtn.setOnAction(e-> {
                controller.editEvent(
                    currEvent.getEventId(),
                    titleField.getText(),
                    locField.getText(),
                    capField.getText(),
                    statusComboBox.getValue());
                app.showEventsManagementView(); // redirect to the events management page
            });
        }
        Button backBtn = new Button("GO BACK");
        backBtn.setOnAction(e -> {
            app.showMainView();
        });

        root.getChildren().addAll(finishBtn, backBtn);
    }

    public Parent getView() {
        return root; //Returns to the VBox layout to be displayed
    }

}
