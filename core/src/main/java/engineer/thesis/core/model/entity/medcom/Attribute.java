package engineer.thesis.core.model.entity.medcom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author MKlaman
 * @since 11.09.2017
 */
@Entity
@Table(name = "dcm_attribute", schema = "hmanager")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attribute {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private Integer code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "value", nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "study_id")
    private Study study;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;

    @ManyToOne
    @JoinColumn(name = "instance_id")
    private Instance instance;
}
