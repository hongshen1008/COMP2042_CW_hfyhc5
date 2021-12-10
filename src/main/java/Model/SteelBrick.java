/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * This is SteelBrick class. Inherits properties of Brick class.
 * Creates steel brick and implements characteristics of steel brick.
 *
 * @author Chin Hong Shen
 * @version 0.2
 * @since 24 November 2021
 */
public class SteelBrick extends Brick {

    private static final Color DEF_INNER = new Color(203, 203, 201);
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int STEEL_STRENGTH = 1;
    private static final double STEEL_PROBABILITY = 0.5;

    private Random rnd;
    private Shape brickFace;

    /**
     * This is SteelBrick class constructor. Creates steel brick object.
     *
     * @param point represents the location of steel brick
     * @param size  represents the width and height of steel brick
     */
    public SteelBrick(Point point, Dimension size){
        super(point,size,DEF_BORDER,DEF_INNER,STEEL_STRENGTH);
        this.rnd = new Random();    //put this
        this.brickFace = super.brickFace;   //put this
    }

    /**
     * This method overrides the method in Brick class.
     * Create steel brick by setting brick location and dimension.
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
    public boolean setImpact(Point2D point , int dir){
        if(super.isBroken())
            return false;
        impact();
        return super.isBroken();
    }

    /**
     * This method determines when to break steel brick.
     */
    public void impact(){
        if(rnd.nextDouble() < STEEL_PROBABILITY){
            super.impact();
        }
    }

}
