package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.MedicalHistoryDTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface IMedicalHistoryService {

    MedicalHistoryDTO save(Long patientId, MedicalHistoryDTO medicalHistoryDTO) throws DataIntegrityException, NoSuchElementExistsException;

    MedicalHistoryDTO update(Long patientId, MedicalHistoryDTO medicalHistoryDTO) throws DataIntegrityException, NoSuchElementExistsException;

    List<MedicalHistoryDTO> getAllByPatientIdFromPeriod(Long id, Date start, Date end) throws NoSuchElementExistsException;

    void delete(Long id) throws NoSuchElementExistsException;
}
