package engineer.thesis.medcom.dicom.repository;

import engineer.thesis.medcom.model.old.DicomArchive;
import engineer.thesis.medcom.model.old.DicomInstance;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * @author MKlaman
 * @since 11.06.2017
 */
public interface ArchiveRepository {

    List<DicomInstance> findAllInstances();

    DicomArchive getFullArchiveTree();

    Optional<File> getInstanceFile(String patientId, String studyInstanceUid, String seriesInstanceUid, String sopInstanceUid);

}
