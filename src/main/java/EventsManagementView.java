import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Collection;

public class EventsManagementView {

    private VBox root;

    public EventsManagementView (MainApp app, EventController controller) {
        root = new VBox();

        Label title = new Label("EVENTS MANAGEMENT");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");

        HBox buttonLayout = new HBox();
        buttonLayout.setSpacing(4);
        Button createEventBtn = new Button ("CREATE EVENT");
        createEventBtn.setOnAction(e -> {
            app.showEventFormView(null); // enable create view
        });
        Button backBtn = new Button ("GO BACK");
        backBtn.setOnAction(e -> {
            app.showMainView();
        });
        buttonLayout.getChildren().addAll(backBtn, createEventBtn);

        root.getChildren().addAll(title, buttonLayout);

        // include the edit page opn a button for each event
        for(Event ev: controller.getEventList()) {
            Button b = new Button(ev.getEventId() + " " + ev.getTitle());
            b.setOnAction(e -> {
                app.showEventFormView(ev);
            });
            root.getChildren().add(b);
        }


    }
    public Parent getView() { return root;}
}
