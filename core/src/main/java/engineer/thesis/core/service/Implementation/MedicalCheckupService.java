package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.Form;
import engineer.thesis.core.model.MedicalCheckup;
import engineer.thesis.core.model.Patient;
import engineer.thesis.core.model.dto.MedicalCheckupDTO;
import engineer.thesis.core.repository.FormRepository;
import engineer.thesis.core.repository.MedicalCheckupRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.service.FormService;
import engineer.thesis.core.service.Interface.BasePatientService;
import engineer.thesis.core.service.Interface.IMedicalCheckupService;
import engineer.thesis.core.utils.CustomObjectMapper;
import engineer.thesis.core.validator.FormDataValidator;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalCheckupService implements BasePatientService, IMedicalCheckupService {

    @Autowired
    private MedicalCheckupRepository medicalCheckupRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private CustomObjectMapper objectMapper;

    @Autowired
    private FormDataValidator formDataValidator;

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private FormService formService;

    @Override
    public List<MedicalCheckupDTO> getPatientCheckups(Long patientId) throws NoSuchElementExistsException {
        if (!patientRepository.exists(patientId)) {
            throw new NoSuchElementExistsException("Patient doesn't exists");
        }
        return medicalCheckupRepository.findAllByPatientIdOrderByLastModifiedDateDesc(patientId).stream()
                .map(medicalCheckup -> objectMapper.convert(medicalCheckup, MedicalCheckupDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MedicalCheckupDTO saveMedicalCheckup(Long patientId, MedicalCheckupDTO medicalCheckupDTO) throws NoSuchElementExistsException {

        Patient patient = findPatient(patientId, patientRepository);
        Form form = findForm(medicalCheckupDTO.getFormId());

        medicalCheckupDTO.setId(null);
        MedicalCheckup medicalCheckup = objectMapper.convert(medicalCheckupDTO, MedicalCheckup.class);

        if (!formDataValidator.isDataValid(medicalCheckup.getMedicalCheckupValues(), form)) {
            throw new ValidationException(formDataValidator.getErrorMessage());
        }
        //set creator
        medicalCheckup.setCreatedDate(new Date());
        medicalCheckup.setLastModifiedDate(new Date());
        medicalCheckup.setPatient(patient);
        medicalCheckup.getMedicalCheckupValues().forEach(val -> {
            val.setMedicalCheckup(medicalCheckup);
            val.setId(null);
        });

        medicalCheckup.setForm(form);

        return objectMapper.convert(medicalCheckupRepository.save(medicalCheckup), MedicalCheckupDTO.class);
    }

    @Override
    public MedicalCheckupDTO updateMedicalCheckup(Long patientId, MedicalCheckupDTO medicalCheckupDTO) throws NoSuchElementExistsException, DataIntegrityException {
        MedicalCheckup medicalCheckup = medicalCheckupRepository.findOne(medicalCheckupDTO.getId());
        if (medicalCheckup == null) {
            throw new NoSuchElementExistsException("Medical Checkup doesn't exist");
        }
        if (!medicalCheckup.getPatient().getId().equals(patientId)) {
            throw new DataIntegrityException("Medical Checkup doesn't belong to patient");
        }

        Form form = findForm(medicalCheckupDTO.getFormId());

        MedicalCheckup updatedMedicalCheckup = objectMapper.convert(medicalCheckupDTO, MedicalCheckup.class);

        if (!formDataValidator.isDataValid(updatedMedicalCheckup.getMedicalCheckupValues(), form)) {
            throw new DataIntegrityException(formDataValidator.getErrorMessage());
        }

        medicalCheckup.getMedicalCheckupValues().clear();
        medicalCheckup.getMedicalCheckupValues().addAll(updatedMedicalCheckup.getMedicalCheckupValues());
        medicalCheckup.setForm(form);

        medicalCheckup.setLastModifiedDate(new Date());
        medicalCheckup.getMedicalCheckupValues().forEach(val -> val.setMedicalCheckup(medicalCheckup));

        return objectMapper.convert(medicalCheckupRepository.save(medicalCheckup), MedicalCheckupDTO.class);
    }

    @Override
    public void delete(Long id) throws NoSuchElementExistsException {
        if (!medicalCheckupRepository.exists(id)) {
            throw new NoSuchElementExistsException("Checkup doesn't exists");
        }
        medicalCheckupRepository.delete(id);
    }

    private Form findForm(Long id) throws NoSuchElementExistsException {
        Form form = formRepository.findOne(id);

        if (form == null) {
            throw new NoSuchElementExistsException("Form does not exist");
        }
        return form;
    }
}
