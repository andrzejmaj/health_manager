package engineer.thesis.medcom.controllers;

import engineer.thesis.core.model.entity.medcom.Instance;
import engineer.thesis.core.model.entity.medcom.Series;
import engineer.thesis.core.repository.medcom.InstanceRepository;
import engineer.thesis.core.repository.medcom.SeriesRepository;
import engineer.thesis.core.repository.medcom.StudyRepository;
import engineer.thesis.core.utils.CustomObjectMapper;
import engineer.thesis.medcom.dicom.repository.ArchiveRepository;
import engineer.thesis.medcom.model.DicomInstance;
import engineer.thesis.medcom.model.DicomSeries;
import engineer.thesis.medcom.model.DicomStudy;
import engineer.thesis.medcom.model.exceptions.ErrorMessage;
import engineer.thesis.medcom.model.exceptions.InstanceNotFoundException;
import engineer.thesis.medcom.model.old.DicomArchive;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author MKlaman
 * @since 11.06.2017
 */
@RestController
public class DicomArchiveController {

    private final static Logger logger = Logger.getLogger(DicomArchiveController.class);

    private final ArchiveRepository archiveRepository;
    private final StudyRepository studyRepository;
    private final SeriesRepository seriesRepository;
    private final InstanceRepository instanceRepository;

    private final CustomObjectMapper objectMapper;

    @Autowired
    public DicomArchiveController(ArchiveRepository archiveRepository,
                                  StudyRepository studyRepository,
                                  SeriesRepository seriesRepository,
                                  InstanceRepository instanceRepository,
                                  CustomObjectMapper objectMapper) {
        this.archiveRepository = archiveRepository;
        this.studyRepository = studyRepository;
        this.seriesRepository = seriesRepository;
        this.instanceRepository = instanceRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping(RequestMappings.ARCHIVE.GET_TREE)
    public DicomArchive getFullArchiveTree() {
        return archiveRepository.getFullArchiveTree();
    }

    @GetMapping(RequestMappings.STUDIES.GET_DETAILS)
    public DicomStudy getStudyDetails(@PathVariable String studyId) {
        return Optional.ofNullable(studyRepository.findOne(studyId))
                .map(entity -> objectMapper.convert(entity, DicomStudy.class))
                .orElseThrow(() -> new InstanceNotFoundException(
                        String.format("study with instanceUID '%s' not found in the database!", studyId)
                ));
    }

    @GetMapping(RequestMappings.STUDIES.GET_SERIES_LIST)
    public List<String> getStudySeries(@PathVariable String studyId) {
        return Optional.ofNullable(studyRepository.findOne(studyId))
                .map(entity ->
                        entity.getSeries().stream()
                                .map(Series::getInstanceUID)
                                .collect(Collectors.toList()))
                .orElseThrow(() -> new InstanceNotFoundException(
                        String.format("study with instanceUID '%s' not found in the database!", studyId)
                ));
    }

    @GetMapping(RequestMappings.SERIES.GET_DETAILS)
    public DicomSeries getSeriesDetails(@PathVariable String seriesId) {
        return Optional.ofNullable(seriesRepository.findOne(seriesId))
                .map(entity -> objectMapper.convert(entity, DicomSeries.class))
                .orElseThrow(() -> new InstanceNotFoundException(
                        String.format("series with instanceUID '%s' not found in the database!", seriesId)
                ));
    }

    @GetMapping(RequestMappings.SERIES.GET_INSTANCES_LIST)
    public List<String> getSeriesInstances(@PathVariable String seriesId) {
        return Optional.ofNullable(seriesRepository.findOne(seriesId))
                .map(entity ->
                        entity.getInstances().stream()
                                .map(Instance::getInstanceUID)
                                .collect(Collectors.toList()))
                .orElseThrow(() -> new InstanceNotFoundException(
                        String.format("series with instanceUID '%s' not found in the database!", seriesId)
                ));
    }

    @GetMapping(RequestMappings.INSTANCES.GET_DETAILS)
    public DicomInstance getInstanceDetails(@PathVariable String instanceId) {
        return Optional.ofNullable(instanceRepository.findOne(instanceId))
                .map(entity -> objectMapper.convert(entity, DicomInstance.class))
                .orElseThrow(() -> new InstanceNotFoundException(
                        String.format("instance with instanceUID '%s' not found in the database!", instanceId)
                ));
    }


    @ExceptionHandler(InstanceNotFoundException.class) // TODO global exception handler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody
    ErrorMessage handleException(InstanceNotFoundException ex) {
        logger.error(ex);
        return new ErrorMessage(ex.getMessage());
    }
}
