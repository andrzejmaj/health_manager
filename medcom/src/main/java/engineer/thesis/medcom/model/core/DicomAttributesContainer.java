package engineer.thesis.medcom.model.core;

import lombok.Getter;
import lombok.Setter;
import org.dcm4che3.data.Attributes;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * DicomAttributesContainer owns a set of dicom attributes
 *
 * @author MKlaman
 * @since 12.09.2017
 */
public abstract class DicomAttributesContainer extends DicomModule {

    @Getter
    @Setter
    private Map<Integer, DicomAttribute> attributes = new HashMap<>();

    public DicomAttributesContainer(DicomModule[] modules) {
        super(modules);
    }

    public Optional<DicomAttribute> getAttribute(Integer tag) {
        return Optional.ofNullable(attributes.get(tag));
    }

    public final void loadAttributes(Attributes dicomAttributes) {
        super.getAttributeTags().forEach(tag -> {
            String value = dicomAttributes.getString(tag.getCode());
            if (!StringUtils.isEmpty(value)) {
                this.attributes.put(tag.getCode(), new DicomAttribute(tag, value));
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
        other.getAttributes().forEach((key, attribute) ->
                this.attributes.merge(key, attribute, (thisAttribute, otherAttribute) -> thisAttribute));
    }

}
