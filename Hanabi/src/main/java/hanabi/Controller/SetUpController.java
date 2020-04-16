package hanabi.Controller;

import hanabi.Model.Board;
import hanabi.Model.Deck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class SetUpController {
    @FXML Slider numOfPlayers;
    @FXML Slider randomOrder;
    @FXML Slider numOfCards;
    @FXML Slider hasRainbows;
    @FXML CheckBox showAdvanced;
    @FXML Button play;
    @FXML Pane advancedSettings;

    public void adjustCards(MouseEvent mouseEvent) {
        if(numOfPlayers.getValue()<=3.0)
            numOfCards.setValue(5.0);
        else if(numOfPlayers.getValue()<=5.0)
            numOfCards.setValue(4.0);
        else
            numOfCards.setValue(3.0);
    }

    public void startGame(ActionEvent actionEvent) {
        int players= ( (Double) numOfPlayers.getValue() ).intValue();
        int cards= ( (Double) numOfCards.getValue() ).intValue();
        boolean rainbow= (hasRainbows.getValue()==1.0);
        boolean random= (randomOrder.getValue()==1.0);

        HanabiMain.setUpWindow.board = new Board(players, 40, 8, 8, cards, new Deck(true, rainbow, true),
                random, Board.randomNames(players));
        HanabiMain.setUpWindow.hasRainbows = rainbow;
        HanabiMain.setUpWindow.stage.close();
    }

    public void advanced(MouseEvent mouseEvent) {
        advancedSettings.setVisible(showAdvanced.isSelected());
    }
}
