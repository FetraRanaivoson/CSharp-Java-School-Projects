using System;
using System.Collections.Generic;
using System.Text;

namespace Exercises
{
    class Exercise2
    {
        static void Main()
        {
            Console.WriteLine("Write the number");
            int userNumber = int.Parse(Console.ReadLine());
            Console.WriteLine("English name is: " + GetEnglishNameOf(userNumber));

        }
        public static string GetEnglishNameOf(int number)
        {
            switch (Math.Abs(number % 10))
            {
                case 0:
                    return "Zero";
                case 1:
                    return "One";
                case 2:
                    return "Two";
                case 3:
                    return "Three";
                case 4:
                    return "Four";
                case 5:
                    return "Five";
                case 6:
                    return "Six";
                case 7:
                    return "Seven";
                case 8:
                    return "Eight";
                case 9:
                    return "Nine";
                default:
                    throw new InvalidProgramException("Last digit must be between 0 and 9");
            }
        }

    }

}
