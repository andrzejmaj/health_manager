package engineer.thesis.medcom.model.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import engineer.thesis.medcom.utils.DicomUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.dcm4che3.data.Keyword;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * DicomObject owns a set of dicom attributes
 *
 * @author MKlaman
 * @since 12.09.2017
 */
@NoArgsConstructor
public abstract class DicomObject {

    private final static Logger logger = Logger.getLogger(DicomObject.class);

    @Getter
    @Setter
    @JsonIgnore
    private Set<DicomAttribute> attributes;

    public DicomObject(Set<DicomAttribute> attributes) {
        this.attributes = attributes;
    }

    /**
     * merges without overriding existing values
     *
     * @param other source of new values
     */
    public abstract void lazyMerge(DicomObject other);

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
        if (!getAttribute(dateTag).isPresent()) {
            setter.accept(null);
            return;
        }

        try {
            Instant instant = DicomUtils.parseDateTime(
                    getAttribute(dateTag).map(DicomAttribute::getValue).orElse(null),
                    getAttribute(timeTag).map(DicomAttribute::getValue).orElse("000000")
            );
            setter.accept(Date.from(instant));
        } catch (Exception ex) {
            logger.warn("could not set dicom DateTime field", ex);
            setter.accept(null);
        }
    }

    protected void lazyAttributesMerge(DicomObject other) {
        // does not add if attribute already present
        attributes.addAll(other.getAttributes());
    }

    @JsonProperty("attributes")
    public Map<String, String> getAttributesAsMap() {
        return attributes.stream()
                .collect(Collectors.toMap(DicomAttribute::getName, DicomAttribute::getValue));
    }

}
