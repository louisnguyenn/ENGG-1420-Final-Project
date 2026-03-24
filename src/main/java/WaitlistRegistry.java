import java.util.HashMap;

//holds one Waitlist per event stored by event ID
public class WaitlistRegistry {

    //hashmap variable "Waitlist" created. key is event ID & value is Waitlist object
    private HashMap<String, Waitlist> waitlists;

    //creates an empty hashmap and assigns it to "waitlists"
    public WaitlistRegistry() {
        this.waitlists = new HashMap<>();
    }

    //returns the waitlist from an eventID. if doesnt exist, creates new one
    public Waitlist getOrCreate(String eventId) {
        //converts eventID to uppercase
        String key = eventId.toUpperCase();

        //checks if map doesnt have waitlist for this eID
        if (!waitlists.containsKey(key)) {
            //creates a new waitlist object in the map under that key
            waitlists.put(key, new Waitlist(eventId));
        }
        //retrieves Waitlist for this event.
        return waitlists.get(key);
    }

    //returns true if a waitlist already exists for this event & returns result directly
    public boolean hasWaitlist(String eventId) {
        return waitlists.containsKey(eventId.toUpperCase());
    }
}
