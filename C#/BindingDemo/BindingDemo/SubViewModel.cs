using System.ComponentModel;

namespace BindingDemo
{
    public class SubViewModel : ViewModel, INotifyPropertyChanged
    {
        private string name;
        public string Name
        {
            get => name;
            set
            {
                name = value;
                NotifyPropertyChanged(nameof(Name));
            }
        }
    }
}
