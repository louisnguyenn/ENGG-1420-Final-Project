import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class UserManagementView {

    private VBox root;

    public UserManagementView(MainApp app) {
        root = new VBox();
        root.setSpacing(15);

        Label title = new Label("USER MANAGEMENT");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");

        HBox buttonLayout = new HBox();
        buttonLayout.setSpacing(4);
        Button createUserBtn = new Button("CREATE USER");
        Button backBtn = new Button("GO BACK");
        backBtn.setOnAction(e -> {
            app.showMainView();
        });
        buttonLayout.getChildren().addAll(backBtn, createUserBtn);

        root.getChildren().addAll(title, buttonLayout);
    }

    public Parent getView() {
        return root;
    }
}
