package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.PatientDTO;
import engineer.thesis.core.model.dto.PersonalDetailsDTO;
import engineer.thesis.core.model.dto.ShortPatientDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPatientService {

    PatientDTO findById(Long id) throws NoSuchElementExistsException;

    PatientDTO findByPesel(String pesel) throws NoSuchElementExistsException;

    PatientDTO findByEmail(String email) throws NoSuchElementExistsException;

    List<ShortPatientDTO> getAllPatientsShort();

    List<PatientDTO> findPatientsByLastName(String lastName);

    PatientDTO savePatient(PatientDTO personalDetailDTO) throws AlreadyExistsException;

    PersonalDetailsDTO findEmergencyById(Long id) throws NoSuchElementExistsException;

    PersonalDetailsDTO saveEmergency(Long id, PersonalDetailsDTO emergencyContact) throws AlreadyExistsException, NoSuchElementExistsException;

    PersonalDetailsDTO updateEmergency(Long id, PersonalDetailsDTO emergencyContact) throws NoSuchElementExistsException;

}
