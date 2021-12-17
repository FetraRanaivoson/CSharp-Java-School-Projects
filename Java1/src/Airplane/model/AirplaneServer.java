package Airplane.model;

import Airplane.model.Exception.*;
import Airplane.view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class AirplaneServer extends JFrame implements AirplaneView {

    private double altitude = 0;
    private final double ALTITUDE_INCREMENT = 1000;
    public final double MAX_SAFE_ALTITUDE = 10000;
    private final double ALTITUDE_OF_EXPLOSION = 12000;
    private AirplaneState status = AirplaneState.ENGINE_OFF;
    private AirplaneGUIv2 airplaneGuiv2 ;
    private FileManager fileManager;

    public JLabel connectionLabel;
    public JTextField connectionInfo;
    public JButton startBtn;
    public JButton takeOffBtn;
    public JButton stopBtn;
    public JButton increaseAltitudeBtn;
    public JButton decreaseAltitudeBtn;
    public JButton exitBtn;

    //Server setting
    private ObjectOutputStream instructAirplane;
    private ObjectInputStream getInstruction;
    private ServerSocket server;
    private Socket connection;
    private String message;

    //Threads
    private ArrayList <AltitudeObserver> observers = new ArrayList<>();
    private Thread increaseThread;
    private int currentAltitude;
    private Thread decreaseThread;

    public void addAltitudeObserver (AltitudeObserver observer)
    {
        observers.add (observer);
    }
    private void notifyObservers ()
    {
        for (AltitudeObserver observer : observers)
        {
            observer.changeAltitude((int)getAltitude());
        }
    }

    public AirplaneServer()
    {

        super("Airplane Server");
        setLayout(new FlowLayout());
        add(startBtn = new JButton("Start"),BorderLayout.CENTER);
        add(takeOffBtn = new JButton("Take Off"));
        add(stopBtn = new JButton("Stop"));
        add(increaseAltitudeBtn = new JButton("Increase Alt"));
        add(decreaseAltitudeBtn = new JButton("Decrease Alt"));
        add(exitBtn = new JButton("Exit"));
        add (connectionLabel = new JLabel("Connection status: "));
        add(connectionInfo = new JTextField(25));
        connectionInfo.setText("Not connected to an Airplane");
        connectionInfo.setEditable(false);
        setSize(550,200);
        setBounds(25,500,550,100);
        setResizable(false);
        setAlwaysOnTop(true);
        setVisible(true);


        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Thread thread = new Thread(() -> {
                    sendInstruction(e.getActionCommand());
                });
               thread.start();
            }
        });

        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread thread = new Thread(() -> {
                    sendInstruction(e.getActionCommand());
                });
                thread.start();
            }
        });

        increaseAltitudeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread thread = new Thread(() -> {
                    sendInstruction(e.getActionCommand());
                    airplaneGuiv2.updateProgressBar((int)getAltitude());
                });
                thread.start();
            }
        });

        takeOffBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread thread = new Thread(() -> {
                    sendInstruction(e.getActionCommand());
                    airplaneGuiv2.updateProgressBar((int)getAltitude());
                });
                thread.start();
            }
        });

        decreaseAltitudeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread thread = new Thread(() -> {
                    sendInstruction(e.getActionCommand());
                    airplaneGuiv2.updateProgressBar((int)getAltitude());
                });
                thread.start();
            }
        });

    }


    private void sendInstruction (String message)
    {
        try {
            instructAirplane.writeObject(message);
            try {
                message = (String) getInstruction.readObject();
            } catch (ClassNotFoundException e) {
                System.out.println("Unknown instruction!");
            }
        }catch (IOException ioException){
            System.out.println("Error: cannot send instruction!");
        }
    }


    public void runAirplaneServer ()
    {
        try {
            server = new ServerSocket(7575,100);
            while (true) {
                try {
                    waitForConnection();
                    setupStreams();
                    executeCommands();
                } catch (EOFException eof) {
                    System.out.println("Server ended connection!");
                } finally {
                    closeServer();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForConnection () throws IOException {
        connectionInfo.setText("Waiting for an airplane");
        connection = server.accept();
        connectionInfo.setText("Connected to" + connection.getInetAddress().getHostName());
        connectionInfo.setEditable(true);
        connectionInfo.setBackground(Color.green);
    }

    private void setupStreams () throws IOException {
        instructAirplane = new ObjectOutputStream(connection.getOutputStream());
        instructAirplane.flush();
        getInstruction = new ObjectInputStream(connection.getInputStream());
        System.out.println("Setup successful");
    }
    private void executeCommands () throws IOException {
        Scanner string = new Scanner(System.in);
        String choice;
        do {
            choice = string.next();
            System.out.println(choice);
            sendInstruction(choice);
        } while (choice != "stop");
    }
    private  void closeServer () {
        connectionInfo.setText("Connection ended.\n");
        connectionInfo.setEditable(false);
        try {
            instructAirplane.close();
            getInstruction.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public AirplaneServer(AirplaneGUIv2 airplaneGUIv2, FileManager fileManager)
    {
        this.airplaneGuiv2 = airplaneGUIv2;
        this.fileManager = fileManager;
        fileManager.executeCommandsFromFileManager(airplaneGuiv2);
    }

    public void startMotor() throws AirplaneExplodedException, MotorHasAlreadyStartedException
    {

        if (status == AirplaneState.EXPLODED)
            throw new AirplaneExplodedException("start:failed:exploded");

        else if (status == AirplaneState.ENGINE_OFF) {  //isMotorOn == false
            status = AirplaneState.ENGINE_ON;
            //isMotorOn = true;
        }
        else
            throw new MotorHasAlreadyStartedException("start:failed:motorOn");

    }


    public void stopMotor() throws AirplaneExplodedException, CannotStopFlyingAirplaneException, MotorHasAlreadyStoppedException
    {

        if (status == AirplaneState.EXPLODED)
            throw new AirplaneExplodedException("stop:failed:exploded");

        else if (status == AirplaneState.ENGINE_ON) {
            status = AirplaneState.ENGINE_OFF;
        }

        else if (status == AirplaneState.FLYING)
            throw new CannotStopFlyingAirplaneException("stop:failed:flying");

        else if (status == AirplaneState.ENGINE_OFF)
            throw new MotorHasAlreadyStoppedException("stop:failed:motorOff");

    }

    //Notifier
    public void increaseAltitude() throws AirplaneExplodedException, MotorIsNotStartedException, AirplaneNotInAirException ,AltitudeDangerException, AirplaneBoomException
    {

        if (status == AirplaneState.EXPLODED)
            throw new AirplaneExplodedException("increase:failed");

        else if (status == AirplaneState.ENGINE_OFF)
            throw new MotorIsNotStartedException("increase:failed:motorOff");

        else if (status == AirplaneState.ENGINE_ON)
            throw new AirplaneNotInAirException("increase:failed:notFlying");
            //altitude += ALTITUDE_INCREMENT;
        else {
            //altitude += ALTITUDE_INCREMENT;
            if (increaseThread != null && increaseThread.isAlive())
            {
                System.out.println("Only 1 thread allowed");
                return;
            }
            increaseThread = new Thread(() -> {
                currentAltitude = (int) getAltitude();
                int targetAltitude = currentAltitude + 1000;
                while (currentAltitude != targetAltitude) {
                    currentAltitude++;
                    setAltitude(currentAltitude);
                    notifyObservers();
                    try {
                        checkAltitude();
                    } catch (AltitudeDangerException | AirplaneBoomException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            increaseThread.start();
        }

    }




    public void decreaseAltitude() throws AirplaneExplodedException, AirplaneAlreadyGroundedException, AltitudeDangerException, AirplaneBoomException {

        if (status == AirplaneState.EXPLODED)
            throw new AirplaneExplodedException("decrease::failed:exploded");

        else if (altitude == 0)
            throw new AirplaneAlreadyGroundedException("decrease:failed:landed");

        else if (status == AirplaneState.FLYING) {
            //altitude -= ALTITUDE_INCREMENT;

            if (decreaseThread != null && decreaseThread.isAlive()) {
                System.out.println("Only 1 thread allowed");
                return;
            }

            decreaseThread = new Thread(() -> {
                currentAltitude = (int) getAltitude();
                int targetAltitude = currentAltitude - 1000;
                while (currentAltitude != targetAltitude) {
                    currentAltitude--;
                    setAltitude(currentAltitude);
                    notifyObservers();
                    try {
                        checkAltitude();
                    } catch (AltitudeDangerException | AirplaneBoomException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            decreaseThread.start();
        }

    }



    public void takeOff () throws AirplaneExplodedException, MotorIsNotStartedException, AirplaneAlreadyTookOffException
    {

        if (status == AirplaneState.EXPLODED)
            throw new AirplaneExplodedException("takeoff:failed:exploded");

        else if (status == AirplaneState.ENGINE_OFF)
            throw new MotorIsNotStartedException("takeoff:failed:motorOff");

        else if (status == AirplaneState.FLYING)
            throw new AirplaneAlreadyTookOffException("takeoff:failed:flying");

        else if (status == AirplaneState.ENGINE_ON)
        {
            status = AirplaneState.FLYING;
            //altitude += ALTITUDE_INCREMENT;
            if (increaseThread != null && increaseThread.isAlive())
            {
                System.out.println("Only 1 thread allowed");
                return;
            }

            increaseThread = new Thread(() -> {
                currentAltitude = (int) getAltitude();
                int targetAltitude = currentAltitude + 1000;
                while (currentAltitude != targetAltitude) {
                    currentAltitude++;
                    setAltitude(currentAltitude);
                    notifyObservers();
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            increaseThread.start();
        }
    }

    //Subscriber/Listener methods
    @Override
    public double getAltitude()
    {
        return this.altitude;
    }



    public void setAltitude(double altitude) //Subscriber/Listener methods
    {

            this.altitude = altitude;

        //checkAltitude();
    }

    public void setMotorOff()               //Subscriber/Listener methods
    {
        this.status = AirplaneState.ENGINE_OFF;
    }



    private void checkAltitude() throws AltitudeDangerException, AirplaneBoomException
    {


        if (getAltitude() >= MAX_SAFE_ALTITUDE && getAltitude() < ALTITUDE_OF_EXPLOSION) {
            throw new AltitudeDangerException("increase:danger:1000");
        }
        else if (altitude == ALTITUDE_OF_EXPLOSION )
        {
            altitude = 0;
            status = AirplaneState.EXPLODED;
            //this.isMotorOn = false;
            //hasNotExploded = false;

            throw new AirplaneBoomException("boom");
        }
        else if (altitude <= 0)
        {
            altitude = 0;
            status = AirplaneState.ENGINE_ON;
        }


    }

    @Override
    public String engineState()
    {
        if (status == AirplaneState.ENGINE_OFF || status == AirplaneState.EXPLODED)
            return "Off";
        else if (status == AirplaneState.ENGINE_ON || status == AirplaneState.FLYING)
            return "On";
        return null;
    }

    @Override
    public String airplaneCondition()
    {
        if (status == AirplaneState.EXPLODED)
            return "Exploded";
        else if (getAltitude() >= MAX_SAFE_ALTITUDE)
            return "Critical";
        return "Stable";

    }

    //public boolean isHasNotExploded()
    //{
    //    return hasNotExploded;
    //}

    @Override
    public String airplaneBoard()
    {
        return "Motor: " + engineState() + "  \\  " + "Altitude: " + getAltitude() + " ft" + "  \\  " + "Airplane's condition: " + airplaneCondition() + "\n";

    }



}
