package ChessClient;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class MenuGUI implements ActionListener {
    private JPanel gui;
    private MenuFrame menuFrame;
    private JButton playButton;
    private JTextField playerName;
    private JLabel status;
    private JLabel chooseLabel;
    private JLabel chooseLabel2;
    private JComboBox chosenColor;
    private JComboBox chosenNumber;
    private boolean isReady;

    final String TEXT_ALIGN_PAD = "text-align: center; padding: 10;";
    final String FONT_FAMILY = "font-family: Comic Sans MS;";
    final String FONT_HEADING_SIZE = "font-size: 15px;";
    final String FONT_LABEL_SIZE = "text-align: center;";
    final String COLOR_GREEN = "color:#7FFF00;";
    final String COLOR_RED = "color:#B30000;";

    public MenuGUI(ChessClient chessClient) {
        menuFrame = new MenuFrame(gui);
        initInputs(chessClient);
        Border raisedBevel = BorderFactory.createRaisedBevelBorder();
        Border loweredBevel = BorderFactory.createLoweredBevelBorder();
        gui.setBorder(BorderFactory.createCompoundBorder(
                raisedBevel, loweredBevel));

        gui.setBackground(Color.gray);
        setStatus("Please fill in the fields above");
    }

    private void initInputs(ChessClient chessClient) {
        playButton.addActionListener(chessClient);
        playButton.setActionCommand("play");
        chosenColor.addItem("Blue");
        chosenColor.addItem("Red");
        chosenColor.addItem("Green");
        for (int i = 1; i <= 10; i++)
            chosenNumber.addItem(i);
    }

    public boolean isReady() { return isReady; }

    public void enablePlay() { playButton.setEnabled(true); }

    public void showErrorMessage(String msg) {
        showMessageDialog(gui, msg, "Error", ERROR_MESSAGE);
    }

    public void close() {
        menuFrame.dispose();
    }

    public String getPlayerName() { return playerName.getText().equals("") ? "Nobody" : playerName.getText(); }

    public int getChosenNumber() {
        return (int)chosenNumber.getSelectedItem();
    }

    public String getChosenColor() {
        return chosenColor.getSelectedItem().toString();
    }

    public void setStatus(String msg) {
        status.setText(msg);
        //status.setForeground(Color.red);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

    }
}

class MenuFrame extends JFrame {
    public MenuFrame(JPanel gui) {
        this.add(gui);

        this.setTitle("Chess Clash");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setResizable(false);
        this.setLocationByPlatform(true);
        this.setLocation(200, 250);

        this.pack();
        this.setVisible(true);
    }
}
