package corrections.enums.Airplane;

import corrections.enums.AirplaneException.*;

public class Airplane {
    private final int RATE_OF_CHANGE = 1000;        //Public because value cannot be changed.
    public final int WARNING_ALTITUDE = 10000;      //Public because value cannot be changed.
    public final int EXPLOSION_ALTITUDE = 12000;    //Public because value cannot be changed.

    private int altitude = 0;
    private AirplaneStatus status = AirplaneStatus.ENGINE_OFF;

    public int GetAltitude() {
        return altitude;
    }

    public boolean IsLanded()
    {
        if (status != AirplaneStatus.EXPLODED && status != AirplaneStatus.FLYING)
        {
            return true;
        }
        return false;
    }


    public void StartMotor() throws AirplaneAlreadyExplodedException, AirplaneAlreadyStartedException {
        switch (status) {
            case EXPLODED:
                throw new AirplaneAlreadyExplodedException("Error: Airplane already Exploded");
            case ENGINE_ON: //If we flying or engine on then we know engine is on.
            case FLYING:
                throw new AirplaneAlreadyStartedException("Error: Airplane already started");
            case ENGINE_OFF:
                status = AirplaneStatus.ENGINE_ON;
                break;
        }
    }


    public void StopMotor() throws AirplaneAlreadyExplodedException, AirplaneCannotStopInAirException, AirplaneAlreadyStoppedException {
        switch (status) {
            case EXPLODED:
                throw new AirplaneAlreadyExplodedException("Error: Airplane already Exploded");
            case FLYING:
                throw new AirplaneCannotStopInAirException("DANGER: Cannot turn off motor when flying !");
            case ENGINE_OFF:
                throw new AirplaneAlreadyStoppedException("Error: Motor is already off");
            case ENGINE_ON:
                status = AirplaneStatus.ENGINE_OFF;
                break;
        }
    }

    public void TakeOff() throws AirplaneWarningException, AirplaneExplodedException, AirplaneNotInAirException, AirplaneAlreadyExplodedException, AirplaneNotStartedException, AirplaneAlreadyInAirException {
        switch (status) {
            case EXPLODED:
                throw new AirplaneAlreadyExplodedException("Error: Airplane already Exploded");
            case ENGINE_OFF:
                throw new AirplaneNotStartedException("Error: Airplane not started.");
            case FLYING:
                throw new AirplaneAlreadyInAirException("Error: Airplane is in the air");
            case ENGINE_ON:
                status = AirplaneStatus.FLYING;
                IncreaseAltitude();
                break;
        }
    }

    /**
     * Increase {@link #altitude} by {@link #RATE_OF_CHANGE}
     *
     * @throws AirplaneAlreadyExplodedException
     * @throws AirplaneNotStartedException
     * @throws AirplaneNotInAirException
     */
    public void IncreaseAltitude() throws AirplaneExplodedException, AirplaneAlreadyExplodedException, AirplaneNotStartedException, AirplaneNotInAirException, AirplaneWarningException {
        switch (status) {
            case EXPLODED:
                throw new AirplaneAlreadyExplodedException("Error: Airplane exploded");
            case ENGINE_OFF:
                throw new AirplaneNotStartedException("Error: Cannot increase altitude without engine turned on!");
            case ENGINE_ON:
                throw new AirplaneNotInAirException("Error: Cannot increase altitude without taking off");
            case FLYING:
                altitude += RATE_OF_CHANGE;
                CheckAltitude();
        }
    }

    private void CheckAltitude() throws AirplaneExplodedException, AirplaneWarningException {
        switch (altitude) {
            case EXPLOSION_ALTITUDE:
                status = AirplaneStatus.EXPLODED;
                throw new AirplaneExplodedException("Aiplane: BOOM!");
            case WARNING_ALTITUDE:
            case WARNING_ALTITUDE + 1000:
                throw new AirplaneWarningException("WARNING: The plane cannot support pressure at 12000 altitude.\nCurrent altitude: " + altitude);
            case 0:
                status = AirplaneStatus.ENGINE_ON;
            default:
                break;
        }
    }

    /**
     * Decrease {@link #altitude} by {@link #RATE_OF_CHANGE}
     * land airplane if {@link #altitude} equals 0
     *
     * @throws AirplaneIsLandedException
     * @throws AirplaneAlreadyExplodedException
     * @throws AirplaneNotStartedException
     * @throws AirplaneNotInAirException
     */
    public void DecreaseAltitude() throws AirplaneWarningException, AirplaneExplodedException, AirplaneIsLandedException, AirplaneAlreadyExplodedException, AirplaneNotStartedException, AirplaneNotInAirException {
        switch (status) {
            case EXPLODED:
                throw new AirplaneAlreadyExplodedException("Error: Airplane is exploded !");
            case ENGINE_OFF:
                throw new AirplaneNotStartedException("Error: Cannot decrease altitude without engine turned on!");
            case ENGINE_ON:
                throw new AirplaneIsLandedException("Error: Cannot decrease altitude when landed");
            case FLYING:
                altitude -= RATE_OF_CHANGE;
                CheckAltitude();
                break;
        }
    }

}
