package engineer.thesis.core.model;


import engineer.thesis.core.model.persistance.JsonbPostgreType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;


@Data
@Entity
@Table(name = "hm_medical_checkup", schema = "hmanager")
@TypeDef(name = "JsonbPostgreType", typeClass = JsonbPostgreType.class)
public class MedicalCheckup {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patientId;

    @Column(name = "creation_date")
    private Date creationDate;

    @Type(type = "JsonbPostgreType")
    private HashMap<String, Object> data;
}
