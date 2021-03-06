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

import Model.Wall;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;


/**
 * This is DebugPanelView class. Inherits JPanel properties.
 * Display game controls such as adjust ball speed, skip levels and reset ball lives.
 *
 * @author Chin Hong Shen
 * @version 0.2
 * @since 24 November 2021
 */
public class DebugPanel extends JPanel {      //windows to control level and speed

    private static final Color DEF_BKG = Color.WHITE;


    private JButton skipLevel;
    private JButton resetBalls;
    private JSlider ballXSpeed;
    private JSlider ballYSpeed;

    private Wall wall;

    /**
     * This is DebugPanel class constructor. Construct an DebugPanel object.
     *
     * @param wall represents wall class
     */
    public DebugPanel(Wall wall){

        this.wall = wall;

        initialize();

        skipLevel = new JButton("Skip Level");
        resetBalls = new JButton("Reset Balls");

        ballXSpeed = makeSlider(2,6);   //changed to (2 - 6)
        ballYSpeed = makeSlider(2,6);   //changed to (2 - 6)

        this.add(skipLevel);
        this.add(resetBalls);

        this.add(ballXSpeed);
        this.add(ballYSpeed);

    }

    /**
     * This method is used to implement the characteristics of the DebugPanel.
     */
    private void initialize(){
        this.setBackground(DEF_BKG);
        this.setLayout(new GridLayout(2,2));
    }

    /**
     * This method is used to display actions after user clicked on the Skip Level button.
     * Game level will be skipped for this action.
     *
     * @param actionEvent represents action events
     */
    public void skipLevelListener(ActionListener actionEvent){
        skipLevel.addActionListener(actionEvent);
    }

    /**
     * This method is used to display actions after user clicked on the Reset Balls button.
     * The ball lives will be reset for this action.
     *
     * @param actionEvent represents action events
     */
    public void resetBallsButtonListener(ActionListener actionEvent){
        resetBalls.addActionListener(actionEvent);
    }

    /**
     * This method is used to display changes for ball speed in horizontal direction when user adjust the slider.
     *
     * @param sliderSpeedX represents ball speed in horizontal direction
     */
    public void makeBallXSpeed(ChangeListener sliderSpeedX){
        ballXSpeed.addChangeListener(sliderSpeedX);
    }

    /**
     * This method is used to display changes for ball speed in vertical direction when user adjust the slider.
     *
     * @param sliderSpeedY represents ball speed in vertical direction
     */
    public void makeBallYSpeed(ChangeListener sliderSpeedY){
        ballYSpeed.addChangeListener(sliderSpeedY);
    }

    /**
     * This method is used to create a slider.
     *
     * @param min represents the minimum value on the slider
     * @param max represents the maximum value on the slider
     * @return    return a slider with minimum, maximum value and ready to perform ChangeEvents.
     */
    private JSlider makeSlider(int min, int max){
        JSlider out = new JSlider(min,max);
        out.setMajorTickSpacing(1);
        out.setSnapToTicks(true);
        out.setPaintTicks(true);
        return out;
    }


    /**
     * This method is used to set value for ball speed.
     *
     * @param x represents speed in x-coordinate
     * @param y represents speed in y-coordinate
     */
    public void setValues(int x,int y){
        ballXSpeed.setValue(x);
        ballYSpeed.setValue(y);
    }

    /**
     * This method is used to get ball speed in horizontal direction.
     *
     * @return ball speed in horizontal direction
     */
    public JSlider getBallXSpeed() {
        return ballXSpeed;
    }

    /**
     * This method is used to get ball speed in vertical direction.
     *
     * @return ball speed in vertical direction
     */
    public JSlider getBallYSpeed() {
        return ballYSpeed;
    }

}
