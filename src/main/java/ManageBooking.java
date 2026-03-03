import java.util.ArrayList;

public class ManageBooking {
    private ArrayList<Booking> bookings;

    public ManageBooking(ArrayList<Booking> bookings) {
        this.bookings = new ArrayList<>();
    }


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
