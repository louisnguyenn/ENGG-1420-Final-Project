public class Concert extends Event {
    private String ageRestriction;

//    constructor
    public Concert(String eventId, String title, LocalDateTime dateTime, String location,
                   int capacity, Status status, String ageRestriction) {
        super(eventId, title, dateTime, location, capacity, status);
        setAgeRestriction(ageRestriction);
    }

//    setter
    public void setAgeRestriction(String ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

//    getter
    public int getAgeRestriction() {
        return this.ageRestriction;
    }

    public void updateTypeSpecificField(String ageRestriction) {
        setAgeRestriction(ageRestriction);
    }
}
