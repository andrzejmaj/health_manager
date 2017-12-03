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

import java.util.Date;
import java.util.Set;

/**
 * @author MKlaman
 * @since 12.09.2017
 */
@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"instanceUID", "studyInstanceUID", "modalityAET", "creationDate", "attributes"})
public class DicomSeries extends DicomObject {

    public static final AttributeModule attributeModule = AttributeModule.combine(
            AttributeModules.generalSeriesModule,
            AttributeModules.clinicalTrialSeriesModule,
            AttributeModules.crSeriesModule,
            AttributeModules.petSeriesModule
    );

    private String instanceUID;
    private Date creationDate;

    // non attribute
    private String studyInstanceUID;
    private String modalityAET;

    private DicomSeries(Set<DicomAttribute> attributes) {
        super(attributes);
        setFields();
    }

    public static DicomSeries.Builder builder() {
        return new DicomSeries.Builder();
    }

    @Override
    public void lazyMerge(DicomObject other) {
        if (!(other instanceof DicomSeries))
            return;

        super.lazyAttributesMerge(other);
        setFields();

        DicomSeries otherSeries = (DicomSeries) other;
        if (studyInstanceUID == null) studyInstanceUID = otherSeries.getStudyInstanceUID();
        if (modalityAET == null) modalityAET = otherSeries.getModalityAET();
    }

    private void setFields() {
        this.setRequiredField(Tag.SeriesInstanceUID, this::setInstanceUID);
        this.setDateTimeField(Tag.SeriesDate, Tag.SeriesTime, this::setCreationDate);
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder extends DicomObjectBuilder<DicomSeries> {

        @Override
        public DicomSeries build() {
            return new DicomSeries(attributes);
        }

        @Override
        public boolean accepts(DicomAttribute attribute) {
            return super.accepts(attribute) && attributeModule.contains(attribute.getCode());
        }

        @Override
        protected int getPriority() {
            return 1;
        }
    }
}
