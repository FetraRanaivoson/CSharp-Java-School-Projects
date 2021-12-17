using System;

namespace Exercise8
{
    class Execise8
    {
        static void Main()
        {
            int sum = Add(5,8,6,5,4,9,3,1,2,4);
            Console.WriteLine("Sum is:" + sum);
        }

        public static int Add (params int [] numbers)
        {
            int sum = 0;

            foreach (int number in numbers)
                sum += number;

            return sum;
        }

    }
}
