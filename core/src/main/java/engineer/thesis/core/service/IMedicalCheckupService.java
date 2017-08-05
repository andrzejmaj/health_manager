package engineer.thesis.core.service;

import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.MedicalCheckupDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public interface IMedicalCheckupService {
    List<MedicalCheckupDTO> getPatientCheckups(Long id) throws NoSuchElementException;

    MedicalCheckupDTO saveMedicalCheckup(Long id, MedicalCheckupDTO medicalCheckupDTO) throws NoSuchElementExistsException;

    MedicalCheckupDTO updateMedicalCheckup(Long id, MedicalCheckupDTO medicalCheckupDTO) throws NoSuchElementExistsException;
}
