package engineer.thesis.core.service;

import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.MedicalCheckup;
import engineer.thesis.core.model.Patient;
import engineer.thesis.core.model.dto.MedicalCheckupDTO;
import engineer.thesis.core.repository.MedicalCheckupRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class MedicalCheckupService implements IMedicalCheckupService {

    @Autowired
    private MedicalCheckupRepository medicalCheckupRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private CustomObjectMapper objectMapper;

    @Override
    public List<MedicalCheckupDTO> getPatientCheckups(Long id) throws NoSuchElementException {
        return medicalCheckupRepository.findAllByPatientId_Id(id).stream().map(medicalCheckup -> objectMapper.convert(medicalCheckup, MedicalCheckupDTO.class)).collect(Collectors.toList());
    }

    @Override
    public MedicalCheckupDTO saveMedicalCheckup(Long id, MedicalCheckupDTO medicalCheckupDTO) throws NoSuchElementExistsException {
        Patient patient = patientRepository.findOne(id);
        if (patient == null) {
            throw new NoSuchElementExistsException("Patient doesn't exist");
        }
        medicalCheckupDTO.setPatientId(patient.getId());

        return objectMapper.convert(medicalCheckupRepository.save(
                objectMapper.convert(medicalCheckupDTO, MedicalCheckup.class)),
                MedicalCheckupDTO.class);
    }

    @Override
    public MedicalCheckupDTO updateMedicalCheckup(Long id, MedicalCheckupDTO medicalCheckupDTO) throws NoSuchElementExistsException {
        Patient patient = patientRepository.findOne(id);
        if (patient == null) {
            throw new NoSuchElementExistsException("Patient doesn't exist");
        }
        MedicalCheckup medicalCheckup = medicalCheckupRepository.findOne(medicalCheckupDTO.getId());
        if (medicalCheckup == null) {
            throw new NoSuchElementExistsException("Medical checkup doesn't exist");
        }
        medicalCheckupDTO.setId(medicalCheckup.getId());
        medicalCheckupDTO.setPatientId(patient.getId());
        return objectMapper.convert(medicalCheckupRepository.save(objectMapper.convert(medicalCheckupDTO, MedicalCheckup.class)), MedicalCheckupDTO.class);

    }


}
