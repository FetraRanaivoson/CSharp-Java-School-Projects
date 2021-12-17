package Airplane.controller;

import Airplane.model.AirplaneServer;
import Airplane.view.AirplaneGUIv2;

public class MainServer {
    public static void main(String[] args) {

        AirplaneServer server = new AirplaneServer();
        server.runAirplaneServer();



        //new AirplaneGUIv2();



        //AirplaneGUIv2 gui = new AirplaneGUIv2();
        //airplane.addAirplaneListener(gui);




 /*
        Airplane airplane = new Airplane();

        int choice = 0;

        while (true) {

            displayOptions(airplane);
            IO.println("------------------");


            IO.println("Select a command (1-6): ");
            choice = IO.getInt();

            try {

                switch (choice) {
                    case 1:
                        airplane.startMotor();
                        IO.println("\tMotor has started!");
                        break;

                    case 2:
                        airplane.takeOff();
                        IO.println("\tAirplane's altitude: " + airplane.getAltitude() + " ft");
                        break;

                    case 3:
                        airplane.stopMotor();
                        IO.println("\tMotor has stopped!");
                        break;


                    case 4:
                        airplane.increaseAltitude();
                        IO.println("\tAirplane's altitude: " + airplane.getAltitude() + " ft");
                        break;


                    case 5:
                        airplane.decreaseAltitude();
                        if (airplane.getAltitude() == 0)
                            IO.println("\tAirplane successfully landed on the ground.");
                        else if (airplane.getAltitude() != 0)
                            IO.println("\tAirplane's altitude: " + airplane.getAltitude() + " ft");
                        break;

                    case 6:
                        IO.println("------------------");
                        IO.println("\tAirplane application terminated.");
                        return;

                    default:
                        IO.println("\tInvalid input!");
                }
            }
            catch (AirplaneExplodedException | MotorHasAlreadyStartedException | MotorIsNotStartedException | AirplaneAlreadyTookOffException | CannotStopFlyingAirplaneException | MotorHasAlreadyStoppedException |AirplaneNotInAirException | AltitudeDangerException | AirplaneBoomException |AirplaneAlreadyGroundedException exception)
            {
                System.err.println(exception.getMessage());
            }
        }
*/

    }

/*
    public static void displayOptions(Airplane airplane) {

        IO.println("------------------");
        IO.println("1  |>  Start motor");
        IO.println("2  /   Take off");
        IO.println("3  |X| Stop motor");
        IO.println("4  +   Increase altitude");
        IO.println("5  -   Decrease altitude");
        IO.println("6  x   Exit ");
        airplane.airplaneBoard();

    }

 */


}
