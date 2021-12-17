package corrections.enums.AirplaneException;

public class AirplaneAlreadyStoppedException extends AirplaneException {
    public AirplaneAlreadyStoppedException(String message) {
        super(message);
    }

    public AirplaneAlreadyStoppedException() {
        super();
    }
}
