package ChessGUI;
import java.awt.*;
import java.awt.geom.Rectangle2D;

class DrawingImage {

    public Image image;
    public Rectangle2D rectangle;

    public DrawingImage(Image image, Rectangle2D rectangle) {
            this.image = image;
            this.rectangle = rectangle;
    }

//    public boolean contains(Graphics2D g2, double x, double y) {
//            return rectangle.contains(x, y);
//    }
//
//    public void adjustPosition(double dx, double dy) {
//            rectangle.setRect(rectangle.getX() + dx, rectangle.getY() + dy, rectangle.getWidth(), rectangle.getHeight());
//    }

    public void draw(Graphics2D g2) {
            Rectangle2D bounds = rectangle.getBounds2D();
            g2.drawImage(image, (int)bounds.getMinX(), (int)bounds.getMinY(), (int)bounds.getMaxX(), (int)bounds.getMaxY(),
                                            0, 0, image.getWidth(null), image.getHeight(null), null);
    }
}
