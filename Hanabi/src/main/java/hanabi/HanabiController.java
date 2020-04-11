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
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HanabiController implements Initializable {


    @FXML Rectangle card1_1,card1_2,card1_3,card1_4,card2_1,card2_2,card2_3,card2_4,card3_1,card3_2,card3_3,card3_4,card4_1,card4_2,card4_3,card4_4;
    @FXML Rectangle card5_1,card5_2,card5_3,card5_4,card6_1,card6_2,card6_3,card6_4,card7_1,card7_2,card7_3,card7_4,card8_1,card8_2,card8_3,card8_4;
    @FXML Rectangle card9_1,card9_2,card9_3,card9_4,card10_1,card10_2,card10_3,card10_4;
    @FXML Label moveHistory;
    @FXML ComboBox<String> hintTypeChoice;
    @FXML ComboBox<Color> colorChoice;
    @FXML ComboBox<Integer> playerChoice, cardChoice, numberChoice;;
    @FXML Label discardPile;
    @FXML Label result;
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
    ArrayList<ArrayList<Rectangle>> cards;
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
        cards = new ArrayList<>();
        int PLAYERAMOUNT = 6; //thats temporary
        board = new Board(PLAYERAMOUNT, 40, 8, 8, 0, new Deck(true, true, true), false, Board.randomNames(PLAYERAMOUNT));
        addCardsToArrayList(PLAYERAMOUNT);
        updateHands();
        showYourTrueColors();
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

        int index = board.getCurrentPlayerIndex();
        Player player = board.getPlayers().get(index);
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
        updateHands(index);
        blurMe((index+1)%board.getPlayerAmount());
        updateResult();
        updateDiscardPile();
        updateMoveHistory();
    }
    public void discardButtonClicked(ActionEvent actionEvent) {
        int index = board.getCurrentPlayerIndex();
        Player player = board.getPlayers().get(index);
        MoveType movetype = MoveType.DISCARD;
        PlayerMove playerMove = new PlayerMove(player,movetype, cardIx);
        try{
            board.action(playerMove);
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateHands();
        updateHands(index);
        blurMe((index+1)%board.getPlayerAmount());
        updateResult();
        updateDiscardPile();
        updateMoveHistory();
        System.out.println(board.getCurrentPlayerIndex());

    }

    public void playButtonClicked(ActionEvent actionEvent) {
        int index = board.getCurrentPlayerIndex();
        Player player = board.getPlayers().get(index);
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
        updateHands(index);
        blurMe((index+1)%board.getPlayerAmount());
        updateResult();
        updateDiscardPile();
        updateMoveHistory();
        System.out.println(board.getCurrentPlayerIndex());
    }

    public void updateHands(){
        for (int i = 0; i< board.getPlayerAmount(); i++) {
            if (board.getCurrentPlayerIndex() == i) {
                players.get(i).setText(board.getPlayers().get(i).getStringBlurredData());
            } else {
                players.get(i).setText(board.getPlayers().get(i).getStringData());
            }
        }
    }
    public void blurMe(int index){
        for(int i = 0; i < 4; ++i){
            cards.get(index).get(i).setFill(javafx.scene.paint.Color.DARKSLATEGRAY);
        }
    }
    public void updateHands(int index){
        for(int i = 0; i < 4; ++i){
            Color color = board.getPlayers().get(index).getHand().get(i).getColor();
            javafx.scene.paint.Color paintColor = javafx.scene.paint.Color.WHITE;
            LinearGradient rainbow = null;
            if(color==Color.Y) paintColor = javafx.scene.paint.Color.YELLOW;
            else if(color==Color.R) paintColor = javafx.scene.paint.Color.RED;
            else if(color==Color.G) paintColor = javafx.scene.paint.Color.GREEN;
            else if(color==Color.B) paintColor = javafx.scene.paint.Color.BLUE;
            else if(color==Color.RAINBOW){
                Stop[] stops = new Stop[] { new Stop(0, javafx.scene.paint.Color.BLUE), new Stop(1, javafx.scene.paint.Color.YELLOWGREEN)};
                rainbow = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
            }
            if(rainbow == null){
                cards.get(index).get(i).setFill(paintColor);
            }
            else{
                cards.get(index).get(i).setFill(rainbow);
            }
        }
    }
    public void showYourTrueColors(){
        blurMe(0);
        for(int index = 1; index < board.getPlayerAmount(); ++index){
            updateHands(index);
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
        cardIx = cardChoice.getValue() - 1;
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
    public void addCardsToArrayList(int numberOfPlayers){
        for(int i = 0; i < 10;++i)
        cards.add(new ArrayList<Rectangle>());
        cards.get(0).add(card1_1);
        cards.get(0).add(card1_2);
        cards.get(0).add(card1_3);
        cards.get(0).add(card1_4);
        cards.get(1).add(card2_1);
        cards.get(1).add(card2_2);
        cards.get(1).add(card2_3);
        cards.get(1).add(card2_4);
        cards.get(2).add(card3_1);
        cards.get(2).add(card3_2);
        cards.get(2).add(card3_3);
        cards.get(2).add(card3_4);
        cards.get(3).add(card4_1);
        cards.get(3).add(card4_2);
        cards.get(3).add(card4_3);
        cards.get(3).add(card4_4);
        cards.get(4).add(card5_1);
        cards.get(4).add(card5_2);
        cards.get(4).add(card5_3);
        cards.get(4).add(card5_4);
        cards.get(5).add(card6_1);
        cards.get(5).add(card6_2);
        cards.get(5).add(card6_3);
        cards.get(5).add(card6_4);
        cards.get(6).add(card7_1);
        cards.get(6).add(card7_2);
        cards.get(6).add(card7_3);
        cards.get(6).add(card7_4);
        cards.get(7).add(card8_1);
        cards.get(7).add(card8_2);
        cards.get(7).add(card8_3);
        cards.get(7).add(card8_4);
        cards.get(8).add(card9_1);
        cards.get(8).add(card9_2);
        cards.get(8).add(card9_3);
        cards.get(8).add(card9_4);
        cards.get(9).add(card10_1);
        cards.get(9).add(card10_2);
        cards.get(9).add(card10_3);
        cards.get(9).add(card10_4);
        for(int i = numberOfPlayers; i < 10; ++i){
            for(int j = 0; j <4; ++j){
                cards.get(i).get(j).setVisible(false);
            }
        }
    }
}