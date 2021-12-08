package Brick;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * This is TitaniumBrick class. Inherits properties of Brick class.
 * Creates titanium brick and implements characteristics of steel brick.
 *
 * @author Chin Hong Shen
 * @version 0.2
 * @since 24 November 2021
 */
public class TitaniumBrick extends Brick{

    private static final Color DEF_INNER = Color.decode("#585855");
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int TITANIUM_STRENGTH = 1;
    private static final double TITANIUM_PROBABILITY = 0.35;

    private Random rnd;
    private Shape brickFace;

    /**
     * This is TitaniumBrick class constructor. Creates titanium brick object.
     *
     * @param point represents the location of titanium brick
     * @param size  represents the width and height of titanium brick
     */
    public TitaniumBrick(Point point, Dimension size){
        super(point,size,DEF_BORDER,DEF_INNER,TITANIUM_STRENGTH);
        this.rnd = new Random();    //put this
        this.brickFace = super.brickFace;   //put this
    }

    /**
     * This method overrides the method in Brick class.
     * Create titanium brick by setting brick location and dimension.
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
     * This method overrides the super method to return the shape of the brick.
     *
     * @return shape of brickFace
     */
    @Override
    public Shape getBrickFace() {
        return brickFace;
    }

    /**
     * This method determines if the brick is broken.
     * Returns true if the brick is not broken.
     *
     * @param point defines a point representing a location in (x,y) coordinate space
     * @param dir   represents the direction of the brick crack
     * @return      true if the brick is not broken
     */
    public boolean setImpact(Point2D point, int dir){
        if(super.isBroken())
            return false;
        impact();
        return super.isBroken();
    }

    /**
     * This method determines when to break steel brick.
     */
    public void impact(){
        if(rnd.nextDouble() < TITANIUM_PROBABILITY){
            super.impact();
        }
    }
}
