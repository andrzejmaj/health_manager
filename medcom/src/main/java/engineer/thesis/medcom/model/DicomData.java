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
    //private final DicomModality modality // TODO
    private final DicomStudy study;
    //private final DicomPatient patient // TODO

}
