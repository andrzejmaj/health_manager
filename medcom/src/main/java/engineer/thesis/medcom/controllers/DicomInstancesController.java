package engineer.thesis.medcom.controllers;

import engineer.thesis.medcom.dicom.repository.ArchiveRepository;
import engineer.thesis.medcom.dicom.store.StoreSCPAdapter;
import engineer.thesis.medcom.model.error.ErrorMessage;
import engineer.thesis.medcom.model.error.InstanceNotFoundException;
import engineer.thesis.medcom.model.old.DicomInstance;
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

    @Autowired
    public DicomInstancesController(ArchiveRepository archiveRepository) {
        this.archiveRepository = archiveRepository;
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

        // TODO: move this logic elsewhere
        DicomOutputStream outputStream = new DicomOutputStream(response.getOutputStream(), UID.ExplicitVRLittleEndian);
        File instance = archiveRepository.getInstanceFile(patientId, studyId, seriesId, instanceId)
                .orElseThrow(() -> new InstanceNotFoundException("instance not found!"));

        Files.copy(instance.toPath(), outputStream);
        response.flushBuffer();
        // outputStream.close();

        // TODO: fix the headers not being added
        response.setContentLength((int) Files.size(instance.toPath()));
        response.setContentType("application/dicom");
    }

    @ExceptionHandler(InstanceNotFoundException.class) // TODO global exception handler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody
    ErrorMessage handleException(InstanceNotFoundException ex) {
        logger.error(ex);
        return new ErrorMessage(ex.getMessage());
    }

}
