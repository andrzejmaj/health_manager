package engineer.thesis.core.model.dto;

import engineer.thesis.core.validator.RegisterUserGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    @NotNull
    @Email
    private String email;
    @NotNull(groups = RegisterUserGroup.class)
    private String password;
    @NotNull
    @Valid
    private PersonalDetailsDTO personalDetails;
    private String insuranceNumber;
}
