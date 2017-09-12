package engineer.thesis.medcom.model.core;

import lombok.Getter;
import lombok.Setter;
import org.dcm4che3.data.Attributes;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * DicomAttributesContainer owns a set of dicom attributes
 *
 * @author MKlaman
 * @since 12.09.2017
 */
public abstract class DicomAttributesContainer extends DicomModule {

    @Getter
    @Setter
    private Set<DicomAttribute> attributes = new HashSet<>();

    public DicomAttributesContainer(DicomModule[] modules) {
        super(modules);
    }

    public Optional<DicomAttribute> getAttribute(Integer code) {
        return attributes.stream()
                .filter(attribute -> attribute.getCode().equals(code))
                .findAny();
    }

    public final void loadAttributes(Attributes dicomAttributes) {
        this.getAttributeTags().forEach(tag -> {
            String value = dicomAttributes.getString(tag.getCode());
            if (!StringUtils.isEmpty(value)) {
                this.attributes.add(new DicomAttribute(tag.getCode(), value, tag.getName()));
            }
        });
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

}
