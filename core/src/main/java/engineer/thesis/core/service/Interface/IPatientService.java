package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.model.dto.PatientDTO;
import engineer.thesis.core.model.dto.PersonalDetailsDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public interface IPatientService {


    List<PatientDTO> getAllPatients();

    PatientDTO findByPesel(String pesel);

    PatientDTO findByEmail(String email);

    PatientDTO findById(Long id);

    List<PatientDTO> findPatientsByLastName(String lastName);

    PatientDTO savePatient(PatientDTO personalDetailDTO) throws AlreadyExistsException;

    PatientDTO updatePatient(PatientDTO patientDTO) throws NoSuchElementException;

    PersonalDetailsDTO findByIdEmergency(Long id);

    PersonalDetailsDTO saveEmergencyContact(Long id, PersonalDetailsDTO emergencyContact) throws AlreadyExistsException;

    PersonalDetailsDTO updateEmergencyContact(Long id, PersonalDetailsDTO emergencyContact);

}
