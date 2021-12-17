using System;
using System.Collections.Generic;
using System.Text;

namespace EmployeeExam.ViewModels
{
    public class AddEmployeeViewModel : ViewModel
    {
        private string firstName;
        public string FirstName
        {
            get => firstName;
            set
            {
                firstName = value;
                NotifyPropertyChanged(nameof(FirstName));
            }
        }
        private string lastName;
        public string LastName
        {
            get => lastName;
            set
            {
                lastName = value;
                NotifyPropertyChanged(nameof(LastName));
            }
        }

        private DateTime dateOfBirth;
        public DateTime DateOfBirth
        {
            get => dateOfBirth;
            set
            {
                dateOfBirth = value;
                NotifyPropertyChanged(nameof(DateOfBirth));
            }
        }

        private string jobTitle;
        public string JobTitle
        {
            get => jobTitle;
            set
            {
                jobTitle = value;
            }
        }

        private decimal hourlyWage;
        public decimal HourlyWage
        {
            get => hourlyWage;
            private set
            {
                hourlyWage = value;
                NotifyPropertyChanged(nameof(HourlyWage));
            }
        }
        public void ClearData()
        {
            FirstName = null;
            LastName = null;
            //DateOfBirth = null;
            JobTitle = null;
            //HourlyWage = null;
        }

    }
}
