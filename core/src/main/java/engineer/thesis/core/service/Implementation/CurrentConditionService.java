package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.CurrentCondition;
import engineer.thesis.core.model.dto.CurrentConditionDTO;
import engineer.thesis.core.repository.CurrentConditionRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.service.Interface.ICurrentConditionService;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CurrentConditionService implements ICurrentConditionService {

    @Autowired
    private CustomObjectMapper objectMapper;

    @Autowired
    private CurrentConditionRepository currentConditionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<CurrentConditionDTO> getPatientCondition(Long patientId) throws NoSuchElementExistsException {
        return currentConditionRepository.findByPatientId(patientId).stream().map(cond -> objectMapper.convert(cond, CurrentConditionDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CurrentConditionDTO saveOrUpdate(Long patientId, CurrentConditionDTO currentConditionDTO) throws NoSuchElementExistsException, DataIntegrityException {
        if (!patientId.equals(currentConditionDTO.getPatientId())) {
            throw new DataIntegrityException("Ids in model and path does not match");
        }

        if (!patientRepository.exists(patientId)) {
            throw new NoSuchElementExistsException("Patient doesn't exists");
        }

        if (currentConditionDTO.getId() != null &&
                currentConditionRepository.findByPatientId(patientId).stream()
                    .noneMatch(cond -> Objects.equals(currentConditionDTO.getId(), cond.getId()))) {

            throw new DataIntegrityException("Current condition doesn't belong to the patient");
        }

        return objectMapper.convert(currentConditionRepository.save(objectMapper.convert(currentConditionDTO, CurrentCondition.class)), CurrentConditionDTO.class);
    }

    @Override
    public void delete(Long patientId, Long conditionId) throws NoSuchElementExistsException {
        if (!currentConditionRepository.exists(conditionId)) {
            throw new NoSuchElementExistsException("Current condition with given id doesn't exists");
        }
        currentConditionRepository.delete(conditionId);
    }
}
