public class Booking {
    private String eventID;
    private String bookingID;
    private String userID;
    private String createdAt;
    public enum BookingStatus { Confirmed, Waitlisted, Cancelled }
    private BookingStatus bookingStatus;

    public String getEventID() {
        return eventID;
    }

    public String getBookingID() {
        return bookingID;
    }

    public String getUserID() {
        return userID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Booking(){

    }

    public Booking(String bookingID, String userID, String eventID, String createdAt, BookingStatus bookingStatus){
        this.bookingID = bookingID;
        this.userID = userID;
        this.eventID = eventID;
        this.createdAt = createdAt;
        this.bookingStatus = bookingStatus;
    }

    @Override
    public String toString() {
        return "BookingID: " + bookingID
                + ", UserID: " + userID
                + ", EventID: " + eventID
                + ", Created: " + createdAt
                + ", Status: " + bookingStatus;
    }
}
