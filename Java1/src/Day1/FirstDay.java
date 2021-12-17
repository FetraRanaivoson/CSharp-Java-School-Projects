package Day1;

import java.sql.SQLOutput;
import java.util.Random;
import java.util.Scanner;

public class FirstDay {

    public static void main(String[] args) {

       int age = 27;
       float money = 23.45f;
       double morePrecision = 45.5664;





       boolean snake_case = true;
       boolean PascalCase = true;
       boolean happyOrNot = false;

       char sex = 'm'; //Any single ASCII value, it could be a '\n' (newline)

       String name = "Fetra";   //Immutable

       char [] myArray = {'F','e','t','r','a' }; //All arrays are Immutable


       System.out.println("Hello");
       System.out.print(age);
       System.out.print(", ");
       System.out.println(happyOrNot);
       System.out.println("Age: " + age);
       System.out.println(age + money);   //IMPLICIT CASTING

       System.out.println(age + (int)money); //EXPLICIT CASTING



       if (age == 45)
       {
          System.out.println("Here we go");
       }
       else if (age == 45)
       {
          System.out.println("Error"); //Skipped!
       }

       switch (age)
       {
          case 45:
             System.out.println(45);
             break;
          case 23:
             System.out.println(23);
             break;
          case 24:
             System.out.println(24);
             break;
          default:
             System.out.println("Else");
       }

       //FOR, WHILE, DO WHILE, FOR EACH ...


       printingAge();


       float floatResult = calcFloat(2.598f, 1.756f);
       float secondFloatResult = calcFloat(floatResult, 2f);
       System.out.println("Your float result is: " + floatResult);
       System.out.println("Your second float result is: " + secondFloatResult);

       print();
       //print("My age is: " + age + ", " + "my money is: " + money );

       Point pointD = new Point("Day1.Point D");
       pointD.printPoints();
       pointD.movePointsBy(5f,5f,5f);
    }

    ///////////////////////////////////////////////////////////METHODS//////////////////////////////////////////////////
   private static void printingAge() {
      Scanner scanner = new Scanner(System.in);

      System.out.print("What is your age: ");
      int userAge = scanner.nextInt();

      while (userAge < 0 || userAge > 100)
      {
         System.out.print("Enter a correct age: ");
         userAge = scanner.nextInt();
      }
      System.out.println("Age: " + userAge);
   }

   public static void something (int a)
    {
       System.out.println("Something A: " + a);
    }

   public static void something (String a)
   {
      System.out.println("Something A: " + a);
   }

   public static float calcFloat (float a, float b )
   {
      float result = a * b;
      return  result;
   }

   public static void print (String string)
   {
      System.out.println(string);
   }
   public static void print ()
   {
      Scanner scanner = new Scanner(System.in);
      print("Enter a string to print: ");
      String userString = scanner.next();
      print("Your string is: " + userString);
   }



   ///////////////////////////////////////////////////////////METHODS//////////////////////////////////////////////////


}
