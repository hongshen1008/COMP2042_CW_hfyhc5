package Brick;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class CrackTest {

    CementBrick cementBrick = new CementBrick(new Point(0,0), new Dimension(60,20));
    Crack crack = new Crack(cementBrick,1,35);

    @Test
    void makeCrack() {
        crack.makeCrack(new Point2D.Double(30.0,20.0), 10);
        assertNotNull(crack);
    }

    @Test
    void testMakeCrack() {
        crack.makeCrack(new Point(30,20), new Point(30,0));
        assertNotNull(crack);
    }

    @Test
    void draw() {
        crack.makeCrack(new Point(30,20),new Point(30,0));
        crack.draw();
        assertNotNull(crack);
    }

    @Test
    void reset() {
        crack.reset();
        assertFalse(cementBrick.isBroken());
    }
}