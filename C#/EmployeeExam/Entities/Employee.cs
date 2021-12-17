using System;
using System.ComponentModel;

namespace EmployeeExam.Entities
{
    public class Employee : Entity, INotifyPropertyChanged
    {
        #region INotifyPropertyChanged
        public event PropertyChangedEventHandler PropertyChanged;
        public void NotifyPropertyChanged(string propertyName)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
        #endregion

        private string firstName;
        public string FirstName
        {
            get => firstName;
            set
            {
                firstName = value;
                NotifyPropertyChanged(nameof(FirstName));
                NotifyPropertyChanged(nameof(FullName));
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
                NotifyPropertyChanged(nameof(FullName));
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
                NotifyPropertyChanged(nameof(PaymentDue));
            }
        }

        private decimal hoursWorked;
        public decimal HoursWorked
        {
            get => hoursWorked;
            private set
            {
                hoursWorked = value;
                NotifyPropertyChanged(nameof(HoursWorked));
                NotifyPropertyChanged(nameof(HoursUnpaid));
                NotifyPropertyChanged(nameof(PaymentDue));
            }
        }

        private decimal hoursPaid;
        public decimal HoursPaid
        {
            get => hoursPaid;
            private set
            {
                hoursPaid = value;
                NotifyPropertyChanged(nameof(HoursPaid));
                NotifyPropertyChanged(nameof(HoursUnpaid));
                NotifyPropertyChanged(nameof(PaymentDue));
            }
        }

        private decimal paymentReceived;
        public decimal PaymentReceived
        {
            get => paymentReceived;
            private set
            {
                paymentReceived = value;
                NotifyPropertyChanged(nameof(PaymentReceived));
            }
        }

        // TODO: 3 calculated properties
        // Full name
        // Hours unpaid
        // Payment due

        
        public string FullName 
        {
            get => FirstName + " " + LastName;
        }

        
        public decimal HoursUnpaid
        {
            get => HoursWorked - HoursPaid ;
        }

        
        public decimal PaymentDue
        {
            get => HoursUnpaid * HourlyWage;
        }



        
        public Employee(string firstName, string lastName, DateTime dateOfBirth, string jobTitle,
                        decimal hourlyWage, decimal hoursWorked, decimal hoursPaid, decimal paymentReceived)
            : this(default, default, default, firstName, lastName, dateOfBirth, jobTitle, hourlyWage, hoursWorked, hoursPaid, paymentReceived)
        { }

        public Employee(long id, DateTime dateCreated, DateTime dateModified,
                        string firstName, string lastName, DateTime dateOfBirth, string jobTitle,
                        decimal hourlyWage, decimal hoursWorked, decimal hoursPaid, decimal paymentReceived)
            : base(id, dateCreated, dateModified)
        {
            FirstName = firstName;
            LastName = lastName;
            DateOfBirth = dateOfBirth;
            JobTitle = jobTitle;
            HourlyWage = hourlyWage;
            HoursWorked = hoursWorked;
            HoursPaid = hoursPaid;
            PaymentReceived = paymentReceived;
        }

        public bool LogHours(decimal additionalHoursWorked)
        {
            if (additionalHoursWorked >= 0)
            {
                HoursWorked += additionalHoursWorked;
                return true;
            }
            return false;
        }

        public bool GiveRaise(decimal raisePercentage)
        {
            if (raisePercentage > 0)
            {
                HourlyWage += HourlyWage * raisePercentage;
                return true;
            }
            return false;
        }

        public bool PayAmountDue()
        {
            // TODO
            return false;
        }

       
    }
}
