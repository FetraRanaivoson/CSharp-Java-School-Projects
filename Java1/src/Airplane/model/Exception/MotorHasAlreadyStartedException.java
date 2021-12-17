package Airplane.model.Exception;

public class MotorHasAlreadyStartedException extends AirplaneException
{
    public MotorHasAlreadyStartedException () {
        super();
    }
    public MotorHasAlreadyStartedException(String message) { super (message); }
}
