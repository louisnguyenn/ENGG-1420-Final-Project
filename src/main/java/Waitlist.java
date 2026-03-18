import java.util.ArrayList;

//class manages the waitlist for a single event.

public class Waitlist {
    //The event this waitlist belongs to [event id]
    private String eventId;

    // Ordered list of waitlisted bookings, index 0 is next to be promoted
    private ArrayList<Booking> waitlist;

    //allows for empty, blank waitlist
    public Waitlist() {
        this.waitlist = new ArrayList<>();
    }

    //allows for waitlist to have event assigned to it
    public Waitlist(String eventId) {
        this.eventId  = eventId;
        this.waitlist = new ArrayList<>();
    }

    public boolean addToWaitlist(Booking booking) {
        //if this waitlist has no eventId assigned, or booking doesn't match, refuse
        if (this.eventId == null || !booking.getEventID().equalsIgnoreCase(this.eventId)) {
            return false;
        }
        //if user already in line, refuse
        if (isUserWaitlisted(booking.getUserID())) {
            return false;
        }
        //append to end of line & set as waitlisted
        booking.setBookingStatus(Booking.BookingStatus.Waitlisted);
        waitlist.add(booking);
        return true;
    }

    //shows a copy of the current line
    public ArrayList<Booking> getWaitlist() {
        return new ArrayList<>(waitlist);
    }

    //removing someone from a line
    public Booking removeFromWaitlist(String bookingId) {
        //scans waitlist for matching reservation id &
        for (int i = 0; i < waitlist.size(); i++) {
            //gets booking for array pos.
            Booking b = waitlist.get(i);
            //if required booking id = bookingid[pos], remove
            if (b.getBookingID().equalsIgnoreCase(bookingId)) {
                waitlist.remove(i);
                //set to cancelled
                b.setBookingStatus(Booking.BookingStatus.Cancelled);
                //returns booking
                return b;
            }
        }
        //if none matching retunr null
        return null;
    }

    //automatically promotes next person in line
    public Booking promoteNext() {
        if (waitlist.isEmpty()) {
            return null;
        }
        //removes first person from line
        Booking promoted = waitlist.remove(0);
        //sets their booking to confirmed
        promoted.setBookingStatus(Booking.BookingStatus.Confirmed);
        //returns "promoted" which is the entire Booking of someone
        return promoted;
    }

    //cancels entire event
    public void clearOnEventCancellation() {
        //goes through every person in line and wipes to Cancelled
        for (Booking b : waitlist) {
            b.setBookingStatus(Booking.BookingStatus.Cancelled);
        }
        //clears array
        waitlist.clear();
    }

    //checks if user is already waitlisted
    public boolean isUserWaitlisted(String userId) {
        //goes through line and checks for the parameter
        for (Booking b : waitlist) {
            if (b.getUserID().equalsIgnoreCase(userId)) {
                return true;
            }
        }
        return false;
    }

    //returns the number of users currently on the waitlist
    public int size() {
        return waitlist.size();
    }

    //returns true if there are no users on the waitlist
    public boolean isEmpty() {
        return waitlist.isEmpty();
    }

    public String getEventId() {
        return eventId;
    }
}