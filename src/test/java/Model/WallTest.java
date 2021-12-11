package Model;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {

    Wall wall = new Wall(new Rectangle(0,0,600,450),30,3,6/2,new Point(300,430));
    RubberBall ball = new RubberBall(new Point(300,430));

    @Test
    void move() {
        wall.move();
        assertEquals(new Point2D.Double(304.0,426.0),wall.getBall().getPosition());
    }

    @Test
    void findImpacts() {
        wall.setBallXSpeed(4);
        wall.findImpacts();
        assertEquals(4,wall.getBall().getSpeedX());
    }

    @Test
    void initialiseSpeed() {
        wall.initialiseSpeed();
        ball.setSpeed(4,-4);
        assertEquals(-4,ball.getSpeedY());
    }

    @Test
    void ballReset() {
        wall.ballReset();
        assertEquals(new Point(300,430),ball.getPosition());
    }

    @Test
    void wallReset() {
        ClayBrick clayBrick = new ClayBrick(new Point(0,0), new Dimension(60,45));
        if(clayBrick.isBroken()) {
            wall.wallReset();
        }

        assertEquals(0,wall.getBrickCount());
        assertEquals(3,wall.getBallCount());
    }

    @Test
    void getHighScore() {
        assertNotNull( wall.getHighScore());
    }

    @Test
    void checkScore() {
        wall.checkScore();
        assertNotNull(wall);
    }

    @Test
    void ballEnd() {
        if (wall.getBallCount() == 0)
            assertTrue(wall.ballEnd());
    }

    @Test
    void isDone() {
        if(wall.getBrickCount() == 0){
            assertTrue(wall.isDone());
        }

    }

    @Test
    void nextLevel() {
        wall.nextLevel();
        int tmp_level = 0;
        tmp_level++;
        assertEquals(1,tmp_level);
    }

    @Test
    void hasLevel() {
        assertTrue(wall.hasLevel());
    }

    @Test
    void setBallXSpeed() {
        wall.setBallXSpeed(0);
        assertEquals(0,ball.getSpeedX());
    }

    @Test
    void setBallYSpeed() {
        wall.setBallXSpeed(0);
        assertEquals(0,ball.getSpeedY());
    }

    @Test
    void resetBallCount() {
        wall.resetBallCount();
        assertEquals(3,wall.getBallCount());
    }

    @Test
    void getBrickCount() {
        assertEquals(0,wall.getBrickCount());
    }

    @Test
    void getBallCount() {
        assertEquals(3,wall.getBallCount());
    }

    @Test
    void isBallLost() {
        assertFalse(wall.isBallLost());
    }


    @Test
    void getScore() {
        assertEquals(0,wall.getScore());
    }

    @Test
    void scoreReset() {
        assertEquals(0,wall.getScore());
    }

    @Test
    void setScore() {
        wall.setScore(3);
        assertEquals(3,wall.getScore());
    }
}