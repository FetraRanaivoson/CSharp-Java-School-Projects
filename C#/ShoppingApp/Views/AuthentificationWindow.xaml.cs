//Author: Fetra Ranaivoson
using ShoppingApp.ViewModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace ShoppingApp
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class AuthentificationWindow : Window
    {
        public AuthentificationWindow(AuthentificationViewModel authentificationViewModel)
        {
            InitializeComponent();
            DataContext = authentificationViewModel;
        }

        private void WindowLoaded(object sender, RoutedEventArgs e)
        {
               MinHeight = Height;
               MinWidth = Width;
        }
    }
}
