package engineer.thesis.medcom.model.error;

/**
 * @author MKlaman
 * @since 27.06.2017
 */
public class ModalityNotFoundException extends RuntimeException  {

    public ModalityNotFoundException(String message) {
        super(message);
    }
}
