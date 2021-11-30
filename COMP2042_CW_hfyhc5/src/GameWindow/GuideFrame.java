package GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuideFrame extends JFrame implements ActionListener {

    private static final int FRAME_WIDTH = 700;   //frame width
    private static final int FRAME_HEIGHT = 500;  //frame height
    private JLabel guideTitle;
    private JButton backButton;
    private GameFrame gameFrame;
    //private ImageIcon background = new ImageIcon(getClass().getResource("/guideImage1.jpg"));

    public GuideFrame(){
        drawGuideMenu();
        enableButton();

        this.setSize(FRAME_WIDTH,FRAME_HEIGHT); //set window size
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.add(guideTitle);
        this.add(backButton);
    }

    private void drawGuideMenu() {
        guideTitle = new JLabel();
        //guideTitle.setIcon(background);
        guideTitle.setText("<html>" + "<h1> Guide Menu</h1>" +
                "1. Player's goal is to destroy all the walls with a small ball.<br/>" +
                "2. If the ball falls to the ground, you will lose a life. <br/>" +
                "3. You will have 3 chances before you lose the game. <br/><br/>" +
                "<b>Game Controls: </b><br/>" +
                "<b>SPACE</b> - start/pause the game. <br/>" +
                "<b>A</b> - move left the player <br/>" +
                "<b>D</b> - move rigth the player <br/>" +
                "<b>ESC</b> - enter/exit pause menu <br/>" +
                "<b>ALT+SHITF+F1</b> - open console <br/><br/>" +
                "Enjoy the game :) " + "</html>");
        guideTitle.setBounds(60,0,600,450);
        guideTitle.setFont(new Font(null, Font.PLAIN, 20));   //set font
    }

    public void enableButton(){
        backButton = new JButton();
        backButton.setBounds(550,400,100, 30);
        backButton.setText("Back");
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(Color.DARK_GRAY);
        backButton.setFocusable(false);
        backButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==backButton){
            dispose();
            gameFrame = new GameFrame();
        }
    }

}
