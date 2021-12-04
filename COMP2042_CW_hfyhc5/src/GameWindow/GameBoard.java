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
package GameWindow;

import GameObject.Ball;
import Brick.Brick;
import DebugWindow.DebugConsole;
import Brick.Wall;
import GameObject.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;

/**
 * This is GameBoard class, used to start game and display Pause Menu.
 *
 * @author Chin Hong Shen
 * @version 0.2
 * @since 24 November 2021
 */
public class GameBoard extends JComponent implements KeyListener,MouseListener,MouseMotionListener {

    private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";
    private static final String PAUSE = "Pause Menu";
    private static final String GUIDE = "Guide";    //add guide
    private static final int TEXT_SIZE = 30;
    private static final Color MENU_COLOR = new Color(0,255,0);
    private static final int DEF_WIDTH = 600;   //frame width
    private static final int DEF_HEIGHT = 450;  //frame height
    private static final Color BG_COLOR = Color.WHITE;  //background color

    private GuideFrame guideFrame;

    private Timer gameTimer;

    private Wall wall;

    private String message;
    private String scoreMessage;
    private String highScore;
    private boolean showPauseMenu;

    private Font menuFont;
    private Font messageFont;

    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;
    private Rectangle guideButtonRect;  //added guide button
    private int strLen;

    private DebugConsole debugConsole;


    /**
     * This is GameBoard constructor. Perform instantiation and initialise variables.
     *
     * @param owner variable of JFrame, used to perform JFrame operations.
     */
    public GameBoard(JFrame owner){
        super();

        strLen = 0;
        showPauseMenu = false;

        menuFont = new Font("Monospaced",Font.PLAIN,TEXT_SIZE);
        messageFont = new Font("MONOSPACED",Font.BOLD,13); //added message font

        this.initialize();
        message = "";
        scoreMessage = "";
        highScore = "";
        wall = new Wall(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),30,3,6/2,new Point(300,430));   //call wall constructor

        debugConsole = new DebugConsole(owner,wall,this);   //call debugConsole constructor
        //initialize the first level
        wall.nextLevel();

        promptMessage();

    }


    /**
     * This method is used to set Window's characteristic.
     */
    private void initialize(){
        this.setPreferredSize(new Dimension(DEF_WIDTH,DEF_HEIGHT)); //set window size
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    /**
     *This method is used to detect the actions during the game play.
     * Display messages on actions.
     */
    public void promptMessage(){    //moved out from constructor.
        gameTimer = new Timer(10,e ->{
            wall.move();
            wall.findImpacts();
            message = String.format("Bricks: %d Balls %d ",wall.getBrickCount(),wall.getBallCount());
            scoreMessage = String.format("Score: %d", wall.getScore());
            highScore = String.format("High Score: " + wall.getHighScore());
            if(wall.isBallLost()){
                if(wall.getScore()>=3) {
                    wall.setScore(wall.getScore()-3);
                }
                else{
                    wall.setScore(0);
                }
                if(wall.ballEnd()){
                    wall.checkScore();
                    wall.wallReset();
                    wall.scoreReset();
                    message = "Game over";
                }
                wall.ballReset();
                gameTimer.stop();
            }
            else if(wall.isDone()){
                if(wall.getBallCount()==3)
                {
                    wall.setScore(wall.getScore()+5);
                }
                if(wall.hasLevel()){
                    message = "Go to Next Level";
                    gameTimer.stop();
                    wall.ballReset();
                    wall.wallReset();
                    wall.nextLevel();
                }
                else{
                    message = "CONGRATULATIONS! ALL WALLS DESTROYED";   //add congratulations
                    wall.checkScore();
                    gameTimer.stop();
                    repaint();
                }
            }

            repaint();
        });
    }


    /**
     * This method is used to deign the game objects and Pause menu.
     *
     * @param g variable of Graphics class used for drawing content.
     */
    public void paint(Graphics g){  //repaint

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);
        g2d.setFont(messageFont);
        g2d.setColor(Color.BLUE);
        g2d.drawString(message,5,100);
        g2d.drawString(scoreMessage,5,115);
        g2d.drawString(highScore, 5, 130);

        drawBall(wall.getBall(),g2d);

        for(Brick b : wall.getBricks())
            if(!b.isBroken())
                drawBrick(b,g2d);

        drawPlayer(wall.getPlayer(),g2d);

        if(showPauseMenu)
            drawMenu(g2d);

        Toolkit.getDefaultToolkit().sync(); //synchronise
    }

    /**
     * This method is used to clear the background and set background to white color.
     *
     * @param g2d variable of Graphics2D class to perform operations of Graphics2D class.
     */
    private void clear(Graphics2D g2d){
        Color tmp = g2d.getColor();
        g2d.setColor(BG_COLOR);
        g2d.fillRect(0,0,getWidth(),getHeight());
        g2d.setColor(tmp);
    }

    /**
     * This method is used to design the brick.
     *
     * @param brick represents brick class object
     * @param g2d represents Graphics2D class to perform operations of Graphics2D class
     */
    private void drawBrick(Brick brick,Graphics2D g2d){
        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrickFace());
        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrickFace());

        g2d.setColor(tmp);
    }

    /**
     * This method is used to design the ball.
     *
     * @param ball represents Ball class
     * @param g2d  represents Graphics2D class
     */
    private void drawBall(Ball ball, Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();   //get the shape of ball

        g2d.setColor(ball.getInnerColor()); //get color of the ball
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());    //get border color of the ball
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    /**
     * This method is used to design player bar.
     *
     * @param p represents Player class
     * @param g2d represents Graphics2D class
     */
    private void drawPlayer(Player p, Graphics2D g2d){  //the bar to bounce the ball
        Color tmp = g2d.getColor();

        Shape s = p.getPlayerFace();
        g2d.setColor(Player.INNER_COLOR);
        g2d.fill(s);

        g2d.setColor(Player.BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    /**
     * This method calls methods to draw Pause Menu.
     *
     * @param g2d represents Graphics2D class
     */
    private void drawMenu(Graphics2D g2d){  //draw pause menu
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
    }

    /**
     * This method is used to hide GameBoard window when opened Pause Menu window.
     * The GameBoard window will become translucent when the Pause Menu is opened.
     *
     * @param g2d represents Graphics2D class
     */
    private void obscureGameBoard(Graphics2D g2d){

        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.55f);  //achieve transparency
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,DEF_WIDTH,DEF_HEIGHT);

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    /**
     * This method is used to set contents for PauseMenu window.
     * Display Pause Menu title, locate Continue, Restart, Guide and Exit Button.
     *
     * @param g2d represents Graphics2D class
     */
    private void drawPauseMenu(Graphics2D g2d){
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();


        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        if(strLen == 0){
            FontRenderContext frc = g2d.getFontRenderContext();     //display the PAUSE MENU title
            strLen = menuFont.getStringBounds(PAUSE,frc).getBounds().width;
        }

        int x = (this.getWidth() - strLen) / 2;
        int y = this.getHeight() / 10;

        g2d.drawString(PAUSE,x,y);

        x = this.getWidth() / 8;
        y = this.getHeight() / 5;


        if(continueButtonRect == null){     //display the CONTINUE button
            FontRenderContext frc = g2d.getFontRenderContext();
            continueButtonRect = menuFont.getStringBounds(CONTINUE,frc).getBounds();
            continueButtonRect.setLocation(x,y-continueButtonRect.height);
        }

        g2d.drawString(CONTINUE,x,y);

        y += 100; //new line

        //display the RESTART button
        if(restartButtonRect == null){
            restartButtonRect = (Rectangle) continueButtonRect.clone();
            restartButtonRect.setLocation(x,y-restartButtonRect.height);
        }

        g2d.drawString(RESTART,x,y);

        y += 100;

        //display GUIDE button
        if(guideButtonRect == null){
            guideButtonRect = (Rectangle) continueButtonRect.clone();
            guideButtonRect.setLocation(x,y-guideButtonRect.height);
        }

        g2d.drawString(GUIDE,x,y);

        y += 100;

        //display EXIT button
        if(exitButtonRect == null){
            exitButtonRect = (Rectangle) continueButtonRect.clone();
            exitButtonRect.setLocation(x,y-exitButtonRect.height);
        }

        g2d.drawString(EXIT,x,y);

        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }

    /**
     * This is a built-in method used to detect any key typed in Pause Menu window.
     * Do Nothing in the program.
     *
     * @param keyEvent represent KeyEvent class
     */
    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    /**
     * This method is used to detect any key pressed during the game.
     * Press A key to move left. Press D key to move right. Press escape key to open Pause Menu.
     * Press Space bar to pause the game. Press Alt-Shift-F1 key to open debug Console.
     *
     * @param keyEvent represent KeyEvent class
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {     //A & D in keyboard
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_A:
                wall.getPlayer().moveLeft();
                break;
            case KeyEvent.VK_D:
                wall.getPlayer().movRight();
                break;
            case KeyEvent.VK_ESCAPE:
                showPauseMenu = !showPauseMenu; //make it true
                repaint();
                gameTimer.stop();
                break;
            case KeyEvent.VK_SPACE:
                if(!showPauseMenu)
                    if(gameTimer.isRunning())
                        gameTimer.stop();
                    else
                        gameTimer.start();
                break;
            case KeyEvent.VK_F1:
                if(keyEvent.isAltDown() && keyEvent.isShiftDown())
                    debugConsole.setVisible(true);  //show debug console
            default:
                wall.getPlayer().stop();
        }
    }

    /**
     * This method is used to detect key released in the game.
     * If A & D key is released, stop the player bar.
     *
     * @param keyEvent represent KeyEvent class
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        wall.getPlayer().stop();
    }   //when release A & D the bar stop

    /**
     * This is a built-in method used to detect mouse clicked in Pause Menu window.
     * If continue button is clicked, go back to game.
     * If restart button is clicked, prompts a message and restart the game.
     * If guide button is clicked, open Guide window.
     * If exit button is clicked, stop the game and close the window.
     *
     * @param mouseEvent represents MouseEvent class to detect mouse action
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(!showPauseMenu)
            return;
        if(continueButtonRect.contains(p)){
            showPauseMenu = false;
            repaint();
        }
        else if(restartButtonRect.contains(p)){
            message = "Restarting Game...";
            wall.ballReset();
            wall.wallReset();
            showPauseMenu = false;
            repaint();
        }
        else if(guideButtonRect.contains(p)){
            guideFrame = new GuideFrame();
        }
        else if(exitButtonRect.contains(p)){
            System.exit(0);
        }


    }

    /**
     * This is a built-in method used to detect mouse pressed in Pause Menu window.
     * Do nothing in Pause Menu window.
     *
     * @param mouseEvent represents MouseEvent class to detect mouse action
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    /**
     * This is a built-in method is used to detect mouse released in Pause Menu window.
     * Do nothing in Pause Menu.
     *
     * @param mouseEvent represents MouseEvent class to detect mouse action
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    /**
     * This is a built-in method used to detect mouse entered in Pause Menu window.
     * Do Nothing in Pause Menu.
     *
     * @param mouseEvent represents MouseEvent class to detect mouse action.
     */
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    /**
     * This is a built-in method used to detect mouse Exited in Pause Menu window.
     * Do Nothing in Pause Menu window.
     *
     * @param mouseEvent represents MouseEvent class to detect mouse action.
     */
    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    /**
     * This is a built-in method used to detect mouse dragged in Pause Menu window.
     * Do Nothing in Pause Menu window.
     *
     * @param mouseEvent represents MouseEvent class to detect mouse action.
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    /**
     * This is a built-in method used to detect mouse moved in Pause Menu window.
     * If the mouse touches the button, the default arrow cursor become the hand cursor.
     *
     * @param mouseEvent represents MouseEvent class to detect mouse action.
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(exitButtonRect != null && showPauseMenu) {   // change the cursor the hand
            if (exitButtonRect.contains(p) || continueButtonRect.contains(p) || restartButtonRect.contains(p) || guideButtonRect.contains(p))
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                this.setCursor(Cursor.getDefaultCursor());
        }
        else{
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * This method is used to prompt the message "Focus Lost" when the game lost focus.
     *
     */
    public void onLostFocus(){
        gameTimer.stop();
        message = "Focus Lost";
        repaint();
    }

}