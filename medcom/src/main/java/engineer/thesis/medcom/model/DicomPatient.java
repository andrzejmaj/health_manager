package engineer.thesis.medcom.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import engineer.thesis.medcom.model.core.AttributeModule;
import engineer.thesis.medcom.model.core.DicomAttribute;
import engineer.thesis.medcom.model.core.DicomObject;
import engineer.thesis.medcom.model.core.DicomObjectBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dcm4che3.data.Tag;

import java.util.Set;

/**
 * @author MKlaman
 * @since 07.10.2017
 */
@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"pesel", "attributes"})
public class DicomPatient extends DicomObject {

    public static final AttributeModule attributeModule = AttributeModule.combine(
            AttributeModules.patientModule,
            AttributeModules.clinicalTrialSubjectModule
    );

    private String pesel;

    private DicomPatient(Set<DicomAttribute> attributes) {
        super(attributes);
        setFields();
    }

    public static DicomPatient.Builder builder() {
        return new DicomPatient.Builder();
    }

    @Override
    public void lazyMerge(DicomObject other) {
        if (!(other instanceof DicomPatient))
            return;

        super.lazyAttributesMerge(other);
        setFields();
    }

    private void setFields() {
        this.setRequiredField(Tag.PatientID, this::setPesel);
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder extends DicomObjectBuilder<DicomPatient> {

        @Override
        public DicomPatient build() {
            return new DicomPatient(attributes);
        }

        @Override
        public boolean accepts(DicomAttribute attribute) {
            return super.accepts(attribute) && attributeModule.contains(attribute.getCode());
        }

        @Override
        protected int getPriority() {
            return 4;
        }
    }
}
