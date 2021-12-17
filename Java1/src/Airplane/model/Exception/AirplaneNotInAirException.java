package Airplane.model.Exception;

public class AirplaneNotInAirException extends AirplaneException{
    public AirplaneNotInAirException () {
        super();
    }
    public AirplaneNotInAirException (String message)
        {
            super(message);
        }
}
