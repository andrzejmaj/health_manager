package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.MedicalInfoDTO;
import org.springframework.stereotype.Service;

@Service
public interface IMedicalInfoService {

    MedicalInfoDTO findByPatientId(Long patientId) throws NoSuchElementExistsException;

    MedicalInfoDTO save(Long id, MedicalInfoDTO medicalInfoDTO) throws NoSuchElementExistsException, AlreadyExistsException, DataIntegrityException;

    MedicalInfoDTO update(Long patientId, Long id, MedicalInfoDTO medicalInfoDTO) throws NoSuchElementExistsException, DataIntegrityException;

    void delete(Long id) throws NoSuchElementExistsException;
}
