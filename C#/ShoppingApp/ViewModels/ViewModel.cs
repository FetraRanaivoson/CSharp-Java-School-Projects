//Author: Fetra Ranaivoson
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Text;

namespace ShoppingApp.ViewModels
{
    public class ViewModel : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
        protected void NotifyPropertyChanged(string propertyName)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}
