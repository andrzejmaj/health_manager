package engineer.thesis.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "hm_disease", schema = "hmanager")
public class Disease {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
}
