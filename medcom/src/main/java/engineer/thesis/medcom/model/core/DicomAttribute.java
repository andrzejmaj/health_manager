package engineer.thesis.medcom.model.core;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.dcm4che3.data.Keyword;

/**
 * @author MKlaman
 * @since 12.09.2017
 */
@Data
@NoArgsConstructor
public class DicomAttribute{

    public DicomAttribute(Integer code, String value, String name) {
        this.code = code;
        this.value = value;
        this.name = name;
    }

    public DicomAttribute(Integer code, String value) {
        this(code, value, Keyword.valueOf(code));
    }

    private Integer code;
    private String name;
    private String value;

    @Override
    public String toString() {
        return String.format("[%s]: %s", name, value);
    }
}