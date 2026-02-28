import java.util.ArrayList;

public class ManageEvent {
    private ArrayList<Event> eventList;

    public ManageEvent() {
        eventList = new ArrayList<Event>();
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

    public Event searchEvent(String title) {
        for (Event event : this.eventList) {
            if (event.getTitle().equalsIgnoreCase(title)) {
                return event;
            }
        }

        return null;
    }

}
