package engineer.thesis.medcom.model.core;

import lombok.Data;
import org.dcm4che3.data.Keyword;

/**
 * @author MKlaman
 * @since 12.09.2017
 */
@Data
public class DicomAttribute {

    public DicomAttribute(Integer code, String value) {
        this(DicomAttributeTag.of(code), value);
    }

    public DicomAttribute(DicomAttributeTag tag, String value) {
        this.tag = tag;
        this.value = value;
    }

    private final DicomAttributeTag tag;

    private final String value;

    @Override
    public String toString() {
        return String.format("[%s]: %s", tag.getName(), value);
    }
}