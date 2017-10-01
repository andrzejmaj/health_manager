package engineer.thesis.medcom.model.error;

/**
 * @author MKlaman
 * @since 11.06.2017
 */
public class ArchiveIOException extends RuntimeException {

    public ArchiveIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArchiveIOException(String message) {
        super(message);
    }


    public ArchiveIOException(Throwable cause) {
        super(cause);
    }
}
