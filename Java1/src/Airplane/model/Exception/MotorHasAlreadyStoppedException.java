package Airplane.model.Exception;

public class MotorHasAlreadyStoppedException extends AirplaneException {

    public MotorHasAlreadyStoppedException () {
        super();
    }
    public MotorHasAlreadyStoppedException(String message) {super(message);}
}
