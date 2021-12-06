package GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GuideFrame class to open Guide window when pressed on Guide button.
 *
 * @author Chin Hong Shen
 * @version 0.2
 * @since 26 November 2021
 */
public class GuideFrame extends JFrame implements ActionListener {

    private static final int FRAME_WIDTH = 700;   //frame width
    private static final int FRAME_HEIGHT = 500;  //frame height
    private JLabel guideTitle;
    private JButton backButton;
    private GameFrame gameFrame;
    private ImageIcon image;
    private JLabel background;

    /**
     * GuideFrame constructor to design Guide Window.
     * <p>Call methods to enable guide screen and back button.</p>
     */
    public GuideFrame(){
        drawGuideMenu();
        enableButton();
        image = new ImageIcon(getClass().getResource("/brick3.jpg"));
        background = new JLabel(image);
        background.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        background.add(guideTitle);
        background.add(backButton);
        this.setSize(FRAME_WIDTH,FRAME_HEIGHT); //set window size
        this.setTitle("Guide Menu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.add(background);
    }

    /**
     * This method is used to set text on Guide Window.
     */
    private void drawGuideMenu() {
        guideTitle = new JLabel();
        guideTitle.setText("<html>" + "<h1> Guide Menu</h1>" +
                "1. Player's goal is to destroy all the walls with a small ball.<br/>" +
                "2. If the ball falls to the ground, you will lose a life. <br/>" +
                "3. You will have 3 lives before you lose the game. <br/>" +
                "4. You will be penalized for 3 scores if you lose a life. <br/>" +
                "5. You will be rewarded for 5 scores if you pass a level without losing any life. <br/><br/>" +
                "<b>Game Controls: </b><br/>" +
                "<b>SPACE</b> - start/pause the game. <br/>" +
                "<b>A</b> - move left the player <br/>" +
                "<b>D</b> - move right the player <br/>" +
                "<b>ESC</b> - enter/exit pause menu <br/>" +
                "<b>ALT+SHIFT+F1</b> - open console <br/><br/>" +
                "Enjoy the game :) " + "</html>");
        guideTitle.setBounds(60,0,600,450);
        guideTitle.setFont(new Font(null, Font.PLAIN, 20));   //set font
    }

    /**
     * This method is used to design button and set button text on Guide Window.
     */
    public void enableButton(){
        backButton = new JButton();
        backButton.setBounds(550,400,100, 30);
        backButton.setText("Back");
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(Color.DARK_GRAY);
        backButton.setFocusable(false);
        backButton.addActionListener(this);
    }

    /**
     * This is a built-in method, used to perform an action when button is clicked.
     *
     * @param e An object to represent the event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==backButton){
            dispose();
            gameFrame = new GameFrame();
        }
    }

}
