package engineer.thesis.medcom.model;

import engineer.thesis.medcom.model.core.DicomAttribute;
import engineer.thesis.medcom.model.core.DicomAttributesContainer;
import engineer.thesis.medcom.model.core.DicomModule;
import engineer.thesis.medcom.model.core.DicomModules;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;

import java.util.List;
import java.util.Map;

/**
 * @author MKlaman
 * @since 12.09.2017
 */
@Getter
@Setter
public class DicomStudy extends DicomAttributesContainer {

    private static final DicomModule[] modules = {
            DicomModules.generalStudyModule,
            DicomModules.patientStudyModule
    };


    private String instanceUID;

    @Singular("series")
    private List<DicomSeries> series;



    public DicomStudy(Attributes dicomAttributes) {
        super(modules);
        loadAttributes(dicomAttributes);

        instanceUID = getAttribute(Tag.StudyInstanceUID)
                .map(DicomAttribute::getValue)
                .orElseThrow(() -> new IllegalArgumentException("can not create study - StudyInstanceUID is missing!"));
    }

    public DicomStudy(String instanceUID, Map<Integer, DicomAttribute> attributes) {
        super(modules);
        this.instanceUID = instanceUID;
        setAttributes(attributes);
    }

}
