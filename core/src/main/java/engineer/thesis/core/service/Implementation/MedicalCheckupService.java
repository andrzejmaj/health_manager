package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.Form;
import engineer.thesis.core.model.MedicalCheckup;
import engineer.thesis.core.model.Patient;
import engineer.thesis.core.model.dto.MedicalCheckupDTO;
import engineer.thesis.core.repository.FormRepository;
import engineer.thesis.core.repository.MedicalCheckupRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.service.FormService;
import engineer.thesis.core.service.Interface.IMedicalCheckupService;
import engineer.thesis.core.utils.CustomObjectMapper;
import engineer.thesis.core.validator.MedicalCheckupValidator;
import org.springframework.batch.item.validator.ValidationException;
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

    @Autowired
    private MedicalCheckupValidator medicalCheckupValidator;

    @Autowired
    private FormRepository formRepository;

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
        Optional<Form> form = Optional.of(formRepository.getOne(medicalCheckupDTO.getFormId()));

        if (!patient.isPresent()) {
            throw new NoSuchElementExistsException("Patient does not exist");
        }

        if (!form.isPresent()) {
            throw new NoSuchElementExistsException("Form does not exist");
        }

        MedicalCheckup medicalCheckup = objectMapper.convert(medicalCheckupDTO, MedicalCheckup.class);
        if (!medicalCheckupValidator.isMedicalCheckupValid(medicalCheckup, form.get())) {
            throw new ValidationException(medicalCheckupValidator.getErrorMessage());
        }

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
