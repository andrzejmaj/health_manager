package engineer.thesis.medcom.model.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dcm4che3.data.Keyword;

/**
 * @author MKlaman
 * @since 12.09.2017
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "code")
public class DicomAttribute{

    public DicomAttribute(Integer code, String value) {
        this(code, value, Keyword.valueOf(code));
    }

    private Integer code;
    private String value;
    private String name;

    @Override
    public String toString() {
        return String.format("[%s]: %s", name, value);
    }
}