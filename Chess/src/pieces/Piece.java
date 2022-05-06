package pieces;

import ChessGUI.ChessBoard;

/*************************************************
 *  Abstract piece class
 *    - provides base template for each concrete chess piece
 ************************************************/

public abstract class Piece {
    private int x;
    private int y;
    final private boolean isWhite;
    private String filePath;
    public ChessBoard chessBoard;
    
    public Piece(int x, int y, boolean isWhite, String filePath, ChessBoard chessBoard)
    {
        this.isWhite = isWhite;
        this.x = x;
        this.y = y;
        this.filePath = filePath;
        this.chessBoard = chessBoard;
    }
    
    public String getFilePath()
    {
        return filePath;
    }
    
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }
    
    public boolean isWhite()
    {
        return isWhite;
    }
    
    public boolean isBlack()
    {
        return !isWhite;
    }
    
    public void setX(int x)
    {
        this.x = x;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }

    public ChessBoard getChessBoard() { return chessBoard; }
    
    public boolean canMove(int targetX, int targetY)
    {
        return false;
    }
}
