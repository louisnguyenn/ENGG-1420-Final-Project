public class Concert extends Event {
    private int ageRestriction;

//    constructor
    public Concert(String eventId, String title, LocalDateTime dateTime, String location,
                   int capacity, Status status
                   int ageRestriction) {
        super(eventId, title, dateTime, location, capacity, status);
        setAgeRestriction((ageRestriction));
    }

//    setter
    public void setAgeRestriction(int ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

//    getter
    public int getAgeRestriction() {
        return this.ageRestriction;
    }
}
