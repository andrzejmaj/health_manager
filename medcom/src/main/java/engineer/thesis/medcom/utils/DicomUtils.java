package engineer.thesis.medcom.utils;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * @author MKlaman
 * @since 19.09.2017
 */
public class DicomUtils {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("yyyyMMdd")
            .appendPattern("HHmmss")
            .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 6, true)
            .toFormatter();


    public static Instant parseDateTime(String dicomDate, String dicomTime) {
        return (dicomDate != null && dicomTime != null)
                ? LocalDateTime.parse(dicomDate + dicomTime, DATE_TIME_FORMATTER).toInstant(ZoneOffset.UTC)
                : null;
    }

    private DicomUtils() {
    }

}
