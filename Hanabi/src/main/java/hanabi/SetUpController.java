package hanabi;

import hanabi.Model.Board;
import hanabi.Model.Deck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SetUpController {
    @FXML Slider numOfPlayers;
    @FXML Slider randomOrder;
    @FXML Slider numOfCards;
    @FXML Slider hasRainbows;
    @FXML Button play;

    public void adjustCards(MouseEvent mouseEvent) {
        if(numOfPlayers.getValue()<=3.0)
            numOfCards.setValue(5.0);
        else
            numOfCards.setValue(4.0);
    }

    public void startGame(ActionEvent actionEvent) {
        int players=new Double(numOfPlayers.getValue()).intValue();
        int cards= new Double(numOfCards.getValue()).intValue();
        boolean rainbow= (hasRainbows.getValue()==1.0);
        boolean random= (randomOrder.getValue()==1.0);

        HanabiMain.setUpWindow.board = new Board(players, 40, 8, 8, cards, new Deck(true, rainbow, true),
                random, Board.randomNames(players));
        HanabiMain.setUpWindow.hasRainbows = rainbow;
        HanabiMain.setUpWindow.stage.close();
    }
}
