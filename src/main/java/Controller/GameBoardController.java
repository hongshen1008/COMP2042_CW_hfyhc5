package Controller;

import Model.Wall;
import View.GameBoard;
import View.GuideFrame;

import java.awt.*;
import java.awt.event.*;

/**
 * This is GameBoardController class, used to perform action events and update to view class.
 *
 * @author Chin Hong Shen
 * @version 0.2
 * @since 24 November 2021
 */
public class GameBoardController {

    private GameBoard gameBoard;    //view
    private Wall wall;              //model

    /**
     * This is GameBoardController class constructor. Construct object and initialise variables.
     *
     * @param wall represents wall class
     * @param gameBoard represents gameBoardView class
     */
    public GameBoardController(Wall wall, GameBoard gameBoard){
        this.gameBoard = gameBoard;
        this.wall = wall;

        this.gameBoard.AddKeyEvent(new inputEvent());
        this.gameBoard.AddMouseListener(new inputEvent());
        this.gameBoard.AddMouseMotionListener(new inputEvent());

    }

    /**
     * This is inputEvent class. Listen for key and mouse events.
     */
    class inputEvent implements KeyListener, MouseListener, MouseMotionListener {

        /**
         * This is a built-in method used to listen for any key typed.
         * Do nothing in the program.
         *
         * @param keyEvent represents key event
         */
        @Override
        public void keyTyped(KeyEvent keyEvent) {

        }

        /**
         * This method is used to listen for any key pressed and perform specific actions.
         *
         * @param keyEvent represents key event
         */
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            switch (keyEvent.getKeyCode()) {
                case KeyEvent.VK_A:
                    wall.getPlayer().moveLeft();
                    break;
                case KeyEvent.VK_D:
                    wall.getPlayer().moveRight();
                    break;
                case KeyEvent.VK_ESCAPE:
                    gameBoard.setShowPauseMenu(true); //make it true
                    gameBoard.repaint();
                    gameBoard.getGameTimer().stop();
                    break;
                case KeyEvent.VK_SPACE:
                    if (!gameBoard.isShowPauseMenu())
                        if (gameBoard.getGameTimer().isRunning())
                            gameBoard.getGameTimer().stop();
                        else
                            gameBoard.getGameTimer().start();
                    break;
                case KeyEvent.VK_F1:
                    if (keyEvent.isAltDown() && keyEvent.isShiftDown())
                        gameBoard.getDebugConsole().setVisible(true);  //show debug console
                default:
                    wall.getPlayer().stop();
            }
        }

        /**
         * This method is used to listen for any key released and perform specific actions.
         *
         * @param keyEvent represents key event
         */
        @Override
        public void keyReleased(KeyEvent keyEvent) {
            wall.getPlayer().stop();
        }

        /**
         * This method is used to listen for any mouse clicked and perform specific actions.
         *
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            Point p = mouseEvent.getPoint();
            if(!gameBoard.isShowPauseMenu())
                return;
            if(gameBoard.getContinueButtonRect().contains(p)){
                gameBoard.setShowPauseMenu(false);
                gameBoard.repaint();
            }
            else if(gameBoard.getRestartButtonRect().contains(p)){
                gameBoard.setMessage("Restarting Game...");
                wall.ballReset();
                wall.wallReset();
                gameBoard.setShowPauseMenu(false);
                gameBoard.repaint();
            }
            else if(gameBoard.getGuideButtonRect().contains(p)){
                GuideFrame guideFrame = new GuideFrame();
            }
            else if(gameBoard.getExitButtonRect().contains(p)){
                System.exit(0);
            }
        }

        /**
         * This method is used to listen for any mouse pressed and perform specific actions.
         * Do nothing in the program.
         *
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        /**
         * This method is used to listen for any mouse released and perform specific actions.
         * Do nothing in the program.
         *
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        /**
         * This method is used to listen for any mouse entered and perform specific actions.
         * Do nothing in the program.
         *
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        /**
         * This method is used to listen for any mouse exited and perform specific actions.
         * Do nothing in the program.
         *
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }

        /**
         * This method is used to listen for any mouse dragged and perform specific actions.
         * Do nothing in the program.
         *
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseDragged(MouseEvent mouseEvent) {

        }

        /**
         * This method is used to listen for any mouse moved and perform specific actions.
         * When the mouse move to the buttons, the cursor shape will change to a hand cursor.
         *
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            Point p = mouseEvent.getPoint();
            if(gameBoard.getExitButtonRect() != null && gameBoard.isShowPauseMenu()) {   // change the cursor the hand
                if (gameBoard.getExitButtonRect().contains(p) || gameBoard.getContinueButtonRect().contains(p) || gameBoard.getRestartButtonRect().contains(p)
                        || gameBoard.getGuideButtonRect().contains(p))
                    gameBoard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                else
                    gameBoard.setCursor(Cursor.getDefaultCursor());
            }
            else{
                gameBoard.setCursor(Cursor.getDefaultCursor());
            }
        }
    }


}
