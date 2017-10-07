package engineer.thesis.core.model.entity.medcom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MKlaman
 * @since 11.09.2017
 */
@Entity
@Table(name = "dcm_modality", schema = "hmanager")
@Data
@Builder
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

    @OneToMany(mappedBy = "modality", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Attribute> attributes;

    @OneToMany(mappedBy = "modality", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(CascadeType.ALL)
    private List<Series> series = new ArrayList<>();
}
