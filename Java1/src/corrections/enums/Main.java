package corrections.enums;

import corrections.IO;
import corrections.enums.Airplane.Airplane;
import corrections.enums.AirplaneException.AirplaneException;

public class Main {
    public static void main(String[] args) {
        Airplane airplane = new Airplane();
        int choice = 0;
        boolean exit = false;

        do {
            System.out.println("-------------");
            System.out.println("1 – Start motor\n2 – Take off\n3 – Stop motor\n4 – Increase altitude\n5 – Decrease altitude\n6 – Exit");
            choice = IO.getInt();
            try {
                switch (choice) {
                    case 1:
                        airplane.StartMotor();  //If error, it would skip the everything in the try
                        System.out.println("Airplane Started");
                        break;
                    case 2:
                        airplane.TakeOff();
                        System.out.println("Airplane: We have take off.");
                        System.out.println("Airplane: Altitude increased to " + airplane.GetAltitude());
                        break;
                    case 3:
                        airplane.StopMotor();
                        System.out.println("Airplane: Stopping Motor");
                        break;
                    case 4:
                        airplane.IncreaseAltitude();
                        System.out.println("Airplane: Altitude increased to " + airplane.GetAltitude());
                        break;
                    case 5:
                        airplane.DecreaseAltitude();
                        System.out.println("Airplane: Altitude decreased to " + airplane.GetAltitude());
                        if (airplane.IsLanded())
                            System.out.println("Airplane: landed successfully");
                        break;
                    case 6:
                        exit = true;
                        break;
                        //return; //break would end switch but not the while(true). We can use return to end main function
                    default:
                        System.out.println("Wrong Input, please try again");
                }
            } catch (AirplaneException exception)
            //We do not catch Exception because it includes errors not related to airplane
            //We do not need to mention which exception as they all have message variable
            {
                System.out.println(exception.getMessage());

            }
        } while (!exit);
    }
}
