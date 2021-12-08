package Brick;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ClayBrickTest {

    ClayBrick clayBrick = new ClayBrick(new Point(0,0), new Dimension(60,45));
    Rectangle brickFace = new Rectangle(new Point(0,0), new Dimension(60,45));

    @Test
    void makeBrickFace() {
        Point pos = new Point(0,0);
        Dimension size = new Dimension(60,45);
        assertEquals(brickFace, clayBrick.makeBrickFace(pos,size));
    }

    @Test
    void getBrickFace() {
        assertEquals(brickFace, clayBrick.getBrickFace());
    }
}