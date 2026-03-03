import java.util.ArrayList;

public class ManageBooking {
    private ArrayList<Booking> bookings;

   public ManageBooking() {
    this.bookings = new ArrayList<>();
}

    public boolean createBooking(Booking newBooking){
        for (Booking booking: this.bookings) {
            if (booking.getBookingID().equalsIgnoreCase(newBooking.getBookingID())
                    && booking.getEventID().equalsIgnoreCase(newBooking.getEventID())) {
                return false;
            }
        }

        this.bookings.add(newBooking);
        return true;
    }

    public Booking cancelBookingAndPromote(String bookingId, Waitlist waitlist) {

    for (Booking b: bookings) {

        if (b.getBookingID().equalsIgnoreCase(bookingId)) {

            // Check if booking was confirmed BEFORE cancelling
            boolean wasConfirmed =
                    b.getBookingStatus() == Booking.BookingStatus.Confirmed;

            // Cancel booking
            b.setBookingStatus(Booking.BookingStatus.Cancelled);

            // If it was confirmed, promote the next person
            if (wasConfirmed) {
                return waitlist.promoteNext();  // Calls Ansh's method in Waitlist.java
            }

            return null;
        }
    }

    return null;
}

    public void bookingView(){

    }
}
