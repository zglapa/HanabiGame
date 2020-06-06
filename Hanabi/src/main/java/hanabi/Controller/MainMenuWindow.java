package hanabi.Controller;

import hanabi.Model.Board;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class MainMenuWindow {
    public static int action;
    public static Stage stage;
    public static void run() throws IOException {
        stage=new Stage();
        Parent setUpRoot = FXMLLoader.load(HanabiMain.class.getResource("/MainMenuFXML.fxml"));
        stage.setTitle("Hanabi");
        stage.setScene(new Scene(setUpRoot, 1600, 900));
        System.out.println(stage);
        action=0;
        stage.showAndWait();
    }
}
