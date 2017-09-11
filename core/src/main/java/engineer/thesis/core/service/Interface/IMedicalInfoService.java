package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.AccessDeniedException;
import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.MedicalInfoDTO;
import org.springframework.stereotype.Service;

@Service
public interface IMedicalInfoService {

    MedicalInfoDTO findByPatientId(Long patientId) throws NoSuchElementExistsException, AccessDeniedException;

    MedicalInfoDTO saveOrUpdate(Long id, MedicalInfoDTO medicalInfoDTO) throws NoSuchElementExistsException, AlreadyExistsException, AccessDeniedException;

    void delete(Long id) throws NoSuchElementExistsException;
}
