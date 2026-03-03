import java.util.ArrayList;

//manages the ordered (first-come, first-served) waitlist for a single event.

public class Waitlist {
    //The event this waitlist belongs to
    private String eventId;

    // Ordered list of waitlisted bookings, index 0 is next to be promoted
    private ArrayList<Booking> waitlist;

    public Waitlist() {
    }

    public Waitlist(String eventId) {
        this.eventId  = eventId;
        this.waitlist = new ArrayList<>();
    }

    //Adds a booking to the end of the waitlist and marks it as Waitlisted.
    //@param booking the booking to add (must belong to this event)
    //@return true if added; false if the user is already on the waitlist or
    //the booking does not belong to this event
    public boolean addToWaitlist(Booking booking) {
        if (!booking.getEventID().equalsIgnoreCase(this.eventId)) {
            return false;
        }

        if (isUserWaitlisted(booking.getUserID())) {
            return false;
        }

        booking.setBookingStatus(Booking.BookingStatus.Waitlisted);
        waitlist.add(booking);
        return true;
    }

    //Returns a copy of the current waitlist in order (position 0 = next up).
    //Each entry includes the Booking object (userId, bookingId, createdAt,
    //status) so the caller can format / display as needed.
    public ArrayList<Booking> getWaitlist() {
        return new ArrayList<>(waitlist);
    }

    //Removes a waitlisted booking by bookingId, preserving the order of
    //remaining entries. The booking's status is set to Cancelled.
    //@param bookingId the ID of the booking to remove
    //@return the removed Booking, or null if not found
    public Booking removeFromWaitlist(String bookingId) {
        for (int i = 0; i < waitlist.size(); i++) {
            Booking b = waitlist.get(i);
            if (b.getBookingID().equalsIgnoreCase(bookingId)) {
                waitlist.remove(i);
                b.setBookingStatus(Booking.BookingStatus.Cancelled);
                return b;
            }
        }
        return null;
    }

    //Promotes the first (oldest) waitlisted booking to Confirmed and removes
    //it from the waitlist. Should be called whenever a Confirmed booking for
    //this event is cancelled and a seat becomes available.
    //@return the promoted Booking (now Confirmed), or null if the waitlist is empty
    public Booking promoteNext() {
        if (waitlist.isEmpty()) {
            return null;
        }

        Booking promoted = waitlist.remove(0);
        promoted.setBookingStatus(Booking.BookingStatus.Confirmed);
        return promoted;
    }

    //Clears the entire waitlist when the associated event is cancelled.
    //Every booking in the waitlist is set to Cancelled.
    public void clearOnEventCancellation() {
        for (Booking b : waitlist) {
            b.setBookingStatus(Booking.BookingStatus.Cancelled);
        }
        waitlist.clear();
    }

    //Returns true if a user already has an entry on this waitlist.
    //Used to enforce the no-duplicate rule (section 1.4 integrity check).
    public boolean isUserWaitlisted(String userId) {
        for (Booking b : waitlist) {
            if (b.getUserID().equalsIgnoreCase(userId)) {
                return true;
            }
        }
        return false;
    }

    //Returns the number of users currently on the waitlist.
    public int size() {
        return waitlist.size();
    }

    //Returns true if there are no users on the waitlist.
    public boolean isEmpty() {
        return waitlist.isEmpty();
    }

    public String getEventId() {
        return eventId;
    }
}