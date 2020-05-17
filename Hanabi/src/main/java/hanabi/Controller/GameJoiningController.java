package hanabi.Controller;

import hanabi.Model.Board;
import hanabi.Model.Deck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;


public class GameJoiningController {
    @FXML TextField name1;
    @FXML TextField ID;


    public void startGame(ActionEvent actionEvent) {
        String finalName = ( name1.getText().equals(""))? Board.randomNames(1)[0] :name1.getText();

        if(finalName.length()>20) {
            AlertBox.display("Wrong input", "Name must not exceed 20 characters!");
            return;
        }

        HanabiMain.gameInformation.playerName = finalName;
        HanabiMain.gameInformation.serverID = ID.getText();
        HanabiMain.gameInformation.settingsStage.close();
    }
}
