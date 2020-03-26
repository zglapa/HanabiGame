package hanabi;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    Button button;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("TCS");
        button = new Button("Click to access Tanie Czyszczenie Sprzatanie");

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene scene = new Scene(layout,500,500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}