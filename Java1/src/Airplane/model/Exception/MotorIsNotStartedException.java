package Airplane.model.Exception;

public class MotorIsNotStartedException extends AirplaneException{

    public MotorIsNotStartedException () {
        super();
    }
    public MotorIsNotStartedException(String message) {super(message);}
}
