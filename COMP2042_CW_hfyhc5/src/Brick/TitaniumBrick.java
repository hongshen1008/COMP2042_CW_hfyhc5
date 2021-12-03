package Brick;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

public class TitaniumBrick extends Brick{

    private static final Color DEF_INNER = Color.decode("#a1a09a");
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int TITANIUM_STRENGTH = 1;
    private static final double TITANIUM_PROBABILITY = 0.4;

    private Random rnd;
    private Shape brickFace;

    public TitaniumBrick(Point point, Dimension size){
        super(point,size,DEF_BORDER,DEF_INNER,TITANIUM_STRENGTH);
        this.rnd = new Random();    //put this
        this.brickFace = super.brickFace;   //put this
    }

    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    @Override
    public Shape getBrickFace() {
        return brickFace;
    }

    public  boolean setImpact(Point2D point, int dir){
        if(super.isBroken())
            return false;
        impact();
        return  super.isBroken();
    }

    public void impact(){
        if(rnd.nextDouble() < TITANIUM_PROBABILITY){
            super.impact();
        }
    }
}
