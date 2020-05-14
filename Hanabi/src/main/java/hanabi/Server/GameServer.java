package hanabi.Server;

import hanabi.Model.Board;
import hanabi.Model.Deck;
import hanabi.Model.MoveType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;



public class GameServer {
    private int NUMBEROFPLAYERS = 2;
    private ServerSocket serverSocket;
    private AtomicInteger numOfPlayers;
    private static int PORT = 9999;
    private static ArrayList<ServerSideConnection> players;
    private static Board board= null;
    public GameServer(){
        System.out.println("[server started]");
        numOfPlayers= new AtomicInteger(0);
        try{
            serverSocket = new ServerSocket(PORT);
        }catch(IOException ex){
            ex.printStackTrace();
        }
        players = new ArrayList<>();
    }
    public void acceptConnections(){
        try{
            System.out.println("[waiting for connections]");
            while(numOfPlayers.get() < NUMBEROFPLAYERS){
                Socket client = serverSocket.accept();
                //System.out.println("Hello Player " + numOfPlayers.incrementAndGet());
                ServerSideConnection ssc = new ServerSideConnection(client,players, numOfPlayers.incrementAndGet());
                players.add(ssc);
                System.out.println("[new thread created]");
                Thread t = new Thread(ssc);
                t.start();
                System.out.println("[ready for another connection]");
            }
            System.out.println("All players are in");
            String[] players = Board.randomNames(NUMBEROFPLAYERS);
            Board board = new Board(NUMBEROFPLAYERS,3,8,8,4,new Deck(true, true, true),false,players);
            sendToAll(board);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
    private class ServerSideConnection implements Runnable{

        private Socket socket;
        private ArrayList<ServerSideConnection> connections;
        private ObjectInputStream in;
        private ObjectOutputStream out;
        private int playerID;

        public ServerSideConnection(Socket s, ArrayList<ServerSideConnection> playrs, int playerID){
            socket = s;
            connections = playrs;
            this.playerID = playerID;
            System.out.println("xxx");
            try {
                out = new ObjectOutputStream(s.getOutputStream());
                out.flush();
                in = new ObjectInputStream(s.getInputStream());
                out.writeInt(playerID);
            }catch(IOException ex){
                ex.printStackTrace();
            }
            System.out.println("done");
        }
        @Override
        public void run() {
            try {
                sendToAll("Waiting for players");

                while (numOfPlayers.get() < NUMBEROFPLAYERS) {
                    Thread.onSpinWait();
                }
                while(true){
                    board = (Board) in.readObject();
                    sendToAll(board);
                    MoveType moveType = (MoveType) in.readObject();
                    sendToAll(moveType);
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }
    public void sendToAll(Object o) throws IOException {
        for(ServerSideConnection ssc : players){
            ssc.out.writeObject(o);
            ssc.out.flush();
        }
    }
    public static void main(String[] args){
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
}
