package engineer.thesis.medcom.model.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import engineer.thesis.medcom.utils.DicomUtils;
import lombok.Getter;
import lombok.Setter;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Keyword;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * DicomAttributesContainer owns a set of dicom attributes
 *
 * @author MKlaman
 * @since 12.09.2017
 */
public abstract class DicomAttributesContainer extends DicomModule {

    @Getter
    @Setter
    @JsonIgnore
    private Set<DicomAttribute> attributes = new HashSet<>();

    public DicomAttributesContainer(DicomModule[] modules) {
        super(modules);
    }

    public Optional<DicomAttribute> getAttribute(Integer code) {
        return attributes.stream()
                .filter(attribute -> attribute.getCode().equals(code))
                .findAny();
    }

    /**
     * Adds attributes from other container to this attributes.
     * In case of conflicts other`s attributes are ignored.
     *
     * @param other
     */
    public void merge(DicomAttributesContainer other) {
        this.attributes.addAll(other.getAttributes());
    }

    protected final void loadAttributes(Attributes dicomAttributes) {
        super.getAttributeTags().forEach(tag -> {
            String value = dicomAttributes.getString(tag.getCode());
            if (!StringUtils.isEmpty(value)) {
                this.attributes.add(new DicomAttribute(tag.getCode(), value, tag.getName()));
            }
        });
    }

    protected void setRequiredField(Integer tag, Consumer<String> setter) {
        String value = getAttribute(tag)
                .map(DicomAttribute::getValue)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("attribute %s is missing!", Keyword.valueOf(tag))
                ));

        setter.accept(value);
    }

    protected void setOptionalField(Integer tag, Consumer<String> setter) {
        getAttribute(tag)
                .map(DicomAttribute::getValue)
                .ifPresent(setter);
    }

    protected void setDateTimeField(Integer dateTag, Integer timeTag, Consumer<Date> setter) {
        Instant instant = DicomUtils.parseDateTime(
                getAttribute(dateTag).map(DicomAttribute::getValue).orElse(null),
                getAttribute(timeTag).map(DicomAttribute::getValue).orElse("000000")
        );

        if (instant != null) {
            setter.accept(Date.from(instant));
        }
    }

    @JsonProperty("attributes")
    public Map<String, String> getAttributesAsMap() {
         return attributes.stream()
                .collect(Collectors.toMap(DicomAttribute::getName, DicomAttribute::getValue));
    }

}
