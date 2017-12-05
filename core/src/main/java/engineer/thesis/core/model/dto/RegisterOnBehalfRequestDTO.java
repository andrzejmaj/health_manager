package engineer.thesis.core.model.dto;

import engineer.thesis.core.model.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterOnBehalfRequestDTO extends RegisterRequestDTO {
    @NotNull
    private UserRole role;
    private SpecializationDTO specialization;

}
