package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.RequestMedicalHistoryDTO;
import engineer.thesis.core.model.dto.ResponseMedicalHistoryDTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface IMedicalHistoryService {

    List<ResponseMedicalHistoryDTO> getAllByPatientIdFromPeriod(Long id, Date start, Date end) throws NoSuchElementExistsException, DataIntegrityException;

    RequestMedicalHistoryDTO save(Long patientId, RequestMedicalHistoryDTO requestMedicalHistoryDTO) throws NoSuchElementExistsException, DataIntegrityException;

    RequestMedicalHistoryDTO update(RequestMedicalHistoryDTO requestMedicalHistoryDTO, Long id) throws NoSuchElementExistsException, DataIntegrityException;

    void delete(Long id) throws NoSuchElementExistsException;
}
