package Model;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SteelBrickTest {

    SteelBrick steelBrick = new SteelBrick(new Point(0,0), new Dimension(60,45));
    Rectangle brickFace = new Rectangle(new Point(0,0), new Dimension(60,45));

    @Test
    void makeBrickFace() {
        Point pos = new Point(0,0);
        Dimension size = new Dimension(60,45);
        assertEquals(brickFace, steelBrick.makeBrickFace(pos,size));
    }

    @Test
    void getBrickFace() {
        assertEquals(brickFace, steelBrick.getBrickFace());
    }

    @Test
    void setImpact() {
        Point2D up = new Point2D.Double(494.0,37.0);
        Random rnd = new Random();
        steelBrick.setImpact(up, Crack.DOWN);
        if(steelBrick.isBroken())
        {
            assertFalse(steelBrick.setImpact(up, Crack.DOWN));
        }
        if(rnd.nextDouble() < 0.5)
        {
            assertTrue(steelBrick.setImpact(up, Crack.DOWN));
        }

    }

    @Test
    void impact() {
        Random rnd = new Random();
        double STEEL_PROBABILITY = 0.5;
        if(rnd.nextDouble() < STEEL_PROBABILITY){
            assertFalse(steelBrick.isBroken());
        }
    }
}