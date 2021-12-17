package Airplane.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExternalControllerGUI extends JFrame {
    public JButton motorOffBtn;
    public JButton motorStartBtn;
    private AirplaneGUIv2 airplaneGUIv2;

    public void setAirplaneGUIv2  (AirplaneGUIv2 airplaneGUIv2) //SET IT AS AN OBSERVER
                                                                        //PUTTING OBSERVERS IN AN ARRAY LIST IS POWERFUL
                                                                        //I CAN MAKE ADD/REMOVE OBSERVER METHOD...
    {
        this.airplaneGUIv2 = airplaneGUIv2;
    }

    public ExternalControllerGUI ()
    {
        setTitle("External controller");
        setSize(250,250);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addComponents();
        setAlwaysOnTop(true);
        setVisible(true);
    }
    public void addComponents()
    {
        setLayout(new FlowLayout());
        add (motorOffBtn = new JButton("Stop Motor"));
        add (motorStartBtn = new JButton("Start Motor"));

        motorOffBtn.addActionListener(e -> airplaneGUIv2.actionPerformed(e)); //CALL THE OBSERVER METHOD
        motorStartBtn.addActionListener(e ->airplaneGUIv2.actionPerformed(e) );
    }

}
