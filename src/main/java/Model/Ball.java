package Model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

/**
 * This is abstract Ball class, used to determine locations of ball during ball movements.
 *
 * @author Chin Hong Shen
 * @version 0.2
 * @since 24 November 2021
 */
abstract public class Ball {

    private Shape ballFace; //the shape of ball

    private Point2D center;

    private Point2D up;     //put access modifier and get method
    private Point2D down;   //put access modifier and get method
    private Point2D left;   //put access modifier and get method
    private Point2D right;  //put access modifier and get method

    private Color border;
    private Color inner;

    private int speedX;
    private int speedY;

    /**
     * This is Ball class constructor, used to initialise variables and perform instantiation.
     *
     * @param center represents center position of the game window
     * @param radiusA represents radius of the ball
     * @param radiusB represents radius of the ball
     * @param inner   represents the inner color of the ball which is yellow
     * @param border represents the border color of the ball which is dark yellow
     */
    public Ball(Point2D center,int radiusA,int radiusB,Color inner,Color border){   //constructor
        this.center = center;

        this.up = new Point2D.Double(center.getX(),center.getY()-(radiusB / 2));    //upper part of the ball
        this.down = new Point2D.Double(center.getX(),center.getY()+(radiusB / 2));  //lower part of the ball
        this.left = new Point2D.Double(center.getX()-(radiusA /2),center.getY());   //left part of the ball
        this.right = new Point2D.Double(center.getX()+(radiusA /2),center.getY());  //right part of the ball


        ballFace = makeBall(center,radiusA,radiusB);
        this.border = border;
        this.inner  = inner;
        speedX = 0;
        speedY = 0;
    }

    /**
     * This is an abstract method, used to hide details of implementations.
     *
     * @param center represents the center position of the game window
     * @param radiusA represents the radius of the ball
     * @param radiusB represents the radius of the ball
     * @return the shape of the ball
     */
    protected abstract Shape makeBall(Point2D center,int radiusA,int radiusB);

    /**
     * This method is used to set the ball movement.
     */
    public void move(){
        center.setLocation((center.getX() + speedX),(center.getY() + speedY));
        getFrameCenter();
    }

    /**
     * This method is used to set the ball location in the beginning of the game.
     *
     * @param p represents location in (x,y) coordinate space
     */
    public void moveTo(Point p){
        center.setLocation(p);
        getFrameCenter();

    }

    /**
     * This method is to set ball in the center location of the frame.
     */
    private void getFrameCenter(){
        RectangularShape tmp = (RectangularShape) ballFace;
        double w = tmp.getWidth();
        double h = tmp.getHeight();
        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        setPoints(w,h);
        ballFace = tmp;
    }

    /**
     * This method is used to set the direction of the ball.
     *
     * @param width represents to width of the game window
     * @param height represents to height of the game window
     */
    private void setPoints(double width,double height){
        up.setLocation(center.getX(),center.getY()-(height / 2));
        down.setLocation(center.getX(),center.getY()+(height / 2));
        left.setLocation(center.getX()-(width / 2),center.getY());
        right.setLocation(center.getX()+(width / 2),center.getY());
    }

    /**
     * This method is used to set the speed of the ball.
     * speedX is the horizontal moving speed, speedY is the vertical moving speed.
     *
     * @param x represents the speed of the ball in x-coordinate
     * @param y represents the speed of the ball in y-coordinate
     */
    public void setSpeed(int x,int y){
        speedX = x;
        speedY = y;
    }

    /**
     * This method is used to set the ball speed in debug console.
     * The speed of the ball in horizontal direction.
     *
     * @param s represents the speed of the ball in horizontal direction
     */
    public void setXSpeed(int s){
        speedX = s;
    }

    /**
     * This method is used to set the ball speed in debug console.
     * The speed of the ball in vertical direction.
     *
     * @param s represents the speed of the ball in vertical direction
     */
    public void setYSpeed(int s){
        speedY = s;
    }

    /**
     * This method is used when the ball bounce back from the wall.
     * The ball reverse in the horizontal direction.
     */
    public void reverseX(){
        speedX *= -1;
    }

    /**
     * This method is used when the ball bounce back from the wall.
     * The ball reverse in the vertical direction.
     */
    public void reverseY(){
        speedY *= -1;
    }

    /**
     * This method is used to get the border color of the ball.
     *
     * @return return the border color
     */
    public Color getBorderColor(){
        return border;
    }

    /**
     * This method is used to get the inner color of the ball.
     *
     * @return return the inner color
     */
    public Color getInnerColor(){
        return inner;
    }

    /**
     * This method is used to get the center position of the ball.
     *
     * @return represents a location in (x,y) coordinate space.
     */
    public Point2D getPosition(){
        return center;
    }

    /**
     * This method is used to get the shape of the ball.
     *
     * @return represents the shape of the ball
     */
    public Shape getBallFace(){
        return ballFace;
    }

    /**
     * This method is to get the speed of the ball in horizontal direction.
     *
     * @return return the speed in integer form
     */
    public int getSpeedX(){
        return speedX;
    }

    /**
     * This method is to get the speed of the ball in vertical direction.
     *
     * @return return the speed in integer form
     */
    public int getSpeedY(){
        return speedY;
    }

    /**
     * This method is to get the upper part of the ball.
     *
     * @return represents a location in (x,y) coordinate space.
     */
    public Point2D getUp() {
        return up;
    }

    /**
     * This method is to get the lower part of the ball.
     *
     * @return represents a location in (x,y) coordinate space.
     */
    public Point2D getDown() {
        return down;
    }

    /**
     * This method is to get the left part of the ball.
     *
     * @return represents a location in (x,y) coordinate space.
     */
    public Point2D getLeft() {
        return left;
    }

    /**
     * This method is to get the right part of the ball.
     *
     * @return represents a location in (x,y) coordinate space.
     */
    public Point2D getRight() {
        return right;
    }
}
