using System;

namespace Exercise3
{
    class Exercise3
    {
        static void Main(string[] args)
        {
            int[] myNumberArray = new int[6];

            Console.WriteLine("Enter 6 numbers: ");
            for (int i = 0; i < 6; i++)
            {
                myNumberArray[i] = int.Parse(Console.ReadLine());
            }
            Console.WriteLine("Enter the number to check for occurrence");
            int numberToCheck = int.Parse(Console.ReadLine());

            Console.WriteLine("Occurence is: " + GetOccurrences(numberToCheck, myNumberArray));
        }

        public static int GetOccurrences (int numberToFind, int [] numberArray)
        {
            int counter = 0; 
            foreach (int integer in numberArray)
            {
                if (integer == numberToFind)
                    counter++;
            }
            return counter;
        }
    }
}
