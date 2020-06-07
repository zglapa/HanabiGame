package hanabi.Controller;

import hanabi.Server.GameServer;

public class StartServer extends Thread {
    @Override
    public void run() {
        GameServer.main(new String[0]);
    }
}
