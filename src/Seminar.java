public class Seminar extends Event {
    private String speakerName;

//    constructors
    public Seminar() {
        this.speakerName = "";
    }

    public Seminar(String speakerName) {
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
