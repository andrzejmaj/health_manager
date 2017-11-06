package engineer.thesis.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "hm_medical_history", schema = "hmanager")
public class MedicalHistory {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "symptoms")
    private String symptoms;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "diseases")
    private String diseaseName;

    @Column(name = "detection_date")
    private Date detectionDate;

    @Column(name = "cure_date")
    private Date cureDate;

}
