package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.entity.Doctor;
import engineer.thesis.core.model.dto.DoctorDTO;
import engineer.thesis.core.model.dto.SpecializationDTO;
import engineer.thesis.core.repository.DoctorRepository;
import engineer.thesis.core.service.Interface.IDoctorService;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorService implements IDoctorService {
    private final static Logger logger = Logger.getLogger(DoctorService.class);

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SpecializationService specializationService;

    @Autowired
    private CustomObjectMapper objectMapper;

    @Override
    public DoctorDTO saveDoctor(DoctorDTO doctorDTO) throws AlreadyExistsException {
        if (accountService.checkExitance(doctorDTO.getAccount().getPersonalDetails().getPesel())) {
            throw new AlreadyExistsException("Account with such pesel number already exists");
        }
        SpecializationDTO specialization = specializationService.findExistingOrSaveNewByDescription(doctorDTO.getSpecialization().getDescription());
        doctorDTO.setSpecialization(specialization);
        logger.info("Saving doctor: " + doctorDTO.toString());
        return objectMapper.convert(doctorRepository.save(objectMapper.convert(doctorDTO, Doctor.class)), DoctorDTO.class);
    }


    @Override
    public DoctorDTO findByID(Long id) throws NoSuchElementException {
        Doctor doctor = doctorRepository.findOne(id);
        if (doctor == null) {
            throw new NoSuchElementException("Doctor not found");
        }
        return objectMapper.convert(doctor, DoctorDTO.class);
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream().map(d -> objectMapper.convert(d, DoctorDTO.class)).collect(Collectors.toList());
    }

    @Override
    public DoctorDTO updateDoctor(DoctorDTO doctorDTO) throws NoSuchElementException {
        Doctor doctor = doctorRepository.findOne(doctorDTO.getId());
        if (doctor == null) {
            throw new NoSuchElementException("Doctor does not exists");
        }
        return objectMapper.convert(doctorRepository.save(objectMapper.convert(doctorDTO, Doctor.class)), DoctorDTO.class);
    }

    @Override
    public DoctorDTO findByEmail(String email) throws NoSuchElementExistsException {
        Optional<Doctor> doctor = Optional.ofNullable(doctorRepository.findByAccount_User_Email(email));
        return objectMapper.convert(doctor.orElseThrow(() -> new NoSuchElementExistsException("Doctor not found")), DoctorDTO.class);
    }

}
