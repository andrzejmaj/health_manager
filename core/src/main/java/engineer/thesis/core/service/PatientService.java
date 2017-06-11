package engineer.thesis.core.service;

import engineer.thesis.core.model.*;
import engineer.thesis.core.model.dto.*;
import engineer.thesis.core.repository.CurrentConditionRepository;
import engineer.thesis.core.repository.CurrentDrugRepository;
import engineer.thesis.core.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PatientService implements IPatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    CurrentConditionRepository currentConditionRepository;

    @Autowired
    CurrentDrugRepository currentDrugRepository;

    @Override
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream().map(PatientDTO::new).collect(Collectors.toList());
    }

    @Override
    public PatientDTO findById(Long id) throws NoSuchElementException {
        Optional<Patient> patient = Optional.ofNullable(patientRepository.findOne(id));
        return new PatientDTO(patient.orElseThrow(NoSuchElementException::new));
    }

    @Override
    public PatientDTO findByPesel(String pesel) throws NoSuchElementException {
        Optional<Patient> patient = Optional.ofNullable(patientRepository.findByPersonalDetails_Pesel(pesel));
        return new PatientDTO(patient.orElseThrow(NoSuchElementException::new));
    }

    @Override
    public List<PatientDTO> findPatientsByLastName(String lastName) {
        return patientRepository.findByPersonalDetails_LastNameLike(lastName).stream().map(PatientDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<CurrentStateDTO> getPatientCurrentCondition(Long id) throws NoSuchElementException {
        Map<Long, CurrentStateDTO> currentState = new HashMap<>();
        findById(id);
        List<CurrentDrug> currentDrugs = currentDrugRepository.findByPatientId(id);
        currentDrugs.forEach(drug -> {
            Long conditionId = drug.getCondition().getId();
            if (currentState.containsKey(conditionId)) {
                currentState.get(conditionId).getTakenDrugs().add(new DrugDTO(drug.getDrug()));
            } else {
                CurrentStateDTO state = new CurrentStateDTO(drug, new ArrayList<>(Arrays.asList(new DrugDTO(drug.getDrug()))));
                currentState.put(conditionId, state);
            }
        });
        return new ArrayList<>(currentState.values());
    }

    @Override
    public PatientMedicalInformationDTO getPatientMedicalInformation(Long id) throws NoSuchElementException {
        Optional<Patient> patient = Optional.ofNullable(patientRepository.findOne(id));
        return new PatientMedicalInformationDTO(patient.orElseThrow(NoSuchElementException::new));
    }

    @Override
    public List<MedicalHistoryDTO> getPatientMedicalHistory(Long id) throws NoSuchElementException {
        Optional<Patient> patient = Optional.ofNullable(patientRepository.findOne(id));
        if (patient.isPresent()) {
            return patient.get().getMedicalHistories().stream().map(MedicalHistoryDTO::new).collect(Collectors.toList());
        } else {
            throw new NoSuchElementException();
        }
    }
}
