package ChessClient;
import ChessGUI.ChessGUI;
import ChessServer.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ChessClient implements ActionListener {
    private Socket socket;
    private static final int PORT = 8888;
    private BufferedReader in;
    private PrintWriter out;

    MenuGUI menuGUI = null;
    ChessGUI chessGUI = null;
    Player player = null;

    int playerId;
    String playerName;

    /*************************************************
     *  Driver code for the client
     ************************************************/
    public static void main(String[] args) {
        try {
            ChessClient chessClient = new ChessClient();
            chessClient.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*************************************************
     *  Constructor for the client
     *    - Setup connection to server
     ************************************************/
    public ChessClient() throws Exception{
        socket = new Socket("127.0.0.1", PORT);
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    /*************************************************
     * This run method handles the main client side communication with the server
     *   - Messages are received and sent via BufferedReader and PrintWriter
     ************************************************/
    private void run() throws IOException {
        String response;
        try {
            response = in.readLine();
            if (response.startsWith("WELCOME")) {
//                System.out.println("Response: " + response);
                playerId = Integer.parseInt(response.split(" ")[2]);
                menuGUI = new MenuGUI(this);
                menuGUI.enablePlay();
            }
            while (true) {
                System.out.println("Awaiting response...");
                response = in.readLine();
                System.out.println("Response: " + response);
                if (response.startsWith("START")){
                    String[] attributes = response.split("\\|");
                    player = new Player(response);
                    //player.setName(attributes[2]);
                    menuGUI.close();
                    System.out.println("Player: " + player);
                    chessGUI = new ChessGUI(player, out);
                }
                else if (response.startsWith("UPDATE_STATUS")) {
                    chessGUI.updateBarStatus();
                }
                else if (response.startsWith("VALID_MOVE")) {
                } else if (response.startsWith("OPPONENT_MOVED")) {
                    String[] cmd = response.split(" ");
                    int row1 = Math.abs(Integer.parseInt(cmd[1]) - 7);
                    int col1 = Math.abs(Integer.parseInt(cmd[2]) - 7);
                    int row2 = Math.abs(Integer.parseInt(cmd[3]) - 7);
                    int col2 = Math.abs(Integer.parseInt(cmd[4]) - 7);
                    System.out.println("Mapped to: " + row1 + " " + col1 + " " + row2 + " " + col2);
                    chessGUI.updateOpponentMove(Integer.parseInt(cmd[1]),Integer.parseInt(cmd[2]) , Integer.parseInt(cmd[3]), Integer.parseInt(cmd[4]));
                    chessGUI.updateBarMove(Integer.parseInt(cmd[1]) + " " + Integer.parseInt(cmd[2]) + " to " + Integer.parseInt(cmd[3]) + " " + Integer.parseInt(cmd[4]));
                    out.println("UPDATE_STATUS ");
                } else if (response.startsWith("CHECK")) {
                    chessGUI.updateCheckDialog(response.split("\\|")[1]);

                }
                else if (response.startsWith("GAME_OVER")) {
                    chessGUI.updateGameOverDialog(response);
                    System.exit(0);
                    break;
                } else if (response.startsWith("MESSAGE")) {
//                } else if (response.startsWith("RESTART")) {
                }
                else if (response.startsWith("QUIT")) {
                    System.exit(0);
                }
            }
            out.println("QUIT");
        }
        catch (SocketException e) {
            System.exit(0);
        } finally {
            out.println("QUIT");
            socket.close();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "play":
                System.out.println("Clicked play");
                playerName = menuGUI.getPlayerName();
                int chosenNumber = menuGUI.getChosenNumber();
                String chosenColor = menuGUI.getChosenColor();
                out.println("NAME |" + 1 + "|" + playerName + "|" + chosenNumber + "|" + chosenColor);
                menuGUI.setStatus("Waiting for another player");
                break;
        }
    }
}
