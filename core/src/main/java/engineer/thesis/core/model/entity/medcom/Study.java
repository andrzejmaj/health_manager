package engineer.thesis.core.model.entity.medcom;

import engineer.thesis.core.model.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
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
    @Column(name = "instance_uid", nullable = false)
    private String instanceUID;

    @Column(name = "creation_date")
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToMany(mappedBy = "study", fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    private List<Attribute> attributes;

    @OneToMany(mappedBy = "study", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(CascadeType.ALL)
    private List<Series> series = new ArrayList<>();


    public void setAttributes(List<Attribute> attributes) {
        attributes.forEach(attribute ->
                attribute.setStudy(this));
        this.attributes = attributes;
    }
}
