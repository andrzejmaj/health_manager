package engineer.thesis.core.model.entity.medcom;

import engineer.thesis.core.model.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.List;

/**
 * @author MKlaman
 * @since 11.09.2017
 */
@Entity
@Table(name = "dcm_study", schema = "hmanager")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Study {

    @Id
    @Column(name = "dicom_id", nullable = false)
    private String dicom_id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToMany(mappedBy = "study", fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private List<Property> properties;

    @OneToMany(mappedBy = "study", fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    private List<Series> series;
}
