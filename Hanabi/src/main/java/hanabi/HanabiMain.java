package hanabi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HanabiMain extends Application {
    Parent root;
    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/GameFXML.fxml"));
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 1200, 600));
        stage.show();
    }

}
