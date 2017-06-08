package engineer.thesis.service;

import engineer.thesis.model.Patient;
import engineer.thesis.model.dto.PatientDTO;
import engineer.thesis.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService implements IPatientService {

    @Autowired
    PatientRepository patientRepository;

    @Override
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream().map(PatientDTO::new).collect(Collectors.toList());
    }

    @Override
    public PatientDTO findById(Long id) throws NoSuchElementException {
        Optional<Patient> patient = Optional.ofNullable(patientRepository.findOne(id));
        return new PatientDTO(patient.orElseThrow(NoSuchElementException::new));
    }

    @Override
    public PatientDTO findByPesel(String pesel) throws NoSuchElementException {
        Optional<Patient> patient = Optional.ofNullable(patientRepository.findByPersonalDetails_Pesel(pesel));
        return new PatientDTO(patient.orElseThrow(NoSuchElementException::new));
    }

    @Override
    public List<PatientDTO> findPatientsByLastName(String lastName) {
        return patientRepository.findByPersonalDetails_LastNameLike(lastName).stream().map(PatientDTO::new).collect(Collectors.toList());
    }
}
