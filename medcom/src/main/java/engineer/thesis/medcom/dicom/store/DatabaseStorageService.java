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
import engineer.thesis.medcom.model.*;
import engineer.thesis.medcom.model.core.DicomObject;
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
    void store(File dicomFile, Association association) {
        // extract data
        DicomData dicomData = getDicomData(dicomFile);
        dicomData.setModalityMetadata(
                association.getSocket().getInetAddress().toString(),
                association.getSocket().getPort()
        );

        // TODO: fix lazy loading attributes not working

        // modality
        Modality modalityEntity = modalityRepository.findOne(dicomData.getModality().getApplicationEntity());
        modalityEntity = mergeEntity(modalityEntity, Modality.class, dicomData.getModality(), DicomModality.class);

        // patient
        String pesel = dicomData.getPatient().getPesel();
        Patient patientEntity = Optional.ofNullable(patientRepository.findByAccount_PersonalDetails_Pesel(pesel))
                .orElseThrow(() -> new DatabaseStorageException(String.format("could not find patient with PESEL: %s", pesel)));
        // ignoring dicomPatientData


        // instance
        Instance instanceEntity = instanceRepository.findOne(dicomData.getInstance().getInstanceUID());
        if(instanceEntity != null)
            logger.warn(String.format("instance '%s' already persisted", dicomData.getInstance().getInstanceUID()));
        instanceEntity = mergeEntity(instanceEntity, Instance.class, dicomData.getInstance(), DicomInstance.class);


        // series
        Series seriesEntity = seriesRepository.findOne(dicomData.getSeries().getInstanceUID());
        seriesEntity = mergeEntity(seriesEntity, Series.class, dicomData.getSeries(), DicomSeries.class);


        // study
        Study studyEntity = studyRepository.findOne(dicomData.getStudy().getInstanceUID());
        studyEntity = mergeEntity(studyEntity, Study.class, dicomData.getStudy(), DicomStudy.class);


        // persisitng
        studyEntity.setPatient(patientEntity);
        seriesEntity.setStudy(studyEntity);
        seriesEntity.addInstance(instanceEntity);
        seriesEntity.setModality(modalityEntity);

        modalityRepository.save(modalityEntity);
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

    // java type parameters kinda suck
    private <Entity, Model extends DicomObject> Entity mergeEntity(Entity currentEntity, Class<Entity> entityClass,
                                                                   Model extracted, Class<Model> modelClass) {
        if (currentEntity == null) { // not in DB
            return objectMapper.convert(extracted, entityClass);
        }

        Model current = objectMapper.convert(currentEntity, modelClass);
        current.lazyMerge(extracted);
        return objectMapper.convert(current, entityClass);
    }
}
