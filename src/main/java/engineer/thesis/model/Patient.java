package engineer.thesis.model;

import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
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

    @Id
    @Column(name = "account_id")
    @Cascade(CascadeType.PERSIST)
    private Account account;

    @Column(name = "insurance_number", nullable = false)
    private String insuranceNumber;

    @OneToOne(mappedBy = "patient", fetch = FetchType.LAZY)
    private MedicalInformation medicalInformation;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private List<MedicalHistory> medicalHistories;
}
