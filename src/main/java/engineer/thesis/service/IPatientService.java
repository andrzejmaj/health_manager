package engineer.thesis.service;


import engineer.thesis.model.dto.PatientDTO;

import java.util.List;

public interface IPatientService {

    List<PatientDTO> getAllPatients();

    PatientDTO findByPesel(String pesel);

    PatientDTO findById(Long id);
}
