using System;

namespace PassportExercise
{
    class Program
    {
        static void Main()
        {
        
            Passport passport1 = new Passport
            (
                "Fetra", 
                "Ranaivoson", 
                new DateTime (1993,2,20) , 
                new Address (7, "Ampitatafika", "370007", "Tana", "Madagascar" ) 
            );

            Console.WriteLine(passport1.ToString());
            Console.WriteLine(passport1.Age);
           
            passport1.Travel("Canada");
            Console.WriteLine(passport1.TimeSinceLastTravel);
            passport1.Travel("Madagascar");
            Console.WriteLine(passport1.TimeSinceLastTravel);

            //Console.WriteLine(passport1.TravelHistory)
            foreach (TravelRecord travelRecord in passport1.TravelHistory)
            {
                Console.WriteLine(travelRecord);
            }

            bool isTraveling = passport1.Traveling;
            Console.WriteLine(isTraveling);
           
        }
    }
}