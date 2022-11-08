import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class GameBoard {

    private Hexagon[][] board;
    private ArrayList<Point2D> centers = new ArrayList<>();
    private Hexagon tile;
    private int spacing;
    private int offsetX;
    private int offsetY;

    public GameBoard(int rows, int columns, Hexagon tile, int offsetX, int offsetY, int spacing){
        board = new Hexagon[rows][columns];
        this.tile = tile;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.spacing = spacing;

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                if(i%2 != 0){
                    board[i][j] = new Hexagon(tile,
                            j * (tile.getWidth() + tile.getLength() + spacing) + spacing/2 + offsetX + tile.getWidth()/2
                                    + (Math.sin(Math.toRadians(30)) * (tile.getLength())/Math.sin(Math.toRadians(90))) ,
                            i * (tile.getHeight() + spacing)/2 + offsetY);
                } else {
                    board[i][j] = new Hexagon(tile,
                            j * (tile.getWidth() + tile.getLength() + spacing) + offsetX,
                            i * (tile.getHeight() + spacing)/2 + offsetY);
                }
                centers.add(board[i][j].getCenter());
            }
        }
    }

    public Hexagon[][] getBoard(){
        return board;
    }

    public ArrayList<Point2D> getCenters() {
        return centers;
    }

    public void drawBoard(Graphics2D g2d){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j].drawHexagon(g2d);
            }
        }
    }
}
