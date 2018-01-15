package engineer.thesis.medcom.controllers;

import engineer.thesis.core.model.entity.medcom.Instance;
import engineer.thesis.medcom.dicom.repository.ArchiveRepository;
import engineer.thesis.medcom.dicom.store.StoreSCPAdapter;
import engineer.thesis.medcom.model.error.ErrorMessage;
import engineer.thesis.medcom.model.error.InstanceNotFoundException;
import engineer.thesis.medcom.model.old.DicomInstance;
import engineer.thesis.medcom.services.DicomArchiveService;
import org.apache.log4j.Logger;
import org.dcm4che3.data.UID;
import org.dcm4che3.io.DicomOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * @author MKlaman
 * @since 11.06.2017
 */
@RestController
public class DicomInstancesController {

    private final static Logger logger = Logger.getLogger(StoreSCPAdapter.class);

    private final ArchiveRepository archiveRepository;
    private final DicomArchiveService dicomArchiveService;

    @Autowired
    public DicomInstancesController(ArchiveRepository archiveRepository,
                                    DicomArchiveService dicomArchiveService) {
        this.archiveRepository = archiveRepository;
        this.dicomArchiveService = dicomArchiveService;
    }

    @GetMapping(RequestMappings.INSTANCES.GET_ALL)
    public List<DicomInstance> getDicomInstancesList() {
        return archiveRepository.findAllInstances();
    }

    @GetMapping(value = RequestMappings.ARCHIVE.GET_DICOM)
    public void getRawDicomInstance(HttpServletResponse response,
                                    @PathVariable String patientId,
                                    @PathVariable String studyId,
                                    @PathVariable String seriesId,
                                    @PathVariable String instanceId) throws IOException {

        File dicom = archiveRepository.getInstanceFile(patientId, studyId, seriesId, instanceId)
                .orElseThrow(() -> new InstanceNotFoundException("instance not found!"));

        sendDicom(dicom, response);
    }

    @GetMapping(value = RequestMappings.INSTANCES.GET_DICOM)
    public void getRawDicomInstance(HttpServletResponse response,
                                    @PathVariable String instanceId) throws IOException {

        Instance instance = dicomArchiveService.getInstanceEntity(instanceId);
        File dicom = archiveRepository.getInstanceFile(
                instance.getSeries().getStudy().getPatient().getAccount().getPersonalDetails().getPesel(),
                instance.getSeries().getStudy().getInstanceUID(),
                instance.getSeries().getInstanceUID(),
                instanceId
        ).orElseThrow(() -> new InstanceNotFoundException("instance not found!"));

        sendDicom(dicom, response);
    }

    @ExceptionHandler(InstanceNotFoundException.class) // TODO global exception handler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody
    ErrorMessage handleException(InstanceNotFoundException ex) {
        logger.error(ex);
        return new ErrorMessage(ex.getMessage());
    }

    private void sendDicom(File dicom, HttpServletResponse response) throws IOException {
        DicomOutputStream outputStream = new DicomOutputStream(response.getOutputStream(), UID.ExplicitVRLittleEndian);

        Files.copy(dicom.toPath(), outputStream);
        response.flushBuffer();
        // outputStream.close();

        // TODO: fix the headers not being added
        response.setContentLength((int) Files.size(dicom.toPath()));
        response.setContentType("application/dicom");
    }

}
