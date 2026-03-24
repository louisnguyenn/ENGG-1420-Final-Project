import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class BookingManagementView {

    private VBox root;

    public BookingManagementView(MainApp app, UserRegistry registry,
                                 EventController eventController,
                                 ManageBooking manageBooking,
                                 WaitlistRegistry waitlistRegistry) {
        root = new VBox();
        root.setSpacing(10);
        root.setStyle("-fx-padding: 15");

        // Title label at the top
        Label title = new Label("BOOKING MANAGEMENT");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");

        // -- Book an Event section --
        Label bookTitle = new Label("Book an Event:");
        bookTitle.setStyle("-fx-font-weight: bold");

        TextField bookUserField  = new TextField();
        TextField bookEventField = new TextField();

        bookUserField.setPromptText("User ID");
        bookEventField.setPromptText("Event ID");

        Label bookStatus = new Label(); // shows result of booking attempt

        Button bookBtn = new Button("BOOK");
        bookBtn.setOnAction(e -> {

            String userId  = bookUserField.getText().trim();
            String eventId = bookEventField.getText().trim();

            // Make sure both fields are filled in
            if (userId.isEmpty()) {
                bookStatus.setText("Please enter a User ID.");
                return;
            }
            if (eventId.isEmpty()) {
                bookStatus.setText("Please enter an Event ID.");
                return;
            }

            // Look up the user and event
            User user = registry.getUserById(userId);
            Event event = eventController.getEventById(eventId);

            // Validate that both exist
            if (user == null) {
                bookStatus.setText("User not found: " + userId);
                return;
            }
            if (event == null) {
                bookStatus.setText("Event not found: " + eventId);
                return;
            }

            // Cannot book a cancelled event
            if (event.getStatus() == Event.Status.Cancelled) {
                bookStatus.setText("Cannot book a cancelled event.");
                return;
            }

            // Generate a unique booking ID using current time
            String bookingId = "B" + System.currentTimeMillis();

            // Create the booking object
            Booking newBooking = new Booking(bookingId, user.getUserId(),
                    event.getEventId(), LocalDateTime.now().toString(),
                    Booking.BookingStatus.Confirmed);

            // Get the waitlist for this event
            Waitlist waitlist = waitlistRegistry.getOrCreate(event.getEventId());

            // Try to create the booking — status gets set inside createBooking
            boolean created = manageBooking.createBooking(newBooking, user, event);

            if (!created) {
                bookStatus.setText("Booking failed: duplicate booking or booking limit reached.");
                return;
            }

            // Tell the user whether they were confirmed or waitlisted
            if (newBooking.getBookingStatus() == Booking.BookingStatus.Waitlisted) {
                waitlist.addToWaitlist(newBooking);
                bookStatus.setText("Event full — added to waitlist. Booking ID: " + bookingId);
            } else {
                bookStatus.setText("Booking confirmed! Booking ID: " + bookingId);
            }
        });

        // -- Cancel a Booking section --
        Label cancelTitle = new Label("Cancel a Booking:");
        cancelTitle.setStyle("-fx-font-weight: bold");

        TextField cancelIdField    = new TextField();
        TextField cancelEventField = new TextField();

        cancelIdField.setPromptText("Booking ID");
        cancelEventField.setPromptText("Event ID (for waitlist)");

        Label cancelStatus = new Label(); // shows result of cancellation

        Button cancelBtn = new Button("CANCEL BOOKING");
        cancelBtn.setOnAction(e -> {

            String bookingId = cancelIdField.getText().trim();
            String eventId   = cancelEventField.getText().trim();

            // Make sure both fields are filled
            if (bookingId.isEmpty() || eventId.isEmpty()) {
                cancelStatus.setText("Both Booking ID and Event ID are required.");
                return;
            }

            // Check booking exists
            Booking target = manageBooking.getBookingById(bookingId);
            if (target == null) {
                cancelStatus.setText("Booking not found: " + bookingId);
                return;
            }

            // Check it is not already cancelled
            if (target.getBookingStatus() == Booking.BookingStatus.Cancelled) {
                cancelStatus.setText("Booking is already cancelled.");
                return;
            }

            // Cancel and promote next waitlisted user if applicable
            Waitlist waitlist = waitlistRegistry.getOrCreate(eventId);
            Booking promoted = manageBooking.cancelBookingAndPromote(bookingId, waitlist);

            if (promoted != null) {
                cancelStatus.setText("Booking cancelled. Promoted from waitlist: "
                        + promoted.getUserID() + " (Booking: " + promoted.getBookingID() + ")");
            } else {
                cancelStatus.setText("Booking cancelled. No one on waitlist to promote.");
            }
        });

        // -- View User's Bookings section --
        Label viewTitle = new Label("View User's Bookings:");
        viewTitle.setStyle("-fx-font-weight: bold");

        TextField viewUserField = new TextField();
        viewUserField.setPromptText("User ID");

        Label viewResult = new Label();
        viewResult.setWrapText(true);

        Button viewBtn = new Button("VIEW BOOKINGS");
        viewBtn.setOnAction(e -> {

            String userId = viewUserField.getText().trim();

            if (userId.isEmpty()) {
                viewResult.setText("Enter a User ID.");
                return;
            }

            ArrayList<Booking> userBookings = manageBooking.getBookingsByUser(userId);

            if (userBookings.isEmpty()) {
                viewResult.setText("No bookings found for user: " + userId);
            } else {
                // Build a string showing all bookings for this user
                String output = "";
                for (Booking b : userBookings) {
                    output += b.toString() + "\n";
                }
                viewResult.setText(output.trim());
            }
        });

        // -- View Event Roster section --
        Label rosterTitle = new Label("View Event Roster:");
        rosterTitle.setStyle("-fx-font-weight: bold");

        TextField rosterEventField = new TextField();
        rosterEventField.setPromptText("Event ID");

        Label rosterResult = new Label();
        rosterResult.setWrapText(true);

        Button rosterBtn = new Button("VIEW ROSTER");
        rosterBtn.setOnAction(e -> {

            String eventId = rosterEventField.getText().trim();

            if (eventId.isEmpty()) {
                rosterResult.setText("Enter an Event ID.");
                return;
            }

            Event event = eventController.getEventById(eventId);
            if (event == null) {
                rosterResult.setText("Event not found: " + eventId);
                return;
            }

            rosterResult.setText(buildRoster(event, manageBooking, waitlistRegistry));
        });

        // Back button returns to main menu
        Button backBtn = new Button("GO BACK");
        backBtn.setOnAction(e -> {
            app.showMainView();
        });

        // Add everything to the layout
        root.getChildren().addAll(
                title,
                new Separator(),
                bookTitle, bookUserField, bookEventField, bookBtn, bookStatus,
                new Separator(),
                cancelTitle, cancelIdField, cancelEventField, cancelBtn, cancelStatus,
                new Separator(),
                viewTitle, viewUserField, viewBtn, viewResult,
                new Separator(),
                rosterTitle, rosterEventField, rosterBtn, rosterResult,
                new Separator(),
                backBtn
        );
    }

    // Builds a text display of confirmed users and waitlist for an event
    private String buildRoster(Event event, ManageBooking manageBooking,
                               WaitlistRegistry waitlistRegistry) {
        String output = "=== Confirmed ===\n";

        ArrayList<Booking> confirmed = manageBooking.getConfirmedByEvent(event.getEventId());
        if (confirmed.isEmpty()) {
            output += "  (none)\n";
        } else {
            for (Booking b : confirmed) {
                output += "  " + b.getUserID() + " | Booking: " + b.getBookingID() + "\n";
            }
        }

        output += "=== Waitlist ===\n";

        Waitlist wl = waitlistRegistry.getOrCreate(event.getEventId());
        ArrayList<Booking> waitlisted = wl.getWaitlist();
        if (waitlisted.isEmpty()) {
            output += "  (none)\n";
        } else {
            int pos = 1;
            for (Booking b : waitlisted) {
                output += "  " + pos + ". " + b.getUserID()
                        + " | Booking: " + b.getBookingID() + "\n";
                pos++;
            }
        }

        return output.trim();
    }

    public Parent getView() {
        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        return scroll;
    }
}
