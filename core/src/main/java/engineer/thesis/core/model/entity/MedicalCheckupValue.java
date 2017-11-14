package engineer.thesis.core.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_medical_checkups_values", schema = "hmanager")
public class MedicalCheckupValue implements FormFieldData {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medical_checkup_id")
    private MedicalCheckup medicalCheckup;

    @Column(name = "form_field_id", nullable = false)
    private Long formFieldId;

    @Column(name = "value")
    private String value;

}
