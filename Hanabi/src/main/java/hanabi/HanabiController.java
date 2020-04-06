package hanabi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import java.net.URL;
import java.util.ResourceBundle;

public class HanabiController implements Initializable {

    @FXML
    Button button1;
    @FXML
    Label firstLabel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View is now loaded!");
    }

    public void handleButtonClick(ActionEvent event) {
        firstLabel.setText("clicked");
    }

}