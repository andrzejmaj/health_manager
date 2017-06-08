package engineer.thesis.service;

import engineer.thesis.model.dto.CurrentStateDTO;
import engineer.thesis.model.dto.PatientDTO;
import engineer.thesis.model.dto.PatientMedicalInformationDTO;
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
}
