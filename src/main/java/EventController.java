package com.guelph.engg1420finalprojectjavafx;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventController {
/*
Acts as the logic for creating events and "translates" code from backend code to frontend
 */
    // ManageEvent handles all the actual event logic
    private ManageEvent manageEvent;

    public EventController() {
        manageEvent = new ManageEvent(); //create a database where it manages all events
    }

    // Adds a pre-built Event object directly - used by DataLoader at startup
    public void addEventObject(Event event) {
        manageEvent.addEvent(event); //passes the event to ManageEvent database
    }

    // Creates a new event, called from EventFormView in create mode
    // eventType must be "Workshop", "Seminar", or "Concert"
    public boolean addEvent(String eventId, String title, String dateTimeStr,
                            String location, String capacityStr,
                            String eventType, String typeSpecificField) {

        // Parse capacity, return false if not a valid number
        int capacity;
        try {
            capacity = Integer.parseInt(capacityStr);
        } catch (NumberFormatException e) {
            return false;
        }

        // Parse date/time, fall back to now if format is wrong
        //For calendar date and time
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(dateTimeStr);
        } catch (Exception e) {
            dateTime = LocalDateTime.now(); //Will use current system time if text is not read as a date
        }

        // Create the correct event subclass based on type
        Event event;
        if (eventType.equalsIgnoreCase("workshop")) {
            event = new Workshop(eventId, title, dateTime, location, capacity,
                    Event.Status.Active, typeSpecificField);
        } else if (eventType.equalsIgnoreCase("seminar")) {
            event = new Seminar(eventId, title, dateTime, location, capacity,
                    Event.Status.Active, typeSpecificField);
        } else if (eventType.equalsIgnoreCase("concert")) {
            event = new Concert(eventId, title, dateTime, location, capacity,
                    Event.Status.Active, typeSpecificField);
        } else {
            return false; // unknown event type
        }

        return manageEvent.addEvent(event);
    }

    // Updates an existing event, called from EventFormView in edit mode
    public boolean editEvent(String eventId, String title, String dateTimeStr,
                             String location, String capacityStr, String typeSpecificField) {

        // Parse capacity — return false if not a valid number
        int capacity;
        try {
            capacity = Integer.parseInt(capacityStr);
        } catch (NumberFormatException e) {
            return false;
        }

        // Parse date/time, fall back to now if format is wrong
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(dateTimeStr);
        } catch (Exception e) {
            dateTime = LocalDateTime.now();
        }
        //return the data to the database of event, overwrites old event information
        return manageEvent.updateEvent(eventId, title, dateTime, location, capacity, typeSpecificField);
    }

    // Cancels an event and all its bookings and waitlist entries
    public boolean cancelEvent(String eventId, ManageBooking manageBooking, Waitlist waitlist) {
        return manageEvent.cancelEvent(eventId, manageBooking, waitlist);
    }

    // Returns a list of all events
    public ArrayList<Event> getEventList() {
        return manageEvent.listEvents(); //returns the list of every stored event and prints it out on the screen
    }

    // Searches events by partial title match
    public ArrayList<Event> searchEvents(String title) {
        return manageEvent.searchEvent(title); //searches for the matching event in the text
    }

    // Filters events by type (Workshop, Seminar, Concert)
    public ArrayList<Event> filterByType(String type) {
        return manageEvent.filterByType(type); //filter out everything except requested categroy
    }

    // Finds and returns a single event by its ID
    public Event getEventById(String eventId) {
        return manageEvent.getEventById(eventId); //located the specific event using ID
    }

    @Override
    public String toString() {
        String output = "";
        //Retrieve the full list of events and loop through the list
        for (Event e : manageEvent.listEvents()) {
            //adds text informaion of current event to the string
            output += e + "\n";
        }
        return output;
    }
}
