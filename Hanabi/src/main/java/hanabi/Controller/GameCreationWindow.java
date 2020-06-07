package hanabi.Controller;

import hanabi.Model.Board;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class GameCreationWindow {
    public Board board;
    public static boolean end;
    public static Board setUpBoard;
    public static void run() throws IOException {
        Parent setUpRoot;
        setUpRoot = FXMLLoader.load(HanabiMain.class.getResource("/GameCreationFXML.fxml"));
        Stage stage=new Stage();
        HanabiMain.gameInformation.settingsStage = stage;
        stage.setOnCloseRequest(e->HanabiMain.gameInformation.exit=true);
        stage.setTitle("Create Game");
        stage.setScene(new Scene(setUpRoot, 1600, 900));
        end=true;
        stage.showAndWait();
    }
}
