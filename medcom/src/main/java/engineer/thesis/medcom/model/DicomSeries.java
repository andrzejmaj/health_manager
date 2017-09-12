package engineer.thesis.medcom.model;

import engineer.thesis.medcom.model.core.DicomAttribute;
import engineer.thesis.medcom.model.core.DicomAttributesContainer;
import engineer.thesis.medcom.model.core.DicomModule;
import engineer.thesis.medcom.model.core.DicomModules;
import lombok.Getter;
import lombok.Setter;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;

import java.util.Set;

/**
 * @author MKlaman
 * @since 12.09.2017
 */
@Getter
@Setter
public class DicomSeries extends DicomAttributesContainer {

    private static final DicomModule[] modules = {
            DicomModules.generalSeriesModule
    };


    private String instanceUID;

    public DicomSeries() {
        super(modules);
    }

    public DicomSeries(Attributes dicomAttributes) {
        super(modules);
        this.loadAttributes(dicomAttributes);

        instanceUID = getAttribute(Tag.SeriesInstanceUID)
                .map(DicomAttribute::getValue)
                .orElseThrow(() -> new IllegalArgumentException("can not create series - SeriesInstanceUID is missing!"));
    }

    public DicomSeries(String instanceUID, Set<DicomAttribute> attributes) {
        super(modules);
        this.instanceUID = instanceUID;
        this.setAttributes(attributes);
    }
}
