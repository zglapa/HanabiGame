package hanabi;

import hanabi.Model.Board;
import hanabi.Model.Deck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.io.IOException;

public class SetUpController {
    @FXML Slider numOfPlayers;
    @FXML Slider randomOrder;
    @FXML Button play;
    Stage stage;
    public void setUp() throws IOException {
        stage=new Stage();
        Parent setUpRoot;
        setUpRoot = FXMLLoader.load(getClass().getResource("/setUpFXML.fxml"));
        stage.setTitle("Set Up");
        stage.setScene(new Scene(setUpRoot, 1200, 600));
        stage.showAndWait();
    }

    public Board board;
    public void startGame(ActionEvent actionEvent) {
        System.out.println("Buton clicked");
        Double num= numOfPlayers.getValue();
        boolean random= (randomOrder.getValue()==1);
        board = new Board(num.intValue(), 40, 8, 8, 3, new Deck(true, true, true),
                random, Board.randomNames(num.intValue()));
        HanabiMain.setUpWindow.stage.close();
    }
}
