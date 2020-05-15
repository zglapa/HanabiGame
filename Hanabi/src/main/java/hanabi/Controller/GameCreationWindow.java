package hanabi.Controller;

import hanabi.Model.Board;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class GameCreationWindow {
    public Board board;
    public Stage stage;
    public boolean hasRainbows;
    public boolean exit=false;
    void run() throws IOException {
        stage=new Stage();
        Parent setUpRoot;
        setUpRoot = FXMLLoader.load(getClass().getResource("/GameCreationFXML.fxml"));
        stage.setTitle("Create Game");
        stage.setScene(new Scene(setUpRoot, 1600, 900));
        stage.showAndWait();
    }
}
