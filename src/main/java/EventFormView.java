package com.guelph.engg1420finalprojectjavafx;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class EventFormView {
/*
This class shows when the user wants to create an event, "CREATE EVENT"
 */
    private VBox root;

    /*
    If currEvent is null, this is create mode (new event)
    If currEvent is not null, this is edit mode (existing event)
    */
    public EventFormView(MainApp app, EventController controller, Event currEvent) {
        root = new VBox(); //Vertical box object
        root.setSpacing(12);
        root.setStyle("-fx-padding: 15");

        //For create mode, sets it to "isCreateMode" to true
        //If event has data, it sets to false so it will be in edit mode
        boolean isCreateMode = currEvent == null;

        // Title changes based on mode, createMode or editMode
        Label title = new Label();
        if (isCreateMode) {
            title.setText("Create New Event");
        } else {
            title.setText("Edit Event: " + currEvent.getEventId());
        }

        // Text fields for event info, empty lines on the screen for userInput
        TextField idField    = new TextField();
        TextField titleField = new TextField();
        TextField dateField  = new TextField();
        TextField locField   = new TextField();
        TextField capField   = new TextField();
        TextField typeField  = new TextField(); // holds topic, speakerName, or ageRestriction

        // Set placeholder text, user will see what to enter
        // Disappear when user clicks the box to type
        idField.setPromptText("Enter Event ID:");
        titleField.setPromptText("Enter Title:");
        dateField.setPromptText("Enter Date/Time (yyyy-MM-ddTHH:mm):");
        locField.setPromptText("Enter Location:");
        capField.setPromptText("Enter Capacity:");
        typeField.setPromptText("Type-specific field:");

        // Event type dropdown, only used in create mode
        ComboBox<String> eventTypeCombo = new ComboBox<>();
        eventTypeCombo.getItems().addAll("Workshop", "Seminar", "Concert"); //Add these types to the dropdown menu
        eventTypeCombo.setPromptText("Select Event Type"); //Text for the user to select a type

        // Update the type field prompt when a type is selected
        eventTypeCombo.setOnAction(e -> {
            if (eventTypeCombo.getValue().equals("Workshop")) {
                typeField.setPromptText("Topic:");
            } else if (eventTypeCombo.getValue().equals("Seminar")) {
                typeField.setPromptText("Speaker Name:");
            } else if (eventTypeCombo.getValue().equals("Concert")) {
                typeField.setPromptText("Age Restriction (e.g. 18+):");
            }
        });

        // If edit mode, pre-fill all fields with existing event data
        if (!isCreateMode) {
            idField.setText(currEvent.getEventId()); //Gets eventID and type it into the ID text
            idField.setDisable(true); // ID cannot be changed
            titleField.setText(currEvent.getTitle()); //get old title and put in title box
            if (currEvent.getDateTime() != null) {
                dateField.setText(currEvent.getDateTime().toString()); //makes sure date isn't empty, translates date object into text and put in box
            }
            locField.setText(currEvent.getLocation());
            capField.setText(String.valueOf(currEvent.getCapacity()));

            // Pre-fill type-specific field and set correct prompt, loads the old event in final box
            if (currEvent instanceof Workshop) {
                //If it's workshop for example, update the text and type into topic
                typeField.setPromptText("Topic:");
                typeField.setText(((Workshop) currEvent).getTopic());
            } else if (currEvent instanceof Seminar) {
                typeField.setPromptText("Speaker Name:");
                typeField.setText(((Seminar) currEvent).getSpeakerName());
            } else if (currEvent instanceof Concert) {
                typeField.setPromptText("Age Restriction (e.g. 18+):");
                typeField.setText(((Concert) currEvent).getAgeRestriction());
            }
        }

        Label statusLabel = new Label(); // Create an empty label on screen, shows success or error message

        // Back button returns to events list
        Button backBtn = new Button("GO BACK!");
        backBtn.setOnAction(e -> {
            app.showEventsManagementView();
        });

        // Build layout and button based on mode
        if (isCreateMode) {

            Button createBtn = new Button("CREATE!");
            createBtn.setOnAction(e -> {

                // Make sure an event type was selected
                if (eventTypeCombo.getValue() == null) {
                    statusLabel.setText("Please select an event type.");
                    return;
                }

                // Make sure all fields are filled in
                if (idField.getText().trim().isEmpty()) {
                    statusLabel.setText("Event ID is required.");
                    return;
                }
                if (titleField.getText().trim().isEmpty()) {
                    statusLabel.setText("Title is required.");
                    return;
                }
                if (dateField.getText().trim().isEmpty()) {
                    statusLabel.setText("Date/Time is required.");
                    return;
                }
                if (locField.getText().trim().isEmpty()) {
                    statusLabel.setText("Location is required.");
                    return;
                }
                if (capField.getText().trim().isEmpty()) {
                    statusLabel.setText("Capacity is required.");
                    return;
                }
                if (typeField.getText().trim().isEmpty()) {
                    statusLabel.setText("Type-specific field is required.");
                    return;
                }

                // Make sure capacity is a valid number greater than 0
                int capacity;
                try {
                    capacity = Integer.parseInt(capField.getText().trim()); //Make computer allow it to be a "real math number" vs. text
                    if (capacity <= 0) {
                        statusLabel.setText("Capacity must be greater than 0.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    //Catch error if capacity is not an error
                    statusLabel.setText("Capacity must be a number.");
                    return;
                }

                // Make sure date/time is in the correct format
                try {
                    java.time.LocalDateTime.parse(dateField.getText().trim());
                } catch (Exception ex) {
                    statusLabel.setText("Date/Time must be in format: yyyy-MM-ddTHH:mm (e.g. 2026-03-25T14:30)");
                    return;
                }
                // Try to create the event, grabds lockedID from currEvent object
                boolean success = controller.addEvent(
                        idField.getText().trim(),
                        titleField.getText().trim(),
                        dateField.getText().trim(),
                        locField.getText().trim(),
                        capField.getText().trim(),
                        eventTypeCombo.getValue(),
                        typeField.getText().trim());
                //Check the controller's answer
                if (success) {
                    app.showEventsManagementView(); //Return to the dashboard
                } else {
                    statusLabel.setText("Failed: Event ID already exists.");
                }
            });
            //Saves changes to the main vertical box
            root.getChildren().addAll(
                    title, idField, titleField, dateField, locField, capField,
                    eventTypeCombo, typeField, statusLabel, createBtn, backBtn);

        } else {

            Button saveBtn = new Button("SAVE CHANGES!");
            saveBtn.setOnAction(e -> {

                // Make sure all fields are filled in
                if (titleField.getText().trim().isEmpty()) {
                    statusLabel.setText("Title is required.");
                    return;
                }
                if (dateField.getText().trim().isEmpty()) {
                    statusLabel.setText("Date/Time is required.");
                    return;
                }
                if (locField.getText().trim().isEmpty()) {
                    statusLabel.setText("Location is required.");
                    return;
                }
                if (capField.getText().trim().isEmpty()) {
                    statusLabel.setText("Capacity is required.");
                    return;
                }
                if (typeField.getText().trim().isEmpty()) {
                    statusLabel.setText("Type-specific field is required.");
                    return;
                }

                // Make sure capacity is a valid number greater than 0
                int capacity;
                try {
                    capacity = Integer.parseInt(capField.getText().trim());
                    if (capacity <= 0) {
                        statusLabel.setText("Capacity must be greater than 0.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    //check if an error
                    statusLabel.setText("Capacity must be a number.");
                    return;
                }

                // Make sure date/time is in the correct format
                try {
                    java.time.LocalDateTime.parse(dateField.getText().trim());
                } catch (Exception ex) {
                    statusLabel.setText("Date/Time must be in format: yyyy-MM-ddTHH:mm (e.g. 2026-03-25T14:30)");
                    return;
                }

                // Try to save the updated event
                boolean success = controller.editEvent(
                        currEvent.getEventId(),
                        titleField.getText().trim(),
                        dateField.getText().trim(),
                        locField.getText().trim(),
                        capField.getText().trim(),
                        typeField.getText().trim());

                if (success) {
                    app.showEventsManagementView();
                } else {
                    statusLabel.setText("Failed: could not save changes.");
                }
            });

            root.getChildren().addAll(
                    title, idField, titleField, dateField, locField, capField,
                    typeField, statusLabel, saveBtn, backBtn);
        }
    }
    //Method is called by mainapp
    public Parent getView() {
        //when all boxes don't fit, user scrollbar, we can also force it to fit the width
        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        return scroll;
    }
}
