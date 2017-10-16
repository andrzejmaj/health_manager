package engineer.thesis.core.model.entity.medcom;

import engineer.thesis.core.model.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author MKlaman
 * @since 11.09.2017
 */
@Entity
@Table(name = "dcm_study", schema = "hmanager")
@Data
@EqualsAndHashCode(of = "instanceUID")
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

    @OneToMany(mappedBy = "key.study", fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    private Set<StudyAttribute> attributes;

    @OneToMany(mappedBy = "study", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(CascadeType.ALL)
    private Set<Series> series = new HashSet<>();


    public void setAttributes(Set<StudyAttribute> attributes) {
        attributes.forEach(attribute ->
                attribute.getKey().setStudy(this));
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return instanceUID;
    }
}
