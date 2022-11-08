import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class Canvas extends JComponent{
    private int width;
    private int height;
    private GameBoard board;
    private Cat cat;

    /**
     * Basic Constructor
     * @param w width
     * @param h height
     */
    public Canvas (int w, int h){
        width = w;
        height = h;
        board = new GameBoard(15, 7, new Hexagon(100, 100, 35, new Color(103, 21, 227)),35,15, 4);
        cat = new Cat(7, 3, board);

        Dimension d = new Dimension();
        d.setSize(w, h);
        setPreferredSize(d);
    }

    public GameBoard getBoard(){
        return board;
    }

    public Cat getCat() {
        return cat;
    }

    /**
     * Handles rendering graphics. Never needs to be called, run on startup
     * @param g graphics component
     */
    @Override
    protected void paintComponent (Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);

        board.drawBoard(g2d);
        cat.drawCat(g2d);
    }
}
