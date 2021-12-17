package guiClient.view;

import Airplane.model.AirplaneServer;
import Airplane.model.FileManager;

import javax.swing.*;
import java.time.LocalTime;
import java.util.Date;


public class NewAirplaneManagerGUI extends JFrame {

    private JButton createNewAirplaneBtn;
    private JButton loadAirplaneBtn;
    private JPanel jPanel;
    private FileManager fileManager = new FileManager();
    JTextField airplaneName = new JTextField();
    private Date date = new Date();
    private LocalTime time = LocalTime.now();
    private String airplane;
    private airplaneGUIv2Client airplaneGUIv2Client;
    private AirplaneServer airplaneServerModel = new AirplaneServer();
    private JTextArea consoleArea = new JTextArea();
    private double defaultAltitude = 0;

    public NewAirplaneManagerGUI()
    {
        //SET UP
        setTitle("Create Airplane");
        setSize(400,150);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        createComponents();
        setVisible(false);
    }

    public void createComponents()
    {
        add(jPanel = new JPanel());
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.add(createNewAirplaneBtn = new JButton("Create New Airplane"));
        jPanel.add(loadAirplaneBtn = new JButton("Load Airplane"));
        createNewAirplaneBtn.setAlignmentX(CENTER_ALIGNMENT);
        loadAirplaneBtn.setAlignmentX(CENTER_ALIGNMENT);

        final JComponent[] inputs = new JComponent[] {
                new JLabel("Choose an airplane name"), airplaneName
        };

        //Creating new Airplane file process
        createNewAirplaneBtn.addActionListener(e -> {
            this.airplane = (String) JOptionPane.showInputDialog(null,"Set up the airplane name", "myAirplane");
            fileManager.createFile(airplane);
            consoleArea.append("Airplane: " + airplane + " logged in on " + date + "\n" );
            consoleArea.append("=============================================\n");
            fileManager.recordAirplane("=============================================");
            fileManager.recordAirplane("Airplane " + airplane + " logged in on " + date);
            airplaneGUIv2Client.setTitle("Airplane Controller" + "(" + getAirplaneName(fileManager) + ")");

            //Revert the airplane to its default state
            setDefaultAirplaneState(0.0);
            System.out.println("airplane: " + airplaneServerModel.getAltitude());

            JOptionPane.showMessageDialog(null,getAirplaneName(fileManager) + " successfully created.");
            this.setVisible(false);


        });

        loadAirplaneBtn.addActionListener(e -> {

        });


    }

    public void logInToAirplane (String airplaneName)
    {
        fileManager.createFile(airplaneName);
        consoleArea.append("Airplane: " + airplaneName + " logged in on " + date + "\n" );
        consoleArea.append("=============================================\n");
        fileManager.recordAirplane("=============================================");
        fileManager.recordAirplane("Airplane " + airplaneName + " logged in on " + date);
        airplaneGUIv2Client.setTitle("Airplane Controller" + "(" + getAirplaneName(fileManager) + ")");
    }


    //Responsible for communication to the GUI, file manager, consoleGUI, ...
    public void createNewAirplaneLog (FileManager fileManager)
    {
        this.fileManager = fileManager;
    }
    public String getAirplaneName(FileManager fileManager)
    {
        return fileManager.toString();
    }
    public void setTitle (airplaneGUIv2Client airplaneGUIv2Client)
    {
        this.airplaneGUIv2Client = airplaneGUIv2Client;
    }
    public void setTextInConsole (JTextArea consoleArea)
    {
        this.consoleArea = consoleArea;
    }

    //For the external controller test
    public void setDefaultAirplaneState(Double altitude) //Will notify AirplaneGuiV2 (listener) to change their altitude to 0 and stop the motor
    {
        this.defaultAltitude = altitude;
        airplaneGUIv2Client.setAltitudeToDefault(defaultAltitude); // airplaneGuiV2 (listener) method
        airplaneGUIv2Client.setMotorOff(); // airplaneGuiV2 (listener) method
    }
}
