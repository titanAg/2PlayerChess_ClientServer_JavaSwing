package ChessServer;

import java.net.ServerSocket;

public class ChessServer {

    /*************************************************
     *  Driver code for this multi-threaded server
     ************************************************/
    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(8888);
        System.out.println("Chess Server is Running...");
        try {
            while (true) {
                // Create game and wait for clients to connect
                Game game = new Game();
                Game.PlayerThread player1 = game.new PlayerThread(listener.accept(), 1, true);
                System.out.println("Player 1 has connected");
                player1.start();

                Game.PlayerThread player2 = game.new PlayerThread(listener.accept(), 2, false);
                System.out.println("Player 2 has connected");
                player2.start();


                // Set opponents
                player1.setOpponent(player2);
                player2.setOpponent(player1);
            }
        } finally {
            listener.close();
        }
    }
}