package engineer.thesis.core.model.entity.medcom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(name = "address", length = 40)
    private String address;

    @Column(name = "port")
    private int port;

    @Column(name = "type", length = 30)
    private String type;

    @Column(name = "stationName")
    private String stationName;

    @Column(name = "description", length = 512)
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "manufacturerModelName")
    private String manufacturerModelName;

    @Column(name = "softwareVersions")
    private String softwareVersions;

    @Column(name = "serialNumber")
    private String serialNumber;
}
