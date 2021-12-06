package Brick;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * This is Crack class. Draw crack and perform crack operations.
 *
 * @author Chin Hong Shen
 * @version 0.2
 * @since 24 November 2021
 */
public class Crack{

        public static final int LEFT = 10;
        public static final int RIGHT = 20;
        public static final int UP = 30;
        public static final int DOWN = 40;
        public static final int VERTICAL = 100;
        public static final int HORIZONTAL = 200;
        private static Random rnd;
        private GeneralPath crack;

        private int crackDepth;
        private int steps;
        private Brick brick;

    /**
     * This is Crack class constructor. Create a crack object.
     * Initialises variables and perform instantiation.
     *
     * @param brick represents brick class
     * @param crackDepth the value of crack depth
     * @param steps represents steps taken to break all the bricks
     */
        public Crack(Brick brick, int crackDepth, int steps){    //constructor, crackDepth = 1, steps = 35

            crack = new GeneralPath();
            rnd = new Random();
            this.crackDepth = crackDepth;
            this.steps = steps;
            this.brick = brick;

        }

    /**
     * This method is used to find the starting and ending point of the crack.
     * The ball contact with the wall in different directions will cause the crack to have different directions.
     *
     * @param point defines a point representing a location in (x,y) coordinate space
     * @param direction represents the direction of the brick crack
     * @see #makeRandomPoint(Point, Point, int)
     */
        protected void makeCrack(Point2D point, int direction){     //determine start and end point of the crack
            Rectangle bounds = brick.getBrickFace().getBounds();

            Point impact = new Point((int)point.getX(),(int)point.getY());
            Point start = new Point();
            Point end = new Point();


            switch(direction){
                case LEFT:
                    start.setLocation(bounds.x + bounds.width, bounds.y);
                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                    Point tmp = makeRandomPoint(start,end,VERTICAL);
                    makeCrack(impact,tmp);

                    break;
                case RIGHT:
                    start.setLocation(bounds.getLocation());
                    end.setLocation(bounds.x, bounds.y + bounds.height);
                    tmp = makeRandomPoint(start,end,VERTICAL);
                    makeCrack(impact,tmp);

                    break;
                case UP:
                    start.setLocation(bounds.x, bounds.y + bounds.height);
                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                    tmp = makeRandomPoint(start,end,HORIZONTAL);
                    makeCrack(impact,tmp);

                    break;
                case DOWN:
                    start.setLocation(bounds.getLocation());
                    end.setLocation(bounds.x + bounds.width, bounds.y);
                    tmp = makeRandomPoint(start,end,HORIZONTAL);
                    makeCrack(impact,tmp);

                    break;
            }
        }

    /**
     * This method is used to draw the crack.
     *
     * @param start represents starting point of the crack
     * @param end represents the ending point of the crack
     */
        protected void makeCrack(Point start, Point end){    //draw the crack

            GeneralPath path = new GeneralPath();


            path.moveTo(start.x,start.y);

            double w = (end.x - start.x) / (double)steps;
            double h = (end.y - start.y) / (double)steps;

            int bound = crackDepth; //bound =1

            double x,y;

            for(int i = 1; i < steps;i++){

                x = (i * w) + start.x;
                y = (i * h) + start.y + randomInBounds(bound);  //bound = 1

                path.lineTo(x,y);

            }

            path.lineTo(end.x,end.y);
            crack.append(path,true);
        }

    /**
     * This method is used to set random value to change y-coordinate of the crack.
     *
     * @param bound a value contributes to make the random value
     * @return a random value
     */
        private int randomInBounds(int bound){  //bound == 1
            int n = (bound * 2) + 1;
            return rnd.nextInt(n) - bound;
        }

    /**
     * This method is used to make random starting and ending point of the crack.
     *
     * @param from represents the starting point of the crack
     * @param to   represents the ending point of the crack
     * @param direction represents horizontal or vertical direction
     * @return a crack point with (x,y) coordinate
     */
        private Point makeRandomPoint(Point from,Point to, int direction){

            Point out = new Point();
            int pos;

            switch(direction){
                case HORIZONTAL:
                    pos = rnd.nextInt(to.x - from.x) + from.x;
                    out.setLocation(pos,to.y);
                    break;
                case VERTICAL:
                    pos = rnd.nextInt(to.y - from.y) + from.y;
                    out.setLocation(to.x,pos);
                    break;
            }
            return out;
        }

    /**
     * This method is used to display the crack on the game window
     *
     * @return a geometric path constructed from the crack
     */
        public GeneralPath draw(){
            return crack;
        }

    /**
     * This is to recover the brick from crack.
     */
        public void reset(){
            crack.reset();
        }
}
