package engineer.thesis.service;

import engineer.thesis.exception.AlreadyExistsException;
import engineer.thesis.model.*;
import engineer.thesis.model.dto.*;
import engineer.thesis.repository.PatientRepository;
import org.modelmapper.ModelMapper;
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
    private PersonalDetailsService personalDetailsService;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream().map(this::convertPatientToDTO).collect(Collectors.toList());
    }

    @Override
    public PatientDTO findById(Long id) throws NoSuchElementException {
        Optional<Patient> patient = Optional.ofNullable(patientRepository.findOne(id));
        return convertPatientToDTO(patient.orElseThrow(NoSuchElementException::new));
    }

    @Override
    public PatientDTO findByPesel(String pesel) throws NoSuchElementException {
        Optional<Patient> patient = Optional.ofNullable(patientRepository.findByAccount_PersonalDetails_Pesel(pesel));
        return convertPatientToDTO(patient.orElseThrow(NoSuchElementException::new));
    }

    @Override
    public List<PatientDTO> findPatientsByLastName(String lastName) {
        return patientRepository.findByAccount_PersonalDetails_LastNameLike(lastName).stream().map(this::convertPatientToDTO).collect(Collectors.toList());
    }

    @Override
    public PatientDTO savePatient(PatientDTO patientDTO) throws AlreadyExistsException {
        if (accountService.doesAccountExist(patientDTO.getAccount().getPersonalDetails().getPesel())) {
            throw new AlreadyExistsException("Account with such pesel number already exists");
        }
        return convertPatientToDTO(patientRepository.save(convertPatientToEntity(patientDTO)));
    }

    @Override
    public PatientDTO updatePatient(PatientDTO patientDTO) throws NoSuchElementException {
        if (!accountService.doesAccountExist(patientDTO.getAccount().getPersonalDetails().getPesel())) {
            throw new NoSuchElementException("Patient does not exist");
        }
        return convertPatientToDTO(patientRepository.save(convertPatientToEntity(patientDTO)));
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
        return convertPersonalDetailsToDTO(patient.getEmergencyContact());
    }

    @Override
    public PersonalDetailsDTO saveEmergencyContact(Long id, PersonalDetailsDTO emergencyContact) {
        Patient patient = patientRepository.findOne(id);
        if (patient == null) {
            throw new NoSuchElementException("Patient not found");
        }
        patient.setEmergencyContact(convertPersonalDetailsToEntity(emergencyContact));
        return convertPersonalDetailsToDTO(patientRepository.save(patient).getEmergencyContact());
    }

    private PatientDTO convertPatientToDTO(Patient patient) {
        PatientDTO patientDTO = modelMapper.map(patient, PatientDTO.class);
        patientDTO.setEmergencyContact(null);
        return patientDTO;
    }

    private PersonalDetailsDTO convertPersonalDetailsToDTO(PersonalDetails personalDetails) {
        return modelMapper.map(personalDetails, PersonalDetailsDTO.class);
    }

    private Patient convertPatientToEntity(PatientDTO patientDTO) {
        return modelMapper.map(patientDTO, Patient.class);
    }

    private PersonalDetails convertPersonalDetailsToEntity(PersonalDetailsDTO personalDetailsDTO) {
        return modelMapper.map(personalDetailsDTO, PersonalDetails.class);
    }

    @Override
    public PatientDTO mapToDTO(Patient patient) {
        return modelMapper.map(patient, PatientDTO.class);
    }

    @Override
    public Patient mapFromDTO(PatientDTO patientDTO) {
        return null;
    }
}