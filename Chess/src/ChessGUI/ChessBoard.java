package ChessGUI;

import ChessServer.Player;
import pieces.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.border.Border;


/*************************************************
 *  ChessBoard class handles most of the board GUI logic
 *    - Maintains lists of white and black pieces and redraws board for each state
 *    - I loosely followed a couple examples for the GUI drawing methods in this class
 *          - https://stackoverflow.com/questions/21142686/making-a-robust-resizable-swing-chess-gui
 ************************************************/

public class ChessBoard extends JComponent {
    private Player player;
    private PrintWriter out;
    public int turnCount = 0;
    private static Image NULL_IMAGE = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);

    private final int squareWidth = 65;
    public ArrayList<Piece> whitePieces;
    public ArrayList<Piece> blackPieces;
    King wKing;
    King bKing;
    

    public ArrayList<DrawingImage> staticShapes;
    public ArrayList<DrawingImage> pieceGraphics;

    public Piece currentPiece;

    private String boardFilePath;
    private String activeSquareFilePath;

    public ChessBoard(Player player, PrintWriter out) {
        Border raisedBevel = BorderFactory.createRaisedBevelBorder();
        Border loweredBevel = BorderFactory.createLoweredBevelBorder();
        this.setBorder(BorderFactory.createCompoundBorder(
                raisedBevel, loweredBevel));
        this.setLayout(new BorderLayout());

        this.out = out;
        this.player = player;

        boardFilePath = "images" + File.separator + "board_" + player.getChosenColor() + ".png";
        activeSquareFilePath = "images" + File.separator + "active_square_" + player.getChosenColor() + ".png";

        staticShapes = new ArrayList();
        pieceGraphics = new ArrayList();
        whitePieces = new ArrayList();
        blackPieces = new ArrayList();


        initPieces();

        this.setPreferredSize(new Dimension(520, 520));
        this.setMinimumSize(new Dimension(100, 100));
        this.setMaximumSize(new Dimension(1000, 1000));

        this.addMouseListener(mouseAdapter);

        this.setVisible(true);
        this.requestFocus();
        drawBoard();
    }

    public void initPieces()
    {
        whitePieces.add(new King(4,0,true,"King.png",this));
        wKing = (King)whitePieces.get(0);
        whitePieces.add(new Queen(3,0,true,"Queen.png",this));
        whitePieces.add(new Bishop(2,0,true,"Bishop.png",this));
        whitePieces.add(new Bishop(5,0,true,"Bishop.png",this));
        whitePieces.add(new Knight(1,0,true,"Knight.png",this));
        whitePieces.add(new Knight(6,0,true,"Knight.png",this));
        whitePieces.add(new Rook(0,0,true,"Rook.png",this));
        whitePieces.add(new Rook(7,0,true,"Rook.png",this));
        for (int i = 0; i < 8; i++)
            whitePieces.add(new Pawn(i,1,true,"Pawn.png",this));

        blackPieces.add(new King(4,7,false,"King.png",this));
        bKing = (King)blackPieces.get(0);
        blackPieces.add(new Queen(3,7,false,"Queen.png",this));
        blackPieces.add(new Bishop(2,7,false,"Bishop.png",this));
        blackPieces.add(new Bishop(5,7,false,"Bishop.png",this));
        blackPieces.add(new Knight(1,7,false,"Knight.png",this));
        blackPieces.add(new Knight(6,7,false,"Knight.png",this));
        blackPieces.add(new Rook(0,7,false,"Rook.png",this));
        blackPieces.add(new Rook(7,7,false,"Rook.png",this));
        for (int i = 0; i < 8; i++)
            blackPieces.add(new Pawn(i,6,false,"Pawn.png",this));

        wKing.setOpponentPieces(blackPieces);
        bKing.setOpponentPieces(whitePieces);
    }


    private void drawBoard()
    {
        pieceGraphics.clear();
        staticShapes.clear();

        Image board = loadImage(boardFilePath);
        staticShapes.add(new DrawingImage(board, new Rectangle2D.Double(0, 0, board.getWidth(null), board.getHeight(null))));
        if (currentPiece != null)
        {
            Image active_square = loadImage(activeSquareFilePath);
            staticShapes.add(new DrawingImage(active_square, new Rectangle2D.Double(squareWidth * currentPiece.getX(), squareWidth * currentPiece.getY(), active_square.getWidth(null), active_square.getHeight(null))));
        }
        for (int i = 0; i < whitePieces.size(); i++)
        {
            int col = whitePieces.get(i).getX();
            int row = whitePieces.get(i).getY();
            Image piece = loadImage("images" + File.separator + "white_pieces" + File.separator + whitePieces.get(i).getFilePath());
            pieceGraphics.add(new DrawingImage(piece, new Rectangle2D.Double(squareWidth *col, squareWidth * row, piece.getWidth(null), piece.getHeight(null))));
        }
        for (int i = 0; i < blackPieces.size(); i++)
        {
            int col = blackPieces.get(i).getX();
            int row = blackPieces.get(i).getY();
            Image piece = loadImage("images" + File.separator + "black_pieces" + File.separator + blackPieces.get(i).getFilePath());
            pieceGraphics.add(new DrawingImage(piece, new Rectangle2D.Double(squareWidth *col, squareWidth *row, piece.getWidth(null), piece.getHeight(null))));
        }
        this.repaint();
    }

    
    public Piece getPiece(int x, int y) {
        for (Piece p : whitePieces)
            if (p.getX() == x && p.getY() == y)
                return p;
        for (Piece p : blackPieces)
            if (p.getX() == x && p.getY() == y)
                return p;
        return null;
    }

    public void updateOpponentMove(int r1, int c1, int r2, int c2) {
        Piece currentPiece = getPiece(c1, r1);
        Piece destinationPiece = getPiece(c2, r2);
        if (destinationPiece != null)
        {
            if (destinationPiece.isWhite())
                whitePieces.remove(destinationPiece);
            else
                blackPieces.remove(destinationPiece);
        }
        currentPiece.setY(r2);
        currentPiece.setX(c2);
        turnCount++;
        drawBoard();
    }

    private MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            int dx = e.getX();
            int dy = e.getY();
            int clickedRow = dy / squareWidth;
            int clickedCol = dx / squareWidth;
            boolean isWhitesTurn = true;
            if (turnCount % 2 == 1)
                isWhitesTurn = false;

            Piece clickedPiece = getPiece(clickedCol, clickedRow);
            
            if (currentPiece == null && clickedPiece != null &&
                    ((isWhitesTurn && clickedPiece.isWhite() && player.isFirst()) || (!isWhitesTurn && clickedPiece.isBlack() && !player.isFirst())))
            {
                currentPiece = clickedPiece;
            }
            else if (currentPiece != null && currentPiece.getX() == clickedCol && currentPiece.getY() == clickedRow)
            {
                currentPiece = null;
            }
            else if (currentPiece != null && currentPiece.canMove(clickedCol, clickedRow)
                    && ((isWhitesTurn && currentPiece.isWhite()) || (!isWhitesTurn && currentPiece.isBlack())))
            {
                // if piece is there, remove it
                if (clickedPiece != null)
                {
                    if (clickedPiece.getFilePath().startsWith("King")) {
                        out.println("GAME_OVER " + (clickedPiece.isWhite() ? " Black wins!" : " White wins!"));
                    }
                    if (clickedPiece.isWhite())
                        whitePieces.remove(clickedPiece);
                    else
                        blackPieces.remove(clickedPiece);
                }
                out.println("MOVE " + currentPiece.getY() + " " + currentPiece.getX() + " " + clickedRow + " " + clickedCol);
                // do move
                currentPiece.setX(clickedCol);
                currentPiece.setY(clickedRow);

                // check opponent if active piece is in range
                Piece oppKing = currentPiece.isWhite() ? bKing : wKing;
                String msg = currentPiece.isWhite() ? "White calls check!" : "Black calls check!";
                if (currentPiece.canMove(oppKing.getX(),oppKing.getY())) {
                    out.println("CHECK|" + msg);
                }

                // if piece is a pawn set has_moved to true
                if (currentPiece.getClass().equals(Pawn.class))
                {
                    Pawn pawn = (Pawn)(currentPiece);
                    pawn.setHasMoved(true);
                }
                
                currentPiece = null;
                turnCount++;
            }
            drawBoard();
        }
    };
      
    private Image loadImage(String imageFile) {
        try {
                return ImageIO.read(new File(imageFile));
        }
        catch (IOException e) {
                return NULL_IMAGE;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        drawBackground(g2);
        drawShapes(g2);
    }

    private void drawBackground(Graphics2D g2) {
        g2.setColor(getBackground());
        g2.fillRect(0,  0, getWidth(), getHeight());
    }
       

    private void drawShapes(Graphics2D g2) {
        for (DrawingImage shape : staticShapes) {
            shape.draw(g2);
        }	
        for (DrawingImage shape : pieceGraphics) {
            shape.draw(g2);
        }
    }

}


