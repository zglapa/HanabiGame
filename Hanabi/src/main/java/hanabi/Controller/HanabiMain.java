package hanabi.Controller;

import javafx.application.Application;
import javafx.stage.Stage;

import static javafx.application.Platform.exit;

public class HanabiMain extends Application {
    public static GameInformation gameInformation = new GameInformation();

    public static void main(String[] args){
        launch(args);
    }

    public boolean startMultiplayer(Stage stage) throws Exception {
        boolean end;
        do {
            end=true;
            MultiplayerModeWindow.run();
            switch (MultiplayerModeWindow.action) {
                case 0: //forced exit
                    break;

                case 1: //create a game
                    GameCreationWindow.run();
                    if(GameCreationWindow.end)
                        StartGame.run(stage);
                    else
                        end=false;
                    break;

                case 2: //join game
                    GameJoiningWindow.run();
                    if(GameJoiningWindow.end)
                        StartGame.run(stage);
                    else
                        end=false;
                    break;

                case 3: // exit
                    exit();

                case 4: //return
                    return false;
            }
        }while(!end);
        return true;
    }

    @Override
    public void start(Stage stage) throws Exception {
        boolean end;
        do {
            end=true;
            MainMenuWindow.run();
            switch (MainMenuWindow.action) {
                case 0: //forced exit
                    break;

                case 1: //hotseat
                    SetUpWindow.run();
                    StartGame.run(stage);
                    break;

                case 2: //multiplayer
                    end=startMultiplayer(stage);
                    break;

                case 3: //exit
                    exit();
                    break;
            }
        }while(!end);
    }
}