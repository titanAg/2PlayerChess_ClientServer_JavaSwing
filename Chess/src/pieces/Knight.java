package pieces;

import ChessGUI.ChessBoard;

public class Knight extends Piece {

    public Knight(int x, int y, boolean isWhite, String filePath, ChessBoard chessBoard)
    {
        super(x,y,isWhite,filePath, chessBoard);
    }
    
    @Override
    public boolean canMove(int targetX, int targetY)
    {
        // make sure target square is blank or an opponent piece
        Piece target = getChessBoard().getPiece(targetX,targetY);
        if (target != null && target.isWhite() == isWhite())
            return false;

        int yShift = getY() < targetY ? 2 : -2;
        int xShift = getX() < targetX ? 2 : -2;
        // check for y -/+ 2
        if (getY() + yShift == targetY) {
            xShift = getX() < targetX ? 1 : -1;
            if (getX() + xShift == targetX) {
                return true;
            }
        }
        // check for x -/+ 2
        else if (getX() + xShift == targetX) {
            yShift = getY() < targetY ? 1 : -1;
            if (getY() + yShift == targetY) {
                return true;
            }
        }
        return false;
    }
}
