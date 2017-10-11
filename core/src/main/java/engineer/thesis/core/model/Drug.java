package engineer.thesis.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
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

    @Column(name = "name", length = 1000)
    private String name;

    @Column(name = "commonName", length = 1000)
    private String commonName;

    @Column(name = "strength", length = 1000)
    private String strength;

    @Column(name = "permitValidity", length = 1000)
    private String permitValidity;

    @Column(name = "entityResponsible", length = 1000)
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
