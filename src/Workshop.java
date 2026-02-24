public class Workshop extends Event {
    private String topic;

    // constructors
    public Workshop() {
        this.topic = "";
    }

    public Workshop(String title, String location, int capacity, String topic) {
        setTitle(title);
        setLocation(location);
        setCapacity(capacity);
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
