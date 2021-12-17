using System;
using System.Collections.Generic;

namespace AdditionalQuestions
{
    class Program
    {
        static void Main(string[] args)
        {
            
        }


        public static double? GetClosest (List <double> listOfNumbers /*out double closest, out double farthest*/)
        {
            double listAverage = 0;
            double sumOfAllNumbers = 0;
            int totalNumber = 0;
            double minDifference = double.MaxValue;
            double? closest = null;

            foreach (double number in listOfNumbers)
            {
                sumOfAllNumbers += number;
                totalNumber++;
            }
            listAverage = sumOfAllNumbers / totalNumber;

            // Number closest to average 
            // = The number that has the smallest distance from the listAverage
            //for each number in listOfNumbers, calculate the distance between that number
            //and the listAverage: distance = Math.Abs(listAverage - number)

            //Put all the results into a List <double> listOfDistance
            //the distance: listOfDistance[i] corresponds to the number: listOfNumbers[i]

            List <double> listOfDistance = new List<double>();
            double distanceFromAverage = 0;
            foreach (double number in listOfNumbers)
            {
               distanceFromAverage = Math.Abs(listAverage - number);
               if (minDifference > distanceFromAverage)
                {
                    minDifference = distanceFromAverage;
                    closest = number;
                }
                //listOfDistance.Add(distanceFromAverage);           
            }

            return closest;

            //Sort
            //Finally get the closest number from average
        }


    }
}
