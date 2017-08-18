package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.exception.AccessDeniedException;
import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.Account;
import engineer.thesis.core.model.Doctor;
import engineer.thesis.core.model.Patient;
import engineer.thesis.core.model.PersonalDetails;
import engineer.thesis.core.model.dto.PersonalDetailsDTO;
import engineer.thesis.core.repository.AccountRepository;
import engineer.thesis.core.repository.DoctorRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.repository.PersonalDetailsRepository;
import engineer.thesis.core.security.model.SecurityUser;
import engineer.thesis.core.service.Interface.BaseService;
import engineer.thesis.core.service.Interface.IPersonalDetailsService;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PersonalDetailsService extends BaseService implements IPersonalDetailsService {

    @Autowired
    private PersonalDetailsRepository personalDetailsRepository;

    @Autowired
    private CustomObjectMapper objectMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public PersonalDetailsDTO getMine() throws NoSuchElementExistsException {
        SecurityUser user = getCurrentUser();
        PersonalDetails personalDetails = personalDetailsRepository.findByAccount_UserId(user.getId());
        if (personalDetails == null) {
            throw new NoSuchElementExistsException("Personal details doesn't exists");
        }
        return objectMapper.convert(personalDetails, PersonalDetailsDTO.class);
    }

    @Override
    public PersonalDetailsDTO saveOrUpdateMine(PersonalDetailsDTO personalDetailsDTO, boolean save) throws NoSuchElementExistsException, AlreadyExistsException {
        SecurityUser user = getCurrentUser();
        PersonalDetails personalDetails = objectMapper.convert(personalDetailsDTO, PersonalDetails.class);

        if (save) {
            personalDetailsDTO.setId(null);
            Account account = accountRepository.findByUser_Id(user.getId());
            if (account.getPersonalDetails() != null) {
                throw new AlreadyExistsException("Account already has personal details attached");
            }
            account.setPersonalDetails(objectMapper.convert(personalDetailsDTO, PersonalDetails.class));
            return objectMapper.convert(accountRepository.save(account).getPersonalDetails(), PersonalDetailsDTO.class);
        } else {
            PersonalDetails existingPersonalDetails = personalDetailsRepository.findByAccount_UserId(user.getId());
            if (existingPersonalDetails == null) {
                throw new NoSuchElementExistsException("Personal details doesn't exists");
            }
            personalDetails.setId(existingPersonalDetails.getId());
            return objectMapper.convert(personalDetailsRepository.save(personalDetails), PersonalDetailsDTO.class);
        }
    }

    @Override
    public PersonalDetailsDTO get(Long patientId) throws NoSuchElementExistsException, AccessDeniedException {
        SecurityUser user = getCurrentUser();
        Patient patient = patientRepository.findOne(patientId);
        if (patient == null) {
            throw new NoSuchElementExistsException("Patient doesn't exists");
        }
        checkCurrentUser(patient.getAccount().getUser().getId());
        if (patient.getAccount().getPersonalDetails() == null) {
            throw new NoSuchElementExistsException("Personal details doesn't exist");
        }
        return objectMapper.convert(patient.getAccount().getPersonalDetails(), PersonalDetailsDTO.class);
    }

    @Override
    public PersonalDetailsDTO saveOrUpdate(Long patientId, PersonalDetailsDTO personalDetailsDTO, boolean save) throws NoSuchElementExistsException, AlreadyExistsException, AccessDeniedException {
        SecurityUser user = getCurrentUser();
        Patient patient = patientRepository.findOne(patientId);
        if (patient == null) {
            throw new NoSuchElementExistsException("Patient doesn't exist");
        }
        checkCurrentUser(patient.getAccount().getUser().getId());
        if (save) {
            personalDetailsDTO.setId(null);
            if (patient.getAccount().getPersonalDetails() != null) {
                throw new AlreadyExistsException("Patients already has personal details");
            }
            patient.getAccount().setPersonalDetails(objectMapper.convert(personalDetailsDTO, PersonalDetails.class));
            return objectMapper.convert(accountRepository.save(objectMapper.convert(patient.getAccount(), Account.class)).getPersonalDetails(), PersonalDetailsDTO.class);
        } else {
            if (patient.getAccount().getPersonalDetails() == null) {
                throw new NoSuchElementExistsException("Patients doesn't have personal details");
            }
            personalDetailsDTO.setId(patient.getAccount().getPersonalDetails().getId());
            return objectMapper.convert(personalDetailsRepository.save(objectMapper.convert(personalDetailsDTO, PersonalDetails.class)), PersonalDetailsDTO.class);
        }


    }
// TODO: 18.08.17
//    Create base class for patient service?
//    private Patient getPatient(Long id) throws NoSuchElementExistsException {
//        Patient patient = patientRepository.findOne(id);
//        if (patient == null) {
//            throw new NoSuchElementExistsException("Patient doesn't exists");
//        }
//        return patient;
//    }

    @Override
    public PersonalDetailsDTO getDoctor(Long doctorId) throws NoSuchElementExistsException, AccessDeniedException {
        SecurityUser user = getCurrentUser();
        Doctor doctor = doctorRepository.findOne(doctorId);
        if (doctor == null) {
            throw new NoSuchElementExistsException("Doctor doesn't exist");
        }
        checkCurrentUser(doctor.getAccount().getUser().getId());
        return objectMapper.convert(doctor.getAccount().getPersonalDetails(), PersonalDetailsDTO.class);
    }

    @Override
    public PersonalDetailsDTO saveOrUpdateDoctor(Long doctorId, PersonalDetailsDTO personalDetailsDTO, boolean save) throws NoSuchElementExistsException, AlreadyExistsException, AccessDeniedException {
        SecurityUser user = getCurrentUser();
        Doctor doctor = doctorRepository.findOne(doctorId);
        if (doctor == null) {
            throw new NoSuchElementExistsException("Doctor doesn't exists");
        }
        checkCurrentUser(doctor.getAccount().getUser().getId());

        if (save) {
            if (doctor.getAccount().getPersonalDetails() != null) {
                throw new AlreadyExistsException("Personal details already exists");
            }
            personalDetailsDTO.setId(null);
            doctor.getAccount().setPersonalDetails(objectMapper.convert(personalDetailsDTO, PersonalDetails.class));
            return objectMapper.convert(accountRepository.save(doctor.getAccount()).getPersonalDetails(), PersonalDetailsDTO.class);
        } else {
            if (doctor.getAccount().getPersonalDetails() == null) {
                throw new AlreadyExistsException("Personal details doesn't exist");
            }
            personalDetailsDTO.setId(doctor.getAccount().getPersonalDetails().getId());
            return objectMapper.convert(personalDetailsRepository.save(objectMapper.convert(personalDetailsDTO, PersonalDetails.class)), PersonalDetailsDTO.class);
        }
    }
}
