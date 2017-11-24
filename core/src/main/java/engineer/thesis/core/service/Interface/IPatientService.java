package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPatientService {

    PatientDTO findById(Long id) throws NoSuchElementExistsException;

    PatientDTO findByPesel(String pesel) throws NoSuchElementExistsException;

    PatientDTO findByEmail(String email) throws NoSuchElementExistsException;

    List<ShortPatientDTO> findAllPatientsShort();

    Page<ShortPatientDTO> findAllPatientsShort(Pageable pageable);

    List<PatientDTO> findPatientsByLastName(String lastName);

    EmergencyContactDTO findEmergencyById(Long id) throws NoSuchElementExistsException;

    EmergencyContactDTO saveEmergency(Long id, EmergencyContactDTO emergencyContact) throws AlreadyExistsException, NoSuchElementExistsException;

    EmergencyContactDTO updateEmergency(Long id, EmergencyContactDTO emergencyContact) throws NoSuchElementExistsException;

    PatientDTO register(PatientDetailsDTO patientDetails) throws AlreadyExistsException;

    PersonalDetailsDTO getPersonalDetails(Long id) throws NoSuchElementExistsException;

    PersonalDetailsDTO savePersonalDetails(Long id, PersonalDetailsDTO personalDetailsDTO) throws NoSuchElementExistsException, AlreadyExistsException;

    PersonalDetailsDTO updatePersonalDetails(Long id, PersonalDetailsDTO personalDetailsDTO) throws NoSuchElementExistsException;

}
