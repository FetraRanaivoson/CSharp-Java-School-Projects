using System;

namespace Exercises
{
    class Exercise1
    {
        static void Main()
        {
            Console.WriteLine("Enter first number: ");
            int num1 = int.Parse(Console.ReadLine());

            Console.WriteLine("Enter second number: ");
            int num2 = int.Parse(Console.ReadLine());

            Console.WriteLine("Enter third number: ");
            int num3 = int.Parse(Console.ReadLine());

            int maxNumber = GetMax( GetMax(num1, num2), num3 );

            Console.WriteLine("Max number is: " + maxNumber);
        }

        public static int GetMax (int a, int b)
        {
            if (a < b)
                return b;
            else
                return a;
        }
    }
}
