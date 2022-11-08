import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Cat {

    private int x;
    private int y;
    private GameBoard board;
    final int[] xMoves = {-2, -1, 1, 2, 1, -1};
    final int[] evenYMoves = {0, 0, 0, 0, -1, -1};
    final int[] oddYMoves = {0, 1, 1, 0, 0, 0};

    public Cat (int startX, int startY, GameBoard board){
        x = startX;
        y = startY;
        this.board = board;
    }

    public int getX() {
        return x;
    } public int getY() {
        return y;
    }

    /**
     * Handles drawing the cat
     * @param g2d graphics object for rendering
     */
    public void drawCat(Graphics2D g2d){
        Point2D point = board.getBoard()[x][y].getCenter();

        g2d.setColor(Color.BLACK);
        g2d.fillOval((int) point.getX()-15, (int) point.getY()-11, 31, 31);

        Path2D ears = new Path2D.Double();
        ears.moveTo(point.getX()-14, point.getY());
        ears.lineTo(point.getX()-19, point.getY()-18);
        ears.lineTo(point.getX(), point.getY()-10);
        ears.lineTo(point.getX()+19, point.getY()-18);
        ears.lineTo(point.getX()+14, point.getY());
        ears.closePath();
        g2d.fill(ears);
    }

    /**
     * Handles moving the cat. Uses moveCalculator to decide which move is best. It fails to move if the tile is pressed
     * If all 6 moves fail, the cat is considered stuck.
     * @return true if moved successfully, false if no move is possible
     */
    public boolean move(){

        if (this.x == 0 || this.x == 1 || this.x == 13 || this.x == 14 || (this.y == 0 && this.x % 2 == 0) || (this.y == 6 && this.x % 2 == 0)){
            return false;
        }

        //Grabs valid neighbours around cat
        ArrayList<Integer> neighbours = checkNeighbours(this.x, this.y);
        //Checks if cat has no valid moves
        //Or if only one move is possible
        if(neighbours.size() == 0){
            return false;
        } else if (neighbours.size() == 1){
            if(this.x % 2 == 0){
                this.y += evenYMoves[neighbours.get(0)];
            }
            else{
                this.y += oddYMoves[neighbours.get(0)];
            }
            this.x += xMoves[neighbours.get(0)];
            return true;
        }

        //Calls move calculator for all possible moves around cat
        for (int i = 0; i < neighbours.size(); i++) {
            if (this.x % 2 == 0) {
                moveCalculator(this.x + xMoves[i], this.y + evenYMoves[i]);
            } else{
                moveCalculator(this.x + xMoves[i], this.y + oddYMoves[i]);
            }
        }

        int best = 0;
        //Grabs the highest score tile around cat
        for (int i = 0; i < neighbours.size(); i++) {

            int topScore = 0;
            if (board.getBoard()[this.x + xMoves[i]][this.y + evenYMoves[i]].getScore() > topScore){
                best = i;
                topScore = board.getBoard()[this.x + xMoves[i]][this.y + evenYMoves[i]].getScore();
            }
        }

        if(this.x % 2 == 0){
            this.y += evenYMoves[neighbours.get(best)];
        }
        else{
            this.y += oddYMoves[neighbours.get(best)];
        }
        this.x += xMoves[neighbours.get(best)];
        return true;
    }

    /**
     * Scores each possible move, and chooses the best move as its action
     * @param x tile index
     * @param y tile index
     */
    public void moveCalculator(int x, int y){
        //Grabs all valid neighbours around this tile.
        ArrayList<Integer> possibleMoves = checkNeighbours(x, y);
        board.getBoard()[x][y].setScore(possibleMoves.size());

    }

    /**
     * Checks all 6 neighbours of the passed tile coordinates
     * @param x tile to have its neighbours checked x index
     * @param y tile to have its neighbours checked y index
     * @return List of possible move indexes
     */
    public ArrayList<Integer> checkNeighbours(int x, int y){

        ArrayList<Integer> possibleMoves = new ArrayList<>();

        //Sets possible changes on the board array[][] to comply with hexagonal board
        //Uses multiple instances of y to make automated selection simple and keep x,y pairs

        for(int i = 0; i < 6; i++){

            boolean tileExists = true;
            int tempX =  x + xMoves[i];
            int tempY = 0;

            if(x % 2 == 0){
                tempY = y + evenYMoves[i];
            }
            else {
                tempY = y + oddYMoves[i];
            }

            if(tempX <= -1 || tempX >= 15 || tempY <= -1 || tempY >= 7){
                tileExists = false;
            }

            if (tileExists && !board.getBoard()[tempX][tempY].getIsPressed()) {
                possibleMoves.add(i);
            }
        }

        return possibleMoves;
    }
}
