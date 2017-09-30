package engineer.thesis.medcom.model;

import engineer.thesis.medcom.utils.DicomUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;

/**
 * @author MKlaman
 * @since 30.09.2017
 */
@Getter
@Setter
@NoArgsConstructor
public class MedcomModality {

    private String applicationEntity;
    private String address;
    private int port;

    private String description;
    private String location;

    private String type;
    private String stationName;
    private String manufacturer;
    private String manufacturerModelName;
    private String softwareVersions;
    private String serialNumber;

    public MedcomModality(String applicationEntity, String address, int port, Attributes attributes) {
        this.applicationEntity = applicationEntity;
        this.address = address;
        this.port = port;

        DicomUtils.applyAttributeValue(attributes, Tag.Modality, this::setType);
        DicomUtils.applyAttributeValue(attributes, Tag.StationName, this::setStationName);
        DicomUtils.applyAttributeValue(attributes, Tag.Manufacturer, this::setManufacturer);
        DicomUtils.applyAttributeValue(attributes, Tag.ManufacturerModelName, this::setManufacturerModelName);
        DicomUtils.applyAttributeValue(attributes, Tag.SoftwareVersions, this::setSoftwareVersions);
        DicomUtils.applyAttributeValue(attributes, Tag.DeviceSerialNumber, this::setSerialNumber);
    }
}
