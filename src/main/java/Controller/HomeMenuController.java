package Controller;

import View.GameFrame;
import View.GuideFrame;
import View.HomeMenu;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class HomeMenuController {

    private GameFrame gameFrame;        //model
    private HomeMenu homeMenu;  //view

    public HomeMenuController(GameFrame gameFrame, HomeMenu view) {
        this.gameFrame = gameFrame;
        this.homeMenu = view;

        this.homeMenu.addMouseEvent(new addMouseEvt());
        this.homeMenu.AddMouseMotionListener(new addMouseEvt());
    }

    class addMouseEvt implements MouseListener, MouseMotionListener{

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

        @Override
        public void mouseEntered(java.awt.event.MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent mouseEvent) {

        }

        @Override
        public void mouseDragged(java.awt.event.MouseEvent mouseEvent) {

        }

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
