package engineer.thesis.core.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_form_available_values", schema = "hmanager")
public class FormAvailableValue {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "form_field_id")
    private FormField formField;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

}
