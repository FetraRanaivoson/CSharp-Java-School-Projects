using System.Windows;
using PassportApp.ViewModels;

namespace PassportApp.Views
{
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
            DataContext = new PassportsViewModel();
        }

        /// <remarks>
        /// After the window is loaded and sized based on content,
        /// set the minimum size to be the actual size so that
        /// the window cannot be resized to be smaller than
        /// the size required by the window's content
        /// </remarks>
        private void WindowLoaded(object sender, RoutedEventArgs e)
        {
            MinWidth = Width;
            MinHeight = Height;
        }
    }
}
