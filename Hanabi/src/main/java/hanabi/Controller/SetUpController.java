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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        advancedSettings.setVisible(true);
        showAdvanced.setVisible(false);
    }

    public void hideAdvanced(MouseEvent mouseEvent) {
        advancedSettings.setVisible(false);
        showAdvanced.setVisible(true);
    }

    public void changeNames(MouseEvent mouseEvent) {
        int players= ( (Double) numOfPlayers.getValue() ).intValue();
        String[] random= Board.randomNames(players);
        for(int i=0;i<players;++i)
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

        HanabiMain.setUpWindow.board = new Board(players, 40, 8, 8, cards, new Deck(true, rainbow, true),
                random, finalNames);
        HanabiMain.setUpWindow.hasRainbows = rainbow;
        HanabiMain.setUpWindow.stage.close();
    }


}
