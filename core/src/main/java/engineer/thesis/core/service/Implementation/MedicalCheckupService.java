package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.MedicalCheckupDTO;
import engineer.thesis.core.model.entity.Form;
import engineer.thesis.core.model.entity.MedicalCheckup;
import engineer.thesis.core.repository.FormRepository;
import engineer.thesis.core.repository.MedicalCheckupRepository;
import engineer.thesis.core.service.Interface.IMedicalCheckupService;
import engineer.thesis.core.utils.CustomObjectMapper;
import engineer.thesis.core.validator.FormDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MedicalCheckupService implements IMedicalCheckupService {

    @Autowired
    private MedicalCheckupRepository medicalCheckupRepository;

    @Autowired
    private CustomObjectMapper objectMapper;

    @Autowired
    private FormDataValidator formDataValidator;

    @Autowired
    private FormRepository formRepository;

    @Override
    public MedicalCheckupDTO saveMedicalCheckup(MedicalCheckupDTO medicalCheckupDTO) throws NoSuchElementExistsException, DataIntegrityException {

        Form form = findForm(medicalCheckupDTO.getFormId());

        medicalCheckupDTO.setId(null);
        MedicalCheckup medicalCheckup = objectMapper.convert(medicalCheckupDTO, MedicalCheckup.class);

        if (!formDataValidator.isDataValid(medicalCheckup.getMedicalCheckupValues(), form)) {
            throw new DataIntegrityException(formDataValidator.getErrorMessage());
        }
        //set creator
        medicalCheckup.setCreatedDate(new Date());
        medicalCheckup.setLastModifiedDate(new Date());
        medicalCheckup.getMedicalCheckupValues().forEach(val -> {
            val.setMedicalCheckup(medicalCheckup);
            val.setId(null);
        });

        medicalCheckup.setForm(form);

        return objectMapper.convert(medicalCheckupRepository.save(medicalCheckup), MedicalCheckupDTO.class);
    }

    @Override
    public MedicalCheckupDTO updateMedicalCheckup(Long checkupId, MedicalCheckupDTO medicalCheckupDTO) throws NoSuchElementExistsException, DataIntegrityException {
        MedicalCheckup medicalCheckup = medicalCheckupRepository.findOne(checkupId);
        if (medicalCheckup == null) {
            throw new NoSuchElementExistsException("Medical Checkup doesn't exist");
        }
        medicalCheckup.setId(checkupId);

        Form form = findForm(medicalCheckupDTO.getFormId());

        MedicalCheckup updatedMedicalCheckup = objectMapper.convert(medicalCheckupDTO, MedicalCheckup.class);

        if (!formDataValidator.isDataValid(updatedMedicalCheckup.getMedicalCheckupValues(), form)) {
            throw new DataIntegrityException(formDataValidator.getErrorMessage());
        }

        medicalCheckup.getMedicalCheckupValues().clear();
        medicalCheckup.getMedicalCheckupValues().addAll(updatedMedicalCheckup.getMedicalCheckupValues());

        updatedMedicalCheckup.setCreatedDate(medicalCheckup.getCreatedDate());
        updatedMedicalCheckup.setId(medicalCheckup.getId());
        updatedMedicalCheckup.setLastModifiedDate(new Date());
        updatedMedicalCheckup.getMedicalCheckupValues().forEach(val -> {
            val.setMedicalCheckup(medicalCheckup);
            val.setId(null);
        });

        return objectMapper.convert(medicalCheckupRepository.save(updatedMedicalCheckup), MedicalCheckupDTO.class);
    }

    @Override
    public void delete(Long id) throws NoSuchElementExistsException {
        MedicalCheckup medicalCheckup = medicalCheckupRepository.findOne(id);
        if (medicalCheckup == null) {
            throw new NoSuchElementExistsException("Checkup doesn't exists");
        }
        medicalCheckup.getMedicalHistories().stream().forEach(medicalHistory -> medicalHistory.setMedicalCheckup(null));

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
