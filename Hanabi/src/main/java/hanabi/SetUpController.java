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
import javafx.stage.Stage;

import java.io.IOException;

public class SetUpController {
    @FXML Slider numOfPlayers;
    @FXML Slider randomOrder;
    @FXML Button play;

    public void startGame(ActionEvent actionEvent) {
        Double num=new Double(numOfPlayers.getValue()) ;
        System.out.println(num.intValue());
        boolean random= (randomOrder.getValue()==1);
        HanabiMain.setUpWindow.board = new Board(num.intValue(), 40, 8, 8, 3, new Deck(true, true, true),
                random, Board.randomNames(num.intValue()));
        HanabiMain.setUpWindow.stage.close();
    }
}
