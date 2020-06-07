package hanabi.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartGame {
    public static void run(Stage stage) throws IOException {
        if(HanabiMain.gameInformation.exit)
            return;
        Parent root = FXMLLoader.load(HanabiMain.class.getResource("/GameFXML.fxml"));
        HanabiMain.gameInformation.gameStage=stage;
        stage.setTitle("Hanabi");
        stage.setScene(new Scene(root, 1600, 900));
        stage.show();
    }
}
