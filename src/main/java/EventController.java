import java.util.ArrayList;
import java.util.List;

public class EventController {
    private ArrayList<Event> eventList;

    public EventController() {
        eventList = new ArrayList<Event>();
    }

    /*
    Add a new event to the list of events
     */
    public void addEvent(String title, String location, String capacity) {
        Event e = new Event(title, location, Integer.parseInt(capacity));
        eventList.add(e);
    }

    /*
    Find the event with the eventId and reset the variables
     */
    public void editEvent(int eventId, String title, String location, String capacity) {
        for(int i = 0; i < eventList.size(); i++) {
            if (eventList.get(i).getEventId() == eventId) {
                eventList.get(i).setTitle(title);
                eventList.get(i).setLocation(location);
                eventList.get(i).setCapacity(Integer.parseInt(capacity));
                System.out.println("EVENT IS UPDATED! " + eventId);
                return; // exit early after update
            }
        }
        System.out.println("NO UPDATED FOR EVENT: " + eventId);
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    @Override
    public String toString() {
        String output = "";
        for(Event e : eventList) {
            output += e;
        }
        return output;
    }
}
