package engineer.thesis.core.service.Interface;

import engineer.thesis.core.model.dto.SpecializationDTO;
import org.springframework.stereotype.Service;

@Service
public interface ISpecializationService {
    SpecializationDTO findExistingOrSaveNewByDescription(String description);
}
