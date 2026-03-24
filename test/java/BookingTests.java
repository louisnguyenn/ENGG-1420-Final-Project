import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class BookingTests {
    private UserRegistry userRegistry;
    private ManageBooking manageBooking;
    private WaitlistRegistry waitlistRegistry;
    private Event testEvent;
    private User studentUser;

    // runs before each test to set up a fresh state
    @BeforeEach
    public void setUp() {

        // create registries for testing
        userRegistry = new UserRegistry();
        manageBooking = new ManageBooking();
        waitlistRegistry = new WaitlistRegistry();

        // create a test event with capacity 2
        testEvent = new Workshop("E001", "Test Workshop",
                LocalDateTime.now(), "Room 101", 2,
                Event.Status.Active, "Testing");

        // create a student user
        studentUser = new User("U001", "Alice Smith", "alice@uoguelph.ca", "student");
        userRegistry.addUser(studentUser);
    }

    // Test 1: booking when capacity is available, status should be confirmed
    @Test
    public void testBookingUnderCapacity() {

        // create a booking for the student
        Booking booking = new Booking("B001", "U001", "E001",
                LocalDateTime.now().toString(), Booking.BookingStatus.Confirmed);

        boolean result = manageBooking.createBooking(booking, studentUser, testEvent);

        // booking should succeed and be confirmed
        assertTrue(result, "Booking should succeed when event has capacity");
        assertEquals(Booking.BookingStatus.Confirmed, booking.getBookingStatus(),
                "Booking status should be Confirmed when capacity is available");
    }

    // Test 2: booking when event is full, status should be waitlisted
    @Test
    public void testBookingWhenFull() {

        // add 2 users to fill the event (capacity = 2)
        User user2 = new User("U002", "Bob Chen", "bob@uoguelph.ca", "student");
        User user3 = new User("U003", "Clara Diaz", "clara@uoguelph.ca", "student");
        userRegistry.addUser(user2);
        userRegistry.addUser(user3);

        // fill both seats
        Booking b1 = new Booking("B001", "U001", "E001",
                LocalDateTime.now().toString(), Booking.BookingStatus.Confirmed);
        Booking b2 = new Booking("B002", "U002", "E001",
                LocalDateTime.now().toString(), Booking.BookingStatus.Confirmed);
        manageBooking.createBooking(b1, studentUser, testEvent);
        manageBooking.createBooking(b2, user2, testEvent);

        // try to book when full
        Booking b3 = new Booking("B003", "U003", "E001",
                LocalDateTime.now().toString(), Booking.BookingStatus.Confirmed);
        boolean result = manageBooking.createBooking(b3, user3, testEvent);

        // booking should succeed but be waitlisted
        assertTrue(result, "Booking should succeed even when event is full");
        assertEquals(Booking.BookingStatus.Waitlisted, b3.getBookingStatus(),
                "Booking status should be Waitlisted when event is full");
    }

    // Test 3: cancel confirmed booking, first waitlisted user gets promoted
    @Test
    public void testCancelBookingPromotesWaitlist() {

        // add users
        User user2 = new User("U002", "Bob Chen", "bob@uoguelph.ca", "student");
        User user3 = new User("U003", "Clara Diaz", "clara@uoguelph.ca", "student");
        userRegistry.addUser(user2);
        userRegistry.addUser(user3);

        // fill the event
        Booking b1 = new Booking("B001", "U001", "E001",
                LocalDateTime.now().toString(), Booking.BookingStatus.Confirmed);
        Booking b2 = new Booking("B002", "U002", "E001",
                LocalDateTime.now().toString(), Booking.BookingStatus.Confirmed);
        manageBooking.createBooking(b1, studentUser, testEvent);
        manageBooking.createBooking(b2, user2, testEvent);

        // add user3 to waitlist
        Booking b3 = new Booking("B003", "U003", "E001",
                LocalDateTime.now().toString(), Booking.BookingStatus.Confirmed);
        manageBooking.createBooking(b3, user3, testEvent);

        // add b3 to the waitlist object
        Waitlist waitlist = waitlistRegistry.getOrCreate("E001");
        waitlist.addToWaitlist(b3);

        // cancel b1 (confirmed), should promote b3
        Booking promoted = manageBooking.cancelBookingAndPromote("B001", waitlist);

        // b1 should be cancelled status
        assertEquals(Booking.BookingStatus.Cancelled, b1.getBookingStatus(),
                "Cancelled booking should have status Cancelled");

        // b3 should be promoted to confirmed
        assertNotNull(promoted, "A booking should have been promoted from the waitlist");
        assertEquals(Booking.BookingStatus.Confirmed, promoted.getBookingStatus(),
                "Promoted booking should have status Confirmed");
    }

    // test 4: duplicate booking, should be rejected
    @Test
    public void testDuplicateBookingPrevention() {

        // book the event once
        Booking b1 = new Booking("B001", "U001", "E001",
                LocalDateTime.now().toString(), Booking.BookingStatus.Confirmed);
        manageBooking.createBooking(b1, studentUser, testEvent);

        // try to book the same event again with the same user
        Booking b2 = new Booking("B002", "U001", "E001",
                LocalDateTime.now().toString(), Booking.BookingStatus.Confirmed);
        boolean result = manageBooking.createBooking(b2, studentUser, testEvent);

        // second booking should be rejected
        assertFalse(result, "Duplicate booking for same user and event should be rejected");
    }
}
