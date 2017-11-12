package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.entity.Patient;
import engineer.thesis.core.repository.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public interface BasePatientService {

    default Patient findPatient(Long id, PatientRepository patientRepository) throws NoSuchElementExistsException {
        Patient patient = patientRepository.findOne(id);
        if (patient == null) {
            throw new NoSuchElementExistsException("Patient doesn't exist");
        }
        return patient;
    }

    default void checkPatientExistence(Long id, PatientRepository patientRepository) throws NoSuchElementExistsException {
        if (!patientRepository.exists(id)) {
            throw new NoSuchElementExistsException("Patient doesn't not exists");
        }
    }


}
