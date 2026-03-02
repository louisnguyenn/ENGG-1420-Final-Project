import java.util.ArrayList;

public class ManageBooking {
    private ArrayList<Booking> bookings;

    public ManageBooking(ArrayList<Booking> bookings) {
        bookings = new ArrayList<>();
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

    public void bookingCancel(){
        //  this.bookingStatus = BookingStatus.Cancelled;
    }

    public void bookingView(){

    }
}
