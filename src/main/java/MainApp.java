import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Stage primaryStage;

    private UserRegistry userRegistry;
    private EventController eventController;
    private ManageBooking manageBooking;
    private WaitlistRegistry waitlistRegistry; // one waitlist per event

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;

        userRegistry = new UserRegistry();
        eventController = new EventController();
        manageBooking = new ManageBooking();
        waitlistRegistry = new WaitlistRegistry();

        // Load the starter CSV files into the system
        DataLoader.loadAll(userRegistry, eventController, manageBooking, waitlistRegistry);

        showMainView();

        primaryStage.setTitle("Campus Event Booking System");

        // Save all data to CSV files when the window is closed
        primaryStage.setOnCloseRequest(e -> {
            DataSaver.saveAll(userRegistry, eventController, manageBooking);
        });

        primaryStage.show();
    }

    public void showMainView() {
        ShowMainView view = new ShowMainView(this);
        Scene scene = new Scene(view.getView(), 700, 500);
        primaryStage.setScene(scene);
    }

    public void showManageUserView() {
        UserManagementView view = new UserManagementView(this, userRegistry, manageBooking);
        Scene scene = new Scene(view.getView(), 700, 500);
        primaryStage.setScene(scene);
    }

    public void showEventsManagementView() {
        EventsManagementView view = new EventsManagementView(this, eventController);
        Scene scene = new Scene(view.getView(), 700, 500);
        primaryStage.setScene(scene);
    }

    public void showEventFormView(Event currEvent) {
        EventFormView view = new EventFormView(this, eventController, currEvent);
        Scene scene = new Scene(view.getView(), 700, 500);
        primaryStage.setScene(scene);
    }

    public void showBookingManagementView() {
        BookingManagementView view = new BookingManagementView(
                this, userRegistry, eventController, manageBooking, waitlistRegistry);
        Scene scene = new Scene(view.getView(), 700, 500);
        primaryStage.setScene(scene);
    }

    public void showWaitlistManagementView() {
        WaitlistManagementView view = new WaitlistManagementView(
                this, manageBooking, waitlistRegistry);
        Scene scene = new Scene(view.getView(), 700, 500);
        primaryStage.setScene(scene);
    }

    // Called by EventsManagementView cancel button — cancels event + all its bookings + waitlist
    public void cancelEvent(String eventId) {
        Waitlist waitlist = waitlistRegistry.getOrCreate(eventId);
        eventController.cancelEvent(eventId, manageBooking, waitlist);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
