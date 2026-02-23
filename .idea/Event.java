class Event {
    private int eventId;
    private String title;
    private String dateTime;
    private String location;
    private int capacity;
    public enum Status { Active, Cancelled }    // Enum for event flags
    private Status status;

//    Event Constructor
    public void Event(int eventId, String title, String dateTime, String location, int capacity,
                      status status) {
        setEventId(eventId);
        setTitle(title);
        setDateTime(dateTime);
        setLocation(location);
        setCapacity(capacity);
        setStatus(Status.Active);
    }

//    Setter Functions
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDateTime(String dateTime) {
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
        this.status = Status.Active;
    }

//    Getter Functions
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
