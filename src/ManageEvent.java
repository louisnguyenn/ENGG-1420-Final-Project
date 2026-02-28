import java.util.ArrayList;

public class ManageEvent {
    private ArrayList<Event> eventList;

    public ManageEvent() {
        eventList = new ArrayList<Event>();
    }

    // polymorphic method: can update type-specific fields
    public void updateTypeSpecificField(String field) {
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

    public Event searchEvent(String title) {
        for (Event event : this.eventList) {
            if (event.getTitle().equalsIgnoreCase(title)) {
                return event;
            }
        }

        return null;
    }

}
