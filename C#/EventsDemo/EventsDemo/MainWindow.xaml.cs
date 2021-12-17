using System.Windows;

namespace EventsDemo
{
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        public void AltitudeChanged(double altitude)
        {
            AltitudeProgressBar.Value = altitude;
        }
    }
}
