using System;
using System.Collections.Generic;
using System.Text;

namespace PassportExercise
{
    class Address
    {
        public int StreetNumber { get; }
        public string StreetName { get; }
        public string PostalCode { get; }
        public string City { get; }
        public string Country { get; }

        public Address(int streetNumber, string streetName, string postalCode, string city, string country)
        {
            Validate(streetNumber, streetName, postalCode, city, country);

            StreetNumber = streetNumber;
            StreetName = streetName;
            PostalCode = postalCode;
            City = city;
            Country = country;
        }

        private static void Validate (int streetNumber, string streetName, string postalCode, string city, string country)
        {
            if (streetNumber <= 0)
                throw new ArgumentException("Street number must be greater than 0.", nameof(streetNumber));
            if (string.IsNullOrWhiteSpace(streetName))
                throw new ArgumentException("Street name must not be null, empty, or whitespace.", nameof(streetName));
            if (string.IsNullOrWhiteSpace(postalCode))
                throw new ArgumentException("Postal code must not be null, empty, or whitespace.", nameof(postalCode));
            if (postalCode.Length != 6)
                throw new ArgumentException("Postal code must contain exactly 6 characters.", nameof(postalCode));
            if (string.IsNullOrWhiteSpace(city))
                throw new ArgumentException("City must not be null, empty, or whitespace.", nameof(city));
            if (string.IsNullOrWhiteSpace(country))
                throw new ArgumentException("Country must not be null, empty, or whitespace.", nameof(country));
        }
         


        public override string ToString()
        {
            return $"{StreetNumber} {StreetName}, {City}, {Country}, {PostalCode}"; // $ " ... "
        }
    }
}
