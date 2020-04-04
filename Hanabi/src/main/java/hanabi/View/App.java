package hanabi.View;


import hanabi.Controller.Controller;
import hanabi.Model.Board;
import hanabi.Model.MoveType;
import hanabi.Model.Player;
import hanabi.Model.PlayerMove;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    void imba(TextArea text) {
        Board board = new Board(4, "twoja", "stara", "twoj", "stary");
        while(true){

            text.appendText(board.toString());
            MoveType type = MoveType.DISCARD;
            Player player = board.getPlayers().get(board.getCurrentPlayerIndex());
            PlayerMove move = new PlayerMove(player,type,1);
            try{
                board.action(move);
            }catch (Exception e){
                e.printStackTrace();
                break;
            }
        }
    }

    @Override
    public void start(Stage stage) {
        /*var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
         */
        stage.setTitle("Kozak window");
        TextArea text=new TextArea("Ale tu sie bedzie dzialo\n");
        Button button=new Button("Start a inba");
        button.setOnAction(e -> imba(text));

        StackPane layout=new StackPane();
        layout.getChildren().addAll(text,button);

        Scene scene=new Scene(layout,500,300);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}