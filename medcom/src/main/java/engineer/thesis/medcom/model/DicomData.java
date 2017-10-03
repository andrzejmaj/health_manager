package engineer.thesis.medcom.model;

import lombok.Getter;

/**
 * @author MKlaman
 * @since 03.10.2017
 */
@Getter
public class DicomData {

    private final DicomInstance instance;
    private final DicomSeries series;
    //private final DicomModality modality // TODO
    private final DicomStudy study;
    //private final DicomPatient patient // TODO


    public DicomData(DicomInstance.Builder instanceBuilder,
                     DicomSeries.Builder seriesBuilder,
                     DicomStudy.Builder studyBuilder) {

        this.instance = instanceBuilder.build();
        this.series = seriesBuilder.build();
        this.study = studyBuilder.build();
    }
}
