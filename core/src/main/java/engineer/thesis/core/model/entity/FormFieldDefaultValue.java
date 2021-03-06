package engineer.thesis.core.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hm_form_field_default_value", schema = "hmanager")
public class FormFieldDefaultValue {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "default_values_set_id")
    private DefaultValuesSet defaultValuesSet;

    //Method used to make lambdas smoother and less clunky, based on builder pattern
    public FormFieldDefaultValue builderSetDefaultValuesSet(DefaultValuesSet defaultValuesSet) {
        this.defaultValuesSet = defaultValuesSet;
        return this;
    }

}

