import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class Window {

    public static void main(String[] args) {
        Screen screen = new Screen();
    }

    public static class Screen extends JFrame {
        public Screen() {
            int height = 620;
            int width = 950;
            JFrame window = new JFrame();

            Canvas canvas = new Canvas(width, height);
            Hexagon[][] board = canvas.getBoard().getBoard();


            window.setSize(width, height);
            window.add(canvas);
            window.setTitle("Window");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setVisible(true);

            window.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    Point2D click = new Point2D.Double(e.getX(), e.getY());
                    for (int i = 0; i < board.length; i++) {
                        for (int j = 0; j < board[0].length; j++) {
                            Hexagon tile = board[i][j];
                            if (tile.collision(click) && !tile.getIsPressed()){
                                tile.setIsPressed();
                                tile.setColor(new Color(51, 4, 51));
                                canvas.repaint();
                            }
                        }
                    }
                }
            });
        }
    }
}
