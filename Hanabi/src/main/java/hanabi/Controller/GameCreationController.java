package hanabi.Controller;

import hanabi.Controller.Boxes.AlertBox;
import hanabi.Controller.Boxes.ConfirmationBox;
import hanabi.Model.Board;
import hanabi.Model.Deck;
import hanabi.Server.ClientSideConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.io.OptionalDataException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class GameCreationController implements Initializable {
    @FXML Pane waitingPane;
    @FXML Slider numOfPlayers;
    @FXML Slider numOfCards;
    @FXML CheckBox hasRainbows;
    @FXML Button randomNames;
    @FXML VBox advancedSettings;
    @FXML TextField ID;
    @FXML Button showAdvanced;
    @FXML VBox namesBox;
    @FXML TextField name1;
    @FXML TextField initialHints;
    @FXML TextField initialLives;
    @FXML TextField limitHints;
    @FXML CheckBox handManagement,smallPenalty;
    @FXML VBox IPBox;
    @FXML CheckBox Server;

    //deckCreation
    @FXML StackPane mainStackPane;
    @FXML Button editDeckButton;
    public ArrayList<HBox> rainbowHBoxes;
    public ArrayList<Button> rainbowButtons;
    public final int smallButtonSize = 25;
    public final int bigButtonSize = 50;
    ArrayList<ArrayList<Label>> cardsAmount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deckCreation();
        //for(int i=0;i<7;++i)
        //names.get(i).setText("Player"+(i+1));
        initialHints.setText("8");
        limitHints.setText("8");
        initialLives.setText("3");
        Tooltip tooltip = new Tooltip(
                "Selecting this will make players' hands automatically react to all hints.\n" +
                        "For new players this may seem counterintuitive, however we still recommend trying it.\n" +
                        "*WARNING* this setting applies globally for all players!"
        );
        tooltip.setShowDelay(new Duration(500));
        tooltip.setShowDuration(Duration.INDEFINITE);
        handManagement.setTooltip(tooltip);
        Tooltip tooltip2 = new Tooltip(
                "Selecting this will make you lose only 2 points if you somehow manage to lose all lives\n" +
                        "Other option is going down to 0 points"
        );
        tooltip2.setShowDelay(new Duration(500));
        tooltip2.setShowDuration(Duration.INDEFINITE);
        smallPenalty.setTooltip(tooltip2);
        smallPenalty.selectedProperty().setValue(true);
        waitingPane.setVisible(false);
        hasRainbows.selectedProperty().setValue(true);
        handManagement.selectedProperty().setValue(true);
    }

    public void handleReturn(ActionEvent actionEvent) {
        GameCreationWindow.end=false;
        HanabiMain.gameInformation.settingsStage.close();
    }

    public void adjustCards(MouseEvent mouseEvent) {
        double players=numOfPlayers.getValue();
        if(players<=3.0)
            numOfCards.setValue(5.0);
        else if(players<=5.0)
            numOfCards.setValue(4.0);
        else
            numOfCards.setValue(3.0);
    }

    public void showAdvanced(MouseEvent mouseEvent) {
        if (showAdvanced.getText().equals("Show")) {
            advancedSettings.setVisible(true);
            showAdvanced.setText("Hide");
        } else {
            advancedSettings.setVisible(false);
            showAdvanced.setText("Show");
        }
    }

    public void hideAdvanced(MouseEvent mouseEvent) {
        advancedSettings.setVisible(false);
        showAdvanced.setVisible(true);
    }

    public void changeNames(MouseEvent mouseEvent) {
        name1.setText( Board.randomNames(1)[0] );
    }

    public void IPClicked(ActionEvent actionEvent) {
        IPBox.setVisible(!Server.isSelected());
    }

    public void startGame(ActionEvent actionEvent) throws InterruptedException {
        int players= ( (Double) numOfPlayers.getValue() ).intValue();
        int cards= ( (Double) numOfCards.getValue() ).intValue();
        boolean rainbow= hasRainbows.isSelected();
        boolean random= false;
        boolean handMan = handManagement.isSelected();
        boolean smallPen = smallPenalty.isSelected();
        String[] finalNames=new String[players];
        finalNames[0] = ( name1.getText().equals(""))?Board.randomNames(1)[0]:name1.getText();
        for(int i=1;i<players;++i) {
            finalNames[i]="Player"+(i+1);
        }

        int lives = stringInt(initialLives.getText());
        int hints = stringInt(initialHints.getText());
        int maxHints = stringInt(limitHints.getText());

        if(finalNames[0].length()>20) {
            AlertBox.display("Wrong input", "Name must not exceed 20 characters!");
            return;
        }
        if (hints < 0) {
            AlertBox.display("Wrong input", "Initial hint amount must be an non-negative integer!");
            return;
        }
        if (maxHints < 0) {
            AlertBox.display("Wrong input", "Maximum hint amount must be an non-negative integer!");
            return;
        }
        if (lives <= 0) {
            AlertBox.display("Wrong input", "Lives amount must be an positive integer!");
            return;
        }

        if (maxHints < hints) {
            boolean result = ConfirmationBox.display("Max hints < hints",
                    "Max hint amount is smaller than initial hint amount\n" +
                            "While your hint amount is bigger than max hint amount\n" +
                            "you won't get any hints for the discard action.\n" +
                        "This may or may not be intended\n" +
                            "Do you wish to proceed?"
            );
            if (!result)
                return;
        }
        Deck deck;

        try {
            deck = Deck.createDeck(cardsAmount, rainbow, players * cards + 1);
        } catch (Exception e) {
            AlertBox.display("Wrong input", "Not enough cards in the deck!");
            return;
        }
        waitingPane.setVisible(true);
        System.out.println(waitingPane.isVisible());
        HanabiMain.gameInformation.board = new Board(players, lives, hints, maxHints, cards, deck,
                random, handMan, smallPen, finalNames);
        HanabiMain.gameInformation.playerName = finalNames[0];
        System.out.println(finalNames[0]);
        if(Server.isSelected()){

            HanabiMain.serverThread= new StartServer();
            HanabiMain.serverThread.start();
            while (!HanabiMain.gameInformation.serverReady) {
                sleep(10);
                System.out.println("waited");
            }
            HanabiMain.gameInformation.serverID="";
        }
        else{
            HanabiMain.gameInformation.serverID = ID.getText();
        }
        GameCreationWindow.setUpBoard = HanabiMain.gameInformation.board;
        HanabiMain.csc = new ClientSideConnection();
        HanabiMain.gameInformation.receivedBoard = null;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Object o = null;
                while(HanabiMain.gameInformation.receivedBoard == null){
                    try{
                        o = HanabiMain.csc.in.readObject();
                        HanabiMain.gameInformation.receivedBoard = (Board)o;
                        System.out.println("[received board]");
                    }catch (ClassCastException | OptionalDataException e){
                        System.out.println(o);
                    } catch (Exception e){
                        try {
                            HanabiMain.csc.socket.close();
                        }
                        catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        finally {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    GameJoiningWindow.end=false;
                                    HanabiMain.gameInformation.settingsStage.close();
                                }
                            });
                            Thread.currentThread().interrupt();
                        }
                    }
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        HanabiMain.gameInformation.settingsStage.close();
                    }
                });
                Thread.currentThread().interrupt();
            }
        });
        t.start();

    }



    private int stringInt(String s) {
        if (s == null)
            return -1;
        int ans = 0;
        for (int i = 0; i< s.length(); i++) {
            if (s.charAt(i) > '9' || s.charAt(i) < '0') {
                return -1;
            }
            ans*=10;
            ans+=s.charAt(i)-'0';
        }
        return ans;
    }

    //some manual fxml stuff, better don't look down
    public void deckCreation() {
        cardsAmount = new ArrayList<>();
        ArrayList<Button> smallButtons = new ArrayList<>();
        ArrayList<Button> bigButtons = new ArrayList<>();
        ArrayList<Rectangle> cards = new ArrayList<>();
        ArrayList<Label> allLabels = new ArrayList<>();
        rainbowHBoxes = new ArrayList<>();
        rainbowButtons = new ArrayList<>();


        StackPane deckCreation = new StackPane();
        deckCreation.setAlignment(Pos.CENTER);
        Rectangle background = new Rectangle(1500, 800);
        background.arcHeightProperty().setValue(50);
        background.arcWidthProperty().setValue(50);
        background.setFill(javafx.scene.paint.Color.CORNFLOWERBLUE);
        GridPane grid = new GridPane();
        grid.setMaxWidth(1300);
        grid.setMinWidth(1300);
        grid.setMaxHeight(600);
        grid.setMinHeight(600);
        grid.setHgap(15);
        grid.setVgap(15);

        Button defaultButt = new Button("Reset");
        Button confirmButt = new Button("Save");

        ArrayList<Button> controlButtons = new ArrayList<>();
        controlButtons.add(defaultButt);
        controlButtons.add(confirmButt);

        int controlIndex = 1;
        for (Button butt : controlButtons) {
            butt.setMinWidth(100);
            butt.setMaxWidth(100);
            butt.setMinHeight(60);
            butt.setMaxHeight(60);
            StackPane sp = new StackPane();
            sp.getChildren().addAll(butt);
            grid.add(sp, controlIndex--, 8);
            butt.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-font-family: Purisa Bold;");
        }

        confirmButt.setOnAction(event -> deckCreation.setVisible(false));
        defaultButt.setOnAction(event -> {
            for (int i = 0; i< 5; i++) {
                for (int j = 0; j< 6; j++) {
                    Label label = cardsAmount.get(i).get(j);
                    if (j==5 || i==4) {
                        label.setText("1");
                        continue;
                    }
                    if (i==0) {
                        label.setText("3");
                        continue;
                    }
                    label.setText("2");
                }
            }
        });

        for (int i = 1; i<= 5; i++) {
            ArrayList<Label> labels = new ArrayList<>();
            for (int j = 0; j< 6; j++) {
                HBox hbox = new HBox();
                hbox.setAlignment(Pos.CENTER);
                hbox.setSpacing(10);
                VBox vbox = new VBox();
                Button buttPlus = new Button();
                Button buttMinus = new Button();
                Label label = new Label();
                if (j==5 || i==5) {
                    label.setText("1");
                } else
                if (i==1) {
                    label.setText("3");
                } else
                    label.setText("2");


                buttMinus.setText("-");
                buttPlus.setText("+");
                buttMinus.setOnAction(event -> decrease(label));
                buttPlus.setOnAction(event -> increase(label));

                vbox.getChildren().addAll(buttPlus, buttMinus);
                Rectangle rec = new Rectangle(bigButtonSize, bigButtonSize);

                hanabi.Model.Color color = hanabi.Model.Color.getReverseOrdinal(j);
                StringBuilder address = new StringBuilder("/Colors/");
                if (color == hanabi.Model.Color.RAINBOW) {
                    address.append("RB");
                    address.append(i);
                    address.append(".jpg");
                } else {
                    assert color != null;
                    address.append(color.toString());
                    for (int k = 9; k < address.length(); k++)
                        address.setCharAt(k, (char)(address.charAt(k) + 32));
                    address.append(i);
                    address.append(".jpg");
                }
                //System.out.println(address);

                try {
                     ImagePattern pattern = new ImagePattern(new Image(getClass().getResource(new String(address)).toURI().toString()), 0, 0, 1, 1, true);
                     rec.setFill(pattern);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Label labelX = new Label("x");
                labelX.setMaxWidth(20);
                labelX.setStyle("-fx-font-size: 32; -fx-font-weight: bold;");
                hbox.getChildren().addAll(vbox, rec, labelX, label);
                labels.add(label);
                //allLabels.add(labelX); IT IS ALL SET HERE
                allLabels.add(label);
                grid.add(hbox, j+1, i+1);
                smallButtons.add(buttPlus);
                smallButtons.add(buttMinus);
                cards.add(rec);
                if (j==5)
                    rainbowHBoxes.add(hbox);
            }
            cardsAmount.add(labels);
        }

        //color ones
        for (int i = 0; i< 6; i++) {
            Button plus = new Button();
            plus.setText("+");

            int finalI = i;
            plus.setOnAction(event -> {
                for (int j = 0; j< 5; j++) {
                    increase(cardsAmount.get(j).get(finalI));
                }
            });

            Button minus = new Button();
            minus.setText("-");
            minus.setOnAction(event -> {
                for (int j = 0; j< 5; j++) {
                    decrease(cardsAmount.get(j).get(finalI));
                }
            });


            for (int j=0; j< 2; j++) {
                HBox hbox = new HBox();
                hbox.setAlignment(Pos.BASELINE_LEFT);
                hbox.setSpacing(10);
                Region reg = new Region();
                reg.setMaxHeight(smallButtonSize*2);
                reg.setMinHeight(smallButtonSize*2);
                reg.setMaxWidth(smallButtonSize);
                reg.setMinWidth(smallButtonSize);
                if (j==0) {
                    hbox.getChildren().addAll(reg, plus);
                    grid.add(hbox, i+1, 0);
                } else {
                    hbox.getChildren().addAll(reg, minus);
                    grid.add(hbox, i+1, 7);
                }
            }

            //grid.add(plus, i+1, 0);
            //grid.add(minus, i+1, 7);


            bigButtons.add(plus);
            bigButtons.add(minus);
            if (i==5) {
                rainbowButtons.add(plus);
                rainbowButtons.add(minus);
            }
        }

        //number ones
        for (int i = 0; i< 5; i++) {
            Button plus = new Button();
            plus.setText("+");

            int finalI = i;
            plus.setOnAction(event -> {
                for (int j = 0; j< 6; j++) {
                    increase(cardsAmount.get(finalI).get(j));
                }
            });

            Button minus = new Button();
            minus.setText("-");
            minus.setOnAction(event -> {
                for (int j = 0; j< 6; j++) {
                    decrease(cardsAmount.get(finalI).get(j));
                }
            });

            grid.add(plus, 7, i+2);
            grid.add(minus, 0, i+2);
            bigButtons.add(plus);
            bigButtons.add(minus);
        }

        //making things pretty
        for (Label label : allLabels) {
            label.setStyle("-fx-font-size: 32; -fx-font-weight: bold;");
            label.setMinWidth(50);
            label.setMaxWidth(50);
        }

        for (Button button : bigButtons) {
            button.setMinWidth(bigButtonSize);
            button.setMaxWidth(bigButtonSize);
            button.setMinHeight(bigButtonSize);
            button.setMaxHeight(bigButtonSize);
            button.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        }

        for (Button button : smallButtons) {
            button.setMinWidth(smallButtonSize);
            button.setMaxWidth(smallButtonSize);
            button.setMinHeight(smallButtonSize);
            button.setMaxHeight(smallButtonSize);
            button.setStyle("-fx-font-size: 10; -fx-font-weight: bold;");
        }

        for (Rectangle rec : cards) {
            rec.setArcHeight(12);
            rec.setArcWidth(12);
        }

        VBox vbox = new VBox();
        Label title = new Label("Forge your own deck!");
        title.setStyle("-fx-font-size: 48; -fx-font-weight: bold; -fx-font-family: Purisa Bold;");
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        vbox.getChildren().addAll(title, grid);

        deckCreation.getChildren().addAll(background, vbox);
        mainStackPane.getChildren().addAll(deckCreation);

        deckCreation.setVisible(false);
        editDeckButton.setOnAction(event -> {
            for (HBox hbox : rainbowHBoxes)
                hbox.setVisible(hasRainbows.isSelected());
            for (Button button : rainbowButtons)
                button.setVisible(hasRainbows.isSelected());
            deckCreation.setVisible(true);
        });
    }

    void decrease (Label label) {
        label.setText((stringInt(label.getText()) == 1) ? "1" : new String(new StringBuilder().append(stringInt(label.getText())-1)));
        return;
    }
    void increase (Label label) {
        label.setText(new String(new StringBuilder().append(stringInt(label.getText())+1)));
        return;
    }

}
