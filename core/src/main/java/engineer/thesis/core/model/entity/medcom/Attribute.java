package engineer.thesis.core.model.entity.medcom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author MKlaman
 * @since 11.09.2017
 */
@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Attribute {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "value", nullable = false)
    private String value;

    public abstract Integer getCode();

    public abstract void setCode(Integer code);


    @Override
    public String toString() {
        return String.format("[%s] %s", name, value);
    }
}
