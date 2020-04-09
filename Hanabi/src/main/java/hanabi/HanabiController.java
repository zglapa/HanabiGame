package hanabi;
import hanabi.Model.*;
import hanabi.Model.Board;
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
    @FXML Label discardPile;
    @FXML Label result;
    @FXML Button chooseCard;
    @FXML Label Player1;
    @FXML Label Player2;
    @FXML Label Player3;
    @FXML Label Player4;
    @FXML Button hintButton;
    @FXML Button playButton;
    @FXML Button discardButton;
    @FXML TextField cardIndex;
    Integer cardIx;
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
        PlayerMove playerMove = new PlayerMove(player,movetype,cardIx.intValue());
        try{
            board.action(playerMove);
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateHands();
        updateResult();
        updateDiscardPile();
        System.out.println(board.getCurrentPlayerIndex());

    }

    public void playButtonClicked(ActionEvent actionEvent) {
        Player player = board.getPlayers().get(board.getCurrentPlayerIndex());
        MoveType movetype = MoveType.PLAY;
        System.out.println(player);
        System.out.println(movetype);
        System.out.println(cardIx.intValue());
        PlayerMove playerMove = new PlayerMove(player,movetype,cardIx.intValue());
        try{
            board.action(playerMove);
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateHands();
        updateResult();
        updateDiscardPile();
        System.out.println(board.getCurrentPlayerIndex());
    }

    public void cardConfirmed(ActionEvent actionEvent) {
        cardIx = Integer.valueOf(cardIndex.getText());
    }
    public void updateHands(){
        Player1.setText(board.getPlayers().get(0).getHand().toString());
        Player2.setText(board.getPlayers().get(1).getHand().toString());
        Player3.setText(board.getPlayers().get(2).getHand().toString());
        Player4.setText(board.getPlayers().get(3).getHand().toString());
    }
    public void updateResult(){
        result.setText(board.getResult().toString());
    }
    public void updateDiscardPile(){
        discardPile.setText(board.getDiscardPile().toString());
    }
}