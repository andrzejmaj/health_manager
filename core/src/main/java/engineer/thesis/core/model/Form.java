package engineer.thesis.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_form", schema= "hmanager")
public class Form {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "form", fetch = FetchType.EAGER)
    private List<FormField> formFields;

}
