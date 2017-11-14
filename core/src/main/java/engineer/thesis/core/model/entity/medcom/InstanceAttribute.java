package engineer.thesis.core.model.entity.medcom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author MKlaman
 * @since 11.09.2017
 */
@Entity
@Table(name = "dcm_instance_attribute", schema = "hmanager")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class InstanceAttribute extends Attribute {

    @EmbeddedId
    private AttributeKey key = new AttributeKey();

    public Integer getCode() {
        return key.getCode();
    }

    public void setCode(Integer code) {
        key.setCode(code);
    }

    @Embeddable
    @Data
    @EqualsAndHashCode(of = "code")
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AttributeKey implements Serializable {

        @Column(name = "code", nullable = false)
        private Integer code;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "instance_id")
        private Instance instance;
    }
}
