package engineer.thesis.service;


import engineer.thesis.model.Patient;

import java.util.List;

public interface IPatientService {

    List<Patient> getAllPatients();

    Patient findByPesel(String pesel);

    Patient findById(Long id);
}
