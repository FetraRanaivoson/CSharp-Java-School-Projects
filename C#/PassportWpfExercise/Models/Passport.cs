using System;

namespace PassportApp.Models
{
    public class Passport
    {
        public int Id { get; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public DateTime DateOfBirth { get; }
        public double Height { get; set; }
        public string Country { get; set; }

        public Passport(int id, string firstName, string lastName, DateTime dateOfBirth, double height, string country)
        {
            Id = id;
            FirstName = firstName;
            LastName = lastName;
            DateOfBirth = dateOfBirth;
            Height = height;
            Country = country;
        }
    }
}
