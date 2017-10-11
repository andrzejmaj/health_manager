package engineer.thesis.core.security.model;

import engineer.thesis.core.model.UserRole;
import engineer.thesis.core.model.dto.PersonalDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterOnBehalfRequest extends RegisterRequest {
    @NotNull
    private UserRole role;
    private PersonalDetailsDTO personalDetails;
    private String insuranceNumber;
}
