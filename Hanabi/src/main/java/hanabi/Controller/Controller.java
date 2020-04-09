package hanabi.Controller;


import hanabi.Model.*;
//import hanabi.View.HanabiView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Controller {
    private static Stage stage;
    public static void main(String[] args) throws Exception {
        /*HanabiView view = new HanabiView();
        Controller c = new Controller(view);
        */
        Scanner s = new Scanner(System.in);
        System.out.println("Number of players 4");
        //int numOfPlayers = s.nextInt();
        String[] players = new String[4];
        for(int i =0; i<4;++i){
            players[i] = s.next();
        }
        Board board = new Board(4, 40, 8, 8, 1, new Deck(true, true, true), false, players[0], players[1], players[2], players[3]);
        while(true){
            System.out.println(board);
            MoveType type = MoveType.PLAY;
            Player player = board.getPlayers().get(board.getCurrentPlayerIndex());
            //Card card = player.getHand().getFirst();
            PlayerMove move = new PlayerMove(player,type,1);
            try{
                board.action(move);
            }catch (Exception e){
                e.printStackTrace();
                break;
            }
        }

    }
   // public Controller(HanabiView view) throws Exception {
        /*view.start(stage);
        String action = "";
        while(!action.equals("stop")){
            action = view.action();
            view.setLabel(action);
        }*/
   // }
}
