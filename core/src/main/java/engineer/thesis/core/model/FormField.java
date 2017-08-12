package engineer.thesis.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_form_field", schema = "hmanager")
public class FormField {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "form_field_type_id")
    private FormFieldType fieldType;

    @ManyToOne
    @JoinColumn(name = "form_id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Form form;

    @Column(name = "name")
    private String name;

    @Column(name = "is_required")
    private Boolean isRequired;

    @Column(name = "is_editable")
    private Boolean isEditable;

    @Column(name = "label")
    private String label;

    @Column(name = "placeholder_text")
    private String placeholder;

    @Column(name = "contextual_help_text")
    private String contextualText;

    @Column(name = "warning_text")
    private String warningText;

    @Column(name = "error_text")
    private String errorText;

    @OneToMany(mappedBy = "formField", fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<FormAvailableValue> fieldAvailableValues;

}
