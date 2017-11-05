package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.Account;
import engineer.thesis.core.model.EmergencyContact;
import engineer.thesis.core.model.Patient;
import engineer.thesis.core.model.PersonalDetails;
import engineer.thesis.core.model.dto.*;
import engineer.thesis.core.repository.EmergencyContactRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.repository.PersonalDetailsRepository;
import engineer.thesis.core.service.Interface.BasePatientService;
import engineer.thesis.core.service.Interface.IPatientService;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService implements BasePatientService, IPatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomObjectMapper objectMapper;

    @Autowired
    private PersonalDetailsRepository personalDetailsRepository;

    @Autowired
    private EmergencyContactRepository emergencyContactRepository;

    @Override
    public PatientDTO findById(Long id) throws NoSuchElementExistsException {
        Optional<Patient> patient = Optional.ofNullable(patientRepository.findOne(id));
        return convertToDTO(patient.orElseThrow(() -> new NoSuchElementExistsException("Patient not found")));
    }

    @Override
    public PatientDTO findByPesel(String pesel) throws NoSuchElementExistsException {
        Optional<Patient> patient = Optional.ofNullable(patientRepository.findByAccount_PersonalDetails_Pesel(pesel));
        return convertToDTO(patient.orElseThrow(() -> new NoSuchElementExistsException("Patient not found")));
    }

    @Override
    public PatientDTO findByEmail(String email) throws NoSuchElementExistsException {
        Optional<Patient> patient = Optional.ofNullable(patientRepository.findByAccount_User_Email(email));
        return convertToDTO(patient.orElseThrow(() -> new NoSuchElementExistsException("Patient not found")));
    }

    @Override
    public List<PatientDTO> findPatientsByLastName(String lastName) {
        return patientRepository.findByAccount_PersonalDetails_LastNameIgnoreCase(lastName).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ShortPatientDTO> findAllPatientsShort() {
        return patientRepository.findAll().stream().map(p -> objectMapper.convert(p, ShortPatientDTO.class)).collect(Collectors.toList());
    }

    @Override
    public Page<ShortPatientDTO> findAllPatientsShort(Pageable pageable) {
        return patientRepository.findAll(pageable).map(p -> objectMapper.convert(p, ShortPatientDTO.class));
    }

    @Override
    public PatientDTO register(PatientDetailsDTO patientDetails) throws AlreadyExistsException {
        if (personalDetailsRepository.findByPesel(patientDetails.getPesel()) != null) {
            throw new AlreadyExistsException("Patient with given pesel already exists");
        }

        Patient patient = new Patient();
        patient.setAccount(accountService.createAccount(objectMapper.convert(patientDetails, PersonalDetails.class), null));
        patient.setInsuranceNumber(patientDetails.getInsuranceNumber());

        return convertToDTO(patientRepository.save(patient));
    }

    @Override
    public PersonalDetailsDTO getPersonalDetails(Long id) throws NoSuchElementExistsException {
        Patient patient = findPatient(id, patientRepository);
        if (patient.getAccount().getPersonalDetails() == null) {
            throw new NoSuchElementExistsException("Personal details don't exist");
        }
        return objectMapper.convert(patient.getAccount().getPersonalDetails(), PersonalDetailsDTO.class);
    }

    @Override
    public PersonalDetailsDTO savePersonalDetails(Long id, PersonalDetailsDTO personalDetailsDTO) throws NoSuchElementExistsException, AlreadyExistsException {

        Patient patient = findPatient(id, patientRepository);

        if (patient.getAccount().getPersonalDetails() != null) {
            throw new AlreadyExistsException("Personal details already exist");
        }

        personalDetailsDTO.setId(null);
        patient.getAccount().setPersonalDetails(objectMapper.convert(personalDetailsDTO, PersonalDetails.class));

        return objectMapper.convert(patientRepository.save(patient).getAccount().getPersonalDetails(), PersonalDetailsDTO.class);
    }

    @Override
    public PersonalDetailsDTO updatePersonalDetails(Long id, PersonalDetailsDTO personalDetailsDTO) throws NoSuchElementExistsException {

        Patient patient = findPatient(id, patientRepository);

        if (patient.getAccount().getPersonalDetails() == null) {
            throw new NoSuchElementExistsException("PersonalDetails don't exist");
        }

        personalDetailsDTO.setId(patient.getAccount().getPersonalDetails().getId());
        patient.getAccount().setPersonalDetails(objectMapper.convert(personalDetailsDTO, PersonalDetails.class));

        return objectMapper.convert(patientRepository.save(patient).getAccount().getPersonalDetails(), PersonalDetailsDTO.class);
    }

    @Override
    public EmergencyContactDTO findEmergencyById(Long id) throws NoSuchElementExistsException {
        Patient patient = findPatient(id, patientRepository);
        if (patient.getEmergencyContact() == null) {
            throw new NoSuchElementExistsException("Emergency contact not found");
        }
        return objectMapper.convert(patient.getEmergencyContact(), EmergencyContactDTO.class);
    }

    @Override
    public EmergencyContactDTO saveEmergency(Long id, EmergencyContactDTO emergencyContact) throws AlreadyExistsException, NoSuchElementExistsException {
        Patient patient = findPatient(id, patientRepository);
        if (patient.getEmergencyContact() != null) {
            throw new AlreadyExistsException("Emergency contact already exists");
        }
        emergencyContact.setId(null);
        patient.setEmergencyContact(objectMapper.convert(emergencyContact, EmergencyContact.class));
        return objectMapper.convert(patientRepository.save(patient).getEmergencyContact(), EmergencyContactDTO.class);
    }

    @Override
    public EmergencyContactDTO updateEmergency(Long id, EmergencyContactDTO emergencyContact) throws NoSuchElementExistsException {
        Patient patient = findPatient(id, patientRepository);
        if (patient.getEmergencyContact() == null) {
            throw new NoSuchElementExistsException("Patient haven't set emergency contact yet");
        }

        emergencyContact.setId(patient.getEmergencyContact().getId());
        patient.setEmergencyContact(objectMapper.convert(emergencyContact, EmergencyContact.class));
        return objectMapper.convert(patientRepository.save(patient).getEmergencyContact(), EmergencyContactDTO.class);
    }

    private PatientDTO convertToDTO(Patient patient) {
        PatientDTO patientDTO = objectMapper.convert(patient.getAccount().getPersonalDetails(), PatientDTO.class);
        patientDTO.setInsuranceNumber(patient.getInsuranceNumber());
        patientDTO.setId(patient.getId());
        return patientDTO;
    }

    private Patient convertFromDTO(PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setAccount(new Account());
        patient.getAccount().setCreatedDate(new Date());
        patient.getAccount().setPersonalDetails(objectMapper.convert(patientDTO, PersonalDetails.class));
        patient.setInsuranceNumber(patientDTO.getInsuranceNumber());
        return patient;
    }
}