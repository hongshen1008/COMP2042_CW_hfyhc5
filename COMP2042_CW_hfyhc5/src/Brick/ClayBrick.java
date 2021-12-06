package Brick;

import java.awt.*;
import java.awt.Point;


/**
 * This is ClayBrick class. Inherits properties of Brick class.
 * Creates clay brick and implements characteristics of clay brick.
 *
 * @author Chin Hong Shen
 * @version 0.2
 * @since 24 November 2021
 */
public class ClayBrick extends Brick {

    private static final Color DEF_INNER = new Color(178, 34, 34).darker();
    private static final Color DEF_BORDER = Color.GRAY;
    private static final int CLAY_STRENGTH = 1;


    /**
     * This is ClayBrick constructor. Construct ClayBrick object.
     *
     * @param point represents the location of clay brick
     * @param size  represents the width and height of clay brick
     */
    public ClayBrick(Point point, Dimension size){
        super(point,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
    }

    /**
     *This method overrides the method in Brick class.
     * Create clay brick by setting brick location and dimension.
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
        return super.brickFace;
    }


}
