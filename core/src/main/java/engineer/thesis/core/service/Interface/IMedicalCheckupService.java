package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.MedicalCheckupDTO;
import org.springframework.stereotype.Service;

@Service
public interface IMedicalCheckupService {

    MedicalCheckupDTO saveMedicalCheckup(MedicalCheckupDTO medicalCheckupDTO) throws NoSuchElementExistsException, DataIntegrityException;

    MedicalCheckupDTO updateMedicalCheckup(Long checkupId, MedicalCheckupDTO medicalCheckupDTO) throws NoSuchElementExistsException, DataIntegrityException;

    void delete(Long id) throws NoSuchElementExistsException;

}
