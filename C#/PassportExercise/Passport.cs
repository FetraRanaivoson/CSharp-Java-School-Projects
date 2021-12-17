using System;
using System.Collections.Generic;
using System.Text;


namespace PassportExercise
{
    class Passport
    {
        private static int NextId = 0;

        //Automatic, manual properties
        public int Id {get;}
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public DateTime DateOfBirth {get;}

        public Address address;
        public Address Address { 
            
            get
            {
                return address;
            }
            set
            {
                if (value == null)
                    throw new ArgumentNullException("Address must not be null",nameof(Address));
                address = value;
            }
        
        }
        public List<TravelRecord> TravelHistory{ get;}
        public bool Traveling {get; private set;}
        


        //Calculated properties
        public string FullName 
        {
            //get => $"{FirstName} {LastName}";
            get => FirstName + " " + LastName;
        }
        //public string FullName => FirstName + " " + LastName;

        public void SetName (string firstName, string lastName)
        {
            if (string.IsNullOrWhiteSpace(firstName))
                throw new ArgumentException("First Name must not be null, empty or white space",nameof(firstName));
            if (string.IsNullOrWhiteSpace(lastName))
                throw new ArgumentException("Last Name must not be null, empty or white space", nameof(lastName));

            FirstName = firstName;
            LastName = lastName;
        
        }



        public int Age
        {
            get 
            {
                DateTime now = DateTime.UtcNow;
                TimeSpan age = now - DateOfBirth;
                //return age.Days / 365 ;
                return (int)age.TotalDays / 365;
            }
               
        }

        public string CurrentLocation
        {
            get => TravelHistory[(TravelHistory.Count) - 1].Country;
        }

        public DateTime TimeSinceLastTravel
        {
            get => TravelHistory[(TravelHistory.Count) - 1].TimeOfEntry;
        }




        public Passport (string firstName, string lastName, DateTime dateOfBirth, Address address)
        {
            Id = NextId ++;
            TravelHistory = new List<TravelRecord>()
            {
                new TravelRecord(Address.Country, DateTime.UtcNow)
            };
            Traveling = false;
            //TravelHistory.Add(new TravelRecord(Address.Country, DateTime.UtcNow));

            FirstName = firstName;
            LastName = lastName;
            DateOfBirth= dateOfBirth;
            Address = address;
        }

        public void Travel (string destinationCountry)
        {
            if (string.IsNullOrWhiteSpace(destinationCountry))
                throw new ArgumentException("Destination country must not be null, empty or white space.",nameof(destinationCountry));

            if (destinationCountry == Address.Country)
                Traveling = false;
            else
                Traveling = true;

            TravelRecord travelRecord = new TravelRecord(destinationCountry, DateTime.UtcNow);
            TravelHistory.Add(travelRecord);
            
        }

        public override string ToString()
        {
            return $"{Id} {FullName}";
        }

    }
}
