package engineer.thesis.medcom.model.error;

/**
 * @author MKlaman
 * @since 12.09.2017
 */
public class DataExtractionException extends RuntimeException {

    public DataExtractionException(String message) {
        super(message);
    }

    public DataExtractionException(String message, Throwable cause) {
        super(message, cause);
    }
}
