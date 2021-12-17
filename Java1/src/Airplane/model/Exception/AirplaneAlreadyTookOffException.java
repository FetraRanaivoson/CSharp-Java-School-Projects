package Airplane.model.Exception;

public class AirplaneAlreadyTookOffException extends AirplaneException{
    public AirplaneAlreadyTookOffException () {
        super();
    }
    public AirplaneAlreadyTookOffException(String message) {super(message);}
}
