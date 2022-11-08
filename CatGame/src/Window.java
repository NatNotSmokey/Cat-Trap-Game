import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class Window {

    public static void main(String[] args) {
        Screen screen = new Screen();
    }

    /**
     * Handles rendering the window
     */
    public static class Screen extends JFrame {
        public Screen() {
            int height = 620;
            int width = 950;
            JPanel p = new JPanel();
            JFrame window = new JFrame();

            Canvas canvas = new Canvas(width, height);
            Hexagon[][] board = canvas.getBoard().getBoard();

            p.add(canvas);
            window.add(p);
            window.setTitle("Window");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setSize(width, height);
            window.setVisible(true);

            p.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    Point2D click = new Point2D.Double(e.getX(), e.getY());

                    int count = 0;
                    Hexagon save = null;
                    //Loops through all tiles and checks if any are clicked
                    for (int i = 0; i < board.length; i++) {
                        for (int j = 0; j < board[0].length; j++) {
                            Hexagon tile = board[i][j];
                            if (tile.collision(click) && !tile.getIsPressed()){

                                if (i == canvas.getCat().getX() && j == canvas.getCat().getY()){
                                    break;
                                } else {
                                    count++;
                                    save = tile;
                                    System.out.println("Tile " + i + ", " + j);
                                }
                            }
                        }
                    }
                    //Checks if multiple tiles have been clicked
                    if (count == 1){
                        save.setIsPressed();
                        boolean moved = canvas.getCat().move();
                        canvas.repaint();
                    }

                }
            });
        }
    }
}
