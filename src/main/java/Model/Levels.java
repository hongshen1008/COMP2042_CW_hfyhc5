package Model;

import java.awt.*;

/**
 * This is Levels class. Implements level operations, to determine the pattern of the brick in different levels.
 *
 * @author Chin Hong Shen
 * @version 0.2
 * @since 24 November 2021
 */
public class Levels {

    private static final int LEVELS_COUNT = 5;
    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;
    private static final int TITANIUM = 4;

    /**
     * This method is used to call methods for different levels.
     * There are five levels in the game.
     *
     * @param drawArea represents the area of the rectangle
     * @param brickCount represents number of bricks
     * @param lineCount  represents the layers of rows to form a wall
     * @param brickDimensionRatio represents the ratio of the height and width of a brick
     * @return level
     */
    Brick[][] makeLevels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio){

        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        tmp[0] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY);
        tmp[1] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,CEMENT);
        tmp[2] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,STEEL);
        tmp[3] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL,CEMENT);
        tmp[4] = makeLastLevel(drawArea,brickCount,lineCount,brickDimensionRatio,TITANIUM,CEMENT,CLAY);
        return tmp;
    }

    /**
     * This method is used to determine the first level's wall pattern.
     *
     * @param drawArea represents the area of the rectangle
     * @param brickCnt represents number of bricks
     * @param lineCnt  represents the layers of rows to form a wall
     * @param brickSizeRatio represents the ratio of the height and width of a brick
     * @param type represents type of bricks
     * @return level
     */
    private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int type){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickPerLine = brickCnt / lineCnt;

        double brickLen = drawArea.getWidth() / brickPerLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickPerLine;
            if(line == lineCnt)
                break;
            double x = (i % brickPerLine) * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,type);
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickPerLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = new ClayBrick(p,brickSize);
        }
        return tmp;

    }

    /**
     * This method is used to determine the second to fourth level's wall pattern.
     *
     * @param drawArea represents the area of the rectangle
     * @param brickCnt represents number of bricks
     * @param lineCnt  represents the layers of rows to form a wall
     * @param brickSizeRatio represents the ratio of the height and width of a brick
     * @param typeA represents type of bricks
     * @param typeB represents type of bricks
     * @return level
     */
    private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt; //set brickCnt to 30 because 31 - (31 % 3) = 1

        int brickPerLine = brickCnt / lineCnt;   //num of brick per line (changed to brickPerLine) ,30/3 = 10

        int centerLeft = brickPerLine / 2 - 1;   // (10/2) -1 = 4
        int centerRight = brickPerLine / 2 + 1;  // (10/2) +1 = 6

        double brickLen = drawArea.getWidth() / brickPerLine;    //length of each brick (600/10 = 60)
        double brickHgt = brickLen / brickSizeRatio;    //height of the line (60/3 = 20)

        brickCnt += lineCnt / 2;    //set brick count back to 31

        Brick[] tmp  = new Brick[brickCnt]; //array[31]

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){    //loop 31 times for all bricks
            int line = i / brickPerLine;     //total will be 3 lines (line 0, 1, 2)
            if(line == lineCnt)             //if 3 ==3
                break;                      //break for loop
            //set coordinate of brick
            int posX = i % brickPerLine;
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
            double x = (brickPerLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,typeA);
        }
        return tmp;
    }

    /**
     * This method is used to determine the last level's wall pattern.
     *
     * @param drawArea represents the area of the rectangle
     * @param brickCnt represents number of bricks
     * @param lineCnt  represents the layers of rows to form a wall
     * @param brickSizeRatio represents the ratio of the height and width of a brick
     * @param typeA represents type of bricks
     * @param typeB represents type of bricks
     * @param typeC represents type of bricks
     * @return level
     */
    private Brick[] makeLastLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB, int typeC){
        brickCnt -= brickCnt % lineCnt; //set brickCnt to 30 because 31 - (31 % 3) = 1

        int brickPerLine = brickCnt / lineCnt;   //num of brick per line (changed to brickPerLine) ,30/3 = 10

        int centerLeft = brickPerLine / 2 - 1;   // (10/2) -1 = 4
        int centerRight = brickPerLine / 2 + 1;  // (10/2) +1 = 6

        double brickLen = drawArea.getWidth() / brickPerLine;    //length of each brick
        double brickHgt = brickLen / brickSizeRatio;    //height of the line

        brickCnt += lineCnt / 2;    //set brick count back to 31

        Brick[] tmp  = new Brick[brickCnt]; //array[31]

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){    //loop 31 times for all bricks
            int line = i / brickPerLine;     //total will be 3 lines (line 0, 1, 2)
            if(line == lineCnt)             //if 3 ==3
                break;                      //break for loop
            //set coordinate of brick
            int posX = i % brickPerLine;
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
            if (b && ((line ==0) || (line ==1)))
            {
                tmp[i] = makeBrick(p,brickSize,typeA);
            }
            else if (!b && ((line == 0) || (line == 1)))
            {
                tmp[i] = makeBrick(p, brickSize, typeB);
            }
            else if(b && (line == 2))
            {
                tmp[i] = makeBrick(p,brickSize,typeC);
            }
            else if(!b && line == 2)
            {
                tmp[i] = makeBrick(p,brickSize,typeB);
            }

        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){   //this loop specifically for the last brick in 2nd line (line 1)
            double x = (brickPerLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,typeA);
        }
        return tmp;
    }

    /**
     * This method is used to make types of bricks.
     * Instantiate different brick types class.
     *
     * @param point represents location of the bricks
     * @param size represents dimension of the bricks
     * @param type represents the types of bricks
     * @return type of brick to Brick class
     */
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
                throw new IllegalArgumentException(String.format("Unknown Type:%d\n",type));
        }
        return out;
    }

}
