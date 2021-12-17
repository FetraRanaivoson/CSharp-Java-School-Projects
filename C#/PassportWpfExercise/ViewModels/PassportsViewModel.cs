using System;
using System.Collections.ObjectModel;
using System.ComponentModel;
using PassportApp.Models;

namespace PassportApp.ViewModels
{
    public class PassportsViewModel : INotifyPropertyChanged
    {
        #region INotifyPropertyChanged
        public event PropertyChangedEventHandler PropertyChanged;

        private void NotifyPropertyChanged(string propertyName)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
        #endregion

        private int nextId;

        public ObservableCollection<Passport> Passports { get; }

        private Passport selectedPassport;
        public Passport SelectedPassport
        {
            get => selectedPassport;
            set
            {
                selectedPassport = value;
                NotifyPropertyChanged(nameof(SelectedPassport));
            }
        }

        public AddPassportViewModel AddPassportViewModel { get; }
        
        public PassportsViewModel()
        {
            nextId = 10001;
            Passports = CreatePassports();
            AddPassportViewModel = new AddPassportViewModel();
        }

        private ObservableCollection<Passport> CreatePassports()
        {
            return new ObservableCollection<Passport>
            {
                new Passport(GetUniqueId(), "Amandeep", "Kaur", new DateTime(1998, 02, 28), 1.4, "Canada"),
                new Passport(GetUniqueId(), "Christophe", "Duval", new DateTime(1973, 09, 12), 1.9, "France"),
                new Passport(GetUniqueId(), "Madeleine", "Smith", new DateTime(1993, 11, 19), 1.6, "USA"),
                new Passport(GetUniqueId(), "Ashwin", "Patel", new DateTime(1995, 06, 04), 1.7, "India")
            };
        }

        private int GetUniqueId()
        {
            return nextId++;
        }
    }
}
