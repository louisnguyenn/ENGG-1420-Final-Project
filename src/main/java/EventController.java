import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventController {

    // ManageEvent handles all the actual event logic
    private ManageEvent manageEvent;

    public EventController() {
        manageEvent = new ManageEvent();
    }

    // Adds a pre-built Event object directly — used by DataLoader at startup
    public void addEventObject(Event event) {
        manageEvent.addEvent(event);
    }

    // Creates a new event — called from EventFormView in create mode
    // eventType must be "Workshop", "Seminar", or "Concert"
    public boolean addEvent(String eventId, String title, String dateTimeStr,
                            String location, String capacityStr,
                            String eventType, String typeSpecificField) {

        // Parse capacity — return false if not a valid number
        int capacity;
        try {
            capacity = Integer.parseInt(capacityStr);
        } catch (NumberFormatException e) {
            return false;
        }

        // Parse date/time — fall back to now if format is wrong
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(dateTimeStr);
        } catch (Exception e) {
            dateTime = LocalDateTime.now();
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

    // Updates an existing event — called from EventFormView in edit mode
    public boolean editEvent(String eventId, String title, String dateTimeStr,
                             String location, String capacityStr, String typeSpecificField) {

        // Parse capacity — return false if not a valid number
        int capacity;
        try {
            capacity = Integer.parseInt(capacityStr);
        } catch (NumberFormatException e) {
            return false;
        }

        // Parse date/time — fall back to now if format is wrong
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(dateTimeStr);
        } catch (Exception e) {
            dateTime = LocalDateTime.now();
        }

        return manageEvent.updateEvent(eventId, title, dateTime, location, capacity, typeSpecificField);
    }

    // Cancels an event and all its bookings and waitlist entries
    public boolean cancelEvent(String eventId, ManageBooking manageBooking, Waitlist waitlist) {
        return manageEvent.cancelEvent(eventId, manageBooking, waitlist);
    }

    // Returns a list of all events
    public ArrayList<Event> getEventList() {
        return manageEvent.listEvents();
    }

    // Searches events by partial title match
    public ArrayList<Event> searchEvents(String title) {
        return manageEvent.searchEvent(title);
    }

    // Filters events by type (Workshop, Seminar, Concert)
    public ArrayList<Event> filterByType(String type) {
        return manageEvent.filterByType(type);
    }

    // Finds and returns a single event by its ID
    public Event getEventById(String eventId) {
        return manageEvent.getEventById(eventId);
    }

    @Override
    public String toString() {
        String output = "";
        for (Event e : manageEvent.listEvents()) {
            output += e + "\n";
        }
        return output;
    }
}
