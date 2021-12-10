package Controller;

import View.GameFrame;
import View.GuideFrame;
import View.HomeMenu;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * This is HomeMenuController class. used to perform action events and update to view class.
 *
 * @author Chin Hong Shen
 * @version 0.2
 * @since 24 November 2021
 */
public class HomeMenuController {

    private GameFrame gameFrame; //model
    private HomeMenu homeMenu;  //view

    /**
     * This is HomeMenuController class constructor. Construct object and initialise variables.
     *
     * @param gameFrame represents GameFrame class
     * @param view represents HomeMenu class
     */
    public HomeMenuController(GameFrame gameFrame, HomeMenu view) {
        this.gameFrame = gameFrame;
        this.homeMenu = view;

        this.homeMenu.addMouseEvent(new addMouseEvt());
        this.homeMenu.AddMouseMotionListener(new addMouseEvt());
    }

    /**
     * This is addMouseEvt class. Used to listen for mouse events and perform actions.
     */
    class addMouseEvt implements MouseListener, MouseMotionListener{

        /**
         * This method is used to listen for any mouse clicked and perform specific actions.
         *
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
            Point p = mouseEvent.getPoint();
            if (homeMenu.getStartButton().contains(p)) {
                gameFrame.enableGameBoard();

            } else if (homeMenu.getExitButton().contains(p)) {
                System.out.println("Goodbye " + System.getProperty("user.name"));
                System.exit(0);
            } else if (homeMenu.getGuideButton().contains(p)) {
                new GuideFrame();
            }
        }

        /**
         * This method is used to listen for any mouse pressed and perform specific actions.
         *
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mousePressed(java.awt.event.MouseEvent mouseEvent) {
            Point p = mouseEvent.getPoint();
            if (homeMenu.getStartButton().contains(p)) {
                homeMenu.setStartClicked(true);
                homeMenu.Repaint(homeMenu.getStartButton());

            } else if (homeMenu.getExitButton().contains(p)) {
                homeMenu.setExitClicked(true);
                homeMenu.Repaint(homeMenu.getExitButton());
            } else if (homeMenu.getGuideButton().contains(p)) {
                homeMenu.setGuideClicked(true);
                homeMenu.Repaint(homeMenu.getGuideButton());
            }
        }

        /**
         * This method is used to listen for any mouse released and perform specific actions.
         *
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseReleased(java.awt.event.MouseEvent mouseEvent) {
            if (homeMenu.isStartClicked()) {
                homeMenu.setStartClicked(false);
                homeMenu.Repaint(homeMenu.getStartButton());
            } else if (homeMenu.isExitClicked()) {
                homeMenu.setExitClicked(false);
                homeMenu.Repaint(homeMenu.getExitButton());
            } else if (homeMenu.isGuideClicked()) {
                homeMenu.setGuideClicked(false);
                homeMenu.Repaint(homeMenu.getGuideButton());
            }
        }

        /**
         * This method is used to listen for any mouse entered and perform specific actions.
         * Do nothing in the program.
         *
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseEntered(java.awt.event.MouseEvent mouseEvent) {

        }

        /**
         * This method is used to listen for any mouse exited and perform specific actions.
         * Do nothing in the program.
         *
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseExited(java.awt.event.MouseEvent mouseEvent) {

        }

        /**
         * This method is used to listen for any mouse dragged and perform specific actions.
         * Do nothing in the program.
         *
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseDragged(java.awt.event.MouseEvent mouseEvent) {

        }

        /**
         * This method is used to listen for any mouse moved and perform specific actions.
         * When the mouse move to the buttons, the cursor shape will change to a hand cursor.
         *
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseMoved(java.awt.event.MouseEvent mouseEvent) {
            Point p = mouseEvent.getPoint();
            if(homeMenu.getStartButton().contains(p) || homeMenu.getExitButton().contains(p) || homeMenu.getGuideButton().contains(p))
                homeMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                homeMenu.setCursor(Cursor.getDefaultCursor());
        }
    }

}
