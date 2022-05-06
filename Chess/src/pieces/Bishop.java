package pieces;

import ChessGUI.ChessBoard;

public class Bishop extends Piece {

    public Bishop(int x, int y, boolean isWhite, String filePath, ChessBoard chessBoard)
    {
        super(x,y,isWhite,filePath, chessBoard);
    }
    
    @Override
    public boolean canMove(int targetX, int targetY)
    {
        // don't allow move in straight line
        if (targetX == getX() || targetY == getY())
            return false;

        boolean isInPath = false;
        // check for -y & -/+x moves if target is in that quadrant
        if (targetY < getY()) {
            int shift = targetX < getX() ? -1 : 1;
            int i = getX();
            // check if next space is target
            if (getX()+shift == targetX && getY()-1 == targetY) {
                isInPath = true;
            }
            // otherwise search diagonally
            else {
                for (int j = getY() - 1; j > targetY; j--) {
                    i += shift;
                    if (getChessBoard().getPiece(i, j) != null)
                        return false;
                    else if (targetX == i + shift && targetY == j - 1)
                        isInPath = true;
                }
            }
        }
        // check for +y & -/+x moves if target is in that quadrant
        else if (targetY > getY()) {
            int shift = targetX < getX() ? -1 : 1;
            int i = getX();
            // check if next space is target
            if (getX()+shift == targetX && getY()+1 == targetY) {
                isInPath = true;
            }
            // otherwise search diagonally
            else {
                for (int j = getY() + 1; j < targetY; j++) {
                    i += shift;
                    if (getChessBoard().getPiece(i, j) != null)
                        return false;
                    else if (targetX == i + shift && targetY == j + 1)
                        isInPath = true;
                }
            }
        }

        //make sure target is in diagonal path
        if (!isInPath)
            return false;

        // make sure target square is blank or an opponent piece
        Piece target = getChessBoard().getPiece(targetX,targetY);
        if (target != null && target.isWhite() == isWhite())
            return false;

        return true;
    }
}
