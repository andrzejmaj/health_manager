package engineer.thesis.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "hm_current_condition", schema = "hmanager")
public class PatientCondition {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "condition_name")
    private String condition;

    @Column(name = "symptoms")
    private String symptoms;

}
