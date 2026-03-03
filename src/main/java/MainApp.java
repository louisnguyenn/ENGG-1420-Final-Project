import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

// STAGE -> SCENE -> H/V Box

// Main app will be the main stage and what we use to switch scenes
public class MainApp extends Application {
    private Stage primaryStage; // main windows of the program
    private EventController eventController;
    private ManageBooking manageBooking;
    private Waitlist waitlist;

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        eventController = new EventController();
        manageBooking = new ManageBooking();
        waitlist = new Waitlist("EVENT");

        // show something
        showMainView();

        primaryStage.setTitle("Main App 123"); // title is on the window
        primaryStage.show();
    }

    public void showMainView() {
        ShowMainView view = new ShowMainView(this);
        Scene scene = new Scene(view.getView(), 600, 400);
        primaryStage.setScene(scene);
    }

    public void showManageUserView() {
        UserManagementView view = new UserManagementView(this);
        Scene scene = new Scene(view.getView(), 600, 400);
        primaryStage.setScene(scene);
    }

    public void showEventsManagementView() {
        EventsManagementView view = new EventsManagementView(this, eventController);
        Scene scene = new Scene(view.getView(), 600, 400);
        primaryStage.setScene(scene);
    }

    // If you pass in null, we enable create mode, if you pass in an event then we use edit mode
    public void showEventFormView(Event currEvent) {
        EventFormView view = new EventFormView(this, eventController, currEvent);
        Scene scene = new Scene(view.getView(), 600, 400);
        primaryStage.setScene(scene);
    }

}

