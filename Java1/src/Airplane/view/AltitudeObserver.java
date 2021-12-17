package Airplane.view;

import Airplane.model.Exception.AirplaneAlreadyGroundedException;

public interface AltitudeObserver {
    void changeAltitude (int altitude);
}
