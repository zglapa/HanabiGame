package hanabi.Controller;

import hanabi.Model.Board;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

import static javafx.application.Platform.exit;


public class GameCreationWindow {
    public Board board;
    public static boolean end;
    public static boolean exit;
    public static Board setUpBoard;
    public static void run() throws IOException {
        Parent setUpRoot;
        Stage stage=new Stage();
        HanabiMain.gameInformation.settingsStage = stage;
        setUpRoot = FXMLLoader.load(HanabiMain.class.getResource("/GameCreationFXML.fxml"));

        stage.setOnCloseRequest(e->HanabiMain.gameInformation.exit=true);
        stage.setTitle("Create Game");
        stage.setScene(new Scene(setUpRoot, 1600, 900));
        end=true;
        exit=false;
        stage.showAndWait();
    }

}
