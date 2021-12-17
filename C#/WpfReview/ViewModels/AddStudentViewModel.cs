using System;
using System.Collections.Generic;
using System.Text;

namespace WpfReview.ViewModels
{
    public class AddStudentViewModel: ViewModel
    {
        public int Id { get; }

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

        public void ClearData()
        {
            FirstName = null;
            LastName = null;

        }
    }
}

