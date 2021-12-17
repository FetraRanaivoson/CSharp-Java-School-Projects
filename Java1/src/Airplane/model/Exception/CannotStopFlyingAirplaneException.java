package Airplane.model.Exception;

public class CannotStopFlyingAirplaneException extends AirplaneException
{
    public CannotStopFlyingAirplaneException () {
        super();
    }
    public CannotStopFlyingAirplaneException(String message) {super(message);}
}
