using System;
using System.Collections.Generic;
using System.Text;

namespace PassportExercise
{
    class TravelRecord
    {
        public string Country { get; set; }
        public DateTime TimeOfEntry { get; set; }

        public TravelRecord (string country, DateTime timeOfEntry)
        {
            Country = country;
            TimeOfEntry = timeOfEntry;
          
        }

        public override string ToString()
        {
            return Country + "," + TimeOfEntry ;
        }

        
    }

}
