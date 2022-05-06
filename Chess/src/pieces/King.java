package pieces;

import ChessGUI.ChessBoard;

import java.util.ArrayList;

public class King extends Piece {

    private boolean hasMoved;
    private ArrayList<Piece> opponentPieces;

    public King(int x, int y, boolean isWhite, String filePath, ChessBoard chessBoard) {
        super(x, y, isWhite, filePath, chessBoard);
        hasMoved = false;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public void setOpponentPieces(ArrayList<Piece> opponentPieces) { this.opponentPieces = opponentPieces; }

    public boolean canMove(int targetX, int targetY) {
        // make sure target square is blank or an opponent piece
        Piece target = getChessBoard().getPiece(targetX, targetY);
        if (target != null && target.isWhite() == isWhite())
            return false;

        // Don't let king move into danger
//        for (Piece p : opponentPieces) {
//            if (p.canMove(targetX, targetY))
//                return false;
//        }

        int yShift = getY() < targetY ? 1 : -1;
        int xShift = getX() < targetX ? 1 : -1;

        // check for y -+ 1
        if (getY() + yShift == targetY) {
            if (getX() == targetX || getX() + xShift == targetX) {
                hasMoved = true;
                return true;
            }
        }
        // check for x -+ 1
        else if (getX() + xShift == targetX) {
            if (getY() == targetY || getY() + yShift == targetY) {
                hasMoved = true;
                return true;
            }
        }

        return false;
    }

//    public boolean isCheckMate() {
//        if (canMove(getX(),getY()))
//        return false;
//    }
}
