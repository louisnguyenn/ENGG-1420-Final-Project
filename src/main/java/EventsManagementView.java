package com.guelph.engg1420finalprojectjavafx;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class EventsManagementView {
/*
This class shows when the user selects EventsManagement, like the main hub for it
 */
    private VBox root;

    public EventsManagementView(MainApp app, EventController controller) {
        root = new VBox();
        root.setSpacing(10);
        root.setStyle("-fx-padding: 15");

        // Title label at the top
        Label title = new Label("EVENTS MANAGEMENT");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");

        // Top row buttons, horizontal box
        HBox topButtons = new HBox();
        topButtons.setSpacing(6);

        Button createEventBtn = new Button("CREATE EVENT");
        createEventBtn.setOnAction(e -> {
            app.showEventFormView(null); // null means create mode
        });

        Button backBtn = new Button("GO BACK");
        backBtn.setOnAction(e -> {
            app.showMainView();
        });

        topButtons.getChildren().addAll(backBtn, createEventBtn); //brings the back and create event button inside the Hbox

        // Search and Filter section
        Label searchLabel = new Label("Search / Filter:");
        searchLabel.setStyle("-fx-font-weight: bold");

        //New textfield for the searchfield
        TextField searchField = new TextField();
        searchField.setPromptText("Search by title...");

        //Dropdown menu for filering the types
        ComboBox<String> filterCombo = new ComboBox<>();
        filterCombo.getItems().addAll("All", "Workshop", "Seminar", "Concert");
        filterCombo.setValue("All");

        //Clickable button for search
        Button searchBtn = new Button("SEARCH");

        //Horizontal box for search
        HBox searchRow = new HBox();
        searchRow.setSpacing(6);
        searchRow.getChildren().addAll(searchField, filterCombo, searchBtn);

        // Container that holds the event list buttons, hold the list of events
        VBox eventListBox = new VBox();
        eventListBox.setSpacing(5);

        // Show all events on first load,
        renderEvents(eventListBox, controller.getEventList(), app, controller);

        // When search is clicked, filter and re-render the list
        searchBtn.setOnAction(e -> {

            String query = searchField.getText().trim(); //grab user typed in the search box, cuts off spaces
            String type  = filterCombo.getValue(); //Grab exactly what the user selected in dropdwon

            // Start with title search or full list, check if its empty
            ArrayList<Event> results;
            if (!query.isEmpty()) {
                results = controller.searchEvents(query); //tells controller to find events that matched the text
            } else {
                results = controller.getEventList(); // returns a copy so safe to filter, get all the events
            }

            // Apply type filter if not set to All
            if (!type.equals("All")) {
                ArrayList<Event> filtered = new ArrayList<>(); //temporary filter list, loop through the results of searchEvents
                for (Event ev : results) {
                    if (ev.getClass().getSimpleName().equalsIgnoreCase(type)) {
                        filtered.add(ev); //Add to temporary list when matches
                    }
                }
                results = filtered; //replace old results with new filtered list
            }

            renderEvents(eventListBox, results, app, controller);
        });

        // Add everything to the layout, add a separator
        root.getChildren().addAll(
                title,
                topButtons,
                new Separator(),
                searchLabel, searchRow,
                new Separator(),
                new Label("Events:"),
                eventListBox
        );
    }

    // Renders the event list; one row per event with edit and cancel buttons
    private void renderEvents(VBox container, ArrayList<Event> events,
                               MainApp app, EventController controller) {
        container.getChildren().clear();

        if (events.isEmpty()) {
            //If empty, print no events on screen
            container.getChildren().add(new Label("No events found."));
            return;
        }

        for (Event ev : events) {
            HBox row = new HBox();
            row.setSpacing(6);

            // Show cancelled status in the button label
            String statusPrefix = "";
            if (ev.getStatus() == Event.Status.Cancelled) {
                statusPrefix = "[CANCELLED] ";
            }

            String btnLabel = statusPrefix + ev.getEventId() + " | " + ev.getTitle()
                    + " | " + ev.getClass().getSimpleName()
                    + " | Cap: " + ev.getCapacity()
                    + " | " + ev.getDateTime();

            // Edit button; disabled if event is cancelled
            Button editBtn = new Button(btnLabel);
            if (ev.getStatus() == Event.Status.Cancelled) { //Check if its cancelled, lock the button if it is
                editBtn.setDisable(true);
            }
            editBtn.setOnAction(e -> {
                app.showEventFormView(ev); //return to mainFormView
            });

            // Cancel button; disabled if already cancelled
            Button cancelBtn = new Button("CANCEL");
            if (ev.getStatus() == Event.Status.Cancelled) {
                cancelBtn.setDisable(true);
            }
            cancelBtn.setOnAction(e -> {
                //Calls main app and cancels the event
                app.cancelEvent(ev.getEventId());
                app.showEventsManagementView(); // refresh the main EventManagementView
            });

            row.getChildren().addAll(editBtn, cancelBtn);
            container.getChildren().add(row);
        }
    }
    //Method called by mainApp window
    public Parent getView() {
        ScrollPane scroll = new ScrollPane(root); //Scrollable window, incase text doesn't fit
        scroll.setFitToWidth(true); //Ensures it text fits the width
        return scroll;
    }
}
