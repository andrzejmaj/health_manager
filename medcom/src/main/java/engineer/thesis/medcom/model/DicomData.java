package engineer.thesis.medcom.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author MKlaman
 * @since 03.10.2017
 */
@Getter
@AllArgsConstructor
public class DicomData {

    private final DicomInstance instance;
    private final DicomSeries series;
    private final DicomStudy study;
    private final DicomModality modality;
    private final DicomPatient patient;

    public void setModalityMetadata(String address, int port) {
        modality.setAddress(address);
        modality.setPort(port);
    }
}
