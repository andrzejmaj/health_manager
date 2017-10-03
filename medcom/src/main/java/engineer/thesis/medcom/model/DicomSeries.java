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
public class DicomSeries extends DicomObject {

    public static final AttributeModule attributeModule = AttributeModule.combine(
            AttributeModules.generalSeriesModule
    );

    private String instanceUID;
    private Date creationDate;

    public static DicomSeries.Builder builder() {
        return new DicomSeries.Builder();
    }

    public DicomSeries(Set<DicomAttribute> attributes) {
        super(attributes);
        this.setRequiredField(Tag.SeriesInstanceUID, this::setInstanceUID);
        this.setDateTimeField(Tag.SeriesDate, Tag.SeriesTime, this::setCreationDate);
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder extends DicomObjectBuilder<DicomSeries> {

        @Override
        public DicomSeries build() {
            return new DicomSeries(attributes.build());
        }

        @Override
        public boolean accepts(DicomAttribute attribute) {
            return attributeModule.contains(attribute.getCode());
        }

        @Override
        protected int getPriority() {
            return 1;
        }
    }
}
