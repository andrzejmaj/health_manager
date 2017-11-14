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
import java.util.HashSet;
import java.util.Set;

/**
 * @author MKlaman
 * @since 11.09.2017
 */
@Entity
@Table(name = "dcm_modality", schema = "hmanager")
@Data
@EqualsAndHashCode(of = "applicationEntity")
@NoArgsConstructor
@AllArgsConstructor
public class Modality {

    @Id
    @Column(name = "application_entity", nullable = false)
    private String applicationEntity;

    @Column(name = "stationName")
    private String stationName;

    @Column(name = "type", length = 30)
    private String type;

    @Column(name = "address", length = 40)
    private String address;

    @Column(name = "port")
    private int port;

    @Column(name = "description", length = 512)
    private String description;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "key.modality", fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    private Set<ModalityAttribute> attributes;

    @OneToMany(mappedBy = "modality", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(CascadeType.ALL)
    private Set<Series> series = new HashSet<>();


    public void setAttributes(Set<ModalityAttribute> attributes) {
        attributes.forEach(attribute ->
                attribute.getKey().setModality(this));
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return applicationEntity;
    }
}
