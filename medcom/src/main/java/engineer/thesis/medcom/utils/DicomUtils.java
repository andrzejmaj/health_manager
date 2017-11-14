package engineer.thesis.medcom.utils;


import org.dcm4che3.data.Attributes;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author MKlaman
 * @since 19.09.2017
 */
public final class DicomUtils {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("yyyyMMdd")
            .appendPattern("HHmmss")
            .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 6, true)
            .toFormatter();


    public static Instant parseDateTime(String dicomDate, String dicomTime) {
        if (dicomDate == null || dicomTime == null)
            throw new IllegalArgumentException("arguments can not be null");
        return LocalDateTime.parse(dicomDate + dicomTime, DATE_TIME_FORMATTER).toInstant(ZoneOffset.UTC);
    }

    public static Optional<String> getAttributeValue(Attributes attributes, int tag) {
        String value = attributes.getString(tag);
        return (value != null && !StringUtils.isEmpty(value))
                ? Optional.of(value)
                : Optional.empty();
    }

    public static void applyAttributeValue(Attributes attributes, int tag, Consumer<String> consumer) {
        getAttributeValue(attributes, tag).ifPresent(consumer);
    }

    private DicomUtils() {
    }

}
