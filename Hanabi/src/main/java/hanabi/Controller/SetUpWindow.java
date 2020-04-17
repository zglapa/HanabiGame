package hanabi.Controller;

import hanabi.Model.Board;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SetUpWindow {
    public Board board;
    public Stage stage;
    public boolean hasRainbows;
    public void setUp() throws IOException {
        stage=new Stage();
        Parent setUpRoot;
        setUpRoot = FXMLLoader.load(getClass().getResource("/setUpFXML.fxml"));
        stage.setTitle("Set Up");
        stage.setScene(new Scene(setUpRoot, 1600, 900));
        stage.showAndWait();
    }
}
