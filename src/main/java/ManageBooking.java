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

            boolean wasConfirmed =
                b.getBookingStatus() == Booking.BookingStatus.Confirmed;

            // cancel it
            b.setBookingStatus(Booking.BookingStatus.Cancelled);

            // if confirmed, promote next from waitlist
            if (wasConfirmed) {
                Booking promoted = waitlist.promoteNext(); // removes from waitlist + sets Confirmed

                if (promoted != null) {
                    // add promoted to bookings list IF it's not already there
                    boolean alreadyExists = false;
                    for (Booking existing: bookings) {
                        if (existing.getBookingID().equalsIgnoreCase(promoted.getBookingID())
                                && existing.getEventID().equalsIgnoreCase(promoted.getEventID())) {
                            alreadyExists = true;
                            break;
                        }
                    }
                    if (!alreadyExists) {
                        bookings.add(promoted);
                    }
                }

                return promoted;
            }

            return null;
        }
    }

    return null;
}

    public void bookingView(){

    }
}
