package engineer.thesis.core.service;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.model.dto.MedicalInfoDTO;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public interface IMedicalInfoService {

    MedicalInfoDTO findById(Long Id) throws NoSuchElementException;

    MedicalInfoDTO save(MedicalInfoDTO medicalInfoDTO) throws AlreadyExistsException;

    MedicalInfoDTO update(MedicalInfoDTO medicalInfoDTO) throws NoSuchElementException;

}
