import java.util.ArrayList;

public class ManageBooking {
    private ArrayList<Booking> bookings;

    public ManageBooking(ArrayList<Booking> bookings) {
        this.bookings = new ArrayList<>();
    }

    /*
    public boolean createBooking(Booking newBooking){
        for (Booking booking : this.bookings) {
            if (booking.getBookingID().equalsIgnoreCase(newBooking.getBookingID())
                    && booking.getEventID().equalsIgnoreCase(newBooking.getEventID())) {
                int confirmedBookingCount = 0;
                for (int i = booking.getNumOfBookings(); i > 0; i--){
                    if (booking.getBookingStatus() == Booking.BookingStatus.Confirmed) {
                        confirmedBookingCount++;
                    }
                }
                if (confirmedBookingCount >= User.booking.getMaxBookings()){
                    return false;
                }
            }
        }

        this.bookings.add(newBooking);
        return true;
    }


     */

    public boolean createBooking(Booking newBooking, User user) {

        // 1) Duplicate check: same USER cannot book same EVENT twice (unless previous is Cancelled)
        for (Booking booking : this.bookings) {
            if (booking.getUserID().equalsIgnoreCase(newBooking.getUserID())
                    && booking.getEventID().equalsIgnoreCase(newBooking.getEventID())
                    && booking.getBookingStatus() != Booking.BookingStatus.Cancelled) {
                return false; // duplicate booking for same event
            }
        }

        // 2) Count how many CONFIRMED bookings this user currently has
        int confirmedBookingCount = 0;
        for (Booking booking : this.bookings) {
            if (booking.getUserID().equalsIgnoreCase(newBooking.getUserID())
                    && booking.getBookingStatus() == Booking.BookingStatus.Confirmed) {
                confirmedBookingCount++;
            }
        }

        // 3) Max booking rule based on user type (1 / 3 / 5)
        int maxBookings = user.getMaxBookings();
        if (confirmedBookingCount >= maxBookings) {
            return false;
        }

        // (Optional) If you also need capacity-based confirmed/waitlisted:
        // If you have event capacity somewhere, you would set:
        // newBooking.setBookingStatus(Confirmed or Waitlisted);

        this.bookings.add(newBooking);
        return true;
    }

    public Booking bookingCancel(Booking newBooking){
        if (newBooking.getBookingStatus() == Booking.BookingStatus.Confirmed){
            newBooking.setBookingStatus(Booking.BookingStatus.Cancelled);
            return Waitlist.promoteNext();
        }

        else if (newBooking.getBookingStatus() == Booking.BookingStatus.Waitlisted){
            newBooking.setBookingStatus(Booking.BookingStatus.Cancelled);
        }
        return null;
    }


    public ArrayList<Booking> viewUserBookings(String userID) {

        ArrayList<Booking> userBookings = new ArrayList<>();

        for (Booking booking : this.bookings) {

            if (booking.getUserID().equalsIgnoreCase(userID)) {
                userBookings.add(booking);
            }

        }

        return userBookings;
    }

    public ArrayList<ArrayList<Booking>> viewEventRoster(String eventID) {

        ArrayList<Booking> confirmed = new ArrayList<>();
        ArrayList<Booking> waitlist = new ArrayList<>();

        for (Booking booking : this.bookings) {

            if (booking.getEventID().equalsIgnoreCase(eventID)
                    && booking.getBookingStatus() != Booking.BookingStatus.Cancelled) {

                if (booking.getBookingStatus() == Booking.BookingStatus.Confirmed) {
                    confirmed.add(booking);
                }
                else if (booking.getBookingStatus() == Booking.BookingStatus.Waitlisted) {
                    waitlist.add(booking);
                }
            }
        }

        ArrayList<ArrayList<Booking>> result = new ArrayList<>();
        result.add(confirmed);  // index 0
        result.add(waitlist);   // index 1

        return result;
    }
}
