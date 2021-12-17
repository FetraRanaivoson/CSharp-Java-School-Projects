package TicTacToe.View.GUI.SplashScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class TicTacToeSplashScreenGUI extends JFrame
{
    private JButton newGameBtn;
    private JButton exitBtn;
    private JPanel imagePanel;
    private JLabel statusBar;

    public TicTacToeSplashScreenGUI ()
    {
        setTitle("Welcome to Tic Tac Toe Game");
        setSize(350,370);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createComponents();
        setVisible(true);
    }
    private void createComponents()
    {
        //////////////////////////////MAIN BUTTONS//////////////////////////////////////////////////////////
        //customJPanel center = new customJPanel(new ImageIcon("src/TicTacToe/TicTacToe.png").getImage());
        JPanel center = new JPanel();
        center.setBackground(Color.getHSBColor(198,44,69));
        center.setBorder(BorderFactory.createEtchedBorder());
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        add(center, BorderLayout.CENTER);

        center.add(imagePanel = new customJPanel(new ImageIcon("src/TicTacToe/TicTacToeTitle2.png").getImage()));
        center.add(newGameBtn = new JButton("New Game"));
        center.add(exitBtn = new JButton("Exit"));


        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel north = new JPanel();
        //north.setSize(50,500);
        north.setLayout(new BoxLayout(north, BoxLayout.PAGE_AXIS));
        add(north, BorderLayout.NORTH);
        north.setBackground(Color.DARK_GRAY);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////


        JPanel south = new JPanel();
        add(south, BorderLayout.SOUTH);
        south.add(statusBar = new JLabel());

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        //LISTENERS
        ////NEW GAME
        newGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameBtn.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                statusBar.setText("Play Tic Tac Toe with an AI, with a friend or Online.");
            }
        });
        newGameBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new GameModeGUI();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                statusBar.setText("");
            }
        });




        ///EXIT BUTTON
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                statusBar.setText("");
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                statusBar.setText("Exit the game.");
            }
        });
        exitBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int userChoice = JOptionPane.showConfirmDialog(null,"Quit the game?", "TicTacToe", 0);
                if (userChoice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                statusBar.setText("");
            }
        });
    }


}
