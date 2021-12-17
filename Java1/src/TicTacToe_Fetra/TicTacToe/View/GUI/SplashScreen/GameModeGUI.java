package TicTacToe.View.GUI.SplashScreen;


import TicTacToe.View.GUI.GameGUI.DoublePlayerBoardGUI;
import TicTacToe.View.GUI.GameGUI.SinglePlayerBoardGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GameModeGUI extends JFrame {

    private JPanel selectGamePanel;
    private JPanel statusBarPanel;
    private JLabel statusBar;

    private JButton singlePlayerBtn;
    private JButton doublePlayerBtn;


    public GameModeGUI ()
    {
        setTitle("Mode");
        setSize(300,320);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createComponents();
        setVisible(true);
    }

    public void createComponents()
    {
        add (selectGamePanel = new JPanel(), BorderLayout.CENTER);
        selectGamePanel.setBorder(BorderFactory.createTitledBorder("Game Mode"));
        Dimension d1 = selectGamePanel.getPreferredSize();
        d1.height = 100;
        d1.width = 250;
        selectGamePanel.setPreferredSize(d1);

        selectGamePanel.setLayout(new GridLayout(2,2,5,5));
        selectGamePanel.add (singlePlayerBtn = new JButton("1 Player"));
        selectGamePanel.add (doublePlayerBtn = new JButton("2 Player"));

        add (statusBarPanel = new JPanel(), BorderLayout.SOUTH);
        statusBarPanel.add(statusBar = new JLabel());
        Dimension d2 = statusBarPanel.getPreferredSize();
        d2.height = 23;
        d2.width = 200;
        statusBarPanel.setPreferredSize(d2);



        singlePlayerBtn.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                statusBar.setText("Play against AI player.");
            }
        });
        singlePlayerBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                try {
                    new SinglePlayerBoardGUI();
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
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

        doublePlayerBtn.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                statusBar.setText("Challenge your friend!");
            }
        });
        doublePlayerBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                try {
                    new DoublePlayerBoardGUI();
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
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
