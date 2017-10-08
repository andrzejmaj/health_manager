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
@JsonPropertyOrder({"applicationEntity", "stationName", "type", "address", "port", "description", "location", "attributes"})
public class DicomModality extends DicomObject {

    public static final AttributeModule attributeModule = AttributeModule.combine(
            AttributeModules.generalEquipmentModule,
            AttributeModules.hardcopyEquipmentModule,
            AttributeModules.scImageEquipmentModule,
            AttributeModules.medcomModalityModule
    );

    private String applicationEntity;
    private String stationName;
    private String type;

    // non attribute fields
    private String address;
    private Integer port;
    private String description; // TODO add/mofify via rest
    private String location; // TODO add/mofify via rest

    private DicomModality(Set<DicomAttribute> attributes) {
        super(attributes);
        setFields();
    }

    @Override
    public void lazyMerge(DicomObject other) {
        if (!(other instanceof DicomModality))
            return;

        super.lazyAttributesMerge(other);
        setFields();

        DicomModality otherModality = (DicomModality) other;
        if (address == null) address = otherModality.getAddress();
        if (port == null) port = otherModality.getPort();
        if (description == null) description = otherModality.getDescription();
        if (location == null) location = otherModality.getLocation();
    }

    private void setFields() {
        this.setRequiredField(Tag.SourceApplicationEntityTitle, this::setApplicationEntity);
        this.setRequiredField(Tag.Modality, this::setType);
        this.setOptionalField(Tag.StationName, this::setStationName);
    }

    public static DicomModality.Builder builder() {
        return new DicomModality.Builder();
    }


    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder extends DicomObjectBuilder<DicomModality> {

        @Override
        public DicomModality build() {
            return new DicomModality(attributes.build());
        }

        @Override
        public boolean accepts(DicomAttribute attribute) {
            return attributeModule.contains(attribute.getCode());
        }

        @Override
        protected int getPriority() {
            return 3;
        }
    }
}
