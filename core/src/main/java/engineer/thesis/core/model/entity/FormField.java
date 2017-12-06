package engineer.thesis.core.model.entity;

import engineer.thesis.core.model.enums.FieldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private FieldType type;

    @ManyToOne
    @JoinColumn(name = "form_id")
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
    @Fetch(value = FetchMode.SUBSELECT)
    @Cascade(CascadeType.ALL)
    private List<FormAvailableValue> options;

}
