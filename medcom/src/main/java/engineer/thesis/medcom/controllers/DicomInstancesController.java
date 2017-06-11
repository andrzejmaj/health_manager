package engineer.thesis.medcom.controllers;

import engineer.thesis.medcom.dicom.repository.ArchiveRepository;
import engineer.thesis.medcom.model.DicomInstance;
import org.dcm4che3.data.UID;
import org.dcm4che3.io.DicomOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author MKlaman
 * @since 11.06.2017
 */
@RestController
public class DicomInstancesController {

    private final ArchiveRepository archiveRepository;

    @Autowired
    public DicomInstancesController(ArchiveRepository archiveRepository) {
        this.archiveRepository = archiveRepository;
    }

    @GetMapping(RequestMappings.INSTANCES.GET_ALL)
    public List<DicomInstance> getDicomInstancesList() {
        return archiveRepository.findAllInstances();
    }

    @GetMapping(value = RequestMappings.INSTANCES.GET_DICOM, produces = "application/dicom")
    public void getRawDicomInstance(HttpServletResponse response) throws IOException {

        DicomOutputStream out = new DicomOutputStream(response.getOutputStream(), UID.ExplicitVRLittleEndian);
        //TODO
    }

}
