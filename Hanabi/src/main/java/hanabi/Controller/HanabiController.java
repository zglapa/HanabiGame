package hanabi.Controller;
import hanabi.Model.*;
import hanabi.Model.Board;
import hanabi.Model.Color;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class HanabiController implements Initializable {

    @FXML Label numberLivesLabel, numberHintsLabel;
    @FXML Pane playPane, hintTypePane, hintPlayerPane, hintPane, nextPlayerPane;
    @FXML Pane numberHintPane, colorHintPane;
    @FXML Pane endGamePane;
    @FXML FlowPane playerHands, discardPane, resultPane;
    @FXML Label moveHistory, nextPlayerName;
    @FXML Button hintButton, playButton, discardButton;
    @FXML Button p1Hint, p2Hint, p3Hint,p4Hint,p5Hint,p6Hint, p7Hint;
    @FXML Button colorButtonHint, numberButtonHint;
    @FXML Button n1Hint, n2Hint,n3Hint,n4Hint,n5Hint;
    @FXML Button c1Hint, c2Hint, c3Hint,c4Hint,c5Hint,c6Hint;
    @FXML Button n1Play,n2Play,n3Play,n4Play,n5Play,n6Play;
    ArrayList<Rectangle> resultCards;
    ArrayList<ImagePattern> whites;
    ArrayList<ImagePattern> yellows;
    ArrayList<ImagePattern> greens;
    ArrayList<ImagePattern> blues;
    ArrayList<ImagePattern> reds;
    ArrayList<ImagePattern> rainbows;
    ArrayList<ImagePattern> blanks;
    ArrayList<ArrayList<ImagePattern>> allColorLists;
    ArrayList<Button> pHintButtons;
    ArrayList<Button> nHintButtons;
    ArrayList<Button> cHintButtons;
    ArrayList<Button> nPlayButtons;
    ImagePattern blank;
    int cardIx;
    boolean isDiscard;
    boolean endGame;
    Integer numberHint;
    Color colorHint;
    Player playerHint;
    String hintType;
    Board board;
    ArrayList<Label> players;
    ArrayList<ArrayList<Rectangle>> cards;
    ArrayList<Rectangle> discardCards;
    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        try {
            addColors();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        endGame = false;
        players = new ArrayList<>();
        resultCards = new ArrayList<>();
        discardCards = new ArrayList<>();
        cards = new ArrayList<>();
        board= HanabiMain.setUpWindow.board;
        int PLAYERAMOUNT = board.getPlayerAmount();
        int HANDSIZE = board.getHandSize();
        boolean WITHRAINBOWS = HanabiMain.setUpWindow.hasRainbows;
        addHands(PLAYERAMOUNT,HANDSIZE);
        addButtonsToArrayLists();
        for(int i = PLAYERAMOUNT; i < 7;++i){
            pHintButtons.get(i).setVisible(false);
        }
        for(int i = HANDSIZE; i < 6; ++i){
            nPlayButtons.get(i).setVisible(false);
        }
        hideHintButtons();
        isDiscard = false;
        playPane.setVisible(false);
        try {
            addResultCards(WITHRAINBOWS);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        updateHands();
        showYourTrueColors();
        cardIx = 0;
        updateHintsAndLives();
        System.out.println("View is now loaded!");
    }
    public void hintDone(){
        int index = board.getCurrentPlayerIndex();
        Player player = board.getPlayers().get(index);
        MoveType movetype = MoveType.HINT;
        if(playerHint == null) playerHint = board.getPlayers().get(0);
        Player hintedPlayer = playerHint;
        Hint hint;
        if(hintType == null) hintType = "NUMBER";
        if(hintType.equals("NUMBER")){
            if(numberHint == null) numberHint=1;
            hint = new Hint(hintedPlayer,numberHint);
        }
        else{
            if(colorHint == null) colorHint = Color.values()[0];
            hint = new Hint(hintedPlayer,colorHint);
        }
        PlayerMove playerMove = new PlayerMove(player,movetype,hint);
        try{
            board.action(playerMove);
        } catch (GameEndException e) {
            endGamePane.setVisible(true);
            disableButtons();
            endGame =true;
        }catch (NoHintsLeftException e){
            NoHints.display("Alert","No hints left");
        }
        nextPlayer(board.getCurrentPlayerIndex());
        updateHands();
        hideHintButtons();
        updateHands(index);
        if(!endGame)blurMe((index+1)%board.getPlayerAmount());
        updateResultCards();
        updateMoveHistory();
        updateHintsAndLives();
    }
    public void playDone(){
        int index = board.getCurrentPlayerIndex();
        Player player = board.getPlayers().get(index);
        MoveType movetype = MoveType.PLAY;
        int discardPileSizeBefore = board.getDiscardPile().getDiscardPile().size();
        System.out.println(player);
        System.out.println(movetype);
        System.out.println(cardIx);
        PlayerMove playerMove = new PlayerMove(player,movetype, cardIx);
        try{
            board.action(playerMove);
        } catch (GameEndException | NoHintsLeftException e) {
            endGamePane.setVisible(true);
            disableButtons();
            endGame = true;
        }
        if(discardPileSizeBefore < board.getDiscardPile().getDiscardPile().size()) updateDiscardPileCards();
        nextPlayer(board.getCurrentPlayerIndex());
        updateHands();
        updateHands(index);
        playPane.setVisible(false);
        if(!endGame)blurMe((index+1)%board.getPlayerAmount());
        updateResultCards();
        updateMoveHistory();
        updateHintsAndLives();
        System.out.println(board.getCurrentPlayerIndex());
    }
    public void discardDone(){
        int index = board.getCurrentPlayerIndex();
        Player player = board.getPlayers().get(index);
        MoveType movetype = MoveType.DISCARD;
        PlayerMove playerMove = new PlayerMove(player,movetype, cardIx);
        try{
            board.action(playerMove);
        } catch (GameEndException | NoHintsLeftException e) {
            endGamePane.setVisible(true);
            disableButtons();
            endGame = true;
        }
        updateDiscardPileCards();
        nextPlayer(board.getCurrentPlayerIndex());
        updateHands();
        updateHands(index);
        if(!endGame)blurMe((index+1)%board.getPlayerAmount());
        playPane.setVisible(false);
        updateResultCards();
        updateMoveHistory();
        updateHintsAndLives();
        System.out.println(board.getCurrentPlayerIndex());
    }
    public void hintButtonClicked(ActionEvent actionEvent){
        playPane.setVisible(false);
        isDiscard=false;
        hintPane.setVisible(true);
        hintPlayerPane.setVisible(true);

    }
    public void discardButtonClicked(ActionEvent actionEvent) {
        hideHintButtons();
        playPane.setVisible(true);
        isDiscard=true;
    }

    public void playButtonClicked(ActionEvent actionEvent) {
        hideHintButtons();
       playPane.setVisible(true);
       isDiscard=false;
    }

    public void updateHands(){
        for (int i = 0; i< board.getPlayerAmount(); i++) {
            players.get(i).setText(board.getPlayers().get(i).toString());
        }
    }
    public void blurMe(int index){
        for(int i = 0; i < board.getHandSize(); ++i){
            cards.get(index).get(i).setFill(blank);
        }
    }
    public void updateHands(int index){
        if(board.getDeck().getSize() == 0){
            cards.get(index).get(board.getHandSize()-1).setVisible(false);
        }
        for(int i = 0; i < board.getPlayers().get(index).getHand().size(); ++i){
            Card card= board.getPlayers().get(index).getHand().get(i);
            int cardValue = card.getValue();
            Color color = card.getColor();
            ImagePattern colorPattern = whites.get(cardValue-1);
            if(color==Color.values()[1]) colorPattern = yellows.get(cardValue-1);
            else if(color==Color.values()[2]) colorPattern = reds.get(cardValue-1);
            else if(color==Color.values()[3]) colorPattern = greens.get(cardValue-1);
            else if(color==Color.values()[4])colorPattern = blues.get(cardValue-1);
            else if(color==Color.values()[5]) colorPattern = rainbows.get(cardValue-1);
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
    public void updateResultCards(){
        for(int i = 0; i< resultCards.size(); ++i){
            int cardValue = board.getResult().get(Color.values()[i]);
            if(cardValue == 0) continue;
            ImagePattern colorPattern = allColorLists.get(i).get(cardValue-1);
            resultCards.get(i).setFill(colorPattern);
        }
    }
    public void updateDiscardPileCards(){
        Rectangle newCard = new Rectangle();
        newCard.arcHeightProperty().setValue(10);
        newCard.arcWidthProperty().setValue(10);
        newCard.widthProperty().setValue(40);
        newCard.heightProperty().setValue(40);
        newCard.setVisible(false);
        discardPane.getChildren().add(newCard);
        discardCards.add(newCard);
        LinkedList<Card> pile = board.getDiscardPile().getDiscardPile();
        for(int i = 0; i<discardCards.size(); ++i){
            Card card = pile.get(i);
            int cardValue = card.getValue();
            Color color = card.getColor();
            ImagePattern colorPattern = whites.get(cardValue-1);
            if(color==Color.values()[1]) colorPattern = yellows.get(cardValue-1);
            else if(color==Color.values()[2]) colorPattern = reds.get(cardValue-1);
            else if(color==Color.values()[3]) colorPattern = greens.get(cardValue-1);
            else if(color==Color.values()[4])colorPattern = blues.get(cardValue-1);
            else if(color==Color.values()[5]) colorPattern = rainbows.get(cardValue-1);
            discardCards.get(i).setFill(colorPattern);
            discardCards.get(i).setVisible(true);
            System.out.println(discardCards.size());

        }
    }

    public void addHands(int numberOfPlayers, int handSize){
        for(int i = 0;i < numberOfPlayers; ++i){
            StackPane stackPane = new StackPane();
            Rectangle box = new Rectangle();
            box.setFill(javafx.scene.paint.Color.WHITE);
            box.setOpacity(0.7);
            box.setVisible(true);
            GridPane outerGrid = new GridPane();
            GridPane gridPane = new GridPane();
            outerGrid.setAlignment(Pos.CENTER);
            outerGrid.setVgap(10);
            gridPane.setHgap(10);
            gridPane.setMaxWidth(Region.USE_COMPUTED_SIZE);
            gridPane.setAlignment(Pos.CENTER);
            Label player = new Label();
            player.setAlignment(Pos.CENTER);
            players.add(player);
            cards.add(new ArrayList<Rectangle>());
            for(int j = 0; j <handSize; ++j){
                Rectangle newCard = new Rectangle();
                newCard.arcHeightProperty().setValue(15);
                newCard.arcWidthProperty().setValue(15);
                newCard.widthProperty().setValue(40);
                newCard.heightProperty().setValue(40);
                cards.get(i).add(newCard);
                gridPane.add(newCard,j,0);
            }
            int height = 80;
            int width = handSize*40+(handSize-1)*10;
            player.setPrefWidth(width);
            outerGrid.add(player, 0,0);
            outerGrid.add(gridPane, 0, 1);
            System.out.println(width);
            box.setHeight(height);
            box.setWidth(width+5);
            box.setArcHeight(20);
            box.setArcWidth(20);
            stackPane.getChildren().addAll(box,outerGrid);
            stackPane.setPrefWidth(width + 5);
            stackPane.setPrefHeight(height);
            stackPane.setMaxWidth(Region.USE_COMPUTED_SIZE);
            stackPane.setMaxHeight(Region.USE_COMPUTED_SIZE);
            playerHands.getChildren().add(stackPane);
        }
    }
    public void addColors() throws URISyntaxException {
        whites = new ArrayList<>();
        yellows = new ArrayList<>();
        reds = new ArrayList<>();
        greens = new ArrayList<>();
        blues = new ArrayList<>();
        rainbows = new ArrayList<>();
        blanks = new ArrayList<>();

        whites.add(new ImagePattern(new Image(getClass().getResource("/Colors/White1.jpg").toURI().toString()),0,0,1,1,true));
        whites.add(new ImagePattern(new Image(getClass().getResource("/Colors/White2.jpg").toURI().toString()),0,0,1,1,true));
        whites.add(new ImagePattern(new Image(getClass().getResource("/Colors/White3.jpg").toURI().toString()),0,0,1,1,true));
        whites.add(new ImagePattern(new Image(getClass().getResource("/Colors/White4.jpg").toURI().toString()),0,0,1,1,true));
        whites.add(new ImagePattern(new Image(getClass().getResource("/Colors/White5.jpg").toURI().toString()),0,0,1,1,true));

        yellows.add(new ImagePattern(new Image(getClass().getResource("/Colors/Yellow1.jpg").toURI().toString()),0,0,1,1,true));
        yellows.add(new ImagePattern(new Image(getClass().getResource("/Colors/Yellow2.jpg").toURI().toString()),0,0,1,1,true));
        yellows.add(new ImagePattern(new Image(getClass().getResource("/Colors/Yellow3.jpg").toURI().toString()),0,0,1,1,true));
        yellows.add(new ImagePattern(new Image(getClass().getResource("/Colors/Yellow4.jpg").toURI().toString()),0,0,1,1,true));
        yellows.add(new ImagePattern(new Image(getClass().getResource("/Colors/Yellow5.jpg").toURI().toString()),0,0,1,1,true));

        reds.add(new ImagePattern(new Image(getClass().getResource("/Colors/Red1.jpg").toURI().toString()),0,0,1,1,true));
        reds.add(new ImagePattern(new Image(getClass().getResource("/Colors/Red2.jpg").toURI().toString()),0,0,1,1,true));
        reds.add(new ImagePattern(new Image(getClass().getResource("/Colors/Red3.jpg").toURI().toString()),0,0,1,1,true));
        reds.add(new ImagePattern(new Image(getClass().getResource("/Colors/Red4.jpg").toURI().toString()),0,0,1,1,true));
        reds.add(new ImagePattern(new Image(getClass().getResource("/Colors/Red5.jpg").toURI().toString()),0,0,1,1,true));

        greens.add(new ImagePattern(new Image(getClass().getResource("/Colors/Green1.jpg").toURI().toString()),0,0,1,1,true));
        greens.add(new ImagePattern(new Image(getClass().getResource("/Colors/Green2.jpg").toURI().toString()),0,0,1,1,true));
        greens.add(new ImagePattern(new Image(getClass().getResource("/Colors/Green3.jpg").toURI().toString()),0,0,1,1,true));
        greens.add(new ImagePattern(new Image(getClass().getResource("/Colors/Green4.jpg").toURI().toString()),0,0,1,1,true));
        greens.add(new ImagePattern(new Image(getClass().getResource("/Colors/Green5.jpg").toURI().toString()),0,0,1,1,true));

        blues.add(new ImagePattern(new Image(getClass().getResource("/Colors/Blue1.jpg").toURI().toString()),0,0,1,1,true));
        blues.add(new ImagePattern(new Image(getClass().getResource("/Colors/Blue2.jpg").toURI().toString()),0,0,1,1,true));
        blues.add(new ImagePattern(new Image(getClass().getResource("/Colors/Blue3.jpg").toURI().toString()),0,0,1,1,true));
        blues.add(new ImagePattern(new Image(getClass().getResource("/Colors/Blue4.jpg").toURI().toString()),0,0,1,1,true));
        blues.add(new ImagePattern(new Image(getClass().getResource("/Colors/Blue5.jpg").toURI().toString()),0,0,1,1,true));

        rainbows.add(new ImagePattern(new Image(getClass().getResource("/Colors/RB1.jpg").toURI().toString()),0,0,1,1,true));
        rainbows.add(new ImagePattern(new Image(getClass().getResource("/Colors/RB2.jpg").toURI().toString()),0,0,1,1,true));
        rainbows.add(new ImagePattern(new Image(getClass().getResource("/Colors/RB3.jpg").toURI().toString()),0,0,1,1,true));
        rainbows.add(new ImagePattern(new Image(getClass().getResource("/Colors/RB4.jpg").toURI().toString()),0,0,1,1,true));
        rainbows.add(new ImagePattern(new Image(getClass().getResource("/Colors/RB5.jpg").toURI().toString()),0,0,1,1,true));

        blanks.add(new ImagePattern(new Image(getClass().getResource("/Colors/White.jpg").toURI().toString()),0,0,1,1,true));
        blanks.add(new ImagePattern(new Image(getClass().getResource("/Colors/Yellow.jpg").toURI().toString()),0,0,1,1,true));
        blanks.add(new ImagePattern(new Image(getClass().getResource("/Colors/Red.jpg").toURI().toString()),0,0,1,1,true));
        blanks.add(new ImagePattern(new Image(getClass().getResource("/Colors/Green.jpg").toURI().toString()),0,0,1,1,true));
        blanks.add(new ImagePattern(new Image(getClass().getResource("/Colors/Blue.jpg").toURI().toString()),0,0,1,1,true));
        blanks.add(new ImagePattern(new Image(getClass().getResource("/Colors/RB.jpg").toURI().toString()),0,0,1,1,true));

        allColorLists = new ArrayList<>();

        allColorLists.add(whites);
        allColorLists.add(yellows);
        allColorLists.add(reds);
        allColorLists.add(greens);
        allColorLists.add(blues);
        allColorLists.add(rainbows);

        blank = new ImagePattern(new Image(getClass().getResource("/Colors/Blank.jpg").toURI().toString()),0,0,1,1,true);
    }
    public void addResultCards(boolean rainbowCard) throws URISyntaxException {
        int numOfResultCards = 5;
        if(rainbowCard) numOfResultCards++;
        for(int i = 0; i < numOfResultCards; ++i){
            Rectangle newCard = new Rectangle();
            newCard.arcHeightProperty().setValue(40);
            newCard.arcWidthProperty().setValue(40);
            newCard.widthProperty().setValue(100);
            newCard.heightProperty().setValue(100);
            newCard.setFill(blanks.get(i));
            resultCards.add(newCard);
            resultPane.getChildren().add(newCard);
        }
    }
    public void disableButtons(){
        hintButton.setDisable(true);
        playButton.setDisable(true);
        discardButton.setDisable(true);
    }
    public void addButtonsToArrayLists(){
        pHintButtons = new ArrayList<>();
        pHintButtons.add(p1Hint);
        pHintButtons.add(p2Hint);
        pHintButtons.add(p3Hint);
        pHintButtons.add(p4Hint);
        pHintButtons.add(p5Hint);
        pHintButtons.add(p6Hint);
        pHintButtons.add(p7Hint);

        cHintButtons = new ArrayList<>();
        cHintButtons.add(c1Hint);
        cHintButtons.add(c2Hint);
        cHintButtons.add(c3Hint);
        cHintButtons.add(c4Hint);
        cHintButtons.add(c5Hint);
        cHintButtons.add(c6Hint);

        nHintButtons = new ArrayList<>();
        nHintButtons.add(n1Hint);
        nHintButtons.add(n2Hint);
        nHintButtons.add(n3Hint);
        nHintButtons.add(n4Hint);
        nHintButtons.add(n5Hint);

        nPlayButtons = new ArrayList<>();
        nPlayButtons.add(n1Play);
        nPlayButtons.add(n2Play);
        nPlayButtons.add(n3Play);
        nPlayButtons.add(n4Play);
        nPlayButtons.add(n5Play);
        nPlayButtons.add(n6Play);

    }
    public void hideHintButtons(){
        hintPlayerPane.setVisible(false);
        colorHintPane.setVisible(false);
        numberHintPane.setVisible(false);
        hintTypePane.setVisible(false);
        hintPane.setVisible(false);
    }
    public void playerHintClicked(ActionEvent actionEvent) {
        int length = actionEvent.getSource().toString().length();
        char playerID = actionEvent.getSource().toString().charAt(length - 2);
        int playerIDInt = Character.getNumericValue(playerID);
        System.out.println(playerIDInt);
        playerHint = board.getPlayers().get(playerIDInt-1);
        hintTypePane.setVisible(true);
    }

    public void hintTypeButtonClicked(ActionEvent actionEvent) {
        int length = actionEvent.getSource().toString().length();
        char hintID = actionEvent.getSource().toString().charAt(length-3);
        System.out.println(hintID);
        if(hintID == 'E') {
            hintType = "NUMBER";
            colorHintPane.setVisible(false);
            numberHintPane.setVisible(true);
        }
        else {
            hintType = "COLOR";
            numberHintPane.setVisible(false);
            colorHintPane.setVisible(true);
        }
    }

    public void numberHintButtonClicked(ActionEvent actionEvent) {
        int length = actionEvent.getSource().toString().length();
        char numberID = actionEvent.getSource().toString().charAt(length-2);
        int numberIDInt = Character.getNumericValue(numberID);
        System.out.println(numberIDInt);
        numberHint = numberIDInt;
        hintDone();
    }

    public void colorHintButtonClicked(ActionEvent actionEvent) {
        char colorID = actionEvent.getSource().toString().charAt(11);
        int colorIDInt = Character.getNumericValue(colorID);
        System.out.println(colorIDInt);
        colorHint = Color.values()[colorIDInt-1];
        hintDone();
    }

    public void cardPlayButtonClicked(ActionEvent actionEvent) {
        int length = actionEvent.getSource().toString().length();
        char numberID = actionEvent.getSource().toString().charAt(length-2);
        int numberIDInt = Character.getNumericValue(numberID);
        System.out.println(numberIDInt);
        cardIx = numberIDInt - 1;
        if(isDiscard) discardDone();
        else playDone();
    }
    public void nextPlayer(int playerIndex){
        nextPlayerPane.setVisible(true);
        nextPlayerName.setText(board.getPlayers().get(playerIndex).getName());
    }

    public void nextPlayerButtonClicked(ActionEvent actionEvent) {
        nextPlayerPane.setVisible(false);
    }
    public void updateHintsAndLives(){
        StringBuilder str = new StringBuilder();
        str.append(board.getCurrentHints());
        str.append('/');
        str.append(board.getMaxHints());
        numberHintsLabel.setText(str.toString());
        StringBuilder str2 = new StringBuilder();
        str2.append(board.getCurrentLives());
        numberLivesLabel.setText(str2.toString());
    }
}