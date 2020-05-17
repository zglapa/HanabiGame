package hanabi.Controller;

import hanabi.Model.Board;
import hanabi.Model.Deck;
import hanabi.Model.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ResourceBundle;

public class MultiplayerModeController implements Initializable {

    @FXML Button createButton;
    @FXML Button joinButton;
    @FXML Button exitButton;
    @FXML
    Rectangle background;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            background.setFill(new ImagePattern(new Image(getClass().getResource("/textures/image0.png").toURI().toString()),0,0,1,1,true));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //image.setVisible(false);
    }

    public void handleCreate(ActionEvent actionEvent) {
        MultiplayerModeWindow.action=1;
        HanabiMain.multiplayerModeWindow.stage.close();
    }

    public void handleJoin(ActionEvent actionEvent) {
        MultiplayerModeWindow.action=2;
        HanabiMain.multiplayerModeWindow.stage.close();
    }

    public void handleExit(ActionEvent actionEvent) {
        MultiplayerModeWindow.action=3;
        HanabiMain.multiplayerModeWindow.stage.close();
    }
}
