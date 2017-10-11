package engineer.thesis.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

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

    @OneToMany(mappedBy = "drug", fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Pack> packs;

}
