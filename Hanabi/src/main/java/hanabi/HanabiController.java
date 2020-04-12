package hanabi;
import hanabi.Model.*;
import hanabi.Model.Board;
import hanabi.Model.Color;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HanabiController implements Initializable {


    @FXML Rectangle resultCardW,resultCardY,resultCardR,resultCardG,resultCardB,resultCardRB;
    @FXML Rectangle card1_1,card1_2,card1_3,card1_4, card1_5, card1_6,card2_1,card2_2,card2_3,card2_4,card2_5, card2_6,
            card3_1,card3_2,card3_3,card3_4,card3_5, card3_6, card4_1,card4_2,card4_3,card4_4,card4_5, card4_6,
            card5_1,card5_2,card5_3,card5_4,card5_5, card5_6, card6_1,card6_2,card6_3,card6_4,card6_5, card6_6,
            card7_1,card7_2,card7_3,card7_4,card7_5, card7_6, card8_1,card8_2,card8_3,card8_4,card8_5, card8_6,
            card9_1,card9_2,card9_3,card9_4,card9_5, card9_6, card10_1,card10_2,card10_3,card10_4,card10_5, card10_6;
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
    ArrayList<Rectangle> resultCards;
    ArrayList<ImagePattern> whites;
    ArrayList<ImagePattern> yellows;
    ArrayList<ImagePattern> greens;
    ArrayList<ImagePattern> blues;
    ArrayList<ImagePattern> reds;
    ArrayList<ImagePattern> rainbows;
    ArrayList<ArrayList<ImagePattern>> allColorLists;
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
    public void initialize(URL location, ResourceBundle resourceBundle) {
        try {
            addColors();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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
        resultCards = new ArrayList<>();
        cards = new ArrayList<>();
        int PLAYERAMOUNT = 7; //thats temporary
        boolean WITHRAINBOWS = true; //temporary as well
        board = new Board(PLAYERAMOUNT, 40, 8, 8, 3, new Deck(true, WITHRAINBOWS, true), false, Board.randomNames(PLAYERAMOUNT));
        addCardsToArrayList(PLAYERAMOUNT);
        try {
            addResultCards(WITHRAINBOWS);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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
        updateResultCards();
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
        updateResultCards();
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
        updateResultCards();
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
        for(int i = 0; i < board.getHandSize(); ++i){
            cards.get(index).get(i).setFill(javafx.scene.paint.Color.DARKSLATEGRAY);
        }
    }
    public void updateHands(int index){
        for(int i = 0; i < board.getHandSize(); ++i){
            Card card= board.getPlayers().get(index).getHand().get(i);
            int cardValue = card.getValue();
            Color color = card.getColor();
            ImagePattern colorPattern = whites.get(cardValue-1);
            if(color==Color.Y) colorPattern = yellows.get(cardValue-1);
            else if(color==Color.R) colorPattern = reds.get(cardValue-1);
            else if(color==Color.G) colorPattern = greens.get(cardValue-1);
            else if(color==Color.B) colorPattern = blues.get(cardValue-1);
            else if(color==Color.RAINBOW) colorPattern = rainbows.get(cardValue-1);
            cards.get(index).get(i).setFill(colorPattern);

        }
    }
    public void showYourTrueColors(){
        blurMe(0);
        for(int index = 1; index < board.getPlayerAmount() ; ++index){
            updateHands(index);
        }

    }
    public void updateMoveHistory(){
        moveHistory.setText(board.getStringPlayerMoveHistory());
    }
    public void updateResult(){
        result.setText(board.getResult().toString());
    }
    public void updateResultCards(){
        for(int i = 0; i< resultCards.size(); ++i){
            int cardValue = board.getResult().get(Color.values()[i]);
            if(cardValue == 0) continue;
            ImagePattern colorPattern = allColorLists.get(i).get(cardValue-1);
            resultCards.get(i).setFill(colorPattern);
        }
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
        cards.get(0).add(card1_5);
        cards.get(0).add(card1_6);
        cards.get(1).add(card2_1);
        cards.get(1).add(card2_2);
        cards.get(1).add(card2_3);
        cards.get(1).add(card2_4);
        cards.get(1).add(card2_5);
        cards.get(1).add(card2_6);
        cards.get(2).add(card3_1);
        cards.get(2).add(card3_2);
        cards.get(2).add(card3_3);
        cards.get(2).add(card3_4);
        cards.get(2).add(card3_5);
        cards.get(2).add(card3_6);
        cards.get(3).add(card4_1);
        cards.get(3).add(card4_2);
        cards.get(3).add(card4_3);
        cards.get(3).add(card4_4);
        cards.get(3).add(card4_5);
        cards.get(3).add(card4_6);
        cards.get(4).add(card5_1);
        cards.get(4).add(card5_2);
        cards.get(4).add(card5_3);
        cards.get(4).add(card5_4);
        cards.get(4).add(card5_5);
        cards.get(4).add(card5_6);
        cards.get(5).add(card6_1);
        cards.get(5).add(card6_2);
        cards.get(5).add(card6_3);
        cards.get(5).add(card6_4);
        cards.get(5).add(card6_5);
        cards.get(5).add(card6_6);
        cards.get(6).add(card7_1);
        cards.get(6).add(card7_2);
        cards.get(6).add(card7_3);
        cards.get(6).add(card7_4);
        cards.get(6).add(card7_5);
        cards.get(6).add(card7_6);
        cards.get(7).add(card8_1);
        cards.get(7).add(card8_2);
        cards.get(7).add(card8_3);
        cards.get(7).add(card8_4);
        cards.get(7).add(card8_5);
        cards.get(7).add(card8_6);
        cards.get(8).add(card9_1);
        cards.get(8).add(card9_2);
        cards.get(8).add(card9_3);
        cards.get(8).add(card9_4);
        cards.get(8).add(card9_5);
        cards.get(8).add(card9_6);
        cards.get(9).add(card10_1);
        cards.get(9).add(card10_2);
        cards.get(9).add(card10_3);
        cards.get(9).add(card10_4);
        cards.get(9).add(card10_5);
        cards.get(9).add(card10_6);
        for(int i = 0; i < numberOfPlayers; ++i){
            for(int j = board.getHandSize(); j < 6;++j) cards.get(i).get(j).setVisible(false);
        }
        for(int i = numberOfPlayers; i < 10; ++i){
            for(int j = 0; j <6; ++j){
                cards.get(i).get(j).setVisible(false);
            }
        }
    }
    public void addColors() throws URISyntaxException {
        whites = new ArrayList<>();
        yellows = new ArrayList<>();
        reds = new ArrayList<>();
        greens = new ArrayList<>();
        blues = new ArrayList<>();
        rainbows = new ArrayList<>();

        whites.add(new ImagePattern(new Image(getClass().getResource("/Colors/White1.jpg").toURI().toString())));
        whites.add(new ImagePattern(new Image(getClass().getResource("/Colors/White2.jpg").toURI().toString())));
        whites.add(new ImagePattern(new Image(getClass().getResource("/Colors/White3.jpg").toURI().toString())));
        whites.add(new ImagePattern(new Image(getClass().getResource("/Colors/White4.jpg").toURI().toString())));
        whites.add(new ImagePattern(new Image(getClass().getResource("/Colors/White5.jpg").toURI().toString())));

        yellows.add(new ImagePattern(new Image(getClass().getResource("/Colors/Yellow1.jpg").toURI().toString())));
        yellows.add(new ImagePattern(new Image(getClass().getResource("/Colors/Yellow2.jpg").toURI().toString())));
        yellows.add(new ImagePattern(new Image(getClass().getResource("/Colors/Yellow3.jpg").toURI().toString())));
        yellows.add(new ImagePattern(new Image(getClass().getResource("/Colors/Yellow4.jpg").toURI().toString())));
        yellows.add(new ImagePattern(new Image(getClass().getResource("/Colors/Yellow5.jpg").toURI().toString())));

        reds.add(new ImagePattern(new Image(getClass().getResource("/Colors/Red1.jpg").toURI().toString())));
        reds.add(new ImagePattern(new Image(getClass().getResource("/Colors/Red2.jpg").toURI().toString())));
        reds.add(new ImagePattern(new Image(getClass().getResource("/Colors/Red3.jpg").toURI().toString())));
        reds.add(new ImagePattern(new Image(getClass().getResource("/Colors/Red4.jpg").toURI().toString())));
        reds.add(new ImagePattern(new Image(getClass().getResource("/Colors/Red5.jpg").toURI().toString())));

        greens.add(new ImagePattern(new Image(getClass().getResource("/Colors/Green1.jpg").toURI().toString())));
        greens.add(new ImagePattern(new Image(getClass().getResource("/Colors/Green2.jpg").toURI().toString())));
        greens.add(new ImagePattern(new Image(getClass().getResource("/Colors/Green3.jpg").toURI().toString())));
        greens.add(new ImagePattern(new Image(getClass().getResource("/Colors/Green4.jpg").toURI().toString())));
        greens.add(new ImagePattern(new Image(getClass().getResource("/Colors/Green5.jpg").toURI().toString())));

        blues.add(new ImagePattern(new Image(getClass().getResource("/Colors/Blue1.jpg").toURI().toString())));
        blues.add(new ImagePattern(new Image(getClass().getResource("/Colors/Blue2.jpg").toURI().toString())));
        blues.add(new ImagePattern(new Image(getClass().getResource("/Colors/Blue3.jpg").toURI().toString())));
        blues.add(new ImagePattern(new Image(getClass().getResource("/Colors/Blue4.jpg").toURI().toString())));
        blues.add(new ImagePattern(new Image(getClass().getResource("/Colors/Blue5.jpg").toURI().toString())));

        rainbows.add(new ImagePattern(new Image(getClass().getResource("/Colors/RB1.jpg").toURI().toString())));
        rainbows.add(new ImagePattern(new Image(getClass().getResource("/Colors/RB2.jpg").toURI().toString())));
        rainbows.add(new ImagePattern(new Image(getClass().getResource("/Colors/RB3.jpg").toURI().toString())));
        rainbows.add(new ImagePattern(new Image(getClass().getResource("/Colors/RB4.jpg").toURI().toString())));
        rainbows.add(new ImagePattern(new Image(getClass().getResource("/Colors/RB5.jpg").toURI().toString())));

        allColorLists = new ArrayList<>();

        allColorLists.add(whites);
        allColorLists.add(yellows);
        allColorLists.add(reds);
        allColorLists.add(greens);
        allColorLists.add(blues);
        allColorLists.add(rainbows);
    }
    public void addResultCards(boolean rainbowCard) throws URISyntaxException {
        resultCardW.setFill(new ImagePattern(new Image(getClass().getResource("/Colors/White.jpg").toURI().toString())));
        resultCardY.setFill(new ImagePattern(new Image(getClass().getResource("/Colors/Yellow.jpg").toURI().toString())));
        resultCardR.setFill(new ImagePattern(new Image(getClass().getResource("/Colors/Red.jpg").toURI().toString())));
        resultCardG.setFill(new ImagePattern(new Image(getClass().getResource("/Colors/Green.jpg").toURI().toString())));
        resultCardB.setFill(new ImagePattern(new Image(getClass().getResource("/Colors/Blue.jpg").toURI().toString())));
        resultCardRB.setFill(new ImagePattern(new Image(getClass().getResource("/Colors/RB.jpg").toURI().toString())));
        resultCards.add(resultCardW);
        resultCards.add(resultCardY);
        resultCards.add(resultCardR);
        resultCards.add(resultCardG);
        resultCards.add(resultCardB);
        if(!rainbowCard) resultCardRB.setVisible(false);
        else resultCards.add(resultCardRB);
    }
}