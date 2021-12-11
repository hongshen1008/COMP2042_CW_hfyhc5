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
package View;

import Controller.GameBoardController;
import Model.Brick;
import Model.Wall;
import Model.Ball;
import Model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;

/**
 * This is GameBoardView class.
 * Display game play and Pause Menu window.
 *
 * @author Chin Hong Shen
 * @version 0.2
 * @since 24 November 2021
 */
public class GameBoard extends JComponent {

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
    private GameBoardController gameBoardController;

    /**
     * This is GameBoard constructor. Perform instantiation and initialise variables.
     *
     * @param owner variable of JFrame, used to perform JFrame operations
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
        gameBoardController = new GameBoardController(wall, this);
        debugConsole = new DebugConsole(owner,wall,this);   //call debugConsole constructor

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
    }

    /**
     * This method is used to detect the actions during the game play.
     * Display messages on actions.
     */
    public void promptMessage(){    //moved out from constructor.
        gameTimer = new Timer(10,e ->{
            wall.move();
            wall.findImpacts();
            message = String.format("Bricks: %d | Balls: %d ",wall.getBrickCount(),wall.getBallCount());
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
     * @param g represents Graphics class used for drawing content
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
     * @param g2d represents Graphics2D class
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
     * This method is used to display actions perform when user pressed on any key.
     *
     * @param keyEvent represents keyboard event
     */
    public void AddKeyEvent(KeyListener keyEvent){
        this.addKeyListener(keyEvent);
    }

    /**
     * This method is used to display actions perform when user perform mouse clicked.
     *
     * @param mouseEvent represents mouse event
     */
    public void AddMouseListener(MouseListener mouseEvent){
        this.addMouseListener(mouseEvent);
    }

    /**
     * This method is used to display actions perform when user moving their mouse.
     *
     * @param mouseEvent listen for mouse motion event
     */
    public void AddMouseMotionListener(MouseMotionListener mouseEvent){
        this.addMouseMotionListener(mouseEvent);
    }


    /**
     * This method is used to prompt the message "Focus Lost" when the game lost focus.
     */
    public void onLostFocus(){
        gameTimer.stop();
        message = "Focus Lost";
        repaint();
    }


    /**
     * This method is used to check if showPauseMenu is true or false.
     *
     * @return true or false
     */
    public boolean isShowPauseMenu() {
        return showPauseMenu;
    }

    /**
     * This method is used to set showPauseMenu to true or false.
     *
     * @param showPauseMenu represents true or false
     */
    public void setShowPauseMenu(boolean showPauseMenu) {
        this.showPauseMenu = showPauseMenu;
    }

    /**
     * This method is used to get game timer.
     *
     * @return game timer
     */
    public Timer getGameTimer(){
        return gameTimer;
    }

    /**
     * This method is used to get Debug Console.
     *
     * @return debugConsole
     */
    public DebugConsole getDebugConsole(){
        return debugConsole;
    }

    /**
     * This method is used to set message.
     *
     * @param message represents message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * This method is used to get the reaction for Continue button.
     *
     * @return continueButtonRect
     */
    public Rectangle getContinueButtonRect() {
        return continueButtonRect;
    }

    /**
     * This method is used to get the reaction for Exit button.
     *
     * @return exitButtonRect
     */
    public Rectangle getExitButtonRect() {
        return exitButtonRect;
    }

    /**
     * This method is used to get the reaction for Restart button.
     *
     * @return restartButtonRect
     */
    public Rectangle getRestartButtonRect() {
        return restartButtonRect;
    }

    /**
     * This method is used to get the reaction for Guide button.
     *
     * @return guideButtonRect
     */
    public Rectangle getGuideButtonRect() {
        return guideButtonRect;
    }
}