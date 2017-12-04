package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.PrescriptionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPrescriptionService {
    PrescriptionDTO get(Long id) throws NoSuchElementExistsException;

    List<PrescriptionDTO> getByPatientId(Long patientId) throws NoSuchElementExistsException;

    List<PrescriptionDTO> getMine();

    PrescriptionDTO save(PrescriptionDTO prescriptionDTO) throws NoSuchElementExistsException;

    PrescriptionDTO update(Long id, PrescriptionDTO prescriptionDTO) throws NoSuchElementExistsException;

    String delete(Long id) throws NoSuchElementExistsException;
}
