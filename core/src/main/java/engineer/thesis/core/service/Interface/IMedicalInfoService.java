package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.AccessDeniedException;
import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.MedicalInfoDTO;
import org.springframework.stereotype.Service;

@Service
public interface IMedicalInfoService {

    MedicalInfoDTO findByPatientId(Long patientId) throws NoSuchElementExistsException, AccessDeniedException;

    MedicalInfoDTO save(Long id, MedicalInfoDTO medicalInfoDTO) throws NoSuchElementExistsException, AlreadyExistsException;

    MedicalInfoDTO update(Long patientId, MedicalInfoDTO medicalInfoDTO) throws NoSuchElementExistsException;

    void delete(Long id) throws NoSuchElementExistsException;
}
