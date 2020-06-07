package hanabi.Controller;

import hanabi.Model.Board;
import javafx.stage.Stage;

public class GameInformation {
    public Board board;
    public String playerName;
    public String serverID;
    public boolean exit=false;
    public boolean serverReady=false;
    public Stage settingsStage;
    public Board receivedBoard;
}
