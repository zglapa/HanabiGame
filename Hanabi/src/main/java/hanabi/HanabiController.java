package hanabi;
import hanabi.Model.*;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.InputMethodEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HanabiController implements Initializable {
    @FXML Label Player1;
    @FXML Label Player2;
    @FXML Label Player3;
    @FXML Label Player4;
    @FXML Button hintButton;
    @FXML ComboBox<String> cardChoice;
    Integer cardIndex;
    Board board;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        board = new Board(4, 40, 8, 8, 0, new Deck(true, true, true), false, Board.randomNames(4));
        Player1.setText(board.getPlayers().get(0).getHand().toString());
        Player2.setText(board.getPlayers().get(1).getHand().toString());
        Player3.setText(board.getPlayers().get(2).getHand().toString());
        Player4.setText(board.getPlayers().get(3).getHand().toString());
        System.out.println("View is now loaded!");
    }
    public void hintButtonClicked(ActionEvent actionEvent){
        Player player = board.getPlayers().get(board.getCurrentPlayerIndex());
        MoveType movetype = MoveType.HINT;
        //PlayerMove playerMove = new PlayerMove(player,movetype,);
    }
    public void discardButtonClicked(ActionEvent actionEvent) {
        Player player = board.getPlayers().get(board.getCurrentPlayerIndex());
        MoveType movetype = MoveType.DISCARD;
        PlayerMove playerMove = new PlayerMove(player,movetype,cardIndex.intValue());
        try{
            board.action(playerMove);
        } catch (hanabi.Model.GameEndException | hanabi.Model.NoHintsLeft ignored) {
        }
    }

    public void playButtonClicked(ActionEvent actionEvent) {
        Player player = board.getPlayers().get(board.getCurrentPlayerIndex());
        MoveType movetype = MoveType.PLAY;
        PlayerMove playerMove = new PlayerMove(player,movetype,cardIndex.intValue());
        try{
            board.action(playerMove);
        } catch (GameEndException | NoHintsLeft ignored) {
        }
    }

    public void selectedCard(InputMethodEvent inputMethodEvent) {
        cardIndex = Integer.valueOf(cardChoice.getValue());
    }
}