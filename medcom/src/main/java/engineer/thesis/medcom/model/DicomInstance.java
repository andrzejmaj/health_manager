package engineer.thesis.medcom.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import engineer.thesis.medcom.model.core.DicomAttributesContainer;
import engineer.thesis.medcom.model.core.DicomModule;
import engineer.thesis.medcom.model.core.DicomModules;
import lombok.Getter;
import lombok.Setter;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;

import java.util.Date;

/**
 * @author MKlaman
 * @since 12.09.2017
 */
@Getter
@Setter
@JsonPropertyOrder({"instanceUID", "creationDate", "attributes"})
public class DicomInstance extends DicomAttributesContainer {

    private static final DicomModule[] modules = {
            DicomModules.commonModule,
            DicomModules.generalImageModule
    };


    private String instanceUID;
    private Date creationDate;
    // TODO SOP class

    public DicomInstance() {
        super(modules);
    }

    public DicomInstance(Attributes dicomAttributes) {
        super(modules);
        this.loadAttributes(dicomAttributes);
        this.setRequiredField(Tag.SOPInstanceUID, this::setInstanceUID);
        creationDate = new Date(); // TODO set from attributes

    }
}
