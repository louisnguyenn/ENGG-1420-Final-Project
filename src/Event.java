import java.time.LocalDateTime;
import java.util.Random;

public class Event {
    private Random rand = new Random();
    private int eventId;
    private String title;
    private LocalDateTime dateTime;
    private String location;
    private int capacity;
    public enum Status { Active, Cancelled }    // enum for event flags
    private Status status;

//    event constructors
    public Event() {
    }

    public Event(String title, String location, int capacity) {
        setTitle(title);
        setLocation(location);
        setCapacity(capacity);
        this.eventId = rand.nextInt(10000);
        this.dateTime = LocalDateTime.now();
        this.status = Status.Active;
    }

//    setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCapacity(int capacity) {
        if (capacity > 0) {
            this.capacity = capacity;
        }
    }

//    getters
    public int getEventId() {
        return this.eventId;
    }

    public String getTitle() {
        return this.title;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public String getLocation() {
        return this.location;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public Status getStatus() {
        return this.status;
    }
}
