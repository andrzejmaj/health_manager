package engineer.thesis.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "hm_pack", schema = "hmanager")
public class Pack {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unit")
    private String unit;

    @Column(name = "count")
    private Float count;

    @ManyToOne
    @JoinColumn(name = "drug_id")
    private Drug drug;

    public Pack(String unit, Float count, Drug drug) {
        this.unit = unit;
        this.count = count;
        this.drug = drug;
    }
}
