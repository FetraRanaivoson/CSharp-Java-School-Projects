package day3;

import java.util.Scanner;

public class ExceptionClass {
    public static void main(String[] args) {
        do {
            int userInt = 0;
            try
            {
                //Ask for an integer
                Scanner scanner = new Scanner(System.in);
                System.out.println("Please enter an integer: ");
                userInt = scanner.nextInt();
                System.out.println("Good choice!");
                break;

            } catch (Exception exception)
            {
                System.out.println(Float.intBitsToFloat(userInt) + " is not a valid integer!");
                System.err.println(exception.getMessage());
            }

        }while (true);

        System.out.println("Continue the program");
    }
}
