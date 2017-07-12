package engineer.thesis.core.service;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.model.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public interface IPatientService {


    List<PatientDTO> getAllPatients();

    PatientDTO findByPesel(String pesel);

    PatientDTO findById(Long id);

    List<PatientDTO> findPatientsByLastName(String lastName);

    PatientDTO savePatient(PatientDTO personalDetailDTO) throws AlreadyExistsException;

    PatientDTO updatePatient(PatientDTO patientDTO) throws NoSuchElementException;

    PersonalDetailsDTO findByIdEmergency(Long id);

    PersonalDetailsDTO saveEmergencyContact(Long id, PersonalDetailsDTO emergencyContact);

//    List<CurrentStateDTO> getPatientCurrentCondition(Long id);
//
//    PatientMedicalInformationDTO getPatientMedicalInformation(Long id);
//
//    List<MedicalHistoryDTO> getPatientMedicalHistory(Long id);
}
