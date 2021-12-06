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
package DebugWindow;

import GameObject.Ball;
import GameWindow.GameBoard;
import Brick.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * This is DebugConsole class. Open a dialog window.
 *
 * @author Chin Hong Shen
 * @version 0.2
 * @since 24 November 2021
 */
public class DebugConsole extends JDialog implements WindowListener{

    private static final String TITLE = "Debug Console";
    private JFrame owner;
    private DebugPanel debugPanel;
    private GameBoard gameBoard;
    private Wall wall;


    /**
     * This is DebugConsole class constructor. Initialize variables and call methods.
     *
     * @param owner     represents JFrame class
     * @param wall      represents Wall class
     * @param gameBoard represents gameBoard class
     */
    public DebugConsole(JFrame owner,Wall wall,GameBoard gameBoard){

        this.wall = wall;
        this.owner = owner;
        this.gameBoard = gameBoard;
        initialize();

        debugPanel = new DebugPanel(wall);
        this.add(debugPanel,BorderLayout.CENTER);


        this.pack();
    }

    /**
     * This method is used to set frame characteristics.
     */
    private void initialize(){
        this.setModal(true);
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.addWindowListener(this);
        this.setFocusable(true);
    }


    /**
     * This method is used to set the location of DebugConsole window when it is opened.
     * The window will be displayed in the center of the game frame.
     */
    private void setLocation(){
        int x = ((owner.getWidth() - this.getWidth()) / 2) + owner.getX();
        int y = ((owner.getHeight() - this.getHeight()) / 2) + owner.getY();
        this.setLocation(x,y);
    }


    /**
     * This is a built-in method used to determine whether the window is opened.
     * Do nothing in this program.
     *
     * @param windowEvent indicates that a window has changed its status
     */
    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    /**
     * This is a built-in method used to determine if the DebugConsole window is closing.
     * The window is repainted if the debugConsole window is closing.
     *
     * @param windowEvent indicates that a window has changed its status
     */
    @Override
    public void windowClosing(WindowEvent windowEvent) {
        gameBoard.repaint();
    }

    /**
     * This is a built-in method used to determine if a window is closed.
     * Do nothing in this program.
     *
     * @param windowEvent indicates that a window has changed its status
     */
    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    /**
     * This is a built-in method.
     * The window iconified event.
     * This event is delivered when the window has been changed from a normal to a minimized state.
     * For many platforms, a minimized window is displayed as the icon specified in the window's iconImage property.
     * Do nothing in this program.
     *
     * @param windowEvent indicates that a window has changed its status
     */
    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    /**
     * This is a built-in method.
     * The window deiconified event type.
     * This event is delivered when the window has been changed from a minimized to a normal state.
     * Do nothing in this program.
     *
     * @param windowEvent indicates that a window has changed its status
     */
    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    /**
     * This is a built-in method.
     * The window-activated event type.
     * This event is delivered when the Window becomes the active Window.
     *
     * @param windowEvent indicates that a window has changed its status
     */
    @Override
    public void windowActivated(WindowEvent windowEvent) {
        setLocation();
        Ball b = wall.getBall();
        debugPanel.setValues(b.getSpeedX(),b.getSpeedY());
    }

    /**
     * This is a built-in method.
     * The window-deactivated event type.
     * This event is delivered when the Window is no longer the active Window.
     * Do nothing in this program.
     *
     * @param windowEvent indicates that a window has changed its status
     */
    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }
}
