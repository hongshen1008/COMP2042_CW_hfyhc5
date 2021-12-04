package Brick;

import java.awt.*;

public class Levels {

    private static final int LEVELS_COUNT = 5;
    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;
    private static final int TITANIUM = 4;

    Brick[][] makeLevels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio){

        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        tmp[0] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY);
        tmp[1] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,CEMENT);
        tmp[2] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,STEEL);
        tmp[3] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL,CEMENT);
        tmp[4] = makeLastLevel(drawArea,brickCount,lineCount,brickDimensionRatio,TITANIUM,CEMENT,CLAY);
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

    private Brick[] makeLastLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB, int typeC){
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
            //tmp[i] = (b && ((line ==0) || (line ==1))) ?  makeBrick(p,brickSize,typeA) : makeBrick(p,brickSize,typeB);

        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){   //this loop specifically for the last brick in 2nd line (line 1)
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,typeA);
        }
        return tmp;
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
        return out;
    }

}
