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
public class DicomStudy extends DicomAttributesContainer {

    private static final DicomModule[] modules = {
            DicomModules.generalStudyModule,
            DicomModules.patientStudyModule
    };


    private String instanceUID;

    public DicomStudy() {
        super(modules);
    }

    public DicomStudy(Attributes dicomAttributes) {
        super(modules);
        this.loadAttributes(dicomAttributes);
        this.setRequiredField(Tag.StudyInstanceUID, this::setInstanceUID);
    }

    public DicomStudy(String instanceUID, Set<DicomAttribute> attributes) {
        super(modules);
        this.instanceUID = instanceUID;
        this.setAttributes(attributes);
    }

}
