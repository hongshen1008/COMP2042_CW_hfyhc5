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


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;


/**
 * This is HomeMenu class. Design and locate content for HomeMenu window.
 *
 * @author Chin Hong Shen
 * @version 0.2
 * @since 24 November 2021
 */
public class HomeMenu extends JComponent {

    private static final String GREETINGS = "Welcome to:";
    private static final String GAME_TITLE = "Brick Destroy";
    private static final String CREDITS = "Version 0.2";
    private static final String START_TEXT = "Start";
    private static final String EXIT_TEXT = "Exit";     //changed to exit_text
    private static final String GUIDE_TEXT = "Guide";     //added guide button

    private static final Color TEXT_COLOR = new Color(0, 0, 0);//changed to black
    private static final Color CLICKED_BUTTON_COLOR = Color.BLUE.darker();
    private static final Color CLICKED_TEXT = Color.BLUE.darker();
    private static final int BORDER_SIZE = 5;
    private static final float[] DASHES = {12,6};

    private Rectangle menuFace;
    private Rectangle startButton;
    private Rectangle exitButton;   //changed to exitButton
    private Rectangle guideButton;  //add guide button

    private BasicStroke borderStoke;
    private BasicStroke borderStoke_noDashes;

    private Font greetingsFont;
    private Font gameTitleFont;
    private Font creditsFont;
    private Font buttonFont;

    private Image background;

    private boolean startClicked;
    private boolean exitClicked;    //changed to exitClicked
    private boolean guideClicked;    //when guide button clicked

    /**
     * This is HomeMenu constructor. Design HomeMenu and perform instantiation.
     *
     * @param area represents the area of the game window.
     */
    public HomeMenu(Dimension area){    //constructor, changed owner to gameFrame

        this.setFocusable(true);
        this.requestFocusInWindow();

        menuFace = new Rectangle(new Point(0,0),area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
        startButton = new Rectangle(btnDim);
        exitButton = new Rectangle(btnDim); //changed to exitButton
        guideButton = new Rectangle(btnDim); //instantiate infoButton
        borderStoke = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0,DASHES,0);
        borderStoke_noDashes = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);

        greetingsFont = new Font("Noto Mono",Font.PLAIN,25);
        gameTitleFont = new Font("Noto Mono",Font.BOLD,40);
        creditsFont = new Font("Monospaced",Font.PLAIN,10);
        buttonFont = new Font("Monospaced",Font.BOLD,startButton.height-2);

    }


    /**
     * This method is used to call method to deign the contents of Home Menu.
     *
     * @param g the variable of Graphics class used for drawing content.
     */
    public void paint(Graphics g){
        drawMenu((Graphics2D)g);
    }


    /**
     * This method is used to design the content of the HomeMenu window.
     *
     * @param g2d variable of Graphics2D class to perform operations of Graphics2D class.
     */
    public void drawMenu(Graphics2D g2d){

        drawContainer(g2d);

        /*
        all the following method calls need a relative
        painting directly into the HomeMenu rectangle,
        so the translation is made here so the other methods do not do that.
         */
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = menuFace.getX();
        double y = menuFace.getY();

        g2d.translate(x,y);

        //method calls
        drawText(g2d);
        drawButton(g2d);

        g2d.translate(-x,-y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    /**
     * This method is used to design the background content and border of HomeMenu window.
     *
     * @param g2d variable of Graphics2D class to perform operations of Graphics2D class.
     */
    private void drawContainer(Graphics2D g2d){
        background = new ImageIcon(getClass().getResource("/BrickBreaker2.jpg")).getImage();
        g2d.drawImage(background, 0,0,550,350,null);
        Color prev = g2d.getColor();
        Stroke tmp = g2d.getStroke();

        g2d.setStroke(borderStoke_noDashes);

        g2d.draw(menuFace);

        g2d.setStroke(tmp);

        g2d.setColor(prev);
    }

    /**
     * This method is used to locate text in the HomeMenu window.
     * @param g2d variable of Graphics2D class to perform operations of Graphics2D class.
     */
    private void drawText(Graphics2D g2d){

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = greetingsFont.getStringBounds(GREETINGS,frc);
        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(GAME_TITLE,frc);
        Rectangle2D creditsRect = creditsFont.getStringBounds(CREDITS,frc);

        int sX,sY;

        sX = (int)(menuFace.getWidth() - greetingsRect.getWidth()) / 2;
        sY = (int)(menuFace.getHeight() / 4);

        g2d.setFont(greetingsFont);
        g2d.drawString(GREETINGS,sX,sY);

        sX = (int)(menuFace.getWidth() - gameTitleRect.getWidth()) / 2;
        sY += (int) gameTitleRect.getHeight() * 1.1;//add 10% of String height between the two strings

        g2d.setFont(gameTitleFont);
        g2d.drawString(GAME_TITLE,sX,sY);

        sX = (int)(menuFace.getWidth() - creditsRect.getWidth()) / 2;
        sY += (int) creditsRect.getHeight() * 2;

        g2d.setFont(creditsFont);
        g2d.drawString(CREDITS,sX,sY);


    }

    /**
     * This method is used to locate button in the HomeMenu window.
     *
     * @param g2d variable of Graphics2D class to perform operations of Graphics2D class.
     */
    private void drawButton(Graphics2D g2d){

        FontRenderContext frc = g2d.getFontRenderContext();    //measure text width

        Rectangle2D startTxtRect = buttonFont.getStringBounds(START_TEXT,frc);  //changed to startTxtRect
        Rectangle2D exitTxtRect = buttonFont.getStringBounds(EXIT_TEXT,frc);   //changed to exitTxtRect
        Rectangle2D infoTxtRect = buttonFont.getStringBounds(GUIDE_TEXT,frc);    //get info button text

        g2d.setFont(buttonFont);

        int x = ((menuFace.width - startButton.width) / 2);
        int y =(int) ((menuFace.height - startButton.height) * 0.7);

        startButton.setLocation(x,y);

        x = (int)(startButton.getWidth() - startTxtRect.getWidth()) / 2;
        y = (int)(startButton.getHeight() - startTxtRect.getHeight()) / 2;

        x += startButton.x;
        y += startButton.y + (startButton.height * 0.9);

        if(startClicked){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(startButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(START_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(startButton);
            g2d.drawString(START_TEXT,x,y);
        }

        x = startButton.x;
        y = startButton.y;

        y *= 1.2;

        //perform exitButton
        exitButton.setLocation(x,y);

        x = (int)(exitButton.getWidth() - exitTxtRect.getWidth()) / 2;
        y = (int)(exitButton.getHeight() - exitTxtRect.getHeight()) / 2;

        x += exitButton.x;
        y += exitButton.y + (startButton.height * 0.9);

        if(exitClicked){
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(exitButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(EXIT_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(exitButton);
            g2d.drawString(EXIT_TEXT,x,y);
        }

        x = exitButton.x;       //set exitButton location
        y = exitButton.y + 44;  //set exitButton location

        //perform infoButton
        guideButton.setLocation(x,y);

        x = (int)(guideButton.getWidth() - infoTxtRect.getWidth()) / 2;
        y = (int)(guideButton.getHeight() - infoTxtRect.getHeight()) / 2;

        x += guideButton.x;
        y += guideButton.y + (exitButton.height * 0.9);

        if(guideClicked){
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(guideButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(GUIDE_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(guideButton);
            g2d.drawString(GUIDE_TEXT,x,y);
        }

    }

    /*
     * This is a built-in method used to detect mouse clicked in HomeMenu window.
     * If the user clicked on start button, the game starts.
     * If the user clicked on Exit Button, the program stops and exits.
     * If the user clicked on Guide Button, it pops up Guide Window.
     *
     * @param mouseEvent represents MouseEvent class to detect mouse action.
     */
    public void addMouseEvent(MouseListener mouseEvent) {
        this.addMouseListener(mouseEvent);
    }

    public void AddMouseMotionListener(MouseMotionListener mouseEvent) {
        this.addMouseMotionListener(mouseEvent);
    }

    public Rectangle getStartButton() {
        return startButton;
    }

    public Rectangle getExitButton() {
        return exitButton;
    }

    public Rectangle getGuideButton() {
        return guideButton;
    }

    public void Repaint(Rectangle button){
        repaint(button.x,button.y,button.width+1,button.height+1);
    }


    public boolean isStartClicked() {
        return startClicked;
    }

    public void setStartClicked(boolean startClicked) {
        this.startClicked = startClicked;
    }

    public boolean isExitClicked() {
        return exitClicked;
    }

    public void setExitClicked(boolean exitClicked) {
        this.exitClicked = exitClicked;
    }

    public boolean isGuideClicked() {
        return guideClicked;
    }

    public void setGuideClicked(boolean guideClicked) {
        this.guideClicked = guideClicked;
    }
}
