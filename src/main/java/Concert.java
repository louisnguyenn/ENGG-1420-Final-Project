import java.time.LocalDateTime;

public class Concert extends Event {
    private String ageRestriction;

    //    no-arg constructor
    public Concert() {
        this.ageRestriction = "";
    }

    //    full constructor
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
    public String getAgeRestriction() {
        return this.ageRestriction;
    }

    public void updateTypeSpecificField(String ageRestriction) {
        setAgeRestriction(ageRestriction);
    }

    @Override
    public String toString() {
        return super.toString() + ", Type: Concert, Age Restriction: " + ageRestriction;
    }
}
