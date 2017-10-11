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

    @Column(name = "count", nullable = false)
    private String unit;

    @Column(name = "unit", nullable = false)
    private Float count;

    @Column(name = "drug_id", nullable = false)
    private Drug drug;

    public Pack(String unit, Float count, Drug drug) {
        this.unit = unit;
        this.count = count;
        this.drug = drug;
    }
}
