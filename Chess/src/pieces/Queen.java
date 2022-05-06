package pieces;

import ChessGUI.ChessBoard;

public class Queen extends Piece {

    public Queen(int x, int y, boolean isWhite, String filePath, ChessBoard chessBoard)
    {
        super(x,y,isWhite,filePath, chessBoard);
    }
    
    @Override
    public boolean canMove(int targetX, int targetY)
    {
        // check straight line moves
        if (targetX == getX() || targetY == getY()) {
            // ensure path is clear
            // check horizontal
            if (targetX != getX()) {
                for (int i = Math.min(getX(), targetX) + 1; i < Math.max(getX(), targetX); i++) {
                    if (getChessBoard().getPiece(i, getY()) != null)
                        return false;
                }
            }
            // check vertical
            else {
                for (int i = Math.min(getY(), targetY) + 1; i < Math.max(getY(), targetY); i++) {
                    if (getChessBoard().getPiece(getX(), i) != null)
                        return false;
                }
            }
        }
        // check diagonal moves
        else {
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
                if (getX() + shift == targetX && getY() + 1 == targetY) {
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
        }

        // make sure target square is blank or an opponent piece
        Piece target = getChessBoard().getPiece(targetX,targetY);
        if (target != null && target.isWhite() == isWhite())
            return false;

        return true;
    }
}
