package Airplane.view;

import Airplane.controller.AirplaneController;
import Airplane.model.AirplaneServer;
import Airplane.model.Exception.*;
import Airplane.model.FileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Date;
import java.util.Scanner;


public class AirplaneGUIv2 extends JFrame implements ActionListener, AltitudeObserver{

    //GUI Client
    private ObjectOutputStream instructServer;
    private ObjectInputStream getInputServerInstruction;
    private String serverIP;
    private Socket connection;
    private String stringCommands;


    //AIRPLANE GUI PANELS CONCERNS
    private JPanel commandsPanel;
    private JPanel guiConsolePanel;
    private JPanel southPanel;
    private JPanel southWestPanel;
    private JPanel southEastPanel;
    private JTextArea consoleArea;

    //AIRPLANE GUI COMMANDS BUTTON CONCERNS
    public JButton startBtn;
    public JButton takeOffBtn;
    public JButton stopBtn;
    public JButton increaseAltitudeBtn;
    public JButton decreaseAltitudeBtn;
    public JButton exitBtn;

    //AIRPLANE GUI SLIDER CONCERNS
    private JLabel progressBarLabel;
    public JTextArea textArea;
    private JButton clearConsoleBtn;
    private JProgressBar altitudeProgressBar;

    //FILE > MenuItems
    private JMenuItem exitItem;
    private JMenuItem loadAirplaneItem;
    private JMenuItem readAirplaneItem;
    private JMenuItem newAirplaneItem;
    private JMenuItem deleteAirplaneItem;
    private JLabel altitudeValue;

    //EXTERNAL CONTROLS TEST
    private ExternalControllerGUI externalControllerGUI = new ExternalControllerGUI();
    private File file;

    //LOG FILE CONCERNS
    private Date date = new Date();
    private LocalTime time = LocalTime.now();

    //Responsible for creating/clearing airplane files, record airplane movement
    private FileManager fileManager = new FileManager();

    //GUI Responsible for creating new airplane and log the corresponding files to the console
    private NewAirplaneManagerGUI newAirplaneManagerGUI = new NewAirplaneManagerGUI();;

    //Responsible for communication between this GUI and the file Manager class
    private AirplaneServer airplaneServer = new AirplaneServer(this, fileManager);

    //MVC: This is the only responsible for Controlling the airplane.
    //It communicates with this GUI and the airplane itself
    private AirplaneController arpController = new AirplaneController(this, airplaneServer);

    //Thread
    private Thread changeAltitudeThread = new Thread(new ChangeAltitudeThread(airplaneServer,this));
    private AirplaneGUIv2 airplaneGUIv2;
    private Thread increaseThread;
    private Thread decreaseThread;


    /////// *THIS GUI CONSTRUCTOR STARTS HERE* /////////////////////////////////////////////////////////////////

    public AirplaneGUIv2(String host)
    {
        airplaneServer.addAltitudeObserver(this);
        //For the externalController GUI to know this GUI
        externalControllerGUI.setAirplaneGUIv2(this);

        //newAirplaneManager = new NewAirplaneManager();;

        //For communication between he newAirplaneManagerGUI and the file manager.
        //Also updating this GUI title everytime a new Airplane logged in.
        newAirplaneManagerGUI.createNewAirplaneLog(fileManager);
        newAirplaneManagerGUI.setTitle(this);


        //SETTING UP THIS GUI
        setTitle("Airplane Controller" + "(" + fileManager.toString() + ")");
        setSize(800,600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(createMenuBar());

        //SETTING UP THIS GUI COMPONENTS STARTS
        createCommandsComponents(); //PANEL WEST: start/takeoff/stop/increase/decreaseAltitude
        createConsoleComponents(); //PANEL EAST: GUI console
        createAltitudeSliderCommands(); //PANEL SOUTH: slider
        fileManager.createFile();

        //SETTING UP THIS GUI COMPONENTS STARTS

        repaint();
        revalidate();
        setVisible (true);
    }
    /////// *THIS GUI CONSTRUCTOR ENDS HERE* //////////////////////////////////////////////////////////////////

    public void startClient ()
    {
        try {
            connectToAirplane();
            setupStreams();
            executeCommands();
        } catch (EOFException eof){
            consoleArea.append("Client ended connection\n");
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            closeClient ();
        }
    }
    private void connectToAirplane () throws IOException
    {
        consoleArea.append("Trying to connect to Airplane\n");
        connection = new Socket(InetAddress.getByName(serverIP),7575);
        consoleArea.append("Connected to: " + connection.getInetAddress().getHostName());
    }
    private void setupStreams () throws IOException
    {
        instructServer = new ObjectOutputStream(connection.getOutputStream());
        instructServer.flush();
        getInputServerInstruction = new ObjectInputStream(connection.getInputStream());
        consoleArea.append("Setup successful\n");
    }
    private void executeCommands () throws IOException
    {
        do {
            try {
                stringCommands = (String) getInputServerInstruction.readObject();
                checkStringCommands(stringCommands);
            } catch (ClassNotFoundException cnf) {
                consoleArea.append("Unknown Client input\n");
            } catch (AirplaneException e) {
                e.printStackTrace();
            }
        } while (true);
    }
    public void closeClient () {
        consoleArea.append("Closing connection. Please run MAIN SERVER first.\n");
        try {
            instructServer.close();
            getInputServerInstruction.close();
            connection.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    private void checkStringCommands(String stringCommands) throws MotorHasAlreadyStartedException, AirplaneExplodedException, MotorHasAlreadyStoppedException, CannotStopFlyingAirplaneException, AirplaneNotInAirException, MotorIsNotStartedException, AltitudeDangerException, AirplaneBoomException, AirplaneAlreadyTookOffException, AirplaneAlreadyGroundedException {
        try
        {
            consoleArea.setForeground(Color.green);
            switch (stringCommands) {
//////HAVE TO BE THE SAME NAME AS THE SERVER BUTTON

                case "Start" -> {
                    airplaneServer.startMotor();
                    fileManager.recordAirplane("start:success:FROM SERVER");
                }
                case "Stop" -> {
                    airplaneServer.stopMotor();
                    fileManager.recordAirplane("stop:success:FROM SERVER");
                }
                case "Increase Alt" -> {
                    airplaneServer.increaseAltitude();
                    fileManager.recordAirplane("increase:success:1000:FROM SERVER");
                }
                case "Decrease Alt" -> {
                    airplaneServer.decreaseAltitude();
                    fileManager.recordAirplane("decrease:success:1000:FROM SERVER");
                }
                case "Take Off" -> {
                    airplaneServer.takeOff();
                    fileManager.recordAirplane("takeOff:success:FROM SERVER");
                }
                default -> consoleArea.append("Airplane GUI Waiting for instruction...\n");
            }
        }
        catch (AirplaneException e) {
            consoleArea.setForeground(Color.red);
            consoleArea.append(e.getMessage() +"\n");
        }
    }

    ///When User decide to create a new Airplane on the fly, Reset the new Airplane to its default state//////////////////
    public void setAltitudeToDefault (double altitude) throws AirplaneAlreadyGroundedException //Listener/Subscriber //Notifier setDefaultAirplaneState (CreateAirplaneGui) should call this method
    {
        arpController.setAltitude(altitude);
        //System.out.println("airplane: " + airplane.getAltitude());
    }
    public void setMotorOff() //Listener/Subscriber //Notifier setDefaultAirplaneState (CreateAirplaneGui) should call this method
    {
        arpController.setMotorOff();
        consoleArea.append(airplaneServer.airplaneBoard());
    }
    public Object startMotor() {
        try {
            arpController.startMotor();
        } catch (AirplaneException e) {
            e.printStackTrace();
        }
        return null;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //For the progressBar Thread
    public void appendTextToConsole (String text)
    {
        consoleArea.append(text+"\n");
    }
    public void printTextToConsole (String text)
    {
        consoleArea.setText(text+"");
    }
    //For the progressBar Thread
    public void updateProgressBar (int currentAltitude)
    {
        altitudeProgressBar.setValue(currentAltitude);
    }
    public JButton getTakeOffBtn()
    {
        return takeOffBtn;
    }
    public JButton getIncreaseAltitudeBtn()
    {
        return increaseAltitudeBtn;
    }
    public JButton getDecreaseAltitudeBtn()
    {

        return decreaseAltitudeBtn;
    }

    public void createCommandsComponents()
    {
        //ICONS
        ImageIcon icon = new ImageIcon("src/Airplane/view/img/blueprintResized.png");
        Icon takeOffIcon = new ImageIcon(getClass().getResource("takeOff.png"));
        Icon startIcon = new ImageIcon(getClass().getResource("engineGo.png"));
        Icon stopIcon = new ImageIcon(getClass().getResource("engineStop.png"));
        Icon increaseIcon = new ImageIcon(getClass().getResource("increase.png"));
        Icon decreaseIcon = new ImageIcon(getClass().getResource("decrease.png"));
        Icon landIcon = new ImageIcon(getClass().getResource("land.png"));
        ////////

        //SETUP
        add (commandsPanel = new JPanel(), BorderLayout.WEST);
        commandsPanel.setBorder(BorderFactory.createTitledBorder("Commands"));
        Dimension dimension = commandsPanel.getPreferredSize();
        dimension.width = 195;
        dimension.height = 375;
        commandsPanel.setPreferredSize(dimension);


        commandsPanel.setLayout(new GridLayout(3,2, 5,5));
        commandsPanel.add(startBtn = new JButton("Start"),BorderLayout.CENTER);
        commandsPanel.add(takeOffBtn = new JButton("Take Off"));
        commandsPanel.add(stopBtn = new JButton("Stop"));
        commandsPanel.add(increaseAltitudeBtn = new JButton("Increase Alt"));
        commandsPanel.add(decreaseAltitudeBtn = new JButton("Decrease Alt"));
        commandsPanel.add(exitBtn = new JButton("Exit"));

        //LISTENERS
        Handler actionHandler = new Handler();
        startBtn.addActionListener(actionHandler);
        takeOffBtn.addActionListener(actionHandler);
        stopBtn.addActionListener(actionHandler);
        increaseAltitudeBtn.addActionListener(actionHandler);
        decreaseAltitudeBtn.addActionListener(actionHandler);
        exitBtn.addActionListener(actionHandler);

    }

    public void createConsoleComponents()
    {
        add(guiConsolePanel = new JPanel());
        guiConsolePanel.setBorder(BorderFactory.createTitledBorder("Airplane Log"));
        guiConsolePanel.add(consoleArea = new JTextArea(), BorderLayout.CENTER);
        File file = new File("src\\Airplane\\view\\img\\bg.png");
        //Dimension dimension = consoleArea.getPreferredSize();
        //dimension.width = 550;
        //dimension.height = 375;
        //consoleArea.setPreferredSize(dimension);
        consoleArea.setSize(550,375);
        consoleArea.setRows(24);
        consoleArea.setLineWrap(true);
        consoleArea.setEditable(false);
        consoleArea.setBackground(Color.BLACK);
        consoleArea.setForeground(Color.GREEN);

        JScrollPane scroll = new JScrollPane(consoleArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        guiConsolePanel.add(scroll);

        consoleArea.setText(airplaneServer.airplaneBoard());
        newAirplaneManagerGUI.setTextInConsole(consoleArea);
        fileManager.setTextInConsole(consoleArea);

        guiConsolePanel.add(clearConsoleBtn = new JButton("Refresh"));

        //LISTENERS
        clearConsoleBtn.addActionListener(e -> {
            consoleArea.setText("");
            consoleArea.setText(airplaneServer.airplaneBoard());
        });

        repaint();
        revalidate();

    }

    public void createAltitudeSliderCommands()
    {
        repaint();
        revalidate();
        //SETUP
        add (southPanel = new JPanel(), BorderLayout.SOUTH);
        southPanel.setBorder(BorderFactory.createTitledBorder("Altitude"));
        Dimension dimension = southPanel.getPreferredSize();
        dimension.height = 75;
        southPanel.setPreferredSize(dimension);
        southPanel.setLayout(new BoxLayout(southPanel,BoxLayout.Y_AXIS));

        southPanel.add(southWestPanel = new JPanel());
        //southWestPanel.setBackground(Color.blue);
        Dimension spDimension = southWestPanel.getPreferredSize();
        spDimension.height = 10;
        spDimension.width = 50;
        southWestPanel.setPreferredSize(spDimension);
        southWestPanel.add(progressBarLabel= new JLabel("Current: "));
        southWestPanel.add(altitudeProgressBar = new JProgressBar(0,12000));
        Dimension pbDimension = altitudeProgressBar.getPreferredSize();
        pbDimension.width = 700;
        altitudeProgressBar.setPreferredSize(pbDimension);
        //altitudeProgressBar.setStringPainted(true);
        altitudeProgressBar.setString("" + airplaneServer.getAltitude());

        southPanel.add(southEastPanel = new JPanel());
        Dimension seDimension = southEastPanel.getPreferredSize();
        seDimension.height = 10;
        seDimension.width = 50;
        southEastPanel.setPreferredSize(seDimension);
        southEastPanel.add(textArea= new JTextArea(1,50),BorderLayout.CENTER);
        textArea.setBackground(Color.black);
        textArea.setForeground(Color.green);
        textArea.setEditable(false);
        textArea.setText("                                                 " + airplaneServer.airplaneBoard());


        /*
        sliderPanel.add(sliderLabel = new JLabel("Value"));
        sliderPanel.add(textField = new JTextField("               "));
        sliderPanel.add(new JLabel("ft"));
        sliderPanel.add(altitudeSlider = new JSlider(JSlider.HORIZONTAL, 0, 12000 , 0));
        altitudeSlider.setMajorTickSpacing(3000);
        altitudeSlider.setMinorTickSpacing(1500);
        altitudeSlider.setPaintTicks(true);
        altitudeSlider.setPaintLabels(true);
        //altitudeSlider.setMaximumSize(dimension);
        */

       //southWestPanel.add(clearConsoleBtn = new JButton("Refresh"));

        //Dimension d = generalInfosArea.getPreferredSize();
        //dimension.width = 100;
        //dimension.height = 50;
        //generalInfosArea.setPreferredSize(dimension);
        //generalInfosArea.setLineWrap(true);

        //




/*
        altitudeSlider.addChangeListener(new ChangeListener()
        {

            @Override
            public void stateChanged(ChangeEvent e)
            {
                consoleArea.setForeground(Color.WHITE);
                JSlider source = (JSlider)e.getSource();
                try {
                    airplane.setAltitude(source.getValue());
                    //consoleArea.append(">>>" + LocalTime.now() + " : Altitude has increased, new altitude is: " + airplane.getAltitude() + "ft\n");
                    textField.setText(""+airplane.getAltitude());
                    //createFileLog.recordAirplane("\t" + time + " : Altitude has changed to: " + airplane.getAltitude() + "ft");

                } catch (AirplaneExplodedException | MotorIsNotStartedException | AirplaneNotInAirException | AltitudeDangerException | AirplaneBoomException |AirplaneAlreadyGroundedException exception) {
                    consoleArea.setForeground(Color.RED);
                    consoleArea.append(LocalTime.now()  + ": ERROR: " + exception.getMessage() + "\n");
                    System.err.println(exception.getMessage());
                }
                finally {
                    textField.setText(""+airplane.getAltitude());
                    //createFileLog.recordAirplane("\t" + LocalTime.now()  + " : Altitude has changed to: " + airplane.getAltitude() + "ft");

                    if (airplane.getAltitude() > airplane.MAX_SAFE_ALTITUDE)
                        textField.setForeground(Color.red);
                    else
                        textField.setForeground(Color.black);
                }

            }

        });
*/

    }



    private JMenuBar createMenuBar()
    {
        //SETUP
        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        menuBar.add (file);

        file.add(newAirplaneItem = new JMenuItem("New Airplane"));
        file.add(loadAirplaneItem = new JMenuItem("Load Airplane..."));
        file.add(readAirplaneItem = new JMenuItem("Read current Airplane"));
        file.add(deleteAirplaneItem = new JMenuItem("Erase current Airplane"));
        file.addSeparator();
        file.add(exitItem = new JMenuItem("Exit"));

        //LISTENERS
        newAirplaneItem.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                newAirplaneManagerGUI.setVisible(true);
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



        loadAirplaneItem.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                JFileChooser fileChooser = new JFileChooser();
                int userChoice = fileChooser.showOpenDialog(null);
                if (userChoice == JFileChooser.APPROVE_OPTION) {
                    consoleArea.append(">>>Reading: " + fileChooser.getSelectedFile().getName() + "\n");
                    String airplaneName = fileChooser.getSelectedFile().getName().replaceFirst("[.][^.]+$", "");
                    File file = fileChooser.getSelectedFile();
                    newAirplaneManagerGUI.logInToAirplane(airplaneName);
                    //createFileLog.openFile(file, airplaneName);

                    try (Scanner scanner = new Scanner (file)) {
                        //scanner.useDelimiter(":");
                        while (scanner.hasNextLine())
                        {
                            String line = scanner.nextLine();
                            //System.out.println(line);
                            String [] words = line.split(":");
                            if (words [0].equalsIgnoreCase("start")){
                                airplaneServer.startMotor();
                                consoleArea.append(">>>" + fileManager.toString() + " : Motor started\n");
                            }
                            else if (words [0].equalsIgnoreCase("fly")){
                                airplaneServer.takeOff();
                                consoleArea.append(">>>" + fileManager.toString() + " : has took off\n");
                            }
                            else if (words [0].equalsIgnoreCase("increase")){
                                if (words [1].equalsIgnoreCase("success")){
                                    airplaneServer.increaseAltitude();
                                    consoleArea.append(">>>" + fileManager.toString() + " : Altitude increased to : " + airplaneServer.getAltitude() + "\n");
                                }
                                if (words [1].equalsIgnoreCase("danger")) {
                                    airplaneServer.increaseAltitude();
                                    consoleArea.append(">>>" + fileManager.toString() + " : Unsafe altitude : " + airplaneServer.getAltitude() + "\n");
                                }
                            }
                            else if (words [0].equalsIgnoreCase("decrease")){
                                if (words [1].equalsIgnoreCase("success")){
                                    airplaneServer.decreaseAltitude();
                                    consoleArea.append(">>>" + fileManager.toString() + " : Altitude decreased to : " + airplaneServer.getAltitude() + "\n");
                                }
                                if (words [1].equalsIgnoreCase("danger")){
                                    airplaneServer.decreaseAltitude();
                                    consoleArea.append(">>>" + fileManager.toString() + " : Unsafe altitude : " + airplaneServer.getAltitude() + "\n");
                                }
                                if (airplaneServer.getAltitude() == 0)
                                    //
                                    consoleArea.append(">>>" + fileManager.toString() + " : has landed.\n");
                            }

                        }
                    } catch (FileNotFoundException | AirplaneException exception) {
                        consoleArea.append(">>>" + fileManager.toString() + " : " + exception.getMessage() + "\n");
                    }
                    finally {

                    }
                    consoleArea.append(airplaneServer.airplaneBoard());
                    consoleArea.append("=============================================\n");
                    JOptionPane.showMessageDialog(null, fileManager.toString() + " successfully loaded.");
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

        readAirplaneItem.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                fileManager.readAirplaneRecord();
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

        deleteAirplaneItem.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                int userChoice = JOptionPane.showConfirmDialog(null, "Clear current airplane log file?", "Warning", 0);
                if (userChoice == JOptionPane.YES_OPTION)
                {
                    consoleArea.append("Log file cleared!\n");
                    fileManager.clearAirplaneRecord();
                    JOptionPane.showMessageDialog(null, "Log successfully cleared!");
                    fileManager.readAirplaneRecord();
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

        exitItem.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                int userChoice = JOptionPane.showConfirmDialog(null,"Quit the application?", "Airplane controller", 0);
                if (userChoice == JOptionPane.YES_OPTION) {
                    fileManager.recordAirplane("\t" + LocalTime.now()  + " : Airplane logged out");
                    fileManager.recordAirplane("=============================================\n");
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


        //Return JMenuBar
        return menuBar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        consoleArea.setForeground(Color.green);
        textArea.setForeground(Color.green);
        try {
            if ( e.getSource() == externalControllerGUI.motorOffBtn) {
                airplaneServer.stopMotor();
                JOptionPane.showMessageDialog(getContentPane(), "Motor has stopped!");
                fileManager.recordAirplane("stop:success:FROM EXTERNAL CONTROLLER");
            }
            if (e.getSource() == externalControllerGUI.motorStartBtn) {
                arpController.startMotor();
                JOptionPane.showMessageDialog(getContentPane(), "Motor has started!");
                fileManager.recordAirplane("start:success:FROM EXTERNAL CONTROLLER");
            }
        } catch (AirplaneException exception) {
            consoleArea.setForeground(Color.red);
            textArea.setForeground(Color.red);
            consoleArea.append(LocalTime.now()  + ": ERROR: " + exception.getMessage() + "\n");
            fileManager.recordAirplane(exception.getMessage());
        }
    }


    @Override
    public void changeAltitude(int altitude) {
        consoleArea.append(">>>" + LocalTime.now()  + " : Altitude has increased, new altitude is: " + altitude + "ft\n");
        updateProgressBar(altitude);
        //textArea.setText("" + altitude);
        textArea.setText("                                                 " + airplaneServer.airplaneBoard());
    }




    private class Handler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
                try {
                    consoleArea.setForeground(Color.GREEN);
                    textArea.setForeground(Color.GREEN);

                    if (event.getSource() == startBtn) {
                        airplaneServer.startMotor();
                        JOptionPane.showMessageDialog(getContentPane(), "Motor has started!");
                        IO.println("\tMotor has started!");
                        consoleArea.append(">>>" + LocalTime.now() + " : Motor has started\n");
                        fileManager.recordAirplane("start:success");

                    } else if (event.getSource() == takeOffBtn) {
                        airplaneServer.takeOff();
                        fileManager.recordAirplane("fly:success");

                    } else if (event.getSource() == stopBtn) {
                        airplaneServer.stopMotor();
                        JOptionPane.showMessageDialog(getContentPane(), "Motor has stopped!");
                        consoleArea.append(">>>" + LocalTime.now() + " : Motor has stopped\n");
                        fileManager.recordAirplane("stop:success");

                    } else if (event.getSource() == increaseAltitudeBtn) {
                        airplaneServer.increaseAltitude();
                        fileManager.recordAirplane("increase:success:1000");

                    } else if (event.getSource() == decreaseAltitudeBtn) {
                        airplaneServer.decreaseAltitude();
                        fileManager.recordAirplane("decrease:success:1000");

                    } else if (event.getSource() == exitBtn) {
                        int userChoice = JOptionPane.showConfirmDialog(null, "Quit the program?", "Warning", 0);
                        if (userChoice == JOptionPane.YES_OPTION) {
                            IO.println("------------------");
                            IO.println("\tAirplane application terminated.");
                            consoleArea.append("=============================================\n");
                            fileManager.recordAirplane("exit:success");
                            fileManager.recordAirplane("=============================================\n");
                            System.exit(0);
                        }
                    }
                } catch (AirplaneException exception) {
                    consoleArea.setForeground(Color.RED);
                    consoleArea.append(LocalTime.now() + ": ERROR: " + exception.getMessage() + "\n");
                    textArea.setForeground(Color.RED);
                    fileManager.recordAirplane(exception.getMessage());
                } finally {
                    altitudeProgressBar.setValue((int) airplaneServer.getAltitude());
                    textArea.setText("                                                 " + airplaneServer.airplaneBoard());

                }
        }
    }
}
