package Airplane.model;

import Airplane.view.AirplaneGUIv2;
import Airplane.view.NewAirplaneManagerGUI;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Date;


public class FileManager {
    private Date date = new Date();
    //private Formatter airplaneLogFile;
    private File file;
    //private  FileWriter fileWriter;

    //private PrintWriter printWriter;
    private Desktop desktop = Desktop.getDesktop();
    private boolean append = false;
    private String airplaneName = "DefaultAirplaneLog";
    private NewAirplaneManagerGUI newAirplaneManagerGUI;
    private AirplaneGUIv2 airplaneGUIv2;//
    private JTextArea consoleArea = new JTextArea();
    //private Scanner scanner;
    //private Airplane airplane = new Airplane(airplaneGUIv2,this);
    //private String[] words;


    public void createFile()
    {
        try
        {
            //airplaneLogFile = new Formatter("src\\Airplane\\AirplaneLog.txt");
            file = new File("src\\Airplane\\SavedAirplane\\" + airplaneName +".txt"); //Two ways to create a file
            //fileWriter = new FileWriter("NewLog.txt",true); //Two ways to create a file
            consoleArea.append("Airplane: " + this.airplaneName + " logged in on " + date + "\n" );
            consoleArea.append("=============================================\n");
            recordAirplane("=============================================");
            recordAirplane("Airplane " + this.airplaneName + " logged in on " + date);


        }
        catch (Exception e)
        {
            System.out.println("Error creating log file");
        }
    }

    public void createFile(String airplaneName)
    {
        try
        {
            this.airplaneName = airplaneName;

            //createAirplaneGUI.createNewAirplaneLog(this);
            //airplaneLogFile = new Formatter("src\\Airplane\\AirplaneLog.txt");
            file = new File("src\\Airplane\\SavedAirplane\\" + airplaneName +".txt"); //Two ways to create a file

            //fileWriter = new FileWriter("NewLog.txt",true); //Two ways to create a file
        }
        catch (Exception e)
        {
            System.out.println("Error creating log file");
        }
    }



    public void recordAirplane (String record)
    {
        //airplaneLogFile.format(record);
        try (PrintWriter out = new PrintWriter(new FileOutputStream(file, true))){;
            out.println(record);
        } catch (FileNotFoundException exception)
        {
            System.out.println("Error writing log file");
        }
    }

    public void recordAirplane(String record, boolean append)
    {
        try (PrintWriter out = new PrintWriter(new FileOutputStream(file, append))){;
            out.println(record);
    } catch (FileNotFoundException exception)
        {
            System.out.println("Error writing log file");
        }
    }

    public void clearAirplaneRecord ()
    {
        recordAirplane("",false);
    }

    public void readAirplaneRecord ()
    {
        try{
            if (!Desktop.isDesktopSupported())
            {
                System.out.println("Not supported");
                return;
            }
            if (file.exists())
                desktop.open(file);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setTextInConsole (JTextArea consoleArea)
    {
        this.consoleArea = consoleArea;
    }


    @Override
    public String toString() {
        return airplaneName;
    }


    public void openFile (File file, String airplaneName) {
        //Try resources method ==> No worry about closing files by scanner.close()



        /*
        try {
            //Open the file
            scanner = new Scanner(file);
            scanner.nextLine(); //Reads by line
        }
        catch(FileNotFoundException ioex) {
            ioex.printStackTrace();
        }
        finally {
            scanner.close(); //Important to close connection with the file.
        }
        */

    }

    public void executeCommandsFromFileManager(AirplaneGUIv2 airplaneGUIv2)
    {
        this.airplaneGUIv2= airplaneGUIv2;
    }


}
