package guiClient.view;

import Airplane.model.AirplaneServer;
import Airplane.model.Exception.*;
import Airplane.model.FileManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class AirplaneGUI extends JFrame {

    public JButton startBtn;
    public JButton takeOffBtn;
    public JButton stopBtn;
    public JButton increaseAltitudeBtn;
    public JButton decreaseAltitudeBtn;
    public JButton exitBtn;
    public JSlider altitudeSlider;
    public ChooseColorGUI cg;

    public JTextArea textArea;
    private AirplaneServer airplaneServer;

    private JMenuItem exitApp;
    private JMenuItem fullScreen;
    private JPanel west;
    private JPanel north;
    private JPanel east;

    private FileManager fileManager;
    private Date date = new Date();
    private LocalTime time = LocalTime.now();


    public AirplaneGUI()
    {
        ///LOG IN THE AIRPLANE
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        formatter.format(date);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");
        time.format(formatter1);


        //createFileLog = new CreateFileLog();
        fileManager.createFile();
        fileManager.recordAirplane("=============================================");
        fileManager.recordAirplane("Airplane logged in on " + date);

        //createFileLog.closeFile();


        setTitle ("Airplane Controller");
        setSize(1444, 900);
        //setBounds(x,y,width,height); //Same as setSize and setLocation
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(createMenuBar());

        //setLayout(new BorderLayout());
        //setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        //setLayout(new BorderLayout());
        //setLayout(new GridLayout(3,3));
        //setLayout(new GridBagLayout());



        createComponents();

        setVisible(true);
    }

    private void createComponents()
    {
        ///All images source//////////////////////////////////////////////////////
        ImageIcon icon = new ImageIcon("src/Airplane/img/blueprintResized.png");
        Icon takeOffIcon = new ImageIcon(getClass().getResource("takeOff.png"));
        Icon startIcon = new ImageIcon(getClass().getResource("engineGo.png"));
        Icon stopIcon = new ImageIcon(getClass().getResource("engineStop.png"));
        Icon increaseIcon = new ImageIcon(getClass().getResource("increase.png"));
        Icon decreaseIcon = new ImageIcon(getClass().getResource("decrease.png"));
        Icon landIcon = new ImageIcon(getClass().getResource("land.png"));
        //////////////////////////////////////////////////////////////////////////

        //Create and add the components

        //MAIN BUTTONS////////////////////////////////////////////////////////////
        west = new JPanel();
        west.setBackground(Color.darkGray);
        add(west, BorderLayout.WEST);

        north = new JPanel();
        north.setBackground(Color.blue);
        add(north,BorderLayout.NORTH);

        east = new JPanel();
        east.setBackground(Color.red);
        add(east,BorderLayout.EAST);
        //Slider and stuff/////////////////////////////////////////////////////
        //add (new JLabel(icon));
        west.add (new JLabel ("Set Altitude:"));
        west.setAlignmentX(Component.LEFT_ALIGNMENT);

        west.add(altitudeSlider = new JSlider(JSlider.VERTICAL,0,12000,0));
        west.add (textArea = new JTextArea("                            "));
        textArea.setRows(5);

        altitudeSlider.setMajorTickSpacing(3000);
        altitudeSlider.setMinorTickSpacing(1500);
        altitudeSlider.setPaintTicks(true);
        altitudeSlider.setPaintLabels(true);




        north.setBackground(Color.darkGray);

        north.add(startBtn = new JButton ("Start",startIcon));
        startBtn.setToolTipText("This will start the engine");
        north.add(takeOffBtn = new JButton ("TakeOff",takeOffIcon));
        //takeOffBtn.setRolloverIcon(landIcon);
        north.add(stopBtn = new JButton ("Stop", stopIcon));
        north.add(increaseAltitudeBtn = new JButton ("Increase altitude", increaseIcon));
        north.add(decreaseAltitudeBtn = new JButton ("Decrease altitude", decreaseIcon));
        north.add(exitBtn = new JButton ("Exit"));
        //add (new JLabel("Infos"));
        //add (textField = new JTextArea(""));
        //textField.setColumns(20);



        JLabel center = new JLabel(icon);
        add (center, BorderLayout.CENTER);


        JTextArea txt = new JTextArea();
        west.add(txt);
        txt.setRows(50);
        add(west, BorderLayout.EAST);

        ///////////////////////////////////////////////////////////////////////////

        //Add colors components //Make this a public method in the other GUI
        //and just call the function w proper args here!
/*
        add (new JLabel("Set Background color: "));
        add ( colorList = new JList(colorNames));
        colorList.setVisibleRowCount(4);
        colorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        colorList.setFixedCellWidth(150);
        add (new JScrollPane(colorList));

*/








        ///////////////////////////////////////////////////////////////////////


        //Action listener: what to run when this button get clicked!
        //3 methods for action listener //

        /*
        ///1- Lambda method (Java 8+)
        startBtn.addActionListener(e -> {
            System.out.println("Motor has started in lambda method");

        });
        */
        /*
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    textField.append("Motor started");

            }
        });
*/

        /*
        ///2- Anonymous class (In our course curriculum)
        //Creating an object out of an interface
        startBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)    //NB: itemStateChange in case  of ItemListener
            {
                //System.out.println("Motor has started in anonymous class");
                //takeOffBtn.setText("Wanna die?");
            }

        });
        */




        ///3- Method signature: method referencing (Java 8+)
        ///Whenever this button's clicked, call this method;
        //NOT LIKE THIS ONE:           startBtn.addActionListener(startMotor());
        ///----> We need to respect the method signature (parameters)
        /*
        startBtn.addActionListener(this::startMotor);
        takeOffBtn.addActionListener(this::takeOff);
        stopBtn.addActionListener(this::stopMotor);
        increaseAltitudeBtn.addActionListener(this::increaseAltitude);
        decreaseAltitudeBtn.addActionListener(this::decreaseAltitude);
        exitBtn.addActionListener(this::exitApp);
        */


        ///4- Other method (implement method)
        //There can be different events handler like ActionListener / Action Event
        // if (event.getSource() == buttonClicked)
        Handler actionHandler = new Handler();
        startBtn.addActionListener(actionHandler);
        takeOffBtn.addActionListener(actionHandler);
        stopBtn.addActionListener(actionHandler);
        increaseAltitudeBtn.addActionListener(actionHandler);
        decreaseAltitudeBtn.addActionListener(actionHandler);
        exitBtn.addActionListener(actionHandler);

        //Adding another event like message pop up

        /*
        startBtn.addActionListener(e ->
        {
            ChooseColorGUI cg = new ChooseColorGUI(this);

        });
        */

        cg = new ChooseColorGUI(this);

        //Or ItemListener /Item event
        // if (event.getStateChange() == itemEvent.SELECTED)
        //if (theItem.selected)  etc...



    }

    public void setColor (ListSelectionEvent event)
    {
        System.out.println("All good in mainGUI!");
        getContentPane().setBackground(cg.colorFeatures[cg.colorList.getSelectedIndex()]);

    }



/* Method signature
    public void startMotor(ActionEvent event)
    {
        try {
            airplane.startMotor();
            IO.println("\tMotor has started!");
        }
        catch (AirplaneExplodedException | MotorHasAlreadyStartedException exception)
        {
            System.err.println(exception.getMessage());
        }
        airplane.airplaneBoard();
    }

    public void takeOff (ActionEvent event)
    {
        try {
            airplane.takeOff();
        } catch (AirplaneExplodedException | MotorIsNotStartedException | AirplaneAlreadyTookOffException exception) {
            System.err.println(exception.getMessage());
        }
        airplane.airplaneBoard();
    }

    public void stopMotor (ActionEvent event)
    {
        try {
            airplane.stopMotor();
            JOptionPane.showMessageDialog(getContentPane(), "\tMotor has stopped!");
            IO.println("\tMotor has stopped!");
        }
        catch (AirplaneExplodedException | CannotStopFlyingAirplaneException | MotorHasAlreadyStoppedException exception)
        {
            System.err.println(exception.getMessage());
        }
        airplane.airplaneBoard();
    }

    public void increaseAltitude (ActionEvent event)
    {
        try {
            airplane.increaseAltitude();
            IO.println("\tAirplane's altitude: " + airplane.getAltitude() + " ft");
        }
        catch (AirplaneExplodedException | MotorIsNotStartedException | AirplaneNotInAirException | AltitudeDangerException | AirplaneBoomException exception)
        {
            System.err.println(exception.getMessage());
        }
        airplane.airplaneBoard();
    }

    public void decreaseAltitude (ActionEvent event)
    {
        try {
            airplane.decreaseAltitude();
            if (airplane.getAltitude() == 0)
                IO.println("\tAirplane successfully landed on the ground.");
            else if (airplane.getAltitude() != 0)
                IO.println("\tAirplane's altitude: " + airplane.getAltitude() + " ft");
        }
        catch (AirplaneExplodedException | AirplaneAlreadyGroundedException | AltitudeDangerException | AirplaneBoomException exception)
        {
            System.err.println(exception.getMessage());
        }
        airplane.airplaneBoard();
    }

    public int exitApp (ActionEvent event)
    {
        IO.println("------------------");
        IO.println("\tAirplane application terminated.");
        return 0;
    }
*/

    private JMenuBar createMenuBar ()
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu ("File");
        JMenu windowMenu = new JMenu ("Window");

        exitApp = new JMenuItem("Exit");
        fullScreen = new JMenuItem("Full Screen");

        menuBar.add(fileMenu);
        menuBar.add(windowMenu);
        fileMenu.add(exitApp);
        windowMenu.add(fullScreen);

        exitApp.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                int userChoice = JOptionPane.showConfirmDialog(null,"Quit the application?", "Airplane controller", 0);
                if (userChoice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        return  menuBar;
    }



    private class Handler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try
            {
                if (event.getSource() == startBtn)
                {
                    airplaneServer.startMotor();
                    JOptionPane.showMessageDialog(getContentPane(), "\tMotor has started!");
                    IO.println("\tMotor has started!");
                    airplaneServer.airplaneBoard();
                    fileManager.recordAirplane("\t" + time + " : Motor started");

                }

                else if (event.getSource() == takeOffBtn)
                {
                    airplaneServer.takeOff();
                    altitudeSlider.setValue((int) airplaneServer.getAltitude());
                    fileManager.recordAirplane("\t" + time + " : Airplane took off");
                }

                else if (event.getSource() == stopBtn) {
                    airplaneServer.stopMotor();
                    JOptionPane.showMessageDialog(getContentPane(), "\tMotor has stopped!");
                    IO.println("\tMotor has stopped!");
                    airplaneServer.airplaneBoard();
                    fileManager.recordAirplane("\t" + time + " : Motor has stopped");
                }

                else if (event.getSource() == increaseAltitudeBtn)
                {
                    airplaneServer.increaseAltitude();
                    altitudeSlider.setValue((int) airplaneServer.getAltitude());
                    fileManager.recordAirplane("\t" + time + " : Altitude has increased, new altitude is: " + airplaneServer.getAltitude() + "ft");
                }

                else if (event.getSource() == decreaseAltitudeBtn)
                {
                    airplaneServer.decreaseAltitude();
                    altitudeSlider.setValue((int) airplaneServer.getAltitude());
                    fileManager.recordAirplane("\t" + time +  " : Altitude has decreased, new altitude is: " + airplaneServer.getAltitude() + "ft");
                    if (airplaneServer.getAltitude() == 0)
                    {
                        IO.println("\tAirplane successfully landed on the ground.");
                        fileManager.recordAirplane("\t" + time + " : Airplane landed, altitude is: " + airplaneServer.getAltitude() + "ft");
                    }
                }

                else if (event.getSource() == exitBtn)
                {

                    int userChoice = JOptionPane.showConfirmDialog(null, "Quit the program?", "Warning", 0);
                    if (userChoice == JOptionPane.YES_OPTION) {
                        IO.println("------------------");
                        IO.println("\tAirplane application terminated.");
                        fileManager.recordAirplane("\t" + time + " : Airplane logged out");
                        fileManager.recordAirplane("=============================================\n");
                        System.exit(0);
                    }
                }
            }
            catch (AirplaneExplodedException | MotorHasAlreadyStartedException | MotorIsNotStartedException | AirplaneAlreadyTookOffException | CannotStopFlyingAirplaneException | MotorHasAlreadyStoppedException |AirplaneNotInAirException | AltitudeDangerException | AirplaneBoomException |AirplaneAlreadyGroundedException exception)
            {
                System.err.println(exception.getMessage());
                fileManager.recordAirplane("\t" + time + " ERROR: " + exception.getMessage());
            }
            finally {
                altitudeSlider.setValue((int) airplaneServer.getAltitude());
            }
            //airplane.airplaneBoard();
        }

    }
}


