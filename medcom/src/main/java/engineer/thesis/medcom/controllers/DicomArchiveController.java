package engineer.thesis.medcom.controllers;

import engineer.thesis.core.model.dto.MedcomPatientDTO;
import engineer.thesis.medcom.dicom.repository.ArchiveRepository;
import engineer.thesis.medcom.model.DicomInstance;
import engineer.thesis.medcom.model.DicomSeries;
import engineer.thesis.medcom.model.DicomStudy;
import engineer.thesis.medcom.model.error.ErrorMessage;
import engineer.thesis.medcom.model.error.InstanceNotFoundException;
import engineer.thesis.medcom.model.old.DicomArchive;
import engineer.thesis.medcom.services.DicomArchiveService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author MKlaman
 * @since 11.06.2017
 */
@RestController
public class DicomArchiveController { // TODO: split into multiple controllers

    private final static Logger logger = Logger.getLogger(DicomArchiveController.class);

    private final DicomArchiveService dicomArchiveService;
    private final ArchiveRepository archiveRepository;

    @Autowired
    public DicomArchiveController(DicomArchiveService dicomArchiveService,
                                  ArchiveRepository archiveRepository) {
        this.dicomArchiveService = dicomArchiveService;
        this.archiveRepository = archiveRepository;
    }


    @Deprecated
    @GetMapping(RequestMappings.ARCHIVE.GET_TREE)
    public DicomArchive getFullArchiveTree() {
        return archiveRepository.getFullArchiveTree();
    }

    @GetMapping(RequestMappings.PATIENT.GET_ALL)
    public List<MedcomPatientDTO> getAllMedcomPatients() {
        return dicomArchiveService.getAllMedcomPatients();
    }

    @GetMapping(RequestMappings.PATIENT.GET_STUDIES_LIST)
    public List<DicomStudy> getPatientStudies(@PathVariable Long patientId) {
        return dicomArchiveService.getPatientStudies(patientId);
    }

    @GetMapping(RequestMappings.STUDIES.GET_ALL)
    public List<DicomStudy> getAllStudies() {
        return dicomArchiveService.getAllStudies();
    }

    @GetMapping(RequestMappings.STUDIES.GET_DETAILS)
    public DicomStudy getStudyDetails(@PathVariable String studyId) {
        return dicomArchiveService.getStudyDetails(studyId);
    }

    @GetMapping(RequestMappings.STUDIES.GET_SERIES_LIST)
    public List<DicomSeries> getStudySeries(@PathVariable String studyId) {
        return dicomArchiveService.getStudySeries(studyId);
    }

    @GetMapping(RequestMappings.SERIES.GET_DETAILS)
    public DicomSeries getSeriesDetails(@PathVariable String seriesId) {
        return dicomArchiveService.getSeriesDetails(seriesId);
    }

    @GetMapping(RequestMappings.SERIES.GET_INSTANCES_LIST)
    public List<DicomInstance> getSeriesInstances(@PathVariable String seriesId) {
        return dicomArchiveService.getSeriesInstances(seriesId);
    }

    @GetMapping(RequestMappings.INSTANCES.GET_DETAILS)
    public DicomInstance getInstanceDetails(@PathVariable String instanceId) {
        return dicomArchiveService.getInstanceDetails(instanceId);
    }


    @ExceptionHandler(InstanceNotFoundException.class) // TODO global exception handler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody
    ErrorMessage handleException(InstanceNotFoundException ex) {
        logger.error(ex);
        return new ErrorMessage(ex.getMessage());
    }
}
