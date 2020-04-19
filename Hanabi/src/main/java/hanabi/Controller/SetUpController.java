package hanabi.Controller;

import hanabi.Model.Board;
import hanabi.Model.Deck;
import hanabi.Model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ResourceBundle;

public class SetUpController implements Initializable {
    @FXML Slider numOfPlayers;
    @FXML Slider numOfCards;
    @FXML CheckBox randomOrder;
    @FXML CheckBox hasRainbows;
    @FXML Button randomNames;
    @FXML VBox advancedSettings;

    @FXML Button showAdvanced;
    @FXML VBox namesBox;
    @FXML TextField name1;
    @FXML TextField name2;
    @FXML TextField name3;
    @FXML TextField name4;
    @FXML TextField name5;
    @FXML TextField name6;
    @FXML TextField name7;
    ArrayList<TextField> names;
    @FXML TextField initialHints;
    @FXML TextField initialLives;
    @FXML TextField limitHints;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HanabiMain.setUpWindow.stage.setOnCloseRequest(e->HanabiMain.setUpWindow.exit=true);
        names=new ArrayList<>();
        names.add(name1);
        names.add(name2);
        names.add(name3);
        names.add(name4);
        names.add(name5);
        names.add(name6);
        names.add(name7);
        for(int i=0;i<7;++i)
            names.get(i).setText("Player"+(i+1));
        initialHints.setText("8");
        limitHints.setText("8");
        initialLives.setText("3");
    }

    public void adjustCards(MouseEvent mouseEvent) {
        double players=numOfPlayers.getValue();
        if(players<=3.0)
            numOfCards.setValue(5.0);
        else if(players<=5.0)
            numOfCards.setValue(4.0);
        else
            numOfCards.setValue(3.0);

        for(int i=0;i<7;++i)
            names.get(i).setVisible(i<players);
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
        String[] random= Board.randomNames(7);
        for(int i=0;i<7;++i)
            names.get(i).setText(random[i]);
    }

    public void startGame(ActionEvent actionEvent) {
        int players= ( (Double) numOfPlayers.getValue() ).intValue();
        int cards= ( (Double) numOfCards.getValue() ).intValue();
        boolean rainbow= hasRainbows.isSelected();
        boolean random= randomOrder.isSelected();
        String[] finalNames=new String[players];
        for(int i=0;i<players;++i)
            finalNames[i]=names.get(i).getText();

        int lives = stringInt(initialLives.getText());
        int hints = stringInt(initialHints.getText());
        int maxHints = stringInt(limitHints.getText());

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


        HanabiMain.setUpWindow.board = new Board(players, lives, hints, maxHints, cards, new Deck(true, rainbow, true),
                random, finalNames);
        HanabiMain.setUpWindow.hasRainbows = rainbow;
        HanabiMain.setUpWindow.stage.close();
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
}
