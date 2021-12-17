package Airplane.model.Exception;

public class AirplaneAlreadyGroundedException extends AirplaneException{
    public AirplaneAlreadyGroundedException () {
        super();
    }
    public AirplaneAlreadyGroundedException (String message) {super(message);}
}
