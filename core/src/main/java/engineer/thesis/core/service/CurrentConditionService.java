package engineer.thesis.core.service;

import engineer.thesis.core.model.Patient;
import engineer.thesis.core.model.PatientCondition;
import engineer.thesis.core.model.dto.CurrentConditionDTO;
import engineer.thesis.core.repository.CurrentConditionRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CurrentConditionService implements ICurrentConditionService {

    @Autowired
    CustomObjectMapper objectMapper;

    @Autowired
    CurrentConditionRepository currentConditionRepository;

    @Autowired
    PatientRepository patientRepository;

    @Override
    public List<CurrentConditionDTO> getPatientCondition(Long id) throws NoSuchElementException {
        return currentConditionRepository.findByPatientId(id).stream().map(cond -> objectMapper.convert(cond, CurrentConditionDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CurrentConditionDTO savePatientCondition(Long id, CurrentConditionDTO currentConditionDTO) throws NoSuchElementException {
        Patient patient = patientRepository.findOne(id);
        if (patient == null) {
            throw new NoSuchElementException("Patient doesn't exists");
        }
        PatientCondition patientCondition = objectMapper.convert(currentConditionDTO, PatientCondition.class);
        patientCondition.setPatient(patient);
        patientCondition.setId(null);
        return objectMapper.convert(currentConditionRepository.save(patientCondition), CurrentConditionDTO.class);
    }

    @Override
    public CurrentConditionDTO updatePatientCondition(Long id, CurrentConditionDTO currentConditionDTO) throws NoSuchElementException {
        Patient patient = patientRepository.findOne(id);
        if (patient == null) {
            throw new NoSuchElementException("Patient doesn't exists");
        }
        PatientCondition patientCondition = objectMapper.convert(currentConditionDTO, PatientCondition.class);
        return objectMapper.convert(currentConditionRepository.save(patientCondition), CurrentConditionDTO.class);
    }

}
