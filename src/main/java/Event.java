import java.time.LocalDateTime;

public class Event {
    private String eventId;
    private String title;
    private LocalDateTime dateTime;
    private String location;
    private int capacity;
    public enum Status { Active, Cancelled }    // enum for event flags
    private Status status;
    ManageBooking booking = new ManageBooking();
    ManageWaitlist waitlist = new ManageWaitlist();

//    event constructors
    public Event() {
    }

    // polymorphic method: update type-specific fields
    public void updateTypeSpecificField(String field) {
    }

    public Event(String eventId, String title, LocalDateTime dateTime, String location,
                 int capacity, Status status) {
        setEventId(eventId);
        setTitle(title);
        setLocation(location);
        setCapacity(capacity);
        setDate(dateTime);
        setStatus(status);
    }

//    setters
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCapacity(int capacity) {
        if (capacity > 0) {
            this.capacity = capacity;
        }
    }

    public void setStatus(Status status) {
        this.status = status;
    }

//    getters
    public String getEventId() {
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

    public void cancelEvent() {
        setStatus(Status.Cancelled);

        // cancel bookings
        booking.bookingCancel();

        // cancel waitlist
        waitlist.clearOnEventCancellation();
    }
}
