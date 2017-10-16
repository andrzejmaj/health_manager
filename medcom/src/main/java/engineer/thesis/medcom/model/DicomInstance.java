package engineer.thesis.medcom.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import engineer.thesis.medcom.model.core.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dcm4che3.data.*;

import java.util.Date;
import java.util.Set;

/**
 * @author MKlaman
 * @since 12.09.2017
 */
@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"instanceUID", "seriesInstanceUID", "creationDate", "sopClassName", "attributes"})
public class DicomInstance extends DicomObject {

    private String instanceUID;
    private Date creationDate;
    private String sopClassName;

    // non attribute
    private String seriesInstanceUID;

    public static DicomInstance.Builder builder() {
        return new DicomInstance.Builder();
    }

    private DicomInstance(Set<DicomAttribute> attributes) {
        super(attributes);
        setFields();
    }

    @Override
    public void lazyMerge(DicomObject other) {
        if (!(other instanceof DicomInstance))
            return;

        super.lazyAttributesMerge(other);
        setFields();

        if (seriesInstanceUID == null)
            seriesInstanceUID = ((DicomInstance) other).getSeriesInstanceUID();
    }

    private void setFields() {
        this.setRequiredField(Tag.SOPInstanceUID, this::setInstanceUID);
        this.setRequiredField(Tag.SOPClassUID, this::setSopClassNameByCode);
        this.setDateTimeField(Tag.InstanceCreationDate, Tag.InstanceCreationTime, this::setCreationDate);
    }

    private void setSopClassNameByCode(String code) {
        sopClassName = UID.nameOf(code);
    }


    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder extends DicomObjectBuilder<DicomInstance> {

        @Override
        public DicomInstance build() {
            return new DicomInstance(attributes);
        }

        @Override
        public boolean accepts(DicomAttribute attribute) {
            return true; // accept all remaining attributes
        }

        @Override
        protected int getPriority() {
            return 0; // lowest getPriority - accepts attributes last
        }
    }
}
