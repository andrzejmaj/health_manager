package engineer.thesis.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "hm_drug", schema = "hmanager")
public class Drug {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "commonName")
    private String commonName;

    @Column(name = "strength")
    private String strength;

    @Column(name = "permitValidity")
    private String permitValidity;

    @Column(name = "entityResponsible")
    private String entityResponsible;

    @Column(name = "drugForm")
    private String drugForm;

    @Column(name = "refund_rate")
    private Integer refundRate;

}
