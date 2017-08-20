package engineer.thesis.core.service.Implementation;

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
    public PersonalDetailsDTO saveOrUpdateMine(PersonalDetailsDTO personalDetailsDTO) {
        SecurityUser user = getCurrentUser();

        Account account = accountRepository.findByUser_Id(user.getId());

        PersonalDetails personalDetails = objectMapper.convert(personalDetailsDTO, PersonalDetails.class);

        if (account.getPersonalDetails() == null) {
            personalDetails.setId(null);
        } else {
            personalDetails.setId(account.getPersonalDetails().getId());
        }

        account.setPersonalDetails(personalDetails);
        return objectMapper.convert(accountRepository.save(account).getPersonalDetails(), PersonalDetailsDTO.class);
    }

    @Override
    public PersonalDetailsDTO getPatient(Long patientId) throws NoSuchElementExistsException {
        Patient patient = patientRepository.findOne(patientId);
        if (patient == null) {
            throw new NoSuchElementExistsException("Patient doesn't exists");
        }
//        checkCurrentUser(patient.getAccount().getUser().getId());
        if (patient.getAccount().getPersonalDetails() == null) {
            throw new NoSuchElementExistsException("Personal details doesn't exist");
        }
        return objectMapper.convert(patient.getAccount().getPersonalDetails(), PersonalDetailsDTO.class);
    }

    @Override
    public PersonalDetailsDTO saveOrUpdatePatient(Long patientId, PersonalDetailsDTO personalDetailsDTO) throws NoSuchElementExistsException {
        Patient patient = patientRepository.findOne(patientId);
        if (patient == null) {
            throw new NoSuchElementExistsException("Patient doesn't exist");
        }
        // TODO: 20.08.17 add this check in filter'
//        checkCurrentUser(patient.getAccount().getUser().getId());


        if (patient.getAccount().getPersonalDetails() == null) {
            personalDetailsDTO.setId(null);
        } else {
            personalDetailsDTO.setId(patient.getAccount().getPersonalDetails().getId());
        }

        patient.getAccount().setPersonalDetails(objectMapper.convert(personalDetailsDTO, PersonalDetails.class));

        return objectMapper.convert(accountRepository.save(objectMapper.convert(patient.getAccount(), Account.class)).getPersonalDetails(), PersonalDetailsDTO.class);

    }

    @Override
    public PersonalDetailsDTO getDoctor(Long doctorId) throws NoSuchElementExistsException {
        Doctor doctor = doctorRepository.findOne(doctorId);
        if (doctor == null) {
            throw new NoSuchElementExistsException("Doctor doesn't exist");
        }
//        checkCurrentUser(doctor.getAccount().getUser().getId());
        return objectMapper.convert(doctor.getAccount().getPersonalDetails(), PersonalDetailsDTO.class);
    }

    @Override
    public PersonalDetailsDTO saveOrUpdateDoctor(Long doctorId, PersonalDetailsDTO personalDetailsDTO) throws NoSuchElementExistsException {
        Doctor doctor = doctorRepository.findOne(doctorId);
        if (doctor == null) {
            throw new NoSuchElementExistsException("Doctor doesn't exists");
        }
        // TODO: 20.08.17 add this check in filter too'
//        checkCurrentUser(doctor.getAccount().getUser().getId());

        if (doctor.getAccount().getPersonalDetails() == null) {
            personalDetailsDTO.setId(null);
        } else {
            personalDetailsDTO.setId(doctor.getAccount().getPersonalDetails().getId());
        }

        doctor.getAccount().setPersonalDetails(objectMapper.convert(personalDetailsDTO, PersonalDetails.class));

        return objectMapper.convert(accountRepository.save(doctor.getAccount()).getPersonalDetails(), PersonalDetailsDTO.class);

    }
}
