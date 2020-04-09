package hanabi;
import hanabi.Model.*;
import hanabi.Model.Board;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
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

    @FXML Label moveHistory;
    @FXML ComboBox<String> hintTypeChoice;
    @FXML Button card0Button, card1Button, card2Button, card3Button;
    @FXML ComboBox<Color> colorChoice;
    @FXML ComboBox<Integer> playerChoice, cardChoice, numberChoice;;
    @FXML TextField hintPlayer;
    @FXML TextField hintValue;
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
    Integer numberHint;
    Color colorHint;
    Player playerHint;
    String hintType;
    Integer cardIx;
    Integer cardValueChoice;
    Integer playerIx;
    Color colorIx;
    Board board;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        board = new Board(4, 40, 8, 8, 0, new Deck(true, true, true), false, Board.randomNames(4));
        Player1.setText(board.getPlayers().get(0).getHand().toString());
        Player2.setText(board.getPlayers().get(1).getHand().toString());
        Player3.setText(board.getPlayers().get(2).getHand().toString());
        Player4.setText(board.getPlayers().get(3).getHand().toString());
        cardIx = Integer.valueOf(0);
        colorChoice.getItems().addAll(Color.values());
        Integer [] arrayOfPlayersID = new Integer[board.getPlayerAmount().intValue()];
        Integer [] arrayOfCardsID = new Integer[4];
        Integer [] arrayOfCardValues = new Integer[5];
        for(int i = 0; i < board.getPlayerAmount().intValue(); ++i){
            arrayOfPlayersID[i] = Integer.valueOf(i);
        }
        for(int i = 0; i < 4; ++i){
            arrayOfCardsID[i] = Integer.valueOf(i);
        }
        for(int i = 0; i < 5; ++i){
            arrayOfCardValues[i] = Integer.valueOf(i+1);
        }
        playerChoice.getItems().addAll(arrayOfPlayersID);
        cardChoice.getItems().addAll(arrayOfCardsID);
        numberChoice.getItems().addAll(arrayOfCardValues);
        hintTypeChoice.getItems().addAll("NUMBER", "COLOR");
        System.out.println("View is now loaded!");
    }
    public void hintButtonClicked(ActionEvent actionEvent){

        Player player = board.getPlayers().get(board.getCurrentPlayerIndex());
        MoveType movetype = MoveType.HINT;
        Player hintedPlayer = board.getPlayers().get(playerIx);
        Hint hint;
        if(hintType == "NUMBER")
        hint = new Hint(hintedPlayer,cardValueChoice);
        else
            hint = new Hint(hintedPlayer,colorIx);
        PlayerMove playerMove = new PlayerMove(player,movetype,hint);
        try{
            board.action(playerMove);
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateHands();
        updateResult();
        updateDiscardPile();
        updateMoveHistory();
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
        updateMoveHistory();
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
        updateMoveHistory();
        System.out.println(board.getCurrentPlayerIndex());
    }

    public void updateHands(){
        Player1.setText(board.getPlayers().get(0).getHand().toString());
        Player2.setText(board.getPlayers().get(1).getHand().toString());
        Player3.setText(board.getPlayers().get(2).getHand().toString());
        Player4.setText(board.getPlayers().get(3).getHand().toString());
    }
    public void updateMoveHistory(){
        moveHistory.setText(board.getPlayerMoveHistory().toString());
    }
    public void updateResult(){
        result.setText(board.getResult().toString());
    }
    public void updateDiscardPile(){
        discardPile.setText(board.getDiscardPile().toString());
    }

    public void cardChosen(ActionEvent actionEvent) {
        cardIx = cardChoice.getValue();
    }

    public void playerChosen(ActionEvent actionEvent) {
        playerIx = playerChoice.getValue();
    }

    public void colorChosen(ActionEvent actionEvent) {
        colorIx = colorChoice.getValue();
    }

    public void numberChosen(ActionEvent actionEvent) { cardValueChoice = numberChoice.getValue();
    }

    public void hintTypeChosen(ActionEvent actionEvent) { hintType = hintTypeChoice.getValue();
    }
}