package engineer.thesis.core.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "hm_current_condition", schema = "hmanager")
public class CurrentCondition {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "condition_name")
    private String conditionName;

    @Column(name = "symptoms")
    private String symptoms;

}
