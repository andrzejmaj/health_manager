package engineer.thesis.medcom.dicom.store;


import engineer.thesis.core.model.entity.Patient;
import engineer.thesis.core.model.entity.medcom.Instance;
import engineer.thesis.core.model.entity.medcom.Modality;
import engineer.thesis.core.model.entity.medcom.Series;
import engineer.thesis.core.model.entity.medcom.Study;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.repository.medcom.InstanceRepository;
import engineer.thesis.core.repository.medcom.ModalityRepository;
import engineer.thesis.core.repository.medcom.SeriesRepository;
import engineer.thesis.core.repository.medcom.StudyRepository;
import engineer.thesis.core.utils.CustomObjectMapper;
import engineer.thesis.medcom.model.DicomInstance;
import engineer.thesis.medcom.model.DicomSeries;
import engineer.thesis.medcom.model.DicomStudy;
import engineer.thesis.medcom.model.MedcomModality;
import engineer.thesis.medcom.model.error.DatabaseStorageException;
import org.apache.log4j.Logger;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.net.Association;
import org.dcm4che3.util.SafeClose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ModalityRepository modalityRepository;
    private final StudyRepository studyRepository;
    private final SeriesRepository seriesRepository;
    private final InstanceRepository instanceRepository;
    private final CustomObjectMapper objectMapper;

    @Autowired
    public DatabaseStorageService(PatientRepository patientRepository,
                                  ModalityRepository modalityRepository,
                                  StudyRepository studyRepository,
                                  SeriesRepository seriesRepository,
                                  InstanceRepository instanceRepository,
                                  CustomObjectMapper objectMapper) {
        this.patientRepository = patientRepository;
        this.modalityRepository = modalityRepository;
        this.studyRepository = studyRepository;
        this.seriesRepository = seriesRepository;
        this.instanceRepository = instanceRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    void store(File dicomFile, Association association) {
        Attributes attributes = parseFile(dicomFile);

        // resolve modality
        MedcomModality modality = new MedcomModality(
                association.getCallingAET(),
                association.getSocket().getInetAddress().toString(),
                association.getSocket().getPort(),
                attributes
        );
        Modality modalityEntity = Optional.ofNullable(modalityRepository.findOne(modality.getApplicationEntity()))
                .orElse(objectMapper.convert(modality, Modality.class)); // TODO merge exisiting if present?
        modalityRepository.save(modalityEntity);

        String pesel = getPesel(attributes);
        Patient patientEntity = Optional.ofNullable(patientRepository.findByAccount_PersonalDetails_Pesel(pesel))
                .orElseThrow(() -> new DatabaseStorageException(String.format("could not find patient with PESEL: %s", pesel)));

        // if corresponding entity already exists then ignores the received model
        // TODO: if corresponding entity already exists merge and save?

        DicomInstance instance = new DicomInstance(attributes);
        if (instanceRepository.exists(instance.getInstanceUID())) {
            logger.warn(String.format("instance '%s' already persisted", instance.getInstanceUID()));
            return;
        }
        Instance instanceEntity = objectMapper.convert(instance, Instance.class);

        DicomSeries series = new DicomSeries(attributes);
        Series seriesEntity = Optional.ofNullable(seriesRepository.findOne(series.getInstanceUID()))
                .orElse(objectMapper.convert(series, Series.class));


        DicomStudy study = new DicomStudy(attributes);
        Study studyEntity = Optional.ofNullable(studyRepository.findOne(study.getInstanceUID()))
                .orElse(objectMapper.convert(study, Study.class));


        studyEntity.setPatient(patientEntity);
        seriesEntity.setStudy(studyEntity);
        seriesEntity.addInstance(instanceEntity);

        studyRepository.save(studyEntity);
        seriesRepository.save(seriesEntity);

        if (studyEntity.getCreationDate() != null &&
                (patientEntity.getLastDicomStudyDate() == null || patientEntity.getLastDicomStudyDate().before(studyEntity.getCreationDate()))) {
            patientEntity.setLastDicomStudyDate(studyEntity.getCreationDate());
            patientRepository.save(patientEntity);
        }

        logger.info("successfully persisted dicom instance in database");
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
