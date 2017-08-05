package engineer.thesis.core.service;

import engineer.thesis.core.model.dto.CurrentConditionDTO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public interface ICurrentConditionService {

    List<CurrentConditionDTO> getPatientCondition(Long id);

    CurrentConditionDTO savePatientCondition(Long id, CurrentConditionDTO currentConditionDTO) throws NoSuchElementException;

    CurrentConditionDTO updatePatientCondition(Long id, CurrentConditionDTO currentConditionDTO) throws NoSuchElementException;
}
