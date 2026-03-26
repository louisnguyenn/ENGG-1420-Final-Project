import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class WaitlistManagementView {

    private VBox root;

    public WaitlistManagementView(MainApp app, ManageBooking manageBooking,
                                  WaitlistRegistry waitlistRegistry) {
        root = new VBox();
        root.setSpacing(10);
        root.setStyle("-fx-padding: 15");

        // Title label at the top
        Label title = new Label("WAITLIST MANAGEMENT");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");

        // -- View Waitlist section --
        Label viewTitle = new Label("View Waitlist:");
        viewTitle.setStyle("-fx-font-weight: bold");

        TextField viewEventField = new TextField();
        viewEventField.setPromptText("Event ID");

        Label waitlistResult = new Label();
        waitlistResult.setWrapText(true);

        Button viewBtn = new Button("VIEW WAITLIST");
        viewBtn.setOnAction(e -> {

            String eventId = viewEventField.getText().trim();

            if (eventId.isEmpty()) {
                waitlistResult.setText("Enter an Event ID.");
                return;
            }

            Waitlist wl = waitlistRegistry.getOrCreate(eventId);
            ArrayList<Booking> list = wl.getWaitlist();

            if (list.isEmpty()) {
                waitlistResult.setText("Waitlist is empty for event: " + eventId);
            } else {
                // Build a numbered list of waitlisted users
                String output = "";
                int pos = 1;
                for (Booking b : list) {
                    output += pos + ". UserID: " + b.getUserID()
                            + " | BookingID: " + b.getBookingID()
                            + " | Created: " + b.getCreatedAt() + "\n";
                    pos++;
                }
                waitlistResult.setText(output.trim());
            }
        });

        // -- Remove Waitlisted Booking section --
        Label removeTitle = new Label("Remove Waitlisted Booking:");
        removeTitle.setStyle("-fx-font-weight: bold");

        TextField removeBookingField = new TextField();
        TextField removeEventField   = new TextField();

        removeBookingField.setPromptText("Booking ID");
        removeEventField.setPromptText("Event ID");

        Label removeStatus = new Label(); // shows result of removal

        Button removeBtn = new Button("REMOVE FROM WAITLIST");
        removeBtn.setOnAction(e -> {

            String bookingId = removeBookingField.getText().trim();
            String eventId   = removeEventField.getText().trim();

            // Make sure both fields are filled
            if (bookingId.isEmpty() || eventId.isEmpty()) {
                removeStatus.setText("Both Booking ID and Event ID are required.");
                return;
            }

            Waitlist wl = waitlistRegistry.getOrCreate(eventId);

            // Remove from the waitlist — also sets booking status to Cancelled
            Booking removed = wl.removeFromWaitlist(bookingId);

            if (removed != null) {
                // Sync the cancellation into ManageBooking's record
                Booking inSystem = manageBooking.getBookingById(bookingId);
                if (inSystem != null) {
                    inSystem.setBookingStatus(Booking.BookingStatus.Cancelled);
                }
                removeStatus.setText("Removed from waitlist. Booking " + bookingId + " cancelled.");
            } else {
                removeStatus.setText("Booking ID not found on waitlist for event: " + eventId);
            }
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
                viewTitle, viewEventField, viewBtn, waitlistResult,
                new Separator(),
                removeTitle, removeBookingField, removeEventField, removeBtn, removeStatus,
                new Separator(),
                backBtn
        );
    }

    public Parent getView() {
        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        return scroll;
    }
}
