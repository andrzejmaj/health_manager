package engineer.thesis.core.model.entity.medcom;

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
@Table(name = "dcm_series", schema = "hmanager")
@Data
@EqualsAndHashCode(of = "instanceUID")
@NoArgsConstructor
@AllArgsConstructor
public class Series {

    @Id
    @Column(name = "instance_uid", nullable = false)
    private String instanceUID;

    @Column(name = "creation_date")
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "modality_id")
    private Modality modality;

    @ManyToOne
    @JoinColumn(name = "study_id")
    private Study study;

    @OneToMany(mappedBy = "key.series", fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    private Set<SeriesAttribute> attributes;

    @OneToMany(mappedBy = "series", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(CascadeType.ALL)
    private Set<Instance> instances = new HashSet<>();


    public void setAttributes(Set<SeriesAttribute> attributes) {
        attributes.forEach(attribute ->
                attribute.getKey().setSeries(this));
        this.attributes = attributes;
    }

    public void addInstance(Instance instance) {
        instance.setSeries(this);
        this.instances.add(instance);
    }

    @Override
    public String toString() {
        return instanceUID;
    }
}
