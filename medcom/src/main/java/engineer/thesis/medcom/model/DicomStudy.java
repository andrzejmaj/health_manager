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
public class DicomStudy extends DicomAttributesContainer {

    private static final DicomModule[] modules = {
            DicomModules.generalStudyModule,
            DicomModules.patientStudyModule
    };


    private String instanceUID;
    private Date creationDate;


    public DicomStudy() {
        super(modules);
    }

    public DicomStudy(Attributes dicomAttributes) {
        super(modules);
        super.loadAttributes(dicomAttributes);
        super.setRequiredField(Tag.StudyInstanceUID, this::setInstanceUID);
        super.setDateTimeField(Tag.StudyDate, Tag.StudyTime, this::setCreationDate);
    }

}
