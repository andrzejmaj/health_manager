package engineer.thesis.core.model.entity.medcom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author MKlaman
 * @since 11.09.2017
 */
@Entity
@Table(name = "dcm_instance", schema = "hmanager")
@Data
@Builder
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

    @OneToMany(mappedBy = "instance", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Attribute> attributes;


    public void setAttributes(List<Attribute> attributes) {
        attributes.forEach(attribute ->
                attribute.setInstance(this));
        this.attributes = attributes;
    }
}
