using System.Windows;
using StudentWpfExercise.ViewModels;

namespace StudentWpfExercise.Views
{
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void WindowLoaded(object sender, RoutedEventArgs e)
        {
            MinWidth = Width;
            MinHeight = Height;
        }
    }
}
