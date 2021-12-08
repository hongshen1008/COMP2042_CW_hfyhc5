package Brick;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class CementBrickTest {

    CementBrick cementBrick = new CementBrick(new Point(0,0), new Dimension(60,45));
    Rectangle brickFace = new Rectangle(new Point(0,0), new Dimension(60,45));

    @Test
    void makeBrickFace() {
        Point pos = new Point(0,0);
        Dimension size = new Dimension(60,45);
        assertEquals(brickFace, cementBrick.makeBrickFace(pos,size));
    }

    @Test
    void setImpact() {
        Point2D down = new Point2D.Double();
        down.setLocation(300.0, 430.0);
        int up = 30;
        assertFalse(cementBrick.setImpact(down, up));
    }

    @Test
    void getBrickFace() {
        assertEquals(brickFace, cementBrick.getBrickFace());
    }

    @Test
    void repair() {
        cementBrick.repair();
        assertFalse(cementBrick.isBroken());
    }
}