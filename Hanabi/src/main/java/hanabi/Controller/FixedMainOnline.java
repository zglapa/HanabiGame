package hanabi.Controller;

import hanabi.Controller.MainMenuWindow;
import hanabi.Controller.SetUpWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Platform.exit;

public class FixedMainOnline extends Application {
    public static void main(String[] args){
        launch(args);
    }
    Parent root;
    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/GameFXML.fxml"));
        stage.setTitle("Hanabi");
        stage.setScene(new Scene(root, 1600, 900));
        stage.show();
    }
}
