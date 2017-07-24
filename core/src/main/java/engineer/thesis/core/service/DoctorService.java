package engineer.thesis.core.service;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.model.Doctor;
import engineer.thesis.core.model.dto.DoctorDTO;
import engineer.thesis.core.model.dto.SpecializationDTO;
import engineer.thesis.core.repository.DoctorRepository;
import engineer.thesis.core.repository.MedicalInfoRepository;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private MedicalInfoRepository medicalInfoRepository;

    @Override
    public DoctorDTO saveDoctor(DoctorDTO doctorDTO) throws AlreadyExistsException {
        if (accountService.doesAccountExist(doctorDTO.getAccount().getPersonalDetails().getPesel())) {
            throw new AlreadyExistsException("Account with such pesel number already exists");
        }
        SpecializationDTO specialization = specializationService.findExistingOrSaveNewByDescription(doctorDTO.getSpecialization().getDescription());
        doctorDTO.setSpecialization(specialization);
        logger.info("Saving doctor: " + doctorDTO.toString());
        return objectMapper.convert(doctorRepository.save(objectMapper.convert(doctorDTO, Doctor.class)), DoctorDTO.class);
    }
}
