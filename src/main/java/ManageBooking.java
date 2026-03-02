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
                if (confirmedBookingCount >= booking.getMaxBookings()){
                    return false;
                }
            }
        }

        this.bookings.add(newBooking);
        return true;
    }

    public boolean bookingCancel(Booking newBooking){
        newBooking.setBookingStatus(Booking.BookingStatus.Cancelled);
        return true;
    }

    public boolean viewBooking(Booking newBooking){

    }

    public void bookingView(){

    }
}
