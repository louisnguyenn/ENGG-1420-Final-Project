public class Workshop extends Event {
    private String topic;

    // constructors
    public Workshop() {
        this.topic = "";
    }

    public Workshop(String eventId, String title, LocalDateTime dateTime, String location,
                    int capacity, Status status
                    String topic) {
        super(eventId, title, dateTime, location, capacity, status);
        setTopic(topic);
    }

//    setter
    public void setTopic(String topic) {
        this.topic = topic;
    }

//    getter
    public String getTopic() {
        return this.topic;
    }
}
