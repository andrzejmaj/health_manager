package engineer.thesis.medcom.services;

import engineer.thesis.medcom.model.*;
import engineer.thesis.medcom.model.core.DicomAttribute;
import engineer.thesis.medcom.model.core.DicomObjectBuilder;
import engineer.thesis.medcom.model.error.DataExtractionException;
import lombok.Getter;
import org.apache.log4j.Logger;
import org.dcm4che3.io.DicomInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author MKlaman
 * @since 03.10.2017
 */
@Service
public class DicomDataService {

    private final static Logger logger = Logger.getLogger(DicomDataService.class);

    private final DicomAttributesReader dicomAttributesReader;

    @Autowired
    public DicomDataService(DicomAttributesReader dicomAttributesReader) {
        this.dicomAttributesReader = dicomAttributesReader;
    }

    public DicomData create(DicomInputStream in) {
        DicomObjectBuildersChain builderChain = new DicomObjectBuildersChain();

        dicomAttributesReader.read(in, builderChain::accept);

        try {
            return new DicomData(
                    builderChain.getInstanceBuilder().build(),
                    builderChain.getSeriesBuilder().build(),
                    builderChain.getStudyBuilder().build(),
                    builderChain.getModalityBuilder().build(),
                    builderChain.getPatientBuilder().build()
            );
        } catch (IllegalArgumentException ex) {
            throw new DataExtractionException("could not create DicomData from read attributes!", ex);
        }
    }

    private class DicomObjectBuildersChain {

        @Getter
        private final DicomObjectBuilder<DicomInstance> instanceBuilder;

        @Getter
        private final DicomObjectBuilder<DicomSeries> seriesBuilder;

        @Getter
        private final DicomObjectBuilder<DicomStudy> studyBuilder;

        @Getter
        private final DicomObjectBuilder<DicomModality> modalityBuilder;

        @Getter
        private final DicomObjectBuilder<DicomPatient> patientBuilder;

        private final List<DicomObjectBuilder> chain;

        DicomObjectBuildersChain() {
            instanceBuilder = DicomInstance.builder();
            seriesBuilder = DicomSeries.builder();
            studyBuilder = DicomStudy.builder();
            modalityBuilder = DicomModality.builder();
            patientBuilder = DicomPatient.builder();

            chain = Stream.<DicomObjectBuilder>of(instanceBuilder, seriesBuilder, studyBuilder, modalityBuilder, patientBuilder)
                    .sorted() // builders ordered according to their priority
                    .collect(Collectors.toList());
        }

        void accept(DicomAttribute attribute) {
            Optional<DicomObjectBuilder> targetBuilder = chain.stream()
                    .filter(builder ->
                            builder.accepts(attribute))
                    .findFirst();

            if (targetBuilder.isPresent()) {
                targetBuilder.get().accept(attribute);
            } else {
                logger.debug("attribute rejected: " + attribute);
            }

        }
    }
}
