package hanabi.Controller;

import hanabi.Model.Board;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class MultiplayerModeWindow {

    public static int action = 0;
    public static Stage stage;
    public void setUp() throws IOException {
        stage=new Stage();
        Parent setUpRoot = FXMLLoader.load(getClass().getResource("/MultiplayerModeFXML.fxml"));
        stage.setTitle("Hanabi");
        stage.setScene(new Scene(setUpRoot, 1600, 900));
        System.out.println(stage);
        stage.showAndWait();
    }
}
