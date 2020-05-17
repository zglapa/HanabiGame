package hanabi.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML Button exitButton;
    @FXML Button playButton;
    @FXML Button settingsButton;
    @FXML Rectangle background;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            background.setFill(new ImagePattern(new Image(getClass().getResource("/textures/hanabi_background.png").toURI().toString()),0,0,1,1,true));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //image.setVisible(false);
    }

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
