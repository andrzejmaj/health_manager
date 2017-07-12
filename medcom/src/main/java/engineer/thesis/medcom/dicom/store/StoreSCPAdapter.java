package engineer.thesis.medcom.dicom.store;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author MKlaman
 * @since 03.04.2017
 */
@Component
public class StoreSCPAdapter {
    private final static Logger logger = Logger.getLogger(StoreSCPAdapter.class);

    // <PatientID>/<StudyInstanceUID>/<SeriesInstanceUID>/<SOPInstanceUID>.dcm
    private final static String ARCHIVE_FILEPATH = "{00100020}/{0020000D}/{0020000E}/{00080018}.dcm'";

    private final static String DEFAULT_ARGS_TEMPLATE =
            "--accept-unknown --directory %s --filepath %s -b %s:%d";

    @Value("${dicom.store.scp.ae.name}")
    private String applicationEntityName;

    @Value("${dicom.store.scp.port}")
    private long port;

    @Value("${dicom.store.scp.archiveDirectory}")
    private String storageDirectory;

    @PostConstruct
    public void initStoreSCP() {
        runStoreSCP(applicationEntityName, port, storageDirectory);
    }

    /* runs STORE SCP with default config */
    private void runStoreSCP(String applicationEntityName, long port, String storageDirectory) {
        String[] args = String.format(DEFAULT_ARGS_TEMPLATE, storageDirectory, ARCHIVE_FILEPATH, applicationEntityName, port)
                .split("\\s+");

        logger.info(String.format("Starting StoreSCP '%s' on port: %d", applicationEntityName, port));
        StoreSCP.main(args);
    }

}
