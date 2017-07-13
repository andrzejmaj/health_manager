package engineer.thesis.core.service;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.model.*;
import engineer.thesis.core.model.dto.*;
import engineer.thesis.core.repository.MedicalInfoRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
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

    @Autowired
    private MedicalInfoRepository medicalInfoRepository;

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
        if (patientRepository.getOne(patientDTO.getId())==null) {
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

    @Override
    public MedicalInfoDTO findIdMedicalInfo(Long id) throws NoSuchElementException{
        Patient patient = patientRepository.findOne(id);
        if (patient == null) {
            throw new NoSuchElementException("Patient not found");
        }
        return objectMapper.convert(patient.getMedicalInformation(), MedicalInfoDTO.class);
    }

    @Override
    public MedicalInfoDTO saveMedicalInfo(Long id, MedicalInfoDTO medicalInfoDTO) throws NoSuchElementException, AlreadyExistsException {
        Patient patient = patientRepository.getOne(id);
        if (patient == null) {
            throw new NoSuchElementException("Patient not found");
        }
        if (patient.getMedicalInformation() != null) {
            throw new AlreadyExistsException("Patient already has medical info");
        }
        medicalInfoDTO.setPatientId(id);
        medicalInfoDTO.setId(null);
        return objectMapper.convert(medicalInfoRepository.save(objectMapper.convert(medicalInfoDTO, MedicalInformation.class)), MedicalInfoDTO.class);

    }

    @Override
    public MedicalInfoDTO updateMedicalInfo(Long id, MedicalInfoDTO medicalInfoDTO) throws NoSuchElementException {
        Patient patient = patientRepository.getOne(id);
        if (patient == null) {
            throw new NoSuchElementException("Patient not found");
        }
        if (patient.getMedicalInformation() == null) {
            throw new NoSuchElementException("Patient doesn't have medical information");
        }
        medicalInfoDTO.setId(patient.getMedicalInformation().getId());
        medicalInfoDTO.setPatientId(patient.getId());
        return objectMapper.convert(medicalInfoRepository.save(objectMapper.convert(medicalInfoDTO, MedicalInformation.class)), MedicalInfoDTO.class);
    }
}