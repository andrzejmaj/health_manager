package engineer.thesis.medcom.controllers;

import engineer.thesis.medcom.dicom.repository.ArchiveRepository;
import engineer.thesis.medcom.model.old.DicomArchive;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MKlaman
 * @since 11.06.2017
 */
@RestController
public class DicomArchiveController {

    private final ArchiveRepository archiveRepository;

    public DicomArchiveController(ArchiveRepository archiveRepository) {
        this.archiveRepository = archiveRepository;
    }

    @GetMapping(RequestMappings.ARCHIVE.GET_TREE)
    public DicomArchive getFullArchiveTree() {
        return archiveRepository.getFullArchiveTree();
    }

}
