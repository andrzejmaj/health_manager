package engineer.thesis.medcom.dicom.repository;

import engineer.thesis.medcom.dicom.store.StoreSCPAdapter;
import engineer.thesis.medcom.model.old.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static engineer.thesis.medcom.utils.FileUtils.deepFileSearch;
import static engineer.thesis.medcom.utils.FileUtils.shallowDirectorySearch;

/**
 * @author MKlaman
 * @since 11.06.2017
 */
@Repository
public class FileArchiveRepository implements ArchiveRepository {

    private final static Logger logger = Logger.getLogger(StoreSCPAdapter.class);

    private static final String DICOM_EXTENSION = ".dcm";
    private static final Predicate<File> IS_DICOM_FILE = (file) -> {
        if (file == null || !file.isFile())
            return false;
        String filename = file.getName();
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex != -1) && (DICOM_EXTENSION.equals(filename.substring(dotIndex)));
    };


    @Value("${dicom.store.scp.archiveDirectory}")
    private String storageDirectory;

    @Override
    public List<DicomInstance> findAllInstances() {
        File archive = new File(storageDirectory);

        return deepFileSearch(archive, IS_DICOM_FILE)
                .stream()
                .map(this::buildInstance)
                .collect(Collectors.toList());
    }

    @Override
    public DicomArchive getFullArchiveTree() {
        File archive = new File(storageDirectory);

        List<Patient> patients = shallowDirectorySearch(archive)
                .stream()
                .map(patientEntry -> {
                    String patientId = patientEntry.getName();
                    List<StudyInstance> studies = shallowDirectorySearch(patientEntry)
                            .stream()
                            .map(studyEntry -> {
                                String studyId = studyEntry.getName();
                                List<SeriesInstance> series = shallowDirectorySearch(studyEntry)
                                        .stream()
                                        .map(seriesEntry -> {
                                            String seriesId = seriesEntry.getName();
                                            List<DicomInstance> dicoms = deepFileSearch(seriesEntry, IS_DICOM_FILE)
                                                    .stream()
                                                    .map(instanceEntry ->
                                                            DicomInstance.builder()
                                                                    .sopInstanceUid(instanceEntry.getName())
                                                                    .build())
                                                    .collect(Collectors.toList());

                                            return SeriesInstance.builder()
                                                    .seriesInstanceUid(seriesId)
                                                    .dicoms(dicoms)
                                                    .build();
                                        })
                                        .collect(Collectors.toList());

                                return StudyInstance.builder()
                                        .studyInstanceUid(studyId)
                                        .series(series)
                                        .build();
                            })
                            .collect(Collectors.toList());

                    return Patient.builder()
                            .patientId(patientId)
                            .studies(studies)
                            .build();
                })
                .collect(Collectors.toList());

        return DicomArchive.builder()
                .patients(patients)
                .build();
    }

    @Override
    public Optional<File> getInstanceFile(String patientId, String studyInstanceUid, String seriesInstanceUid, String sopInstanceUid) {
        String filePath = storageDirectory + '/' + patientId + '/' + studyInstanceUid + '/' + seriesInstanceUid + '/' + sopInstanceUid + DICOM_EXTENSION;
        File instance = new File(filePath);

        return(instance.isFile()) ? Optional.of(instance) : Optional.empty();
    }

    private DicomInstance buildInstance(File dicomFile) {
        String instanceId = dicomFile.getName().substring(
                0,
                dicomFile.getName().length() - DICOM_EXTENSION.length()
        );
        File series = dicomFile.getParentFile();
        File study = series.getParentFile();
        File patient = study.getParentFile();

        return DicomInstance.builder()
                .sopInstanceUid(instanceId)
                .seriesInstanceUid(series.getName())
                .studyInstanceUid(study.getName())
                .patientId(patient.getName())
                .build();
    }
}
