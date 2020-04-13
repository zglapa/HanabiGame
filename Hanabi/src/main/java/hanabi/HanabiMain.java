package hanabi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HanabiMain extends Application {
    Parent root,setUpRoot;
    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        /*setUpRoot = FXMLLoader.load(getClass().getResource("/setUpFXML.fxml"));
        stage.setTitle("Set Up");
        stage.setScene(new Scene(setUpRoot, 1200, 600));
        stage.show();*/

        root = FXMLLoader.load(getClass().getResource("/GameFXML.fxml"));
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 1200, 600));
        stage.show();
    }

}
