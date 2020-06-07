package hanabi.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameJoiningWindow {
    public static boolean end;
    public static void run() throws IOException {
        Parent setUpRoot;
        setUpRoot = FXMLLoader.load(HanabiMain.class.getResource("/GameJoiningFXML.fxml"));
        Stage stage=new Stage();
        stage.setResizable(false);
        HanabiMain.gameInformation.settingsStage = stage;
        stage.setOnCloseRequest(e->HanabiMain.gameInformation.exit=true);
        stage.setTitle("Join Game");
        stage.setScene(new Scene(setUpRoot, 1600, 900));
        end=true;
        stage.showAndWait();
    }
}
