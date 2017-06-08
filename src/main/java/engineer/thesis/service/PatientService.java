package engineer.thesis.service;

import engineer.thesis.model.*;
import engineer.thesis.model.dto.CurrentStateDTO;
import engineer.thesis.model.dto.DrugDTO;
import engineer.thesis.model.dto.PatientDTO;
import engineer.thesis.model.dto.PatientMedicalInformationDTO;
import engineer.thesis.repository.CurrentConditionRepository;
import engineer.thesis.repository.CurrentDrugRepository;
import engineer.thesis.repository.MedicalInformationRepository;
import engineer.thesis.repository.PatientRepository;
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

    @Autowired
    MedicalInformationRepository medicalInformationRepository;

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
                CurrentStateDTO state = new CurrentStateDTO(drug, new ArrayList<DrugDTO>(Arrays.asList(new DrugDTO(drug.getDrug()))));
                currentState.put(conditionId, state);
            }
        });
        return new ArrayList<>(currentState.values());
    }

    @Override
    public PatientMedicalInformationDTO getPatientMedicalInformation(Long id) throws NoSuchElementException {
        findById(id);
        return new PatientMedicalInformationDTO(medicalInformationRepository.findByPatientId(id));
    }


}
