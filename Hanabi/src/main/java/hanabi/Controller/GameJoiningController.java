package hanabi.Controller;

import hanabi.Controller.Boxes.AlertBox;
import hanabi.Controller.Boxes.ConnectionBox;
import hanabi.Model.Board;
import hanabi.Server.ClientSideConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.EOFException;
import java.io.IOException;
import java.io.OptionalDataException;
import java.net.ConnectException;


public class GameJoiningController {
    @FXML Pane waitingPane;
    @FXML TextField name1;
    @FXML TextField ID;
    @FXML Button randomNames;
    public void changeNames(MouseEvent mouseEvent) {
        name1.setText( Board.randomNames(1)[0] );
    }

    public void handleReturn(ActionEvent actionEvent) {
        GameJoiningWindow.end=false;
        HanabiMain.gameInformation.settingsStage.close();
    }

    public void startGame(ActionEvent actionEvent) {
        String finalName = ( name1.getText().equals(""))? Board.randomNames(1)[0] :name1.getText();

        if(finalName.length()>20) {
            AlertBox.display("Wrong input", "Name must not exceed 20 characters!");
            return;
        }

        HanabiMain.gameInformation.playerName = finalName;
        HanabiMain.gameInformation.serverID = ID.getText();

        try{
            HanabiMain.csc = new ClientSideConnection();
        }catch (RuntimeException e){
            ConnectionBox.display("Alert", "Server does not exist! \n\n");
            return;
        }
        waitingPane.setVisible(true);
        System.out.println(waitingPane.isVisible());
        Object o = null;
        HanabiMain.gameInformation.receivedBoard = null;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Object o = null;
                while(HanabiMain.gameInformation.receivedBoard == null){
                    try{
                        o = HanabiMain.csc.in.readObject();
                        HanabiMain.gameInformation.receivedBoard = (Board)o;
                        System.out.println("[received board]");
                    }catch (ClassCastException | OptionalDataException e){
                        System.out.println(o);
                    } catch (Exception e){
                        try {
                            HanabiMain.csc.socket.close();
                        }
                        catch (IOException ioException) {
                            ioException.printStackTrace();
                        }catch (NullPointerException ignored){}
                        GameJoiningWindow.end=false;
                        break;
                    }
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        HanabiMain.gameInformation.settingsStage.close();
                    }
                });
                Thread.currentThread().interrupt();
            }
        });
        t.start();
    }
}
