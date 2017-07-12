package engineer.thesis.core.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "hm_patient_medical_information", schema = "hmanager")
public class MedicalInformation {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "allergies")
    private String allergies;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "height")
    private Integer height;

    @Column(name = "other_notes")
    private String otherNotes;

}
