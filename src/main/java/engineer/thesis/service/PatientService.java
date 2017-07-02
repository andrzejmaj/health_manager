package engineer.thesis.service;

import engineer.thesis.model.*;
import engineer.thesis.model.dto.*;
import engineer.thesis.repository.CurrentConditionRepository;
import engineer.thesis.repository.CurrentDrugRepository;
import engineer.thesis.repository.PatientRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotSupportedException;
import java.io.NotSerializableException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PatientService implements IPatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PersonalDetailsService personalDetailsService;

    @Autowired
    private IUserService userService;

    @Override
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public PatientDTO findById(Long id) throws NoSuchElementException {
        Optional<Patient> patient = Optional.ofNullable(patientRepository.findOne(id));
        return mapToDTO(patient.orElseThrow(NoSuchElementException::new));
    }

    @Override
    public PatientDTO findByPesel(String pesel) throws NoSuchElementException {
        Optional<Patient> patient = Optional.ofNullable(patientRepository.findByUser_PersonalDetails_Pesel(pesel));
        return mapToDTO(patient.orElseThrow(NoSuchElementException::new));
    }

    @Override
    public List<PatientDTO> findPatientsByLastName(String lastName) {
        return patientRepository.findByUser_PersonalDetails_LastNameLike(lastName).stream().map(this::mapToDTO).collect(Collectors.toList());
    }


    @Override
    public PatientDTO saveNewPatient(PatientDTO patientDTO, String email) throws NoSuchElementException{
        Optional<User> user = userService.findByEmail(email);

        Patient patient = new Patient();
        patient.setInsuranceNumber(patientDTO.getInsuranceNumber());
        patient.setUser(user.orElseThrow(NoSuchElementException::new));
        patient.setEmergencyContact(personalDetailsService.mapFromDTO(patientDTO.getEmergencyContact()));
        patientDTO.getPersonalDetails().setId(null);
        user.get().setPersonalDetails(personalDetailsService.mapFromDTO(patientDTO.getPersonalDetails()));

        return mapToDTO(patientRepository.save(patient));
    }

    @Override
    public PatientDTO changePatientDetails(PatientDTO patientDTO) throws NoSuchElementException {
        personalDetailsService.save(patientDTO.getPersonalDetails());

        Patient patient = patientRepository.getOne(patientDTO.getId());
        if (patient == null) {
            throw new NoSuchElementException("Patient not found");
        }
        patient.setInsuranceNumber(patient.getInsuranceNumber());
        return mapToDTO(patientRepository.save(patient));
    }

    @Override
    public PatientDTO mapToDTO(Patient patient) {
        return new PatientDTO(
                patient.getId(),
                personalDetailsService.mapToDTO(patient.getUser().getPersonalDetails()),
                personalDetailsService.mapToDTO(patient.getEmergencyContact()),
                patient.getInsuranceNumber()
                );
    }

    @Override
    public Patient mapFromDTO(PatientDTO patientDTO) {
        return null;
    }
}












//    @Override
//    public List<CurrentStateDTO> getPatientCurrentCondition(Long id) throws NoSuchElementException {
//        Map<Long, CurrentStateDTO> currentState = new HashMap<>();
//        findById(id);
//        List<CurrentDrug> currentDrugs = currentDrugRepository.findByPatientId(id);
//        currentDrugs.forEach(drug -> {
//            Long conditionId = drug.getCondition().getId();
//            if (currentState.containsKey(conditionId)) {
//                currentState.get(conditionId).getTakenDrugs().add(new DrugDTO(drug.getDrug()));
//            } else {
//                CurrentStateDTO state = new CurrentStateDTO(drug, new ArrayList<>(Arrays.asList(new DrugDTO(drug.getDrug()))));
//                currentState.put(conditionId, state);
//            }
//        });
//        return new ArrayList<>(currentState.values());
//    }
//
//    @Override
//    public PatientMedicalInformationDTO getPatientMedicalInformation(Long id) throws NoSuchElementException {
//        Optional<Patient> patient = Optional.ofNullable(patientRepository.findOne(id));
//        return new PatientMedicalInformationDTO(patient.orElseThrow(NoSuchElementException::new));
//    }
//
//    @Override
//    public List<MedicalHistoryDTO> getPatientMedicalHistory(Long id) throws NoSuchElementException {
//        Optional<Patient> patient = Optional.ofNullable(patientRepository.findOne(id));
//        if (patient.isPresent()) {
//            return patient.get().getMedicalHistories().stream().map(MedicalHistoryDTO::new).collect(Collectors.toList());
//        } else {
//            throw new NoSuchElementException();
//        }
//    }
