public class Seminar extends Event {
    private String speakerName;

//    constructors
    public Seminar() {
        this.speakerName = "";
    }

    public Seminar(String eventId, String title, LocalDateTime dateTime, String location,
                   int capacity, Status status
                   String speakerName) {
        super(eventId, title, dateTime, location, capacity, status);
        setSpeakerName(speakerName);
    }

//    setter
    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

//    getter
    public void getSpeakerName() {
        return this.speakerName;
    }
}
