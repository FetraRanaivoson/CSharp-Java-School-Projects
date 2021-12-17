//Author: Michael Roshin
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Text;
using Utility.Authentication;

namespace ShoppingApp.Entities
{
    public class Admin : Entity, INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
        protected void NotifyPropertyChanged(string propertyName)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

        private string userName;
        public string UserName
        {
            get => userName;
            set
            {
                userName = value;
                NotifyPropertyChanged(nameof(UserName));
            }
        }

        public PasswordHash Password { get; set; }
        public byte[] Salt => Password.Salt;
        public byte[] Hash => Password.Hash;

    }
}
