package Brick;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;


/**
 * This is CementBrick class. Inherits properties of Brick class.
 * Creates cement brick and implements characteristics of cement brick.
 *
 * @author Chin Hong Shen
 * @version 0.2
 * @since 24 November 2021
 */
public class CementBrick extends Brick {

    private static final Color DEF_INNER = new Color(147, 147, 147);
    private static final Color DEF_BORDER = new Color(217, 199, 175);
    private static final int CEMENT_STRENGTH = 2;

    private Crack crack;
    private Shape brickFace;


    /**
     * This is CementBrick class constructor. Construct CementBrick object.
     * Initialises variables and instantiate Crack class.
     *
     * @param point represents the location of cement brick
     * @param size  represents the width and height of cement brick
     */
    public CementBrick(Point point, Dimension size){
        super(point,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
        crack = new Crack(this, DEF_CRACK_DEPTH,DEF_STEPS);
        brickFace = super.brickFace;
    }

    /**
     * This method overrides the method in Brick class.
     * Create cement brick by setting brick location and dimension.
     *
     * @param pos  represents a location of the brick in (x,y) coordinate space
     * @param size represents the width and height of the brick
     * @return     rectangle shape of the brick
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * This method overrides the method in Brick class.
     * If the brick is not broken make the brick crack and return true.
     *
     * @param point defines a point representing a location in (x,y) coordinate space
     * @param dir   represents the direction of the brick crack
     * @return      true if the brick is not broken
     */
    @Override
    public boolean setImpact(Point2D point, int dir) {
        if(super.isBroken())
            return false;
        super.impact();
        if(!super.isBroken()){
            crack.makeCrack(point,dir);
            updateBrick();
            return false;
        }
        return true;
    }


    /**
     * This method overrides the super method to return the shape of the brick.
     *
     * @return shape of brickFace
     */
    @Override
    public Shape getBrickFace() {
        return brickFace;
    }

    /**
     * This method is used to update the brick after drawing the crack.
     */
    private void updateBrick(){
        if(!super.isBroken()){
            GeneralPath gp = crack.draw();
            gp.append(super.brickFace,false);
            brickFace = gp;
        }
    }

    /**
     * This method is used to reset the brick to original state when there is no crack.
     */
    public void repair(){
        super.repair();
        crack.reset();
        brickFace = super.brickFace;
    }
}
