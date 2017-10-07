package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtendedDrugDTO {
    private Long id;
    private String name;
    private String commonName;
    private String strength;
    private String permitValidity;
    private String entityResponsible;
    private String drugForm;
}
