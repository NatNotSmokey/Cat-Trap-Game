import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Hexagon {

    private double[] x = new double[6];
    private double[] y = new double[6];
    private double length;
    private Color color;
    private Path2D hexagon;
    private Point2D center;
    private double radius;
    private boolean isPressed;
    private int score = 0;

    /**
     * Creates new hexagon
     * @param x starting x position of top left point
     * @param y starting y position of top left point
     * @param length length of sides
     * @param color color of hex
     */
    public Hexagon(double x, double y, double length, Color color){
        this.length = length;
        this.color = color;
        coordCalc(x, y);

        this.hexagon = new Path2D.Double();
        hexagon.moveTo(this.x[0], this.y[0]);
        for(int i = 1; i <= 5; i++){
            hexagon.lineTo(this.x[i], this.y[i]);
        }
        hexagon.closePath();
        center = getCenter();
        radius = getRadius();
        isPressed = false;
    }

    /**
     * Duplicate passed Hexagon to same position
     * @param hex passed hexagon to duplicate
     */
    public Hexagon(Hexagon hex){
        this.length = hex.getLength();
        this.color = hex.getColor();
        coordCalc(hex.getStrX(), hex.getStrY());

        this.hexagon = new Path2D.Double();
        hexagon.moveTo(this.x[0], this.y[0]);
        for(int i = 1; i <= 5; i++){
            hexagon.lineTo(this.x[i], this.y[i]);
        }
        hexagon.closePath();
        center = getCenter();
        radius = getRadius();
        isPressed = false;
    }

    /**
     * Duplicates passed hexagon at new position
     * @param hex passed hexagon to duplicate
     * @param x new position x
     * @param y new position y
     */
    public Hexagon(Hexagon hex, double x, double y){
        this.length = hex.getLength();
        this.color = hex.getColor();
        coordCalc(x, y);

        this.hexagon = new Path2D.Double();
        hexagon.moveTo(this.x[0], this.y[0]);
        for(int i = 1; i < 6; i++){
            hexagon.lineTo(this.x[i], this.y[i]);
        }
        hexagon.closePath();
        center = getCenter();
        radius = getRadius();
        isPressed = false;
    }

    public Color getColor(){
        return this.color;
    } public double getLength() {
        return this.length;
    }
    public double getStrX(){
        return x[0];
    } public double getStrY(){
        return y[0];
    }
    public Boolean getIsPressed(){
        return isPressed;
    }
    public int getScore() {
        return score;
    }
    public void setColor(Color color){
        this.color = color;
    }
    public void setIsPressed(){
        isPressed = true;
        setColor(new Color(51, 4, 51));
        setScore(0);
    }
    public void setScore(int i){
        this.score = i;
    }
    public void addScore(int i){
        this.score += i;
    }

    /**
     * Calculates the height of the hexagon
     * @return height of hex, from top side to bottom side
     */
    public double getHeight(){
        return (2*(Math.sin(Math.toRadians(60))*(length/(Math.sin(Math.toRadians(90))))));
    }

    /**
     * Calculates the width of the hexagon
     * @return width of hex, from left point to right point
     */
    public double getWidth(){
        return (2*(Math.sin(Math.toRadians(30))*(length/(Math.sin(Math.toRadians(90))))) + length);
    }

    /**
     * Finds center of hexagon for use in collision cals
     * @return center point
     */
    public Point2D getCenter(){
        return new Point2D.Double(getStrX() + length/2, getStrY() + getHeight()/2);
    }

    public double getRadius(){
        return getWidth()/2;
    }

    /**
     * Calculates all 6 coordinate points for the Hexagon.
     * @param strX starting x coordinate
     * @param strY starting y coordinate
     */
    public void coordCalc(double strX, double strY){

        double temp = length/(Math.sin(Math.toRadians(90)));

        //Starts at top point and calculates clockwise around the shape
        x[0] = strX;
        x[1] = x[0] + length;
        x[2] = x[1] + (Math.sin(Math.toRadians(30))*temp);
        x[3] = x[1];
        x[4] = strX;
        x[5] = x[4] - (Math.sin(Math.toRadians(30))*temp);

        y[0] = strY;
        y[1] = strY;
        y[2] = y[1] + (Math.sin(Math.toRadians(60))*temp);
        y[3] = y[2] + (Math.sin(Math.toRadians(60))*temp);
        y[4] = y[3];
        y[5] = y[4] - (Math.sin(Math.toRadians(60))*temp);

    }

    /**
     * Draws hexagon
     * @param g2d graphics2d object used for handling the canvas
     */
    public void drawHexagon(Graphics2D g2d){
        g2d.setColor(color);
        g2d.fill(hexagon);
        g2d.setColor(Color.black);
        g2d.draw(hexagon);
        g2d.setColor(Color.WHITE);
        g2d.drawString("" + score, (int) getCenter().getX(), (int) getCenter().getY());
    }

    /**
     * Checks if click was within radius of tile
     * @param point point where mouse was clicked
     * @return true if point is within radius of tile
     */
    public boolean collision(Point2D point){
        return hexagon.contains(point);
    }
}
