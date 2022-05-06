package pieces;

import ChessGUI.ChessBoard;

public class Pawn extends Piece {

    private boolean hasMoved;
    
    public Pawn(int x, int y, boolean isWhite, String filePath, ChessBoard chessBoard)
    {
        super(x,y,isWhite,filePath, chessBoard);
        hasMoved = false;
    }
    
    public void setHasMoved(boolean hasMoved)
    {
        this.hasMoved = hasMoved;
    }
    
    public boolean getHasMoved()
    {
        return hasMoved;
    }
    
    @Override
    public boolean canMove(int targetX, int targetY)
    {
        // TODO revise and add pawn promotion
        if (isWhite()) {
            int maxYMove = hasMoved ? getY() + 1 : getY() + 2;
            Piece target = getChessBoard().getPiece(targetX, targetY);

            // don't allow piece to move backwards
            if (targetY < getY())
                return false;

            // Allow piece to move forward legally
            if (targetX == getX() && targetY <= maxYMove && target == null)
                return true;
            else if (targetY == getY() + 1 && (targetX == getX() + 1 || targetX == getX() - 1)) {
                // Allow attack only if there is a piece of a different color
                if (target != null && (isWhite() && target.isBlack() || isBlack() && target.isWhite()))
                    return true;
            }
        }
        else {
            int maxYMove = hasMoved ? getY()-1 : getY()-2;
            Piece target = getChessBoard().getPiece(targetX,targetY);

            // don't allow piece to move backwards
            if (targetY > getY())
                return false;

            // Allow piece to move forward legally
            if (targetX == getX() && targetY >= maxYMove && target == null)
                return true;
            else if (targetY == getY()-1 && (targetX == getX()+1 || targetX == getX()-1)) {
                // Allow attack only if there is a piece of a different color
                if (target != null && (isWhite() && target.isBlack() || isBlack() && target.isWhite()))
                    return true;
            }
        }

        // don't allow any other illegal moves!
        return false;
    }
}
