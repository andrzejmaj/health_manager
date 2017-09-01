package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.MedicalCheckupDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IMedicalCheckupService {

    List<MedicalCheckupDTO> getPatientCheckups(Long id) throws NoSuchElementExistsException;

    MedicalCheckupDTO saveMedicalCheckup(Long patientId, MedicalCheckupDTO medicalCheckupDTO) throws NoSuchElementExistsException, DataIntegrityException;

    MedicalCheckupDTO updateMedicalCheckup(Long patientId, MedicalCheckupDTO medicalCheckupDTO) throws NoSuchElementExistsException, DataIntegrityException;

    void delete(Long id) throws NoSuchElementExistsException;
}
