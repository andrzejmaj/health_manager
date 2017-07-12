package engineer.thesis.medcom.model.exceptions;

/**
 * @author MKlaman
 * @since 27.06.2017
 */
public class InstanceNotFoundException extends RuntimeException  {

    public InstanceNotFoundException(String message) {
        super(message);
    }
}
