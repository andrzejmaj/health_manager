package engineer.thesis.service;

import engineer.thesis.exception.AlreadyExistsException;
import engineer.thesis.model.Patient;
import engineer.thesis.model.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPatientService extends IBasicService<Patient,PatientDTO>{


    List<PatientDTO> getAllPatients();

    PatientDTO findByPesel(String pesel);

    PatientDTO findById(Long id);

    List<PatientDTO> findPatientsByLastName(String lastName);

    PatientDTO savePatient(PatientDTO personalDetailDTO) throws AlreadyExistsException;

    PersonalDetailDTO findByIdEmergency(Long id);

//    List<CurrentStateDTO> getPatientCurrentCondition(Long id);
//
//    PatientMedicalInformationDTO getPatientMedicalInformation(Long id);
//
//    List<MedicalHistoryDTO> getPatientMedicalHistory(Long id);
}
