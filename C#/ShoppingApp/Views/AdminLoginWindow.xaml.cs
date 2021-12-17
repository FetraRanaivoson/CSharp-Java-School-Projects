//Author: Fetra Ranaivoson
using ShoppingApp.ViewModels;
using System;
using System.Collections.Generic;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace ShoppingApp.Views
{
    /// <summary>
    /// Interaction logic for AdminLoginWindow.xaml
    /// </summary>
    public partial class AdminLoginWindow : Window
    {
        public AdminLoginWindow(AuthentificationViewModel authentificationViewModel)
        {
            InitializeComponent();
            DataContext = authentificationViewModel;
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            MinHeight = Height;
            MinWidth = Width;
        }
    }
}
