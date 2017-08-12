package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.CurrentConditionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICurrentConditionService {

    List<CurrentConditionDTO> getPatientCondition(Long patientId) throws NoSuchElementExistsException;

    CurrentConditionDTO savePatientCondition(Long patientId, CurrentConditionDTO currentConditionDTO) throws NoSuchElementExistsException, DataIntegrityException;

    CurrentConditionDTO updatePatientCondition(Long patientId, CurrentConditionDTO currentConditionDTO) throws NoSuchElementExistsException, DataIntegrityException;

    void delete(Long patientId) throws NoSuchElementExistsException;
}
