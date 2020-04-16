package hanabi.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
