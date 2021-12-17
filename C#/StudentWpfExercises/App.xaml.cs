﻿using System.Windows;
using StudentWpfExercise.Views;

namespace StudentWpfExercise
{
    public partial class App : Application
    {
        protected override void OnStartup(StartupEventArgs e)
        {
            base.OnStartup(e);

            MainWindow window = new MainWindow();
            window.Show();
        }
    }
}
