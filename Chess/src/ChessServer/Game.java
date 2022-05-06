package ChessServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Game {

    class PlayerThread extends Thread {
        Player player;
        PlayerThread opponent;
        Socket socket;
        BufferedReader input;
        PrintWriter output;

        String playerName = null;

        /*************************************************
         *  Player Thread constructor
         *    - Sets up initial communication with the client instance
         ************************************************/
        public PlayerThread(Socket socket, int playerID, boolean isFirst) {
            this.socket = socket;
            player = new Player(playerID, isFirst);
            try {
                input = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                System.out.println("Player died: " + e);
            }
        }

        /*************************************************
         *  Update opponent for this player
         ************************************************/
        public void setOpponent(PlayerThread opponent) {
            this.opponent = opponent;
        }

        /*************************************************
         * This run method handles the main server side game logic
         *   - Messages are received and sent via BufferedReader and PrintWriter to each player
         ************************************************/
        public void run() {
            try {
                output.println("WELCOME Player " + player.getId());
                String response = input.readLine();
                if (response.startsWith("NAME")) {
                    System.out.println("Response: " + response);

                    String[] res = response.split("\\|");
                    playerName = res.length < 3 ? "Anonymous" : res[2];
                    player.setName(playerName);
                    player.setChosenNumber(res[3]);
                    player.setChosenColor(res[4]);
                    System.out.println("Player " + player.getId() + " name received: " + player.getName());

                }

                // Setup and start game once both players are connected
                if (opponent != null && opponent.playerName != null) {
                    System.out.println("All players are ready, starting game");

                    // choose first player
                    int luckyNumber = (int)(Math.random() * 10) + 1;
                    int n1 = player.getChosenNumber();
                    int n2 = opponent.player.getChosenNumber();
                    System.out.println("luckyNumber " + luckyNumber + " n1 " + n1 + " n2 " + n2);
                    boolean isFirst = Math.abs(n1-luckyNumber) < Math.abs(n2-luckyNumber);
                    player.setFirst(isFirst);
//                    output.println("FIRST " + luckyNumber + " " + player.getChosenNumber());
                    opponent.player.setFirst(!isFirst);
//                    opponent.output.println("LAST " + luckyNumber + " " + player.getChosenNumber());

                    // start game sending player
                    output.println("START" + player);
                    opponent.output.println("START" + opponent.player);
                }

                // Get commands from client and process
                while (true) {
                    System.out.println("Awaiting response from " + playerName + "...");
                    response = input.readLine();
                    System.out.println("Response: " + response);
                    if (response.startsWith("MOVE")) {
                        String[] coord = response.split(" ");
                        System.out.println("Player " + player.getName() + " made move " + coord[1] + " " + coord[2] + " to " + coord[3] + " " + coord[4]);
                        opponent.output.println("OPPONENT_MOVED " + coord[1] + " " + coord[2] + " " + coord[3] + " " + coord[4]);

                    }
                    else if (response.startsWith("UPDATE_STATUS")) {
                        output.println("UPDATE_STATUS");
                        opponent.output.println("UPDATE_STATUS");
                    }
                    else if (response.startsWith("CHECK")) {
                        output.println(response);
                        opponent.output.println(response);
                    }
                    else if (response.startsWith("GAME_OVER")) {
                        output.println(response);
                        opponent.output.println(response);
                    }
                    else if (response.startsWith("QUIT")) {
                        return;
                    }
//                    else if (response.startsWith("RESTART")) {
//                        System.out.println("Response: " + response);
//                        output.println(response);
//                    }
                    else if (response.startsWith("MESSAGE")) {
                    }
                }
            } catch (IOException e) {
                System.out.println("Player disconnected: " + e);
                if (opponent != null)
                    opponent.output.println("QUIT");
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
