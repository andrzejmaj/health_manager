package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DoctorDTO {
    Long id;
    private AccountDTO account;
    private Set<SpecializationDTO> specialisations;
}
