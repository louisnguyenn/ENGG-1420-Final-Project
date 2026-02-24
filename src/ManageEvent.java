import java.util.ArrayList;

public class ManageEvent {
    private ArrayList<Event> eventList;

    public ManageEvent(ArrayList<Event> events) {
        this.eventList = new ArrayList<Event>();
    }

    public ArrayList<Event> getEvents() {
        return new ArrayList<>(events);
    }

    public void addEvents(Event event) {
        this.eventList.add(event);
    }

    public Event searchEvent(String title) {
        for (Event event : this.eventList) {
            if (event.getTitle().equalsIgnoreCase(title)) {
                return event;
            }
        }
    }

}
