package ChessGUI;

import ChessServer.Player;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.PrintWriter;

/*************************************************
 *  Custom JPanel class for game side bar
 *    - Shows game state updates
 ************************************************/

public class GameBarPanel extends JPanel {
    Player player;
    PrintWriter out;
    JLabel statusLabel;
    JLabel welcomeLabel;
    JLabel nameLabel;
    JLabel colorLabel;
    JLabel moveLabel;
    JLabel checkedLabel;

    boolean isYourTurn;

    final String TEXT_ALIGN_PAD = "text-align: center; padding: 10;";
    final String FONT_FAMILY = "font-family: Comic Sans MS;";
    final String FONT_HEADING_SIZE = "font-size: 15px;";
    final String FONT_LABEL_SIZE = "text-align: center;";
    final String COLOR_GREEN = "color:#7FFF00;";
    final String COLOR_RED = "color:#B30000;";

    public GameBarPanel(Player player, PrintWriter out) {
        this.player = player;
        this.out = out;
        isYourTurn = player.isFirst();

        this.setLayout(new BoxLayout (this, BoxLayout.Y_AXIS));

        welcomeLabel = makeLabel("Welcome Player " + player.getId() + "!");
        colorLabel = makeLabel("Color: " + (player.isFirst() ? "White" : "Black"));
        nameLabel = makeLabel("Name: " + player.getName());
        statusLabel = new JLabel("", SwingConstants.CENTER);
        moveLabel = makeLabel("Opponent's Last Move:<br> No moves yet!");
        checkedLabel = new JLabel("", SwingConstants.CENTER);


                updateBarStatus();
        this.add(welcomeLabel);
        this.add(colorLabel);
        this.add(nameLabel);
        this.add(statusLabel);
        this.add(moveLabel);
        this.add(checkedLabel);

        Border raisedBevel = BorderFactory.createRaisedBevelBorder();
        Border loweredBevel = BorderFactory.createLoweredBevelBorder();
        this.setBorder(BorderFactory.createCompoundBorder(
                raisedBevel, loweredBevel));

        this.setBackground(Color.gray);
    }

    private JLabel makeLabel(String text) {
        return new JLabel(makeDivString(text),SwingConstants.CENTER);
    }

    private String makeDivString(String text) {
        return "<html><div style='" + TEXT_ALIGN_PAD + FONT_HEADING_SIZE + FONT_FAMILY +  "'>" + text + "</div></html>";
    }

    public void updateBarStatus() {
        String text = isYourTurn ? "<p style='"+ COLOR_GREEN + "'>Your Move" : "<p style='"+ COLOR_RED + "'>Opponent's Move";
        text += "</p>";
        statusLabel.setText(makeDivString("Status" + text));
        isYourTurn = !isYourTurn;
    }

    public void showCheckDialog(String message) {
        //checkedLabel.setText(makeDivString(msg));
        JOptionPane.showMessageDialog(this, message);
    }

    public void showGameOverDialog(String message) {
        int result = JOptionPane.showConfirmDialog(this,message, "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if(result == JOptionPane.YES_OPTION){
            out.println("RESTART");
        }else if (result == JOptionPane.NO_OPTION){
            out.println("QUIT");
        }
//        else {
//            label.setText("None selected");
//        }
    }


    public void updateBarMove(String move) {
        moveLabel.setText(makeDivString("Opponent's Last Move:<br>" + move));
    }
}
