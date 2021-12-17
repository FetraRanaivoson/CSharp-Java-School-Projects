using System;
using System.Threading;
using System.Windows;

namespace EventsDemo
{
    public partial class App : Application
    {
        private Random random;
        private Airplane airplane;
        private MainWindow window;

        protected override void OnStartup(StartupEventArgs e)
        {
            base.OnStartup(e);

            random = new Random();
            InitializeMvc();
            StartAnimation();
        }

        private void InitializeMvc()
        {
            // Model
            airplane = new Airplane();

            // View
            window = new MainWindow();

            // Controller
            window.RandomizeAltitudeButton.Click += RandomizeClicked;
            // Whenever the view button is clicked, then RandomizeClicked controller code will be executed

            // Add view as listener to model
            airplane.AltitudeChanged += window.AltitudeChanged;
            // Whenever the model altitude changes, then window.AltitudeChanged will be executed

            window.Show();
        }

        private void StartAnimation()
        {
            Thread thread = new Thread(AnimateAirplane);
            thread.Start();
        }

        private void AnimateAirplane()
        {
            while (Application.Current != null)
            {
                Application.Current.Dispatcher.BeginInvoke(new Action(IncrementAirplaneAltitude));
                Thread.Sleep(100);
            }
        }

        private void IncrementAirplaneAltitude()
        {
            airplane.Altitude += 1;
        }

        private void RandomizeClicked(object sender, RoutedEventArgs eventArgs)
        {
            // Respond to button click
            double newAltitude = random.NextDouble() * 100;
            airplane.Altitude = newAltitude;
        }
    }
}
