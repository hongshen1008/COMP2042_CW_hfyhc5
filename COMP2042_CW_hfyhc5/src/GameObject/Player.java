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
package GameObject;

import java.awt.*;


public class Player {


    public static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    public static final Color INNER_COLOR = Color.GREEN;

    private static final int DEF_MOVE_AMOUNT = 5;

    private Rectangle playerFace;   //the bar to bounce the ball
    private Point ballPoint;
    private int moveAmount;
    private int min;
    private int max;


    public Player(Point ballPoint,int width,int height,Rectangle container) {   //player constructor
        this.ballPoint = ballPoint;
        stop();                           //change movement to stop method
        this.playerFace = makeRectangle(width, height); //put this
        this.min = container.x + (width / 2);           //put this
        this.max = min + container.width - width;       //put this

    }

    private Rectangle makeRectangle(int width,int height){  //construct rectangle for player bar
        Point p = new Point((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());
        return  new Rectangle(p,new Dimension(width,height));   //point, dimension
    }

    public boolean impact(Ball b){
        //true if the ball is within the player bar && contact with the bar
        return playerFace.contains(b.getPosition()) && playerFace.contains(b.getDown()) ;
    }

    public void move(){
        double x = ballPoint.getX() + moveAmount;
        if(x < min || x > max)
            return;
        ballPoint.setLocation(x,ballPoint.getY());  //set location of the point of ball
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);  //set location of bar
    }

    public void moveTo(Point p){    //player go to center, reordered code structure
        ballPoint.setLocation(p);
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }

    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    public void movRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }

    public void stop(){
        moveAmount = 0;
    }

    public Shape getPlayerFace(){
        return playerFace;
    }
}
