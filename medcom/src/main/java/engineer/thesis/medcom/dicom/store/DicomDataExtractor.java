package engineer.thesis.medcom.dicom.store;

import engineer.thesis.medcom.model.DicomData;
import org.dcm4che3.io.DicomInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author MKlaman
 * @since 03.10.2017
 */
@Component
public class DicomDataExtractor {

    private final DicomAttributesReader dicomAttributesReader;

    @Autowired
    public DicomDataExtractor(DicomAttributesReader dicomAttributesReader) {
        this.dicomAttributesReader = dicomAttributesReader;
    }

    public DicomData extract(DicomInputStream in) {
        return null;


    }
}
