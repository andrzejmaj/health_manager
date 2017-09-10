package engineer.thesis.core.model.entity;

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

    @Column(name = "allergies")
    private String allergies;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "height")
    private Integer height;

    @Column(name = "other_notes")
    private String otherNotes;

}
