package engineer.thesis.core.model;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@EqualsAndHashCode(exclude = {"medicalInformation","medicalHistories"})
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_patient", schema = "hmanager")
public class Patient {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id")
    @Cascade(CascadeType.ALL)
    private Account account;

    @Column(name = "insurance_number")
    private String insuranceNumber;

    @OneToOne
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "emergency_contact_id")
    private EmergencyContact emergencyContact;

    @OneToOne
    @JoinColumn(name = "medical_information_id")
    @Cascade({CascadeType.PERSIST, CascadeType.MERGE})
    private MedicalInformation medicalInformation;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private List<MedicalHistory> medicalHistories;
}
