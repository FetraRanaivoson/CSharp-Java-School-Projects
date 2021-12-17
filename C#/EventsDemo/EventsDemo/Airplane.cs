
namespace EventsDemo
{
    public class Airplane
    {
        public delegate void AltitudeChangedHandler(double altitude);

        private AltitudeChangedHandler altitudeChanged;
        public event AltitudeChangedHandler AltitudeChanged
        {
            add => altitudeChanged += value;
            remove => altitudeChanged -= value;
        }

        private double altitude;
        public double Altitude
        {
            get => altitude;
            set
            {
                altitude = value;
                altitudeChanged.Invoke(value);
            }
        }
    }
}
