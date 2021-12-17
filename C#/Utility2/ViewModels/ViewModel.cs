using System.ComponentModel;

namespace Utility.ViewModels
{
    public class ViewModel : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;

        protected void NotifyPropertyChanged(string propertyName)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

        private bool visible;
        public bool Visible
        {
            get => visible;
            set
            {
                visible = value;
                NotifyPropertyChanged(nameof(Visible));
            }
        }
    }
}
