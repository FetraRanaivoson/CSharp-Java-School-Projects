using System;
using System.Collections.Generic;
using System.Text;

namespace PassportApp
{
    class Passport
    {
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public DateTime DateOfBirth { get; set; }
        public double Height { get; set; }
        public string Country { get; set; }


        public Passport (string firstName, string lastName, DateTime dateOfBirth, double height, string country)
        {
            FirstName = firstName;
            LastName = lastName;
            DateOfBirth = dateOfBirth;
            Height = height;
            Country = country;
        }
    }
}
