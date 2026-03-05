package com.guelph.engg1420finalprojectjavafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

// STAGE -> SCENE -> H/V Box

// Main app will be the main stage and what we use to switch scenes and coordinate controllers and views
// It extends
public class MainApp extends Application { //Mainapp inherits the application
    private Stage primaryStage; // main windows of the program

    // ALL CONTROLLERS (Handles
    private EventController eventController;
    private UserController userController;


    @Override
    public void start(Stage stage) throws Exception { //the interface starts
        this.primaryStage = stage; //For our mainstage
        eventController = new EventController(); //creation of an eventController to combine GUI with backend code for User Management
        userController = new UserController(); //Our controller of UserController to combine GUI with backend code for User Management

        // show something
        showMainView();

        primaryStage.setTitle("ENGG-1420 Final Project"); // title is on the window
        primaryStage.show();
    }

    public void showMainView() { //to show the mainMenu View, "this" represents the mainApp function
        ShowMainView view = new ShowMainView(this); //this will display the mainMenu of all the managements
        Scene scene = new Scene(view.getView(), 600, 400); //How the viewers sees the pixels
        primaryStage.setScene(scene); //Sets the scene for users view
    }

    public void showEventsManagementView() {
        EventsManagementView view = new EventsManagementView(this, eventController); //Displays eventsManagement
        Scene scene = new Scene(view.getView(), 600, 400);
        primaryStage.setScene(scene);
    }

    // If you pass in null, we enable creatEvent mode, if you pass in an event then we use editEvents mode
    public void showEventFormView(Event currEvent) {
        EventFormView view = new EventFormView(this, eventController, currEvent); //We set as parameter in order
        Scene scene = new Scene(view.getView(), 600, 400);
        primaryStage.setScene(scene);
    }

    public void showManageUserView() {
        UserManagementView view = new UserManagementView(this, userController); //Displays the userManagement View
        Scene scene = new Scene(view.getView(), 600, 400);
        primaryStage.setScene(scene);
    }

    public void showUserFormView(User currUser) {
        UserFormView view = new UserFormView(this, userController, currUser); //Will display the views, and accessing
        Scene scene = new Scene(view.getView(), 600, 400);
        primaryStage.setScene(scene);
    }
}
