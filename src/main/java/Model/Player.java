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
package Model;

import java.awt.*;


/**
 * This is Player class. Create player bar and implement operations of player bar.
 *
 * @author Chin Hong Shen
 * @version 0.2
 * @since 24 November 2021
 */
public class Player {


    public static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    public static final Color INNER_COLOR = Color.GREEN;

    private static final int DEF_MOVE_AMOUNT = 5;

    private Rectangle playerFace;   //the bar to bounce the ball
    private Point ballPoint;
    private int moveAmount;
    private int min;
    private int max;


    /**
     * This is Player class constructor. Initialize variables.
     *
     * @param ballPoint represents the location of the ball
     * @param width     represents the width of player bar
     * @param height    represents the height of player bar
     * @param container represents the area of the frame
     */
    public Player(Point ballPoint,int width,int height,Rectangle container) {   //player constructor (300,430), 150, 10, (0,0,600,450)
        this.ballPoint = ballPoint;
        stop();                           //change movement to stop method
        this.playerFace = makeRectangle(width, height); //put this
        this.min = container.x + (width / 2);           //put this
        this.max = min + container.width - width;       //put this

    }

    /**
     * This method is used to create rectangular player bar.
     *
     * @param width represents the width of player bar
     * @param height represents the heigth of player bar.
     * @return  return (x,y) coordinate space, width and height to form rectangle shape
     */
    private Rectangle makeRectangle(int width,int height){  //construct rectangle for player bar
        Point p = new Point((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY()); //(225,430)
        return  new Rectangle(p,new Dimension(width,height));   //point(225, 430), dimension (150, 10)
    }

    /**
     * This method is used to determine whether the ball has contact with the player bar.
     *
     * @param b represents ball class.
     * @return  return true if the ball has contact with player bar
     */
    public boolean impact(Ball b){
        //true if the ball is within the player bar && contact with the bar
        return playerFace.contains(b.getPosition()) && playerFace.contains(b.getDown()) ;
    }

    /**
     * This method is used to determine the movement of player bar.
     */
    public void move(){
        double x = ballPoint.getX() + moveAmount;
        if(x < min || x > max)
            return;
        ballPoint.setLocation(x,ballPoint.getY());  //set location of the point of ball
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);  //set location of bar
    }

    /**
     * This method is used to set the player bar to center position in the beginning of the game.
     *
     * @param p represents the start point of the player bar.
     */
    public void moveTo(Point p){    //player go to center, reordered code structure
        ballPoint.setLocation(p);
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }

    /**
     * This method is used to set the move amount of the player bar.
     * Move player bar to the left.
     */
    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    /**
     * This method is used to set the move amount of the player bar.
     * Move player bar to the right.
     */
    public void moveRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }

    /**
     * This method represents the move amount when the player bar stopped.
     * The move amount is 0 when there is no movement.
     */
    public void stop(){
        moveAmount = 0;
    }

    /**
     * This method is used to get playerFace variable which is the player bar.
     *
     * @return outline of the player bar's shape
     */
    public Shape getPlayerFace(){
        return playerFace;
    }

    public int getMoveAmount(){
        return moveAmount;
    }

    public void setMoveAmount(int moveAmount) {
        this.moveAmount = moveAmount;
    }
}
