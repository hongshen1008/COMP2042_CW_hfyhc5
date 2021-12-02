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
import java.util.Random;


public class Wall {

    private static final int LEVELS_COUNT = 5;

    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;
    private static final int TITANIUM = 4;

    private Random rnd;
    private Rectangle area;

    private Brick[] bricks; //put access modifier and getter/setter method
    private Ball ball;      //put access modifier and getter/setter method
    private Player player;  //put access modifier and getter/setter method

    private Brick[][] levels;
    private int level;

    private Point startPoint;
    private int brickCount;
    private int ballCount;
    private int score;
    private String highScore = "";
    private boolean ballLost;

    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){

        this.startPoint = new Point(ballPos);

        levels = makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio); //brickCount = num of brick, line count = 3 rows of brick
        level = 0;
        setScore(0);
        ballCount = 3;
        ballLost = false;
        rnd = new Random();

        makeBall(ballPos);  //set ball position
        int speedX,speedY;
        do{
            speedX = rnd.nextInt(5) - 2;
        }while(speedX == 0);
        do{
            speedY = -rnd.nextInt(3);
        }while(speedY == 0);

        getBall().setSpeed(speedX,speedY);

        this.player = new Player((Point) ballPos.clone(),150,10, drawArea);     //instantiate player class

        if(highScore.equals(""))
        {
            highScore = this.getHighScore();
        }

        area = drawArea;


    }

    private Brick[][] makeLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        tmp[0] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY);
        tmp[1] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,CEMENT);
        tmp[2] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,STEEL);
        tmp[3] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL,CEMENT);
        tmp[4] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,TITANIUM,CEMENT);
        return tmp;
    }

    private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int type){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            double x = (i % brickOnLine) * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,type);
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = new ClayBrick(p,brickSize);
        }
        return tmp;

    }

    private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt; //set brickCnt to 30 because 31 - (31 % 3) = 1

        int brickOnLine = brickCnt / lineCnt;   //num of brick per line (changed to brickPerLine) ,30/3 = 10

        int centerLeft = brickOnLine / 2 - 1;   // (10/2) -1 = 4
        int centerRight = brickOnLine / 2 + 1;  // (10/2) +1 = 6

        double brickLen = drawArea.getWidth() / brickOnLine;    //length of each brick
        double brickHgt = brickLen / brickSizeRatio;    //height of the line

        brickCnt += lineCnt / 2;    //set brick count back to 31

        Brick[] tmp  = new Brick[brickCnt]; //array[31]

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){    //loop 31 times for all bricks
            int line = i / brickOnLine;     //total will be 3 lines (line 0, 1, 2)
            if(line == lineCnt)             //if 3 ==3
                break;                      //break for loop
            //set coordinate of brick
            int posX = i % brickOnLine;
            double x = posX * brickLen;
            //line 0, 2 will set the coordinate for brick normally
            //line 1 will start with half of the brick
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);
            //line 0 and line 2, pattern is the same
            //line 1 posX > 4 && posX <= 6
            //make type of brick(level) according to the condition
            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            tmp[i] = b ?  makeBrick(p,brickSize,typeA) : makeBrick(p,brickSize,typeB);
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){   //this loop specifically for the last brick in 2nd line (line 1)
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,typeA);
        }
        return tmp;
    }

    private void makeBall(Point2D ballPos){
        setBall(new RubberBall(ballPos));   // instantiate rubber ball class to set ball position
    }

    public void move(){
        getPlayer().move();
        getBall().move();
    }

    public void findImpacts(){
        if(getPlayer().impact(getBall())){  //if the ball contacts with player bar
            getBall().reverseY();
        }
        else if(impactWall()){
            /*for efficiency reverse is done into method impactWall
            * because for every brick program checks for horizontal and vertical impacts
            */
            brickCount--;
            score++;
        }
        else if(impactBorder()) {   //if the ball touch the window left and right border bounce back
            getBall().reverseX();
        }
        else if(getBall().getPosition().getY() < area.getY()){  //if the ball goes beyond the upper border, then bounce back
            getBall().reverseY();
        }
        else if(getBall().getPosition().getY() > area.getY() + area.getHeight()){   //if ball goes beyond the down border, ball lost
            ballCount--;
            ballLost = true;
        }
    }

    private boolean impactWall(){
        for(Brick b : getBricks()){
            switch(b.findImpact(getBall())) {   //get ball position
                //Vertical Impact
                case Brick.UP_IMPACT:    //if ball contact with the upper side of the brick
                    getBall().reverseY();
                    return b.setImpact(getBall().getDown(), Crack.UP);
                case Brick.DOWN_IMPACT:
                    getBall().reverseY();
                    return b.setImpact(getBall().getUp(),Crack.DOWN);

                //Horizontal Impact
                case Brick.LEFT_IMPACT:
                    getBall().reverseX();
                    return b.setImpact(getBall().getRight(),Crack.RIGHT);
                case Brick.RIGHT_IMPACT:
                    getBall().reverseX();
                    return b.setImpact(getBall().getLeft(),Crack.LEFT);
            }
        }
        return false;
    }

    private boolean impactBorder(){
        Point2D p = getBall().getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    public void ballReset(){
        getPlayer().moveTo(startPoint);
        getBall().moveTo(startPoint);
        int speedX,speedY;
        do{
            speedX = rnd.nextInt(5) - 2;
        }while(speedX == 0);
        do{
            speedY = -rnd.nextInt(3);
        }while(speedY == 0);

        getBall().setSpeed(speedX,speedY);
        ballLost = false;
    }

    public void wallReset(){
        for(Brick b : getBricks())
            b.repair();
        brickCount = getBricks().length;
        ballCount = 3;
    }

    private Brick makeBrick(Point point, Dimension size, int type){
        Brick out;
        switch(type){
            case CLAY:
                out = new ClayBrick(point,size);
                break;
            case STEEL:
                out = new SteelBrick(point,size);
                break;
            case CEMENT:
                out = new CementBrick(point, size);
                break;
            case TITANIUM:
                out = new TitaniumBrick(point, size);
                break;
            default:
                throw  new IllegalArgumentException(String.format("Unknown Type:%d\n",type));
        }
        return  out;
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
                /*FileWriter writerFile = null;
                BufferedWriter writer = null;
                try {
                    writerFile = new FileWriter(scoreFile);
                    writer = new BufferedWriter(writerFile);
                    writer.write(this.highScore);
                }

                catch (Exception e) {

                }
                finally {
                    try {
                        if (writer != null)
                            writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }*/
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
        setBricks(levels[level++]);
        this.brickCount = getBricks().length;
    }

    public boolean hasLevel(){
        return level < levels.length;
    }

    public void setBallXSpeed(int s){
        getBall().setXSpeed(s);
    }

    public void setBallYSpeed(int s){
        getBall().setYSpeed(s);
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

    public void setBricks(Brick[] bricks) {
        this.bricks = bricks;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public Player getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public int scoreReset(){
        score = 0;
        return score;
    }
}
