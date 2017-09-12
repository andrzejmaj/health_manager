package engineer.thesis.medcom.dicom.store;


import engineer.thesis.core.model.entity.Patient;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.medcom.model.DicomInstance;
import engineer.thesis.medcom.model.DicomSeries;
import engineer.thesis.medcom.model.DicomStudy;
import engineer.thesis.medcom.model.exceptions.DatabaseStorageException;
import org.apache.log4j.Logger;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.util.SafeClose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * @author MKlaman
 * @since 12.09.2017
 */
@Service
public class DatabaseStorageService {

    private final static Logger logger = Logger.getLogger(DatabaseStorageService.class);

    private final PatientRepository patientRepository;

    @Autowired
    public DatabaseStorageService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void store(File dicomFile) {
        Attributes attributes = parseFile(dicomFile);

        String pesel = getPesel(attributes);
        Patient patient = Optional.ofNullable(patientRepository.findByAccount_PersonalDetails_Pesel(pesel))
                .orElseThrow(() -> new DatabaseStorageException(String.format("could not find patient with PESEL: %s", pesel)));


        // DONE: create DicomStudy, DicomSeries and DicomInstance from attributes
        DicomStudy study = new DicomStudy(attributes);
        DicomSeries series = new DicomSeries(attributes);
        DicomInstance instance = new DicomInstance(attributes);

        logger.info("succesfully parsed data");

        // TODO: bidirectional conversion between model classes and entity classes

        // TODO: if corresponding entity does not exist then save

        // TODO: if corresponding entity already exists merge and save
    }

    private Attributes parseFile(File dicomFile) {
        DicomInputStream in = null;
        try {
            in = new DicomInputStream(dicomFile);
            in.setIncludeBulkData(DicomInputStream.IncludeBulkData.NO);
            return in.readDataset(-1, Tag.PixelData);
        } catch (IOException e) {
            throw new DatabaseStorageException("failed to persist dicom - fatal error while reading file", e);
        } finally {
            if (in != null)
                SafeClose.close(in);
        }
    }

    private String getPesel(Attributes attributes) {
        return Optional.ofNullable(attributes.getString(Tag.PatientID))
                .orElseThrow(() -> new DatabaseStorageException("could not extract patient`s PESEL"));
    }


}
