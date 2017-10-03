package engineer.thesis.medcom.model.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import engineer.thesis.medcom.utils.DicomUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Keyword;

import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

/**
 * DicomObject owns a set of dicom attributes
 *
 * @author MKlaman
 * @since 12.09.2017
 */
@NoArgsConstructor
@AllArgsConstructor
public abstract class DicomObject {

    private final static Logger logger = Logger.getLogger(DicomObject.class);

    @Getter
    @Setter
    @JsonIgnore
    private Set<DicomAttribute> attributes;


    public Optional<DicomAttribute> getAttribute(Integer code) {
        return attributes.stream()
                .filter(attribute -> attribute.getCode().equals(code))
                .findAny();
    }

    protected void setRequiredField(Integer tag, Consumer<String> setter) {
        String value = getAttribute(tag)
                .map(DicomAttribute::getValue)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("missing required attribute: %s", Keyword.valueOf(tag))
                ));

        setter.accept(value);
    }

    protected void setOptionalField(Integer tag, Consumer<String> setter) {
        getAttribute(tag)
                .map(DicomAttribute::getValue)
                .ifPresent(setter);
    }

    protected void setDateTimeField(Integer dateTag, Integer timeTag, Consumer<Date> setter) {
        try {
            Instant instant = DicomUtils.parseDateTime(
                    getAttribute(dateTag).map(DicomAttribute::getValue).orElse(null),
                    getAttribute(timeTag).map(DicomAttribute::getValue).orElse("000000")
            );
            setter.accept(Date.from(instant));
        } catch (Exception ex) {
            logger.error("could not set dicom DateTime filed", ex);
        }
    }

    @JsonProperty("attributes")
    public Map<String, String> getAttributesAsMap() {
        return attributes.stream()
                .collect(Collectors.toMap(DicomAttribute::getName, DicomAttribute::getValue));
    }

}
