using System.Windows;

namespace BindingDemo
{
    public partial class MainWindow : Window
    {
        private readonly MainViewModel mainViewModel;
        public MainWindow()
        {
            InitializeComponent();
            DataContext = mainViewModel = new MainViewModel();
        }

        private void SetNameAaaaaClicked(object sender, RoutedEventArgs e)
        {
            //mainViewModel.Name = "AAAAA";
            mainViewModel.SubViewModel.Name = "AAAAA";
        }

        private void SetName12345Clicked(object sender, RoutedEventArgs e)
        {
            //mainViewModel.Name = "12345";
            mainViewModel.SubViewModel.Name = "12345";
        }

        private void SetNameEmptyClicked(object sender, RoutedEventArgs e)
        {
            //mainViewModel.Name = "";
            mainViewModel.SubViewModel.Name = "";
        }

        private void BreakpointClicked(object sender, RoutedEventArgs e)
        {

        }
    }
}
