package engineer.thesis.service;

import engineer.thesis.model.Patient;
import engineer.thesis.model.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public interface IPatientService extends IBasicService<Patient,PatientDTO>{


    List<PatientDTO> getAllPatients();

    PatientDTO findByPesel(String pesel);

    PatientDTO findById(Long id);

    List<PatientDTO> findPatientsByLastName(String lastName);

    PersonalDetailDTO saveNewPatient(PersonalDetailDTO personalDetailDTO, String email) throws NoSuchElementException;

    PatientDTO changePatientDetails(PatientDTO patientDTO) throws NoSuchElementException;

//    List<CurrentStateDTO> getPatientCurrentCondition(Long id);
//
//    PatientMedicalInformationDTO getPatientMedicalInformation(Long id);
//
//    List<MedicalHistoryDTO> getPatientMedicalHistory(Long id);
}
