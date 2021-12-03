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

    /*private void makeBall(Point2D ballPos){
        this.ball = new RubberBall(ballPos);   // instantiate rubber ball class to set ball position
    }*/

    public void move(){
        player.move();
        ball.move();
    }

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
                    return b.setImpact(ball.getRight(),Crack.RIGHT);
                case Brick.RIGHT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.getLeft(),Crack.LEFT);
            }
        }
        return false;
    }

    private boolean impactBorder(){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    public void initialiseSpeed(Point2D ballPos){
        ball = new RubberBall(ballPos);
        int speedX = 3, speedY = -3;
        ball.setSpeed(speedX,speedY);

    }

    public void ballReset(){
        player.moveTo(startPoint);
        ball.moveTo(startPoint);

        int speedX = 2, speedY = -3;
        ball.setSpeed(speedX,speedY);
        ballLost = false;
    }

    public void wallReset(){
        for(Brick b : bricks)
            b.repair();
        brickCount = bricks.length;
        ballCount = 3;
    }


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

    public boolean ballEnd(){
        return ballCount == 0;
    }

    public boolean isDone(){
        return brickCount == 0;
    }

    public void nextLevel(){
        this.bricks = brick_level[tmp_level++];
        this.brickCount = bricks.length;
    }

    public boolean hasLevel(){
        return tmp_level < brick_level.length;
    }

    public void setBallXSpeed(int s){
        ball.setXSpeed(s);
    }

    public void setBallYSpeed(int s){
        ball.setYSpeed(s);
    }

    public void resetBallCount(){
        ballCount = 3;
    }

    public int getBrickCount(){
        return brickCount;
    }

    public int getBallCount(){
        return ballCount;
    }

    public boolean isBallLost(){
        return ballLost;
    }

    public Brick[] getBricks() {
        return bricks;
    }

    public Ball getBall() {
        return ball;
    }

    public Player getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    public int scoreReset(){
        score = 0;
        return score;
    }
}
