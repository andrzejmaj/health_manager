package engineer.thesis.medcom.model.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Keyword;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
        this.getAttributeTags().forEach(tag -> {
            String value = dicomAttributes.getString(tag.getCode());
            if (!StringUtils.isEmpty(value)) {
                this.attributes.add(new DicomAttribute(tag.getCode(), value, tag.getName()));
            }
        });
    }

    protected void setRequiredField(Integer attributeCode, Consumer<String> setter) {
        String value = getAttribute(attributeCode)
                .map(DicomAttribute::getValue)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("attribute %s is missing!", Keyword.valueOf(attributeCode))
                ));

        setter.accept(value);
    }

    protected void setOptionalField(Integer attributeCode, Consumer<String> setter) {
        getAttribute(attributeCode)
                .map(DicomAttribute::getValue)
                .ifPresent(setter);
    }

    @JsonProperty("attributes")
    public Map<String, String> getAttributesAsMap() {
         return attributes.stream()
                .collect(Collectors.toMap(DicomAttribute::getName, DicomAttribute::getValue));
    }

}
