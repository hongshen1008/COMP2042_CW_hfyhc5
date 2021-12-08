package Brick;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TitaniumBrickTest {

    TitaniumBrick titaniumBrick = new TitaniumBrick(new Point(0,0), new Dimension(60,45));
    Rectangle brickFace = new Rectangle(new Point(0,0), new Dimension(60,45));

    @Test
    void makeBrickFace() {
        Point pos = new Point(0,0);
        Dimension size = new Dimension(60,45);
        assertEquals(brickFace, titaniumBrick.makeBrickFace(pos,size));
    }

    @Test
    void getBrickFace() {
        assertEquals(brickFace, titaniumBrick.getBrickFace());
    }

    @Test
    void setImpact() {
        Point2D down = new Point2D.Double();
        down.setLocation(300.0, 430.0);
        int up = 30;
        assertFalse(titaniumBrick.setImpact(down, up));
    }

    @Test
    void impact() {
        Random rnd = new Random();
        double STEEL_PROBABILITY = 0.5;
        if(rnd.nextDouble() < STEEL_PROBABILITY){
            assertFalse(titaniumBrick.isBroken());
        }
    }
}