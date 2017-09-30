package engineer.thesis.medcom.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import engineer.thesis.medcom.model.core.DicomAttributesContainer;
import engineer.thesis.medcom.model.core.DicomModule;
import engineer.thesis.medcom.model.core.DicomModules;
import lombok.Getter;
import lombok.Setter;
import org.dcm4che3.data.*;

import java.util.Date;

/**
 * @author MKlaman
 * @since 12.09.2017
 */
@Getter
@Setter
@JsonPropertyOrder({"instanceUID", "creationDate", "sopClassName", "attributes"})
public class DicomInstance extends DicomAttributesContainer {

    private static final DicomModule[] modules = {
            DicomModules.commonModule,
            DicomModules.generalImageModule
    };


    private String instanceUID;
    private Date creationDate;
    private String sopClassName;


    public DicomInstance() {
        super(modules);
    }

    public DicomInstance(Attributes dicomAttributes) {
        super(modules);
        super.loadAttributes(dicomAttributes);
        super.setRequiredField(Tag.SOPInstanceUID, this::setInstanceUID);
        super.setRequiredField(Tag.SOPClassUID, this::setSopClassNameByCode);
        super.setDateTimeField(Tag.InstanceCreationDate, Tag.InstanceCreationTime, this::setCreationDate);
//        IOD magicalIOD = new IOD(); // TODO ???
    }

    private void setSopClassNameByCode(String code) {
        sopClassName = UID.nameOf(code);
    }
}
