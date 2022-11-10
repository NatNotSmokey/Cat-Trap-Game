import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Cat {

    private int x;
    private int y;
    final private GameBoard board;
    final int[] xMoves = {-2, -1, 1, 2, 1, -1};
    final int[] evenYMoves = {0, 0, 0, 0, -1, -1};
    final int[] oddYMoves = {0, 1, 1, 0, 0, 0};
    private ScoreManager manager;

    public Cat(int startX, int startY, GameBoard board, ScoreManager manager) {
        x = startX;
        y = startY;
        this.board = board;
        this.manager = manager;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Handles drawing the cat
     *
     * @param g2d graphics object for rendering
     */
    public void drawCat(Graphics2D g2d) {
        Point2D point = board.getBoard()[x][y].getCenter();

        g2d.setColor(Color.BLACK);
        g2d.fillOval((int) point.getX() - 15, (int) point.getY() - 11, 31, 31);

        Path2D ears = new Path2D.Double();
        ears.moveTo(point.getX() - 14, point.getY());
        ears.lineTo(point.getX() - 19, point.getY() - 18);
        ears.lineTo(point.getX(), point.getY() - 10);
        ears.lineTo(point.getX() + 19, point.getY() - 18);
        ears.lineTo(point.getX() + 14, point.getY());
        ears.closePath();
        g2d.fill(ears);
    }

    /**
     * Handles moving the cat. Uses moveCalculator to decide which move is best. It fails to move if the tile is pressed
     * If all 6 moves fail, the cat is considered stuck.
     *
     * @return true if moved successfully, false if no move is possible
     */
    public boolean move() {

        if (this.x == 0 || this.x == 1 || this.x == 13 || this.x == 14 || (this.y == 0 && this.x % 2 == 0) || (this.y == 6 && this.x % 2 != 0)) {
            return false;
        }

        //Decides what set of moves to use based on if x is even or odd
        int[] yMoves;
        if (this.x % 2 == 0) {
            yMoves = evenYMoves;
        } else {
            yMoves = oddYMoves;
        }

        //Grabs valid neighbours around cat
        ArrayList<Integer> neighbours = manager.checkNeighbours(this.x, this.y);
        //Checks if cat has no valid moves
        //Or if only 1 move is possible
        if (neighbours.size() == 0) {
            return false;
        } else if (neighbours.size() == 1) {
            this.y += yMoves[neighbours.get(0)];
            this.x += xMoves[neighbours.get(0)];
            return true;
        }

        ArrayList<Integer> scores = new ArrayList<>();
        for(Integer index : neighbours){
            scores.add(board.getBoard()[this.x + xMoves[index]][this.y + yMoves[index]].getScore());
        }
        int best = Collections.max(scores);


        this.y += yMoves[neighbours.get(scores.indexOf(best))];
        this.x += xMoves[neighbours.get(scores.indexOf(best))];
        return true;
    }
}


