package engineer.thesis.medcom.services;

import engineer.thesis.medcom.model.DicomData;
import engineer.thesis.medcom.model.DicomInstance;
import engineer.thesis.medcom.model.DicomSeries;
import engineer.thesis.medcom.model.DicomStudy;
import engineer.thesis.medcom.model.core.DicomAttribute;
import engineer.thesis.medcom.model.core.DicomObjectBuilder;
import engineer.thesis.medcom.model.error.DataExtractionException;
import lombok.Getter;
import org.dcm4che3.io.DicomInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author MKlaman
 * @since 03.10.2017
 */
@Service
public class DicomDataService {

    private final DicomAttributesReader dicomAttributesReader;

    @Autowired
    public DicomDataService(DicomAttributesReader dicomAttributesReader) {
        this.dicomAttributesReader = dicomAttributesReader;
    }

    public DicomData extract(DicomInputStream in) {
        DicomObjectBuildersChain builderChain = new DicomObjectBuildersChain();

        dicomAttributesReader.read(in, builderChain::accept);

        try {
            return new DicomData(
                    builderChain.getInstanceBuilder().build(),
                    builderChain.getSeriesBuilder().build(),
                    builderChain.getStudyBuilder().build()
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

        private final List<DicomObjectBuilder> chain;

        DicomObjectBuildersChain() {
            instanceBuilder = DicomInstance.builder();
            seriesBuilder = DicomSeries.builder();
            studyBuilder = DicomStudy.builder();

            chain = Stream.<DicomObjectBuilder>of(instanceBuilder, seriesBuilder, studyBuilder)
                    .sorted()
                    .collect(Collectors.toList());
        }

        void accept(DicomAttribute attribute) {
            chain.stream()
                    .filter(builder ->
                            builder.accepts(attribute))
                    .findFirst()
                    .ifPresent(builder ->
                            builder.attribute(attribute));
        }
    }
}
