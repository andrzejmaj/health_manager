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
public class DicomInstance extends DicomAttributesContainer {

    private static final DicomModule[] modules = {
            DicomModules.commonModule,
            DicomModules.generalImageModule
    };


    private String instanceUID;

    // TODO SOP class


    public DicomInstance() {
        super(modules);
    }

    public DicomInstance(Attributes dicomAttributes) {
        super(modules);
        this.loadAttributes(dicomAttributes);
        this.setRequiredField(Tag.SOPInstanceUID, this::setInstanceUID);
    }

    public DicomInstance(String instanceUID, Set<DicomAttribute> attributes) {
        super(modules);
        this.instanceUID = instanceUID;
        this.setAttributes(attributes);
    }
}
