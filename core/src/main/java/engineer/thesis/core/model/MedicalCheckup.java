package engineer.thesis.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_medical_checkups", schema = "hmanager")
public class MedicalCheckup {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "form_id")
    private Long formId;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "created_by_id")
    private User creator;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @OneToMany(mappedBy = "medicalCheckup", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<MedicalCheckupValue> medicalCheckupValues;

}
