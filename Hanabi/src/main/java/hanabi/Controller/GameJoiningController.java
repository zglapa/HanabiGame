package hanabi.Controller;

import hanabi.Model.Board;
import hanabi.Model.Deck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class GameJoiningController {
    @FXML TextField name1;
    @FXML TextField ID;

    public void startGame(ActionEvent actionEvent) {
        HanabiMain.gameJoiningWindow.name = name1.getText();
        HanabiMain.gameJoiningWindow.addressID = ID.getText();
        HanabiMain.gameJoiningWindow.stage.close();
    }
}
