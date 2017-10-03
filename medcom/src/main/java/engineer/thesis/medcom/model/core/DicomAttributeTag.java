package engineer.thesis.medcom.model.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dcm4che3.data.Keyword;

/**
 * @author MKlaman
 * @since 12.09.2017
 */
@Data
@EqualsAndHashCode(exclude = {"name"})
public class DicomAttributeTag {

    private final String name;

    private final Integer code;

    public static DicomAttributeTag of(Integer code) {
        return new DicomAttributeTag(Keyword.valueOf(code), code);
    }
}