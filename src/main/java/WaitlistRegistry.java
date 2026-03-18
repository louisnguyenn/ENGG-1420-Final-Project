import java.util.HashMap;

// Holds one Waitlist per event, stored by event ID
public class WaitlistRegistry {

    // Maps event ID to its waitlist
    private HashMap<String, Waitlist> waitlists;

    public WaitlistRegistry() {
        this.waitlists = new HashMap<>();
    }

    // Returns the waitlist for an event — creates one if it doesn't exist yet
    public Waitlist getOrCreate(String eventId) {

        String key = eventId.toUpperCase();

        // If no waitlist exists for this event, create one
        if (!waitlists.containsKey(key)) {
            waitlists.put(key, new Waitlist(eventId));
        }

        return waitlists.get(key);
    }

    // Returns true if a waitlist already exists for this event
    public boolean hasWaitlist(String eventId) {
        return waitlists.containsKey(eventId.toUpperCase());
    }
}
