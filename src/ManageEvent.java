import java.time.LocalDateTime;
import java.util.ArrayList;

public class ManageEvent {
    private ArrayList<Event> eventList;

    public ManageEvent() {
        eventList = new ArrayList<>();
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
                event.cancelEvent();

                return true;
            }
        }

        return false;
    }

    public ArrayList<Event> listEvents() {
            return new ArrayList<>(eventList);
        }

    public ArrayList<Event> searchEvent(String title) {
        ArrayList<Event> res = new ArrayList<Event>();
        String query = title.toLowerCase();

        for (Event event : eventList) {
            String stringTitle =  event.getTitle().toLowerCase();
            if (stringTitle.contains(query)) {
                res.add(event);
            }
        }

        return res;
    }
}
