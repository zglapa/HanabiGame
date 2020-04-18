package hanabi.Controller;

import hanabi.Controller.MainMenuWindow;
import hanabi.Controller.SetUpWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Platform.exit;

public class HanabiMain extends Application {
    Parent root;
    public static SetUpWindow setUpWindow=new SetUpWindow();
    public static MainMenuWindow mainMenuWindow = new MainMenuWindow();

    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        mainMenuWindow.setUp();
        switch (MainMenuWindow.action) {
            case 0: //forced exit
            case 2: //settings
            case 3: //exit
                exit();
                break;
            case 1: //play
                setUpWindow.setUp();
                root = FXMLLoader.load(getClass().getResource("/GameFXML.fxml"));
                stage.setTitle("Hanabi");
                stage.setScene(new Scene(root, 1600, 900));
                stage.show();
                break;
        }
    }

}