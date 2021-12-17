package corrections.enums.AirplaneException;

/**
 * For all exceptions related to Airplane.model.Airplane.
 * We do not want to catch any exceptions not related.
 * Now, all Airplane.model.Airplane Exceptions will extend this one.
 */
public class AirplaneException extends Exception{

    public AirplaneException()
    {
        super();
    }

    public AirplaneException(String message)
    {
        super(message);
    }

}
