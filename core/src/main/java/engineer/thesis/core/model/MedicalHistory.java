package engineer.thesis.core.model;

import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
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

    @ManyToOne
    @JoinColumn(name = "disease_id")
    @Cascade(CascadeType.PERSIST)
    private Disease disease;

    @Column(name = "detection_date")
    private Date detectionDate;

    @Column(name = "cure_date")
    private Date cureDate;

}
