import java.util.ArrayList;

public class ManageBooking {
    private ArrayList<Booking> bookings;

    public ManageBooking() {
        this.bookings = new ArrayList<>();
    }

    // Adds a pre-built Booking directly — used by DataLoader at startup
    // Skips all rule checks since the CSV data is already validated
    public void loadBooking(Booking booking) {
        this.bookings.add(booking);
    }

    // Creates a new booking for a user at an event
    // Returns false if the event is cancelled, user already booked, or user is at their limit
    public boolean createBooking(Booking newBooking, User user, Event event) {

        // Event must be active to book
        if (event.getStatus() != Event.Status.Active) {
            return false;
        }

        // Check if user already has a booking for this event
        for (Booking booking : this.bookings) {
            if (booking.getUserID().equalsIgnoreCase(newBooking.getUserID())
                    && booking.getEventID().equalsIgnoreCase(newBooking.getEventID())
                    && booking.getBookingStatus() != Booking.BookingStatus.Cancelled) {
                return false; // duplicate booking
            }
        }

        // Count how many confirmed bookings this user already has
        int confirmedCount = 0;
        for (Booking booking : this.bookings) {
            if (booking.getUserID().equalsIgnoreCase(user.getUserId())
                    && booking.getBookingStatus() == Booking.BookingStatus.Confirmed) {
                confirmedCount++;
            }
        }

        // If user is at their limit, reject the booking
        if (confirmedCount >= user.getMaxBookings()) {
            return false;
        }

        // Count how many confirmed seats are taken for this event
        int confirmedForEvent = 0;
        for (Booking booking : this.bookings) {
            if (booking.getEventID().equalsIgnoreCase(event.getEventId())
                    && booking.getBookingStatus() == Booking.BookingStatus.Confirmed) {
                confirmedForEvent++;
            }
        }

        // If event has space, confirm the booking — otherwise waitlist it
        if (confirmedForEvent < event.getCapacity()) {
            newBooking.setBookingStatus(Booking.BookingStatus.Confirmed);
        } else {
            newBooking.setBookingStatus(Booking.BookingStatus.Waitlisted);
        }

        this.bookings.add(newBooking);
        return true;
    }

    // Cancels a booking and promotes the first waitlisted user if the booking was confirmed
    public Booking cancelBookingAndPromote(String bookingId, Waitlist waitlist) {

        for (Booking b : bookings) {

            if (b.getBookingID().equalsIgnoreCase(bookingId)) {

                boolean wasConfirmed =
                        b.getBookingStatus() == Booking.BookingStatus.Confirmed;

                // cancel the booking
                b.setBookingStatus(Booking.BookingStatus.Cancelled);

                // if it was confirmed, promote the next person on the waitlist
                if (wasConfirmed) {
                    Booking promoted = waitlist.promoteNext();

                    if (promoted != null) {
                        // add promoted booking to list if not already there
                        boolean alreadyExists = false;
                        for (Booking existing : bookings) {
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

                // if it was waitlisted, just remove it from the waitlist
                waitlist.removeFromWaitlist(bookingId);
                return null;
            }
        }

        return null;
    }

    // Cancels all bookings for a given event (used when event is cancelled)
    public void cancelAllForEvent(String eventId) {
        for (Booking b : bookings) {
            if (b.getEventID().equalsIgnoreCase(eventId)
                    && b.getBookingStatus() != Booking.BookingStatus.Cancelled) {
                b.setBookingStatus(Booking.BookingStatus.Cancelled);
            }
        }
    }

    // Returns all bookings for a specific user
    public ArrayList<Booking> getBookingsByUser(String userId) {
        ArrayList<Booking> result = new ArrayList<>();
        for (Booking b : bookings) {
            if (b.getUserID().equalsIgnoreCase(userId)) {
                result.add(b);
            }
        }
        return result;
    }

    // Returns all confirmed bookings for a specific event
    public ArrayList<Booking> getConfirmedByEvent(String eventId) {
        ArrayList<Booking> result = new ArrayList<>();
        for (Booking b : bookings) {
            if (b.getEventID().equalsIgnoreCase(eventId)
                    && b.getBookingStatus() == Booking.BookingStatus.Confirmed) {
                result.add(b);
            }
        }
        return result;
    }

    // Finds and returns a booking by its ID
    public Booking getBookingById(String bookingId) {
        for (Booking b : bookings) {
            if (b.getBookingID().equalsIgnoreCase(bookingId)) {
                return b;
            }
        }
        return null; // not found
    }

    // Returns a copy of all bookings
    public ArrayList<Booking> getAllBookings() {
        return new ArrayList<>(bookings);
    }
}