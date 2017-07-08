package engineer.thesis.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@EqualsAndHashCode(exclude = {"medicalInformation","medicalHistories"})
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_patient", schema = "hmanager")
public class Patient extends Account {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "insurance_number", nullable = false)
    private String insuranceNumber;

    @OneToOne
    @JoinColumn(name = "emergency_contact_id")
    private PersonalDetails emergencyContact;

    @OneToOne(mappedBy = "patient", fetch = FetchType.LAZY)
    private MedicalInformation medicalInformation;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private List<MedicalHistory> medicalHistories;
}
