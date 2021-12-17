//Author: Fetra Ranaivoson
using ShoppingApp.ViewModels;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace ShoppingApp
{

    public partial class MainApplicationWindow : Window
    {
        public MainApplicationWindow(AuthentificationViewModel authentificationViewModel )
        {
            InitializeComponent();
            DataContext = authentificationViewModel;
        }

        private void WindowLoaded(object sender, RoutedEventArgs e)
        {
            MinWidth = Width;
            MinHeight = Height;
        }
    }
}
