package engineer.thesis.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "hm_current_drug", schema = "hmanager")
public class CurrentDrug {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "condition_id")
    private CurrentCondition condition;

    @OneToOne
    @JoinColumn(name = "drug_id")
    private Drug drug;

}
