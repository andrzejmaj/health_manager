package engineer.thesis.core.model.entity.medcom;

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
@Table(name = "dcm_series", schema = "hmanager")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Series {

    @Id
    @Column(name = "dicom_id", nullable = false)
    private String dicom_id;

    @OneToOne
    @JoinColumn(name = "modality_id")
    @Cascade(CascadeType.ALL)
    private Modality modality;

    @ManyToOne
    @JoinColumn(name = "study_id")
    private Study study;

    @OneToMany(mappedBy = "series", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Property> properties;

    @OneToMany(mappedBy = "series", fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    private List<Instance> instances;
}
