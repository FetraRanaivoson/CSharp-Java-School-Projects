using System;
using System.ComponentModel;

namespace PassportApp.ViewModels
{
    public class AddPassportViewModel : INotifyPropertyChanged
    {
        #region INotifyPropertyChanged
        public event PropertyChangedEventHandler PropertyChanged;
        private void NotifyPropertyChanged(string propertyName)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
        #endregion

        private string firstName;
        public string FirstName
        {
            get
            {
                return firstName;
            }
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

        private DateTime? dateOfBirth;
        public DateTime? DateOfBirth
        {
            get => dateOfBirth;
            set
            {
                dateOfBirth = value;
                NotifyPropertyChanged(nameof(DateOfBirth));
            }
        }

        private double? height;
        public double? Height
        {
            get => height;
            set
            {
                height = value;
                NotifyPropertyChanged(nameof(Height));
            }
        }

        private string country;
        public string Country
        {
            get => country;
            set
            {
                country = value;
                NotifyPropertyChanged(nameof(Country));
            }
        }
    }
}
