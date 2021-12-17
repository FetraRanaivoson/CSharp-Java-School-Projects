package Airplane.view;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;

public class ChooseColorGUI extends JFrame
{
    public JList colorList;
    public String[] colorNames = {"Black", "Gray", "White"};
    public Color [] colorFeatures = {Color.BLACK, Color.DARK_GRAY, Color.WHITE};
    private AirplaneGUI airplaneGUI; //THE REFERENCE

    public ChooseColorGUI(AirplaneGUI airplaneGUI)
    {
        this.airplaneGUI = airplaneGUI; //MUST BE DECLARED HERE
        setTitle ("Color Chooser");
        //setSize(500, 700);
        setBounds(0,0,200,300); //Same as setSize and setLocation
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createComponents();
        setVisible(true);

    }
    public void createComponents()
    {

        setLayout(new FlowLayout(FlowLayout.CENTER, 20,20));
        add (new JLabel("Set Background color: "));
        add ( colorList = new JList(colorNames));
        colorList.setVisibleRowCount(4);
        colorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        colorList.setFixedCellWidth(150);
        add (new JScrollPane(colorList));
        colorList.addListSelectionListener(this::changeColor); //THE EVENT TO LISTEN

    }
    public void changeColor (ListSelectionEvent event)
    {
        System.out.println("All good in chooseColor GUI!");
        //airplaneGUI.setColor(event);
        airplaneGUI.setColor(event);
        //getContentPane().setBackground(colorFeatures[colorList.getSelectedIndex()]);

    }

}
