import java.time.LocalDateTime;
import java.util.ArrayList;

public class ManageEvent {
    private ArrayList<Event> eventList;

    public ManageEvent() {
        eventList = new ArrayList<>();
    }

    public ArrayList<Event> getEvents() {
        return new ArrayList<>(eventList);
    }

    public boolean addEvent(Event newEvent) {
        for (Event event : this.eventList) {
            if (event.getEventId().equalsIgnoreCase(newEvent.getEventId())) {
                return false;
            }
        }
        this.eventList.add(newEvent);

        return true;
    }

    public boolean updateEvent(String eventId, String newTitle, LocalDateTime newDateTime,
                               String newLocation, int newCapacity, String newField) {
        for (Event event : this.eventList) {
            if (event.getEventId().equalsIgnoreCase(eventId)) {
                event.setTitle(newTitle);
                event.setDate(newDateTime);
                event.setLocation(newLocation);
                event.setCapacity(newCapacity);
                event.updateTypeSpecificField(newField);

                return true;
            }
        }

        return false;
    }

    public boolean cancelEvent(String eventId) {
        for (Event event : this.eventList) {
            if (event.getEventId().equalsIgnoreCase(eventId)) {
                event.setStatus(Event.Status.Cancelled);
//                TODO: 1. cancel confirm and waitlisted bookings
//                      2. empty waitlist

                return true;
            }
        }

        return false;
    }

    public void listEvents() {
        for (Event event : this.eventList) {
//            TODO: display all events with their key details
            return;
        }
    }

    public Event searchEvent(String title) {
        for (Event event : this.eventList) {
            if (event.getTitle().equalsIgnoreCase(title)) {
                return event;
            }
        }

        return null;
    }

}
