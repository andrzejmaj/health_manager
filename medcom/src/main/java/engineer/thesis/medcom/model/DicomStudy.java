package engineer.thesis.medcom.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import engineer.thesis.medcom.model.core.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dcm4che3.data.Tag;

import java.util.Date;
import java.util.Set;

/**
 * @author MKlaman
 * @since 12.09.2017
 */
@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"instanceUID", "patientPesel", "creationDate", "attributes"})
public class DicomStudy extends DicomObject {

    public static final AttributeModule attributeModule = AttributeModule.combine(
            AttributeModules.generalStudyModule,
            AttributeModules.patientStudyModule,
            AttributeModules.clinicalTrialStudyModule

    );

    private String instanceUID;
    private Date creationDate;

    // non attribute
    private String patientPesel;

    public static DicomStudy.Builder builder() {
        return new DicomStudy.Builder();
    }

    private DicomStudy(Set<DicomAttribute> attributes) {
        super(attributes);
        setFields();
    }

    @Override
    public void lazyMerge(DicomObject other) {
        if (!(other instanceof DicomStudy))
            return;

        super.lazyAttributesMerge(other);
        setFields();

        if (patientPesel == null)
            patientPesel = ((DicomStudy) other).getPatientPesel();
    }

    private void setFields() {
        this.setRequiredField(Tag.StudyInstanceUID, this::setInstanceUID);
        this.setDateTimeField(Tag.StudyDate, Tag.StudyTime, this::setCreationDate);
    }

    public void setCreationDate(Date creationDate) {
        if (creationDate == null) {
            creationDate = new Date();
        }
        this.creationDate = creationDate;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder extends DicomObjectBuilder<DicomStudy> {

        @Override
        public DicomStudy build() {
            return new DicomStudy(attributes.build());
        }

        @Override
        public boolean accepts(DicomAttribute attribute) {
            return attributeModule.contains(attribute.getCode());
        }

        @Override
        protected int getPriority() {
            return 2;
        }
    }
}
