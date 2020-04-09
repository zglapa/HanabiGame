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
import java.util.ArrayList;
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
    @FXML Label player1;
    @FXML Label player2;
    @FXML Label player3;
    @FXML Label player4;
    @FXML Label player5;
    @FXML Label player6;
    @FXML Label player7;
    @FXML Label player8;
    @FXML Label player9;
    @FXML Label player10;
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
    ArrayList<Label> players;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);
        players.add(player6);
        players.add(player7);
        players.add(player8);
        players.add(player9);
        players.add(player10);
        int PLAYERAMOUNT = 5; //thats temporary
        board = new Board(PLAYERAMOUNT, 40, 8, 8, 0, new Deck(true, true, true), false, Board.randomNames(PLAYERAMOUNT));
        updateHands();
        cardIx = 0;
        colorChoice.getItems().addAll(Color.values());
        Integer [] arrayOfPlayersID = new Integer[board.getPlayerAmount()];
        Integer [] arrayOfCardsID = new Integer[board.getHandSize()];
        Integer [] arrayOfCardValues = new Integer[5];

        for(int i = 0; i < board.getPlayerAmount(); ++i){
            arrayOfPlayersID[i] = i;
        }
        System.out.println(board.getHandSize());
        for(int i = 0; i < board.getHandSize(); ++i){

            arrayOfCardsID[i] = i+1;
        }
        for(int i = 0; i < 5; ++i){
            arrayOfCardValues[i] = i + 1;
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
        //System.out.println("a");
        Hint hint;
        if(hintType.equals("NUMBER"))
            hint = new Hint(hintedPlayer,cardValueChoice);
        else
            hint = new Hint(hintedPlayer,colorIx);
        PlayerMove playerMove = new PlayerMove(player,movetype,hint);
        //System.out.println("b");
        try{
            board.action(playerMove);
        } catch (Exception e) {
            System.out.println("AAAAA");
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
        PlayerMove playerMove = new PlayerMove(player,movetype, cardIx);
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
        PlayerMove playerMove = new PlayerMove(player,movetype, cardIx);
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
        for (int i = 0; i< board.getPlayerAmount(); i++) {
            if (board.getCurrentPlayerIndex() == i) {
                players.get(i).setText(board.getPlayers().get(i).getStringBlurredData());
            } else
                players.get(i).setText(board.getPlayers().get(i).getStringData());
        }
    }
    public void updateMoveHistory(){
        moveHistory.setText(board.getStringPlayerMoveHistory());
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