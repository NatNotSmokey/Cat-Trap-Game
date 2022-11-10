import java.util.ArrayList;

public class ScoreManager {

    private Hexagon[][] board;
    final int[] xMoves = {-2, -1, 1, 2, 1, -1};
    final int[] evenYMoves = {0, 0, 0, 0, -1, -1};
    final int[] oddYMoves = {0, 1, 1, 0, 0, 0};

    public ScoreManager(GameBoard board){
        this.board = board.getBoard();
        boardSetup();
    }

    public int scoreCalculator(){



        return 0;
    }

    public void boardSetup(){
        for (int i = 0; i < (board.length); i++){
            for (int j = 0; j < (board[0].length); j++){
                ArrayList<Integer> neighbours = checkNeighbours(i, j);
                board[i][j].addScore(neighbours.size());

                if (i == 0 || i == 1 || i == 13 || i == 14 || (j == 0 && i % 2 == 0) || (j == 6 && i % 2 != 0)){
                    board[i][j].setScore(50);
                }
            }
        }
    }

    public void updateTiles(int x, int y){

        int[] yMoves;
        if (x % 2 == 0) {
            yMoves = evenYMoves;
        } else {
            yMoves = oddYMoves;
        }

        ArrayList<Integer> neighbours = checkNeighbours(x, y);
        for (Integer index : neighbours){
            board[x + xMoves[index]][y + yMoves[index]].addScore(-1);
        }
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

            //Catches out of bounds calls
            if(tempX <= -1 || tempX >= 15 || tempY <= -1 || tempY >= 7){
                tileExists = false;
            }

            if (tileExists && !board[tempX][tempY].getIsPressed()) {
                possibleMoves.add(i);
            }
        }

        return possibleMoves;
    }
}
