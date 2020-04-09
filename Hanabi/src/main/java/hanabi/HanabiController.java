package hanabi;
import hanabi.Model.Board;
import hanabi.Model.Deck;
import hanabi.Model.Player;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HanabiController implements Initializable {

    Board board;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        board = new Board(4, 40, 8, 8, 0, new Deck(true, true, true), false, "Player1", "Player2", "Player3", "Player4");
        System.out.println("View is now loaded!");
    }



}