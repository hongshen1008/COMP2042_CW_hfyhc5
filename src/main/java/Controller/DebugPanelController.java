package Controller;

import Model.Wall;
import View.DebugPanel;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is DebugPanelController class. Perform action events and update to view class.
 *
 * @author Chin Hong Shen
 * @version 0.2
 * @since 24 November 2021
 */
public class DebugPanelController {

    private Wall wall;
    private DebugPanel debugPanel;

    /**
     * This is DebugPanelController class constructor. Initialise variables and call methods.
     *
     * @param wall represents wall
     * @param debugPanel represents DebugPanel
     */
    public DebugPanelController(Wall wall, DebugPanel debugPanel){
        this.wall = wall;
        this.debugPanel = debugPanel;

        this.debugPanel.skipLevelListener(new enableSkipLevelButton());
        this.debugPanel.resetBallsButtonListener(new enableResetBallsButton());
        this.debugPanel.makeBallXSpeed(new listenSliderSpeedX());
        this.debugPanel.makeBallYSpeed(new listenSliderSpeedY());
    }

    /**
     * This is enableSkipLevelButton class. Perform actions after user clicked on the Skip Level button.
     */
    class enableSkipLevelButton implements ActionListener {

        /**
         * This method is used to skip levels.
         *
         * @param actionEvent represents an action
         */
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            wall.nextLevel();
        }
    }

    /**
     * This is enableResetBallsButton class. Perform actions after user clicked on the Reset Ball button.
     */
    class enableResetBallsButton implements ActionListener {

        /**
         * This method is used to reset ball lives.
         *
         * @param actionEvent represents an action
         */
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            wall.resetBallCount();
        }
    }

    /**
     * This is listenSliderSpeedX class. Perform actions after user adjust the slider.
     */
    class listenSliderSpeedX implements ChangeListener{

        /**
         * This method is used to change ball speed in horizontal direction when user adjust the slider.
         *
         * @param listenerEvent listen for an event changes.
         */
        @Override
        public void stateChanged(ChangeEvent listenerEvent) {
            wall.setBallXSpeed(debugPanel.getBallXSpeed().getValue());
        }
    }

    /**
     * This is listenSliderSpeedY class. Perform actions after user adjust the slider.
     */
    class listenSliderSpeedY implements ChangeListener{

        /**
         * This method is used to change ball speed in vertical direction when user adjust the slider.
         *
         * @param listenerEvent listen for an event changes.
         */
        @Override
        public void stateChanged(ChangeEvent listenerEvent) {
            wall.setBallYSpeed(debugPanel.getBallYSpeed().getValue());
        }
    }
}
