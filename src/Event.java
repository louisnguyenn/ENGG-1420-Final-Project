import java.time.LocalDateTime;

public class Event {
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

    public Event(int eventId, String title, LocalDateTime dateTime, String location, int capacity,
                      Status status) {
        setEventId(eventId);
        setTitle(title);
        setLocation(location);
        setCapacity(capacity);
        this.dateTime = LocalDateTime.now();    // set current time
        this.status = Status.Active;
    }

//    setters
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

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
    public void getEventId() {
        return this.eventId;
    }

    public void getTitle() {
        return this.title;
    }

    public void getDateTime() {
        return this.dateTime;
    }

    public void getLocation() {
        return this.location;
    }

    public void getCapacity() {
        return this.capacity;
    }

    public void getStatus() {
        return this.status;
    }
}
