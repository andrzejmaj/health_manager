package engineer.thesis.core.service;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.model.*;
import engineer.thesis.core.model.dto.*;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PatientService implements IPatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private CustomObjectMapper objectMapper;

    @Override
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream().map(p -> objectMapper.convert(p, PatientDTO.class)).collect(Collectors.toList());
    }

    @Override
    public PatientDTO findById(Long id) throws NoSuchElementException {
        Optional<Patient> patient = Optional.ofNullable(patientRepository.findOne(id));
        return objectMapper.convert(patient.orElseThrow(NoSuchElementException::new), PatientDTO.class);
    }

    @Override
    public PatientDTO findByPesel(String pesel) throws NoSuchElementException {
        Optional<Patient> patient = Optional.ofNullable(patientRepository.findByAccount_PersonalDetails_Pesel(pesel));
        return objectMapper.convert(patient.orElseThrow(NoSuchElementException::new), PatientDTO.class);
    }

    @Override
    public List<PatientDTO> findPatientsByLastName(String lastName) {
        return patientRepository.findByAccount_PersonalDetails_LastNameLike(lastName).stream().map(p -> objectMapper.convert(p, PatientDTO.class)).collect(Collectors.toList());
    }

    @Override
    public PatientDTO savePatient(PatientDTO patientDTO) throws AlreadyExistsException {
        if (accountService.doesAccountExist(patientDTO.getAccount().getPersonalDetails().getPesel())) {
            throw new AlreadyExistsException("Account with such pesel number already exists");
        }
        return objectMapper.convert(patientRepository.save(objectMapper.convert(patientDTO, Patient.class)), PatientDTO.class);
    }

    @Override
    public PatientDTO updatePatient(PatientDTO patientDTO) throws NoSuchElementException {
        if (!accountService.doesAccountExist(patientDTO.getAccount().getPersonalDetails().getPesel())) {
            throw new NoSuchElementException("Patient does not exist");
        }
        return objectMapper.convert(patientRepository.save(objectMapper.convert(patientDTO, Patient.class)), PatientDTO.class);
    }

    @Override
    public PersonalDetailsDTO findByIdEmergency(Long id) {
        Patient patient = patientRepository.findOne(id);
        if (patient == null) {
            throw new NoSuchElementException("Patient not found");
        }
        if (patient.getEmergencyContact() == null) {
            throw new NoSuchElementException("Emergency contact not found");
        }
        return objectMapper.convert(patient.getEmergencyContact(), PersonalDetailsDTO.class);
    }

    @Override
    public PersonalDetailsDTO saveEmergencyContact(Long id, PersonalDetailsDTO emergencyContact) {
        Patient patient = patientRepository.findOne(id);
        if (patient == null) {
            throw new NoSuchElementException("Patient not found");
        }
        patient.setEmergencyContact(objectMapper.convert(emergencyContact, PersonalDetails.class));
        return objectMapper.convert(patientRepository.save(patient).getEmergencyContact(), PersonalDetailsDTO.class);
    }
}