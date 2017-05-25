package engineer.thesis.service;


import engineer.thesis.model.dto.PatientDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPatientService {

    List<PatientDTO> getAllPatients();

    PatientDTO findByPesel(String pesel);

    PatientDTO findById(Long id);
}
