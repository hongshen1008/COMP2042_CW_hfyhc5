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
import Controller.GameBoardController;
import Controller.HomeMenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;


/**
 * This is GameFrame class. Initialise the window for HomeMenu and GameBoard
 *
 * @author Chin Hong Shen
 * @version 0.2
 * @since 24 November 2021
 */
public class GameFrame extends JFrame implements WindowFocusListener {

    private static final String DEF_TITLE = "Brick Destroy";

    private GameBoard gameBoard;
    private HomeMenu homeMenu;
    private HomeMenuController homeMenuController;
    private GameBoardController gameBoardController;
    private Wall wall;

    private boolean gaming;

    /**
     * GameFrame constructor. Initialise variables, design GameFrame and instantiate GameBoard & HomeMenu class.
     */
    public GameFrame(){
        super();

        gaming = false;

        this.setLayout(new BorderLayout());

        gameBoard = new GameBoard(this);    //instantiation

        gameBoardController = new GameBoardController(wall, gameBoard);

        homeMenu = new HomeMenu(new Dimension(550,350));   //changed frame size
        homeMenuController = new HomeMenuController(this, homeMenu);

        this.add(homeMenu,BorderLayout.CENTER);
        this.setUndecorated(true);
        this.setResizable(false);

    }

    /**
     * This method is used to design Home Menu window.
     */
    public void initialize(){
        this.setTitle(DEF_TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.autoLocate();
        this.setVisible(true);
    }

    /**
     * This method is used to design GameBoard window.
     */
    public void enableGameBoard(){
        this.dispose();
        this.remove(homeMenu);
        this.add(gameBoard,BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
        /*to avoid problems with graphics focus controller is added here*/
        this.addWindowFocusListener(this);

    }

    /**
     * This method is used to locate the Game window in the center of the screen.
     */
    private void autoLocate(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (size.width - this.getWidth()) / 2;
        int y = (size.height - this.getHeight()) / 2;
        this.setLocation(x,y);
    }


    /**
     * This is built-in method to determine if the Game window is opened on screen.
     * The window is opened represents focus gained.
     *
     * @param windowEvent indicates that a window has changed its status
     */
    @Override
    public void windowGainedFocus(WindowEvent windowEvent) {
        /*
            the first time the frame loses focus is because
            it has been disposed to install the GameBoard,
            so went it regains the focus it's ready to play.
            of course calling a method such as 'onLostFocus'
            is useful only if the GameBoard as been displayed
            at least once
         */
        gaming = true;
    }

    /**
     * This is a built-in method to determine if the Game window is hidden from screen.
     * The window is hidden represents focus lost.
     *
     * @param windowEvent indicates that a window has changed its status
     */
    @Override
    public void windowLostFocus(WindowEvent windowEvent) {
        if(gaming)
            gameBoard.onLostFocus();

    }
}
