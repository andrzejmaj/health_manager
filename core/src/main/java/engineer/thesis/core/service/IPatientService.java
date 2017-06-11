package engineer.thesis.core.service;

import engineer.thesis.core.model.dto.CurrentStateDTO;
import engineer.thesis.core.model.dto.MedicalHistoryDTO;
import engineer.thesis.core.model.dto.PatientDTO;
import engineer.thesis.core.model.dto.PatientMedicalInformationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPatientService {

    List<PatientDTO> getAllPatients();

    PatientDTO findByPesel(String pesel);

    PatientDTO findById(Long id);

    List<PatientDTO> findPatientsByLastName(String lastName);

    List<CurrentStateDTO> getPatientCurrentCondition(Long id);

    PatientMedicalInformationDTO getPatientMedicalInformation(Long id);

    List<MedicalHistoryDTO> getPatientMedicalHistory(Long id);
}
