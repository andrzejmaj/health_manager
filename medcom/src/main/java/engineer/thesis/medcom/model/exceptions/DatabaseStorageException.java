package engineer.thesis.medcom.model.exceptions;

/**
 * @author MKlaman
 * @since 12.09.2017
 */
public class DatabaseStorageException extends RuntimeException {

    public DatabaseStorageException(String message) {
        super(message);
    }

    public DatabaseStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
