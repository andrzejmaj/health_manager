package engineer.thesis.core.service;

import engineer.thesis.core.model.dto.MedicalHistoryDTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public interface IMedicalHistoryService {

    MedicalHistoryDTO save(Long id, MedicalHistoryDTO medicalHistoryDTO) throws NoSuchElementException;

    MedicalHistoryDTO update(MedicalHistoryDTO medicalHistoryDTO) throws NoSuchElementException;

    List<MedicalHistoryDTO> getAllByPatientIdFromPeriod(Long id, Date start, Date end) throws NoSuchElementException;
}
