package engineer.thesis.medcom.dicom.repository;

import engineer.thesis.medcom.model.DicomArchive;
import engineer.thesis.medcom.model.DicomInstance;

import java.util.List;

/**
 * @author MKlaman
 * @since 11.06.2017
 */
public interface ArchiveRepository {

    public List<DicomInstance> findAllInstances();

    public DicomArchive getFullArchiveTree();

}
