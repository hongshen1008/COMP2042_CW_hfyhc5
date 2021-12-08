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
package Brick;

import GameObject.Ball;
import GameObject.Player;
import GameObject.RubberBall;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.*;

/**
 * This is Wall class. Implements game logics and operations.
 *
 *  @author Chin Hong Shen
 *  @version 0.2
 *  @since 24 November 2021
 */
public class Wall {

    private Rectangle area;

    private Brick[] bricks; //put access modifier and get method
    private Ball ball;      //put access modifier and get method
    private Player player;  //put access modifier and get method
    private Levels levels;
    private Brick[][] brick_level;
    private int tmp_level;
    private Point startPoint;
    private int brickCount;
    private int ballCount;
    private int score;
    private String highScore = "";
    private boolean ballLost;

    /**
     * This is Wall class constructor. Construct wall object.
     * Initialises variables and performs instantiation.
     *
     * @param drawArea represents area of the rectangle
     * @param brickCount represents number of bricks
     * @param lineCount  represents the layers to form a wall
     * @param brickDimensionRatio represents the ratio of the height and width of a brick
     * @param ballPos represents the ball location
     */
    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){

        this.startPoint = new Point(ballPos);
        levels = new Levels();
        brick_level = levels.makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio); //brickCount = num of brick, line count = 3 rows of brick

        tmp_level = 0;
        this.score = 0;    //set score
        ballCount = 3;
        initialiseSpeed(ballPos);
        ballLost = false;

        this.player = new Player((Point) ballPos.clone(),150,10, drawArea);     //instantiate player class

        if(highScore.equals(""))
        {
            highScore = this.getHighScore();
        }

        area = drawArea;
    }

    /**
     * This method is used to determine the movement of player and ball.
     */
    public void move(){
        player.move();
        ball.move();
    }

    /**
     * This method is used to determine what operations should be done if the ball contacts with player bar or the border of the game window.
     */
    public void findImpacts(){
        if(player.impact(ball)){  //if the ball contacts with player bar
            ball.reverseY();
        }
        else if(impactWall()){
            /*for efficiency reverse is done into method impactWall
            * because for every brick program checks for horizontal and vertical impacts
            */
            brickCount--;
            score++;
        }
        else if(impactBorder()) {   //if the ball touch the window left and right border bounce back
            ball.reverseX();
        }
        else if(ball.getPosition().getY() < area.getY()){  //if the ball goes beyond the upper border, then bounce back
            ball.reverseY();
        }
        else if(ball.getPosition().getY() > area.getY() + area.getHeight()){   //if ball goes beyond the down border, ball lost
            ballCount--;
            ballLost = true;
        }
    }

    /**
     * This method is used to determine if the ball contact with which direction of the brick.
     * Different directions (UP, DOWN, LEFT, RIGHT) of the brick will lead to the different crack directions.
     *
     * @return true if the ball contact with the brick
     */
    private boolean impactWall(){
        for(Brick b : bricks){
            switch(b.findImpact(ball)) {   //get ball position
                //Vertical Impact
                case Brick.UP_IMPACT:    //if ball contact with the upper side of the brick
                    ball.reverseY();
                    return b.setImpact(ball.getDown(), Crack.UP);
                case Brick.DOWN_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.getUp(),Crack.DOWN);

                //Horizontal Impact
                case Brick.LEFT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.getRight(),Crack.LEFT);
                case Brick.RIGHT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.getLeft(),Crack.RIGHT);
            }
        }
        return false;
    }

    /**
     * This method is used to determine if the ball contact with the border of the game window.
     *
     * @return true is the ball has contact with the border of game window
     */
    private boolean impactBorder(){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    /**
     * This method is used to initialise the ball speed in the beginning of the game.
     * Instantiate RubberBall class.
     *
     * @param ballPos represents the ball position.
     */
    public void initialiseSpeed(Point2D ballPos){
        ball = new RubberBall(ballPos);
        int speedX = 4, speedY = -4;
        ball.setSpeed(speedX,speedY);

    }

    /**
     * This method is used to determine the ball and player ball location in the beginning of the game.
     * The ball speed is reset if the ball is lost.
     */
    public void ballReset(){
        player.moveTo(startPoint);
        ball.moveTo(startPoint);

        int speedX = 4, speedY = -4;
        ball.setSpeed(speedX,speedY);
        ballLost = false;
    }

    /**
     * This method is used to reset the wall in the original state after game over, next level and restarting the game.
     */
    public void wallReset(){
        for(Brick b : bricks)
            b.repair();
        brickCount = bricks.length;
        ballCount = 3;
    }


    /**
     * This method is used to get and display high score during the game.
     *
     * @return a string that display the player's name and high score
     */
    public String getHighScore(){
        FileReader readFile = null;
        BufferedReader reader = null;
        try {
            readFile = new FileReader("highscore.dat");
            reader = new BufferedReader(readFile);
            return reader.readLine();
        }
        catch (Exception e) {
            return "Mr Nobody:0";
        }
        finally {
            try{
                if(reader != null)
                    reader.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is used to check high score.
     * Compares old and current high score.
     * If player create new high score, pop up a panel to let player enters their name.
     */
    public void checkScore(){
        if (highScore.equals(""))
        {
            return;
        }
        if(getScore() > Integer.parseInt((getHighScore().split(":")[1]))){
            String name = JOptionPane.showInputDialog("You've create a new High score! What is your name?");
            highScore = name + ":" + getScore();
            System.out.println(highScore);
            File scoreFile = new File("highscore.dat");
            if(!scoreFile.exists())
            {
                try {
                    scoreFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                FileWriter writer = new FileWriter(scoreFile);
                writer.write(this.highScore);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * This method is used to determine if the number of ball lives depleted.
     *
     * @return true if the ball count equals to zero
     */
    public boolean ballEnd(){
        return ballCount == 0;
    }

    /**
     * This method is used to determine if the level is finished.
     *
     * @return true if there is no brick left in a level
     */
    public boolean isDone(){
        return brickCount == 0;
    }

    /**
     * This method is used to set next level.
     */
    public void nextLevel(){
        this.bricks = brick_level[tmp_level++];
        this.brickCount = bricks.length;
    }

    /**
     * This method is used to determine if there is another level.
     *
     * @return true if there is a next level
     */
    public boolean hasLevel(){
        return tmp_level < brick_level.length;
    }

    /**
     * This method is used to determine the ball speed in horizontal direction.
     *
     * @param s represents the horizontal speed value
     */
    public void setBallXSpeed(int s){
        ball.setXSpeed(s);
    }

    /**
     * This method is used to determine the ball speed in vertical direction.
     *
     * @param s represents the vertical speed value
     */
    public void setBallYSpeed(int s){
        ball.setYSpeed(s);
    }

    /**
     * This method is used to reset ball count if player press on Reset Balls button in debug panel.
     */
    public void resetBallCount(){
        ballCount = 3;
    }

    /**
     * This method is used to get brick count.
     *
     * @return number of brick count
     */
    public int getBrickCount(){
        return brickCount;
    }

    /**
     * This method is used to get ball count.
     *
     * @return number of ball count
     */
    public int getBallCount(){
        return ballCount;
    }

    /**
     * This method is used to determine if the ball is lost.
     *
     * @return true if the ball is lost
     */
    public boolean isBallLost(){
        return ballLost;
    }

    /**
     * This method is used to get bricks.
     *
     * @return array of brick
     */
    public Brick[] getBricks() {
        return bricks;
    }

    /**
     * This method is used to get ball.
     *
     * @return the ball
     */
    public Ball getBall() {
        return ball;
    }

    /**
     * This method is used to get player bar.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * This method is used to get score.
     * If users break a brick, they will be rewarded for one score.
     *
     * @return score value
     */
    public int getScore() {
        return score;
    }

    /**
     * This method is used to reset score to zero after game over.
     *
     * @return zero score
     */
    public int scoreReset(){
        score = 0;
        return score;
    }

    /**
     * This method is used to set the score.
     *
     * @param score represents the score value
     */
    public void setScore(int score) {
        this.score = score;
    }

}
