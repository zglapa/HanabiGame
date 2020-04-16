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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    @FXML Button exitButton;
    @FXML Button playButton;
    @FXML Button settingsButton;


    public void handleExitButton() {
        MainMenuWindow.action = 3;
        MainMenuWindow.stage.close();
    }

    public void handleSettingsButton() {
        MainMenuWindow.action = 2;
        MainMenuWindow.stage.close();
    }

    public void handlePlayButton() {
        MainMenuWindow.action = 1;
        MainMenuWindow.stage.close();
    }
}
