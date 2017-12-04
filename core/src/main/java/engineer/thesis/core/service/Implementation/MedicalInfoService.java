package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.NoContentException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.MedicalInfoDTO;
import engineer.thesis.core.model.entity.MedicalInformation;
import engineer.thesis.core.model.entity.Patient;
import engineer.thesis.core.repository.MedicalInfoRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.service.Interface.BasePatientService;
import engineer.thesis.core.service.Interface.BaseService;
import engineer.thesis.core.service.Interface.IMedicalInfoService;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MedicalInfoService implements IMedicalInfoService, BasePatientService, BaseService {

    @Autowired
    private MedicalInfoRepository medicalInfoRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private CustomObjectMapper objectMapper;

    @Override
    public MedicalInfoDTO findByPatientId(Long patientId) throws NoSuchElementExistsException {
        Patient patient = findPatient(patientId, patientRepository);
        if (patient.getMedicalInformation() == null) {
            throw new NoSuchElementExistsException("Patient doesn't have medical information");
        }
        return objectMapper.convert(patient.getMedicalInformation(), MedicalInfoDTO.class);
    }

    @Override
    public MedicalInfoDTO save(Long id, MedicalInfoDTO medicalInfoDTO) throws NoSuchElementExistsException, AlreadyExistsException {
        Patient patient = findPatient(id, patientRepository);
        return saveForPatient(patient, medicalInfoDTO);
    }

    @Override
    public MedicalInfoDTO update(Long patientId, MedicalInfoDTO medicalInfoDTO) throws NoSuchElementExistsException {
        Patient patient = findPatient(patientId, patientRepository);

        return updateForPatient(patient, medicalInfoDTO);
    }

    @Override
    public void delete(Long id) throws NoSuchElementExistsException {
        if (!medicalInfoRepository.exists(id)) {
            throw new NoSuchElementExistsException("Medical record doesn't exists");
        }
        medicalInfoRepository.delete(id);
    }

    @Override
    public MedicalInfoDTO findMine() {
        MedicalInformation medicalInformation = patientRepository.findByAccount_User_Email(getCurrentLoggedUser().getEmail()).getMedicalInformation();
        if (medicalInformation == null) {
            throw new NoContentException("Patient doesn't have medical information");
        }
        return objectMapper.convert(medicalInformation, MedicalInfoDTO.class);
    }

    @Override
    public MedicalInfoDTO saveMine(MedicalInfoDTO medicalInfoDTO) throws AlreadyExistsException {
        Patient patient = patientRepository.findByAccount_User_Email(getCurrentLoggedUser().getEmail());
        return saveForPatient(patient, medicalInfoDTO);
    }

    @Override
    public MedicalInfoDTO updateMine(MedicalInfoDTO medicalInfoDTO) throws NoSuchElementExistsException {
        Patient patient = patientRepository.findByAccount_User_Email(getCurrentLoggedUser().getEmail());
        return updateForPatient(patient, medicalInfoDTO);
    }

    private MedicalInfoDTO saveForPatient(Patient patient, MedicalInfoDTO medicalInfoDTO) throws AlreadyExistsException {
        if (patient.getMedicalInformation() != null) {
            throw new AlreadyExistsException("Patient already has medical info");
        }
        medicalInfoDTO.setId(null);
        patient.setMedicalInformation(objectMapper.convert(medicalInfoDTO, MedicalInformation.class));
        patient.getMedicalInformation().setLastMeasurement(new Date());
        return objectMapper.convert(patientRepository.save(patient).getMedicalInformation(), MedicalInfoDTO.class);

    }

    private MedicalInfoDTO updateForPatient(Patient patient, MedicalInfoDTO medicalInfoDTO) throws NoSuchElementExistsException {
        if (patient.getMedicalInformation() == null) {
            throw new NoSuchElementExistsException("Patient doesn't have existing medical information");
        }

        medicalInfoDTO.setId(patient.getMedicalInformation().getId());
        MedicalInformation medicalInformation = objectMapper.convert(medicalInfoDTO, MedicalInformation.class);
        medicalInformation.setLastMeasurement(new Date());
        return objectMapper.convert(medicalInfoRepository.save(medicalInformation), MedicalInfoDTO.class);
    }


}
