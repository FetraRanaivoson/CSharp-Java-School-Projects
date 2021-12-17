using System;

namespace Exercise4
{
    class Exercise4
    {
        static void Main(string[] args)
        {
            int[] myNumberArray = new int[6] {1,2,3,4,5,6};
            Console.WriteLine(checkNeighbors(5, myNumberArray));
        }

        public static bool checkNeighbors (int element, int [] numberArray)
        {
            if (numberArray.Length == 0)
                throw new ArgumentException("Numbers array must not be empty", nameof(numberArray));

            if (element < 0 || element >= numberArray.Length)
                throw new ArgumentOutOfRangeException("Index must be between 0 and ", nameof(element));

   
            if (element == 0)
            {
                if (numberArray[0] > numberArray[1] && numberArray[0] > numberArray[numberArray.Length - 1])
                    return true;
                return false;
            }
            if (element == numberArray.Length - 1)
            {
                if (numberArray[numberArray.Length - 1] > numberArray[numberArray.Length - 2] && numberArray[numberArray.Length - 1] > numberArray[0])
                    return true;
                return false;
            }
            else
            {
                if (numberArray[element] > numberArray[element - 1] && numberArray[element] > numberArray[element + 1])
                    return true;
                return false;
            }
        }

    }
}
