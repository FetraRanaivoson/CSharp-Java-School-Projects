using System;

namespace Exercise7
{
    class Exercise7
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Enter an integer to double");
            int myInteger= int.Parse(Console.ReadLine());
            Double(ref myInteger);
            Console.WriteLine("My integer outside the method: " + myInteger);
        }

        public static void Double (ref int integer)
        {
            integer *= 2;
            Console.WriteLine("My integer inside the method: " + integer);
        }
    }
}
