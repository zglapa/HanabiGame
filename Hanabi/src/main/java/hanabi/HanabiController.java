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
    @FXML
    public Button addPlayerButton;
    @FXML
    public TextField textF2;
    @FXML
    public ComboBox<Integer> numPlayers;
    public TableView<Player> tableOfPlayers;
    public Label selectedNames;
    @FXML
    TextField playerNames;
    @FXML
    Button startGameButton;
    String[] players;
    static int iter;
    static int numOfPlayers;
    @FXML
    TextField textF1;
    @FXML
    Button button1;
    @FXML
    Label firstLabel;
    Board board;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View is now loaded!");
    }

    public void handleButtonClick(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage)startGameButton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/GameFXML.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        board = new Board(numOfPlayers, 40, 8, 8, 0, new Deck(true, true, true), false, players[0], players[1], players[2], players[3]);
        stage.show();
    }

    public void addPlayer(ActionEvent event) {
        if(iter < numOfPlayers){
            players[iter] = playerNames.getText();
            selectedNames.setText(selectedNames.getText() + ", " + players[iter]);
            iter++;
        }

    }

    public void selectedNumberOfPlayers(ActionEvent event) {
        numOfPlayers = numPlayers.getValue();
        players = new String[numOfPlayers];
    }
}