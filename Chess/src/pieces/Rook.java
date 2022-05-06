package pieces;

import ChessGUI.ChessBoard;

public class Rook extends Piece {

    public Rook(int x, int y, boolean is_white, String file_path, ChessBoard chessBoard)
    {
        super(x,y,is_white,file_path, chessBoard);
    }
    
    @Override
    public boolean canMove(int targetX, int targetY)
    {
        // ensure movement is in a straight line
        if (targetX != getX() && targetY != getY())
            return false;

        // ensure path is clear
        if (targetX != getX())
            for (int i = Math.min(getX(),targetX)+1; i < Math.max(getX(),targetX); i++) {
                if (getChessBoard().getPiece(i, getY()) != null)
                    return false;
            }
        else
            for (int i = Math.min(getY(),targetY)+1; i < Math.max(getY(),targetY); i++) {
                if (getChessBoard().getPiece(getX(), i) != null)
                    return false;
            }

        // make sure target square is blank or an opponent piece
        Piece target = getChessBoard().getPiece(targetX,targetY);
        if (target != null && target.isWhite() == isWhite())
            return false;

        return true;
    }
}
