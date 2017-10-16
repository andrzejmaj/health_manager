package engineer.thesis.core.model.entity.medcom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @author MKlaman
 * @since 11.09.2017
 */
@Entity
@Table(name = "dcm_instance", schema = "hmanager")
@Data
@EqualsAndHashCode(of = "instanceUID")
@NoArgsConstructor
@AllArgsConstructor
public class Instance {

    @Id
    @Column(name = "instance_uid", nullable = false)
    private String instanceUID;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "sop_class_name")
    private String sopClassName;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;

    @OneToMany(mappedBy = "key.instance", fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<InstanceAttribute> attributes;


    public void setAttributes(Set<InstanceAttribute> attributes) {
        attributes.forEach(attribute ->
                attribute.getKey().setInstance(this));
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return instanceUID;
    }
}
