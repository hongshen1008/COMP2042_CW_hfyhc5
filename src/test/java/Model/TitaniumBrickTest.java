package Model;

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
        Point2D up = new Point2D.Double(494.0,37.0);
        Random rnd = new Random();
        titaniumBrick.setImpact(up, Crack.DOWN);
        if(titaniumBrick.isBroken())
        {
            assertFalse(titaniumBrick.setImpact(up, Crack.DOWN));
        }
        if(rnd.nextDouble() < 0.35)
        {
            assertTrue(titaniumBrick.setImpact(up, Crack.DOWN));
        }
    }

    @Test
    void impact() {
        Random rnd = new Random();
        double TITANIUM_PROBABILITY = 0.35;
        if(rnd.nextDouble() < TITANIUM_PROBABILITY){
            assertFalse(titaniumBrick.isBroken());
        }
    }
}