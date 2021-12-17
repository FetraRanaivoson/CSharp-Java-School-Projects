using System;
using System.Collections.Generic;
using System.Text;

namespace PassportExercise
{
    class PassportManager
    {
        private List<Passport> Passports;





        public PassportManager ()
        {
            Passports = new List<Passport>();
            
        }

        public void GetTravelingPassports ()
        {

        }
        public void GetNonTravelingPassports()
        {

        }

        public Passport GetPassport (int id)
        {
            Passport passports = null;

            foreach (Passport passport in Passports)
            {
                if (passport.Id == id)
                    passports = passport;
            }
            return passports;
        }

        public void AddPassport (Passport passport)
        {
            foreach (Passport passports in Passports)
            {
                if (passports.Id == passport.Id)
                    throw new ArgumentException("Passport Id must be unique.",nameof(passport));
            }
            Passports.Add(passport);
        }

        public void RemovePassport (int id)
        {
            
            
        }
    }

    
}
