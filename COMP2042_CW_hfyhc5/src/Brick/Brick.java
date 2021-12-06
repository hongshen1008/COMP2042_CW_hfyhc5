package Brick;

import GameObject.Ball;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * This is Abstract Brick class. Designs brick and contains brick operations.
 *
 * @author Chin Hong Shen
 * @version 0.2
 * @since 24 November 2021
 *
 */
abstract public class Brick  {

    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;
    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;

    private static Random rnd;
    Shape brickFace;

    private Color border;
    private Color inner;
    private int fullStrength;
    private int strength;

    private boolean broken;


    /**
     * This is Brick class constructor. Construct a brick object.
     * Initialize variables and perform instantiation.
     *
     * @param pos       represents brick position
     * @param size      represents width and height of the brick
     * @param border    indicates the border color of the brick
     * @param inner     indicates the inner color of the brick
     * @param strength  represents the brick strength
     */
    public Brick(Point pos,Dimension size,Color border,Color inner,int strength){   //brick constructor
        rnd = new Random();
        broken = false;
        brickFace = makeBrickFace(pos,size);
        this.border = border;
        this.inner = inner;
        this.fullStrength = this.strength = strength;   //clay and steel strength = 1 , cement strength = 2

    }

    /**
     * This is an abstract method.
     * Create the shape of the brick.
     *
     * @param pos  represents a location of the brick in (x,y) coordinate space
     * @param size represents the width and height of the brick
     * @return     the shape of the brick.
     */
    protected abstract Shape makeBrickFace(Point pos,Dimension size);

    /**
     * This method returns the shape of the brick.
     *
     * @return shape of brickFace
     */
    public abstract Shape getBrickFace();

    /**
     * This method determines if the brick is broken.
     * Returns true if the brick is not broken.
     *
     * @param point defines a point representing a location in (x,y) coordinate space
     * @param dir   represents the direction of the brick crack
     * @return      true if the brick is not broken
     */
    public boolean setImpact(Point2D point , int dir){
        if(broken)
            return false;
        impact();
        return broken;
    }

    /**
     * Gets the border color of the brick.
     *
     * @return border color of the brick
     */
    public Color getBorderColor(){
        return border;
    }

    /**
     * Gets the inner color of the brick.
     *
     * @return inner color of the brick
     */
    public Color getInnerColor(){
        return inner;
    }


    /**
     * This method is used to determine the direction when the ball contact with the brick.
     *
     * @param b represents ball object.
     * @return  value of the impact direction.
     */
    public final int findImpact(Ball b){
        if(broken)
            return 0;
        int out  = 0;
        if(brickFace.contains(b.getRight()))     //if the ball contact with the left of the brick
            out = LEFT_IMPACT;
        else if(brickFace.contains(b.getLeft()))
            out = RIGHT_IMPACT;
        else if(brickFace.contains(b.getUp()))
            out = DOWN_IMPACT;
        else if(brickFace.contains(b.getDown()))
            out = UP_IMPACT;
        return out;
    }

    /**
     * This method is used to determine if the brick is broken.
     *
     * @return true if the brick is broken
     */
    public final boolean isBroken(){
        return broken;
    }

    /**
     * This method is used to rebuild the brick.
     * Sets the brick back to full strength.
     */
    public void repair() {
        broken = false;     //set broken back to false
        strength = fullStrength;    //set strength back to fullStrength
    }

    /**
     * This method is used when the ball contact with the brick.
     * Decreases the brick strength. If strength equals to zero, brick is broken.
     */
    public void impact(){
        strength--;
        broken = (strength == 0);   //if strength == 0, set broken to true else false
    }

    /**
     * This method is used to get random values.
     *
     * @return random values.
     */
    public static Random getRnd() {
        return rnd;
    }


}





