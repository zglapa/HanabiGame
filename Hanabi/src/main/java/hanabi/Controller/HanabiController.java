package hanabi.Controller;
import hanabi.Model.*;
import hanabi.Model.Board;
import hanabi.Model.Color;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class HanabiController implements Initializable {


    @FXML FlowPane playerHands, discardPane, resultPane;
    @FXML Label moveHistory;
    @FXML ComboBox<String> hintTypeChoice;
    @FXML ComboBox<Color> colorChoice;
    @FXML ComboBox<Integer> playerChoice, cardChoice, numberChoice;;
    @FXML Label discardPile;
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
    ArrayList<ImagePattern> blanks;
    ArrayList<ArrayList<ImagePattern>> allColorLists;
    ImagePattern blank;
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
    ArrayList<Rectangle> discardCards;
    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        try {
            addColors();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        players = new ArrayList<>();
        /*players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);
        players.add(player6);
        players.add(player7);
        players.add(player8);
        players.add(player9);
        players.add(player10);
        */
        resultCards = new ArrayList<>();
        discardCards = new ArrayList<>();
        cards = new ArrayList<>();
        board= HanabiMain.setUpWindow.board;
        int PLAYERAMOUNT = board.getPlayerAmount();
        int HANDSIZE = board.getHandSize();
        boolean WITHRAINBOWS = HanabiMain.setUpWindow.hasRainbows;
        //addCardsToArrayList(PLAYERAMOUNT);
        addHands(PLAYERAMOUNT,HANDSIZE);
        try {
            addResultCards(WITHRAINBOWS);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        updateHands();
        //addDiscardCards();
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
        if(playerIx == null) playerIx = 0;
        Player hintedPlayer = board.getPlayers().get(playerIx);
        Hint hint;
        if(hintType == null) hintType = "NUMBER";
        if(hintType.equals("NUMBER")){
            if(cardValueChoice == null) cardValueChoice=1;
            hint = new Hint(hintedPlayer,cardValueChoice);
        }
        else{
            if(colorIx == null) colorIx = Color.values()[0];
            hint = new Hint(hintedPlayer,colorIx);
        }
        PlayerMove playerMove = new PlayerMove(player,movetype,hint);
        try{
            board.action(playerMove);
        } catch (GameEndException e) {
            //endGameLabel.setText("Game over");
        }catch (NoHintsLeftException e){
            NoHints.display("Alert","No hints left");
        }
        updateHands();
        updateHands(index);
        blurMe((index+1)%board.getPlayerAmount());
        updateResultCards();
        updateMoveHistory();
    }
    public void discardButtonClicked(ActionEvent actionEvent) {
        int index = board.getCurrentPlayerIndex();
        Player player = board.getPlayers().get(index);
        MoveType movetype = MoveType.DISCARD;
        PlayerMove playerMove = new PlayerMove(player,movetype, cardIx);
        try{
            board.action(playerMove);
        } catch (GameEndException | NoHintsLeftException e) {
            //endGameLabel.setText("Game over");
        }
        updateHands();
        updateHands(index);
        blurMe((index+1)%board.getPlayerAmount());
        updateResultCards();
        updateDiscardPileCards();
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
        } catch (GameEndException | NoHintsLeftException e) {
            //endGameLabel.setText("Game over");
        }
        updateHands();
        updateHands(index);
        blurMe((index+1)%board.getPlayerAmount());
        updateResultCards();
        updateMoveHistory();
        System.out.println(board.getCurrentPlayerIndex());
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
    public void addHands(int numberOfPlayers, int handSize){
        for(int i = 0;i < numberOfPlayers; ++i){
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
                newCard.arcHeightProperty().setValue(10);
                newCard.arcWidthProperty().setValue(10);
                newCard.widthProperty().setValue(40);
                newCard.heightProperty().setValue(40);
                cards.get(i).add(newCard);
                gridPane.add(newCard,j,0);
            }
            player.setPrefWidth(gridPane.getPrefWidth());
            outerGrid.add(player, 0,0);
            outerGrid.add(gridPane, 0, 1);
            playerHands.getChildren().add(outerGrid);
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

        blanks.add(new ImagePattern(new Image(getClass().getResource("/Colors/White.jpg").toURI().toString())));
        blanks.add(new ImagePattern(new Image(getClass().getResource("/Colors/Yellow.jpg").toURI().toString())));
        blanks.add(new ImagePattern(new Image(getClass().getResource("/Colors/Red.jpg").toURI().toString())));
        blanks.add(new ImagePattern(new Image(getClass().getResource("/Colors/Green.jpg").toURI().toString())));
        blanks.add(new ImagePattern(new Image(getClass().getResource("/Colors/Blue.jpg").toURI().toString())));
        blanks.add(new ImagePattern(new Image(getClass().getResource("/Colors/RB.jpg").toURI().toString())));

        allColorLists = new ArrayList<>();

        allColorLists.add(whites);
        allColorLists.add(yellows);
        allColorLists.add(reds);
        allColorLists.add(greens);
        allColorLists.add(blues);
        allColorLists.add(rainbows);

        blank = new ImagePattern(new Image(getClass().getResource("/Colors/Blank.jpg").toURI().toString()));
    }
    public void addResultCards(boolean rainbowCard) throws URISyntaxException {
        int numOfResultCards = 5;
        if(rainbowCard) numOfResultCards++;
        for(int i = 0; i < numOfResultCards; ++i){
            Rectangle newCard = new Rectangle();
            newCard.arcHeightProperty().setValue(30);
            newCard.arcWidthProperty().setValue(30);
            newCard.widthProperty().setValue(80);
            newCard.heightProperty().setValue(80);
            newCard.setFill(blanks.get(i));
            resultCards.add(newCard);
            resultPane.getChildren().add(newCard);
        }
    }
}