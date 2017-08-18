package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.exception.AccessDeniedException;
import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.MedicalInformation;
import engineer.thesis.core.model.Patient;
import engineer.thesis.core.model.dto.MedicalInfoDTO;
import engineer.thesis.core.repository.MedicalInfoRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.service.Interface.BaseService;
import engineer.thesis.core.service.Interface.IMedicalInfoService;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalInfoService extends BaseService implements IMedicalInfoService {

    @Autowired
    private MedicalInfoRepository medicalInfoRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private CustomObjectMapper objectMapper;

    @Override
    public MedicalInfoDTO findByPatientId(Long patientId) throws NoSuchElementExistsException, AccessDeniedException {
        checkCurrentUser(patientId);
        Patient patient = patientRepository.findOne(patientId);
        if (patient == null) {
            throw new NoSuchElementExistsException("Patient not found");
        }
        if (patient.getMedicalInformation() == null) {
            throw new NoSuchElementExistsException("Patient doesn't have medical information");
        }
        return objectMapper.convert(patient.getMedicalInformation(), MedicalInfoDTO.class);
    }

    @Override
    public MedicalInfoDTO save(Long id, MedicalInfoDTO medicalInfoDTO) throws NoSuchElementExistsException, AlreadyExistsException {
        Patient patient = patientRepository.findOne(id);
        if (patient == null) {
            throw new NoSuchElementExistsException("Patient not found");
        }
        if (patient.getMedicalInformation() != null) {
            throw new AlreadyExistsException("Patient already has medical info");
        }
        medicalInfoDTO.setId(null);
        patient.setMedicalInformation(objectMapper.convert(medicalInfoDTO, MedicalInformation.class));
        return objectMapper.convert(patientRepository.save(patient).getMedicalInformation(), MedicalInfoDTO.class );

    }

    @Override
    public MedicalInfoDTO update(Long patientId, MedicalInfoDTO medicalInfoDTO) throws NoSuchElementExistsException {
        Patient patient = patientRepository.findOne(patientId);
        if (patient == null) {
            throw new NoSuchElementExistsException("Patient not found");
        }
        if (patient.getMedicalInformation() == null) {
            throw new NoSuchElementExistsException("Patient doesn't have existing medical information");
        }
        medicalInfoDTO.setId(patient.getMedicalInformation().getId());
        return objectMapper.convert(medicalInfoRepository.save(objectMapper.convert(medicalInfoDTO, MedicalInformation.class)), MedicalInfoDTO.class);
    }

    @Override
    public void delete(Long id) throws NoSuchElementExistsException {
        if (!medicalInfoRepository.exists(id)) {
            throw new NoSuchElementExistsException("Medical record doesn't exists");
        }
        medicalInfoRepository.delete(id);
    }
}
