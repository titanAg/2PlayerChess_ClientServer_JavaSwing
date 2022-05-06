package ChessServer;

/*************************************************
 *  Player object
 *   - holds various attributes about player instance
 ************************************************/

public class Player {
    private int id;
    private String name;
    private boolean isFirst;
    private int chosenNumber;
    private String chosenColor;
    private boolean wantsRestart = false;
    public Player(int id, boolean isFirst) {
        this.id = id;
        this.isFirst = isFirst;
    }

    public Player(String response) {
        String[] attributes = response.split("\\|");
        id = Integer.parseInt(attributes[1]);
        name = attributes[2];
        isFirst = Boolean.valueOf(attributes[3]);
        chosenNumber = Integer.parseInt(attributes[4]);
        chosenColor = attributes[5];
    }

    public String getName() { return name; }
    public int getId() { return id; }
    public int getChosenNumber() { return chosenNumber; }
    public String getChosenColor() { return chosenColor.toLowerCase(); }

    public void setName(String name) { this.name = name; }
    public void setChosenNumber(String number) { chosenNumber = Integer.parseInt(number); }
    public void setChosenColor(String color) { chosenColor = color; }

    public void setFirst(boolean isFirst) { this.isFirst = isFirst; }
    public boolean isFirst() { return isFirst; }

    @Override
    public String toString() {
        return "|" + id + "|" + name + "|" + isFirst + "|" + chosenNumber + "|" + chosenColor;
    }
}
