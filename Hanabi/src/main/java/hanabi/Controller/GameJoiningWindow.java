package hanabi.Controller;

import hanabi.Model.Board;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameJoiningWindow {
    public Stage stage;
    public String name;
    public String addressID;
    public boolean exit=false;
    public void run() throws IOException {
        stage=new Stage();
        Parent setUpRoot;
        setUpRoot = FXMLLoader.load(getClass().getResource("/GameJoiningFXML.fxml"));
        stage.setTitle("Set Up");
        stage.setScene(new Scene(setUpRoot, 1600, 900));
        stage.showAndWait();
    }
}
