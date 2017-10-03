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
@JsonPropertyOrder({"instanceUID", "creationDate", "attributes"})
public class DicomStudy extends DicomObject {

    public static final AttributeModule attributeModule = AttributeModule.combine(
            AttributeModules.generalStudyModule,
            AttributeModules.patientStudyModule
    );

    private String instanceUID;
    private Date creationDate;

    public static DicomStudy.Builder builder() {
        return new DicomStudy.Builder();
    }

    public DicomStudy(Set<DicomAttribute> attributes) {
        super(attributes);
        this.setRequiredField(Tag.StudyInstanceUID, this::setInstanceUID);
        this.setDateTimeField(Tag.StudyDate, Tag.StudyTime, this::setCreationDate);
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
