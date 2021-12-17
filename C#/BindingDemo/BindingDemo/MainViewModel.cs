using System.ComponentModel;

namespace BindingDemo
{
    public class MainViewModel : ViewModel, INotifyPropertyChanged
    {
        //private string name;
        //public string Name
        //{
        //    get => name;
        //    set
        //    {
        //        name = value;
        //        NotifyPropertyChanged(nameof(Name));
        //    }
        //}

        public SubViewModel SubViewModel { get; }

        public MainViewModel()
        {
            SubViewModel = new SubViewModel();
        }
    }
}
