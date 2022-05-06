package ChessGUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.PrintWriter;

import ChessServer.Player;

public class ChessGUI {
    public GameFrame gameFrame;

    public ChessGUI(Player player, PrintWriter out) {
        gameFrame = new GameFrame(player, out);
        gameFrame.setVisible(true);
    }

    public void updateOpponentMove(int r1, int c1, int r2, int c2) {
        gameFrame.updateOpponentMove(r1,c1,r2,c2);
    }

    public void updateBarStatus() { gameFrame.gameBarComponent.updateBarStatus();}

    public void updateBarMove(String move) { gameFrame.gameBarComponent.updateBarMove(move); }

    public void updateCheckDialog(String msg) { gameFrame.gameBarComponent.showCheckDialog(msg); }

    public void updateGameOverDialog(String msg) {
//        gameFrame.gameBarComponent.showGameOverDialog(msg);
        gameFrame.gameBarComponent.showCheckDialog(msg);
    }

}

class GameFrame extends JFrame {
    ChessBoard chessBoardComponent;
    GameBarPanel gameBarComponent;
    public GameFrame(Player player, PrintWriter out)
    {
        JPanel panel = new JPanel();
        panel.setBackground(Color.gray);

        // set up and add border
        Border raisedBevel = BorderFactory.createRaisedBevelBorder();
        Border loweredBevel = BorderFactory.createLoweredBevelBorder();
        panel.setBorder(BorderFactory.createCompoundBorder(
                raisedBevel, loweredBevel));
        panel.setLayout(new BorderLayout());

        // Create Board component and add to game panel
        chessBoardComponent = new ChessBoard(player, out);
        panel.add(chessBoardComponent, BorderLayout.CENTER);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Create GameBar component and add to game panel
        gameBarComponent = new GameBarPanel(player, out);
        panel.add(gameBarComponent,BorderLayout.EAST);

        // Assemble GameFrame
        this.add(panel);

        // Set title and additional GameFrame properties
        this.setTitle("Chess - Player " + player.getId() + ": " + player.getName());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setResizable(false);
        this.setLocation(200, 250);

        this.pack();
        this.setVisible(true);
    }

    public void updateOpponentMove(int r1, int c1, int r2, int c2) {
        chessBoardComponent.updateOpponentMove(r1,c1,r2,c2);
    }
}
