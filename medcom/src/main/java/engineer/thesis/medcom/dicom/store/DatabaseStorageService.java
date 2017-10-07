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
import engineer.thesis.medcom.model.DicomData;
import engineer.thesis.medcom.model.error.DatabaseStorageException;
import engineer.thesis.medcom.services.DicomDataService;
import org.apache.log4j.Logger;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.net.Association;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
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

    private final DicomDataService dicomDataService;
    private final CustomObjectMapper objectMapper;

    @Autowired
    public DatabaseStorageService(PatientRepository patientRepository,
                                  ModalityRepository modalityRepository,
                                  StudyRepository studyRepository,
                                  SeriesRepository seriesRepository,
                                  InstanceRepository instanceRepository,
                                  DicomDataService dicomDataService,
                                  CustomObjectMapper objectMapper) {
        this.patientRepository = patientRepository;
        this.modalityRepository = modalityRepository;
        this.studyRepository = studyRepository;
        this.seriesRepository = seriesRepository;
        this.instanceRepository = instanceRepository;
        this.dicomDataService = dicomDataService;
        this.objectMapper = objectMapper;
    }

    @Transactional
    void store(File dicomFile, Association association) { // TODO merge existing model attributes with received dicomData instead of ignoring if already present
        DicomData dicomData = getDicomData(dicomFile);
        dicomData.setModalityMetadata(
                association.getSocket().getInetAddress().toString(),
                association.getSocket().getPort()
        );
        //dicomData.getModality().setApplicationEntity(association.getCallingAET()); // should be set from atts

        // modality
        Modality modalityEntity = Optional.ofNullable(modalityRepository.findOne(dicomData.getModality().getApplicationEntity()))
                .orElse(objectMapper.convert(dicomData.getModality(), Modality.class));
        modalityRepository.save(modalityEntity);

        // patient
        String pesel = dicomData.getPatient().getPesel();
        Patient patientEntity = Optional.ofNullable(patientRepository.findByAccount_PersonalDetails_Pesel(pesel)) // ignoring dicomPatientData
                .orElseThrow(() -> new DatabaseStorageException(String.format("could not find patient with PESEL: %s", pesel)));

        // instance
        if (instanceRepository.exists(dicomData.getInstance().getInstanceUID())) {
            logger.warn(String.format("instance '%s' already persisted", dicomData.getInstance().getInstanceUID()));
            return; // TODO proceed anyway and merge
        }
        Instance instanceEntity = objectMapper.convert(dicomData.getInstance(), Instance.class);

        // series
        Series seriesEntity = Optional.ofNullable(seriesRepository.findOne(dicomData.getSeries().getInstanceUID()))
                .orElse(objectMapper.convert(dicomData.getSeries(), Series.class));

        // study
        Study studyEntity = Optional.ofNullable(studyRepository.findOne(dicomData.getStudy().getInstanceUID()))
                .orElse(objectMapper.convert(dicomData.getStudy(), Study.class));

        // persisitng
        studyEntity.setPatient(patientEntity);
        seriesEntity.setStudy(studyEntity);
        seriesEntity.setModality(modalityEntity);
        seriesEntity.addInstance(instanceEntity);

        studyRepository.save(studyEntity);
        seriesRepository.save(seriesEntity);

        logger.info("successfully persisted dicom instance in database");
    }

    private DicomData getDicomData(File dicomFile) {
        try {
            DicomInputStream in = new DicomInputStream(dicomFile);
            return dicomDataService.create(in);
        } catch (Exception ex) {
            throw new DatabaseStorageException("failed to persist dicom", ex);
        }
    }
}
