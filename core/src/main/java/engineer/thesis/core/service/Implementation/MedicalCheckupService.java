package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.MedicalCheckup;
import engineer.thesis.core.model.Patient;
import engineer.thesis.core.model.dto.MedicalCheckupDTO;
import engineer.thesis.core.repository.MedicalCheckupRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.service.Interface.IMedicalCheckupService;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    public List<MedicalCheckupDTO> getPatientCheckups(Long patientId) throws NoSuchElementExistsException {
        if (!patientRepository.exists(patientId)) {
            throw new NoSuchElementExistsException("Patient doesn't exists");
        }
        return medicalCheckupRepository.findAllByPatientIdOrderByLastModifiedDateDesc(patientId).stream().map(medicalCheckup -> objectMapper.convert(medicalCheckup, MedicalCheckupDTO.class)).collect(Collectors.toList());
    }

    @Override
    public MedicalCheckupDTO saveMedicalCheckup(Long patientId, MedicalCheckupDTO medicalCheckupDTO) throws NoSuchElementExistsException {

        Optional<Patient> patient = Optional.ofNullable(patientRepository.findOne(patientId));
        if (!patient.isPresent()) {
            throw new NoSuchElementExistsException("Patient doesn't exist");
        }

        MedicalCheckup medicalCheckup = objectMapper.convert(medicalCheckupDTO, MedicalCheckup.class);

        if (medicalCheckup.getId() != null) {
            medicalCheckup.setPatient(patient.get());
            medicalCheckup.setCreatedDate(new Date());
        }

        medicalCheckup.setLastModifiedDate(new Date());

        return objectMapper.convert(medicalCheckupRepository.save(medicalCheckup), MedicalCheckupDTO.class);
    }


    @Override
    public void delete(Long id) throws NoSuchElementExistsException {
        if (!medicalCheckupRepository.exists(id)) {
            throw new NoSuchElementExistsException("Checkup doesn't exists");
        }
        medicalCheckupRepository.delete(id);
    }
}
