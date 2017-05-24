package engineer.thesis.service;

import engineer.thesis.model.Patient;
import engineer.thesis.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PatientService implements IPatientService {

    @Autowired
    PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return null;
    }

    @Override
    public Patient findByPesel(String pesel) {
        return null;
    }

    @Override
    public Patient findById(Long id) {
        return null;
    }
}
