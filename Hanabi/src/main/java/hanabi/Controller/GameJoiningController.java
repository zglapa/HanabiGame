package hanabi.Controller;

import hanabi.Model.Board;
import hanabi.Model.Deck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class GameJoiningController {
    @FXML TextField name1;
    @FXML TextField ID;

    public void startGame(ActionEvent actionEvent) {
        HanabiMain.gameInformation.playerName = name1.getText();
        HanabiMain.gameInformation.serverID = ID.getText();
        HanabiMain.gameInformation.settingsStage.close();
    }
}
