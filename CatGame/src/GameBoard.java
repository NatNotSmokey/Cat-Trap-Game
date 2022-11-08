import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GameBoard {

    private Hexagon[][] board;
    private Hexagon tile;
    private int spacing;
    private int offsetX;
    private int offsetY;

    /**
     * GameBoard constructor.
     * @param rows number of rows on game board
     * @param columns number of columns on game board
     * @param tile reference tile to duplicate for all board tiles
     * @param spacing spacing between tiles
     */
    public GameBoard(int rows, int columns, Hexagon tile,int offsetX, int offsetY, int spacing){
        board = new Hexagon[rows][columns];
        this.tile = tile;
        this.spacing = spacing;
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        //Creates all tiles and properly spaces them
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                if(i%2 != 0){
                    board[i][j] = new Hexagon(tile,
                            j * (tile.getWidth() + tile.getLength() + spacing) + offsetX + spacing/2 + tile.getWidth()/2
                                    + (Math.sin(Math.toRadians(30)) * (tile.getLength())/Math.sin(Math.toRadians(90))) ,
                            i * (tile.getHeight() + spacing)/2 + offsetY);
                } else {
                    board[i][j] = new Hexagon(tile,
                            j * (tile.getWidth() + tile.getLength() + spacing) + offsetX,
                            i * (tile.getHeight() + spacing)/2 + offsetY);
                }
            }
        }

        //Randomly sets a random amount of tiles as pressed
        //Currently, same tiles can be rolled twice, nothing happens in that case
        int random = ThreadLocalRandom.current().nextInt(9, 15);
        for(int i = 0; i <= random; i++){

            int ranX = ThreadLocalRandom.current().nextInt(0, 15);
            int ranY = ThreadLocalRandom.current().nextInt(0, 7);
            System.out.println("Tile " + ranX + ", " + ranY);

            //Makes sure a tile cant be pressed on the cats starting position
            if(ranX != 7 && ranY != 3){
                board[ranX][ranY].setIsPressed();
            }
        }
    }

    public Hexagon[][] getBoard(){
        return board;
    }

    /**
     * Draws each hexagon tile
     * @param g2d canvas graphic
     */
    public void drawBoard(Graphics2D g2d){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j].drawHexagon(g2d);
            }
        }
    }
}
