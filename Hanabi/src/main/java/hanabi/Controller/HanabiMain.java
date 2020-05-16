package hanabi.Controller;

import javafx.application.Application;
import javafx.stage.Stage;

import static javafx.application.Platform.exit;

public class HanabiMain extends Application {
    public static MainMenuWindow mainMenuWindow = new MainMenuWindow();
    public static MultiplayerModeWindow multiplayerModeWindow = new MultiplayerModeWindow();
    public static GameInformation gameInformation = new GameInformation();

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainMenuWindow.setUp();
        switch (MainMenuWindow.action) {
            case 0: //forced exit
            break;
            case 1: //hotseat
                SetUpWindow.run();
                StartGame.run(stage);
            break;
            case 2: //multiplayer
                multiplayerModeWindow.setUp();
                switch (MultiplayerModeWindow.action) {
                    case 0: //forced exit
                    break;
                    case 1: //create a game
                        GameCreationWindow.run();
                        StartGame.run(stage);
                    break;
                    case 2: //join game
                        GameJoiningWindow.run();
                        StartGame.run(stage);
                    break;
                    case 3: // exit
                        exit();
                    break;
                }
            break;
            case 3: //exit
                exit();
            break;
        }
    }
}