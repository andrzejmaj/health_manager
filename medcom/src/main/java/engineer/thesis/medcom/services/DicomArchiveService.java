package engineer.thesis.medcom.services;

import engineer.thesis.core.model.dto.MedcomPatientDTO;
import engineer.thesis.core.model.dto.PatientDTO2;
import engineer.thesis.core.model.entity.Patient;
import engineer.thesis.core.model.entity.medcom.Study;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.repository.medcom.InstanceRepository;
import engineer.thesis.core.repository.medcom.SeriesRepository;
import engineer.thesis.core.repository.medcom.StudyRepository;
import engineer.thesis.core.utils.CustomObjectMapper;
import engineer.thesis.medcom.model.DicomInstance;
import engineer.thesis.medcom.model.DicomSeries;
import engineer.thesis.medcom.model.DicomStudy;
import engineer.thesis.medcom.model.error.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author MKlaman
 * @since 13.09.2017
 */
@Service
public class DicomArchiveService { // TODO generify

    private final PatientRepository patientRepository;
    private final StudyRepository studyRepository;
    private final SeriesRepository seriesRepository;
    private final InstanceRepository instanceRepository;

    private final CustomObjectMapper objectMapper;

    @Autowired
    public DicomArchiveService(PatientRepository patientRepository,
                               StudyRepository studyRepository,
                               SeriesRepository seriesRepository,
                               InstanceRepository instanceRepository,
                               CustomObjectMapper objectMapper) {
        this.patientRepository = patientRepository;
        this.studyRepository = studyRepository;
        this.seriesRepository = seriesRepository;
        this.instanceRepository = instanceRepository;
        this.objectMapper = objectMapper;
    }

    public List<MedcomPatientDTO> getAllMedcomPatients() {
        return patientRepository.findAll()
                .stream()
                .filter(patientEntity -> !patientEntity.getDicomStudies().isEmpty())
                .map(entity ->
                        new MedcomPatientDTO(
                                objectMapper.convert(entity, PatientDTO2.class),
                                entity.getDicomStudies().size(),
                                findLastStudyDate(entity)
                        ))
                .sorted(Comparator.comparing(MedcomPatientDTO::getLastStudyDate).reversed())
                .collect(Collectors.toList());
    }

    private Date findLastStudyDate(Patient patient) {
        return patient.getDicomStudies().stream()
                .sorted(Comparator.comparing(Study::getCreationDate).reversed())
                .map(Study::getCreationDate)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("no study date found"));
    }

    public List<DicomStudy> getPatientStudies(Long patientId) {
        return Optional.ofNullable(patientRepository.findOne(patientId))
                .map(patientEntity ->
                        patientEntity.getDicomStudies().stream()
                                .map(studyEntity -> {
                                    DicomStudy study = objectMapper.convert(studyEntity, DicomStudy.class);
                                    study.setPatientPesel(patientEntity.getAccount().getPersonalDetails().getPesel()); // TODO objectMapper config
                                    return study;
                                })
                                .sorted(Comparator.comparing(DicomStudy::getCreationDate).reversed())
                                .collect(Collectors.toList()))
                .orElseThrow(() -> new InstanceNotFoundException(
                        String.format("patient with id '%s' not found in the database!", patientId)
                ));
    }

    public List<DicomStudy> getAllStudies() {
        return studyRepository.findAll()
                .stream()
                .map(studyEntity -> {
                    DicomStudy study = objectMapper.convert(studyEntity, DicomStudy.class);
                    study.setPatientPesel(studyEntity.getPatient().getAccount().getPersonalDetails().getPesel()); // TODO objectMapper config
                    return study;
                })
                .collect(Collectors.toList());
    }

    public DicomStudy getStudyDetails(String studyId) {
        return Optional.ofNullable(studyRepository.findOne(studyId))
                .map(studyEntity -> {
                    DicomStudy study = objectMapper.convert(studyEntity, DicomStudy.class);
                    study.setPatientPesel(studyEntity.getPatient().getAccount().getPersonalDetails().getPesel()); // TODO objectMapper config
                    return study;
                })
                .orElseThrow(() -> new InstanceNotFoundException(
                        String.format("study with instanceUID '%s' not found in the database!", studyId)
                ));
    }

    public List<DicomSeries> getStudySeries(String studyId) {
        return Optional.ofNullable(studyRepository.findOne(studyId))
                .map(studyEntity ->
                        studyEntity.getSeries().stream()
                                .map(seriesEntity -> {
                                    DicomSeries series = objectMapper.convert(seriesEntity, DicomSeries.class); // TODO objectMapper config
                                    series.setStudyInstanceUID(studyEntity.getInstanceUID());
                                    series.setModalityAET(seriesEntity.getModality().getApplicationEntity());
                                    return series;
                                })
                                .collect(Collectors.toList()))
                .orElseThrow(() -> new InstanceNotFoundException(
                        String.format("study with instanceUID '%s' not found in the database!", studyId)
                ));
    }

    public DicomSeries getSeriesDetails(String seriesId) {
        return Optional.ofNullable(seriesRepository.findOne(seriesId))
                .map(seriesEntity -> {
                    DicomSeries series = objectMapper.convert(seriesEntity, DicomSeries.class); // TODO objectMapper config
                    series.setStudyInstanceUID(seriesEntity.getStudy().getInstanceUID());
                    series.setModalityAET(seriesEntity.getModality().getApplicationEntity());
                    return series;
                })
                .orElseThrow(() -> new InstanceNotFoundException(
                        String.format("series with instanceUID '%s' not found in the database!", seriesId)
                ));
    }

    public List<DicomInstance> getSeriesInstances(String seriesId) {
        return Optional.ofNullable(seriesRepository.findOne(seriesId))
                .map(seriesEntity ->
                        seriesEntity.getInstances().stream()
                                .map(instanceEntity -> {
                                    DicomInstance instance = objectMapper.convert(instanceEntity, DicomInstance.class); // TODO objectMapper config
                                    instance.setSeriesInstanceUID(seriesEntity.getInstanceUID());
                                    return instance;
                                })
                                .collect(Collectors.toList()))
                .orElseThrow(() -> new InstanceNotFoundException(
                        String.format("series with instanceUID '%s' not found in the database!", seriesId)
                ));
    }

    public DicomInstance getInstanceDetails(String instanceId) {
        return Optional.ofNullable(instanceRepository.findOne(instanceId))
                .map(instanceEntity -> {
                    DicomInstance instance = objectMapper.convert(instanceEntity, DicomInstance.class); // TODO objectMapper config
                    instance.setSeriesInstanceUID(instanceEntity.getSeries().getInstanceUID());
                    return instance;
                })
                .orElseThrow(() -> new InstanceNotFoundException(
                        String.format("instance with instanceUID '%s' not found in the database!", instanceId)
                ));
    }
}
