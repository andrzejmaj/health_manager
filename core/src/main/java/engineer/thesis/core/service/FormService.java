package engineer.thesis.core.service;

import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.DefaultValuesSet;
import engineer.thesis.core.model.Doctor;
import engineer.thesis.core.model.Form;
import engineer.thesis.core.model.FormFieldDefaultValue;
import engineer.thesis.core.model.dto.DefaultValuesSetDTO;
import engineer.thesis.core.model.dto.FormDTO;
import engineer.thesis.core.repository.DefaultValuesSetRepository;
import engineer.thesis.core.repository.DoctorRepository;
import engineer.thesis.core.repository.FormRepository;
import engineer.thesis.core.utils.CustomObjectMapper;
import engineer.thesis.core.validator.FormDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FormService implements IFormService {

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private CustomObjectMapper objectMapper;

    @Autowired
    private DefaultValuesSetRepository defaultValuesSetRepository;

    @Autowired
    private FormDataValidator formDataValidator;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public FormDTO getFormById(Long id) {
        Optional<Form> form = Optional.ofNullable(formRepository.findOne(id));
        if (!form.isPresent()) {
            throw new NoSuchElementException("Form doesn't exist");
        }
        return objectMapper.convert(form.get(), FormDTO.class);
    }

    @Override
    public List<FormDTO> getAllForms() {
        return formRepository.findAll().stream().map(form -> objectMapper.convert(form, FormDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<FormDTO> getFormsByName(String name) {
        return formRepository.findByNameAndActiveIsTrue(name).stream().map(form -> objectMapper.convert(form, FormDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<FormDTO> getFormsByOwnerId(Long id) {
        return formRepository.findByOwner_IdAndActiveIsTrue(id).stream().map(form -> objectMapper.convert(form, FormDTO.class)).collect(Collectors.toList());
    }

    @Override
    public FormDTO saveForm(FormDTO formDTO) throws NoSuchElementExistsException {
        Doctor doctor = doctorRepository.findOne(formDTO.getOwnerId());
        if (doctor == null) {
            throw new NoSuchElementExistsException("Doctor doesn't exist");
        }

        Form form = convertFromDTO(formDTO);
        form.setOwner(doctor);
        return objectMapper.convert(formRepository.save(form), FormDTO.class);
    }

    @Override
    public FormDTO updateForm(Long id, FormDTO formDTO) throws DataIntegrityException, NoSuchElementExistsException {
        Form form = formRepository.findByIdAndActiveIsTrue(id);
        if (form == null) {
            throw new NoSuchElementExistsException("Form doesn't exist");
        }
        form.setActive(false);
        formRepository.save(form);

        formDTO.setId(null);
        Form updatedForm = convertFromDTO(formDTO);
        updatedForm.setOwner(form.getOwner());

        return objectMapper.convert(formRepository.save(updatedForm), FormDTO.class);
    }

    @Override
    public String deleteForm(Long id) {
        if (!doesFormExist(id)) {
            throw new NoSuchElementException("Form doesn't exist");
        }
        formRepository.delete(id);
        return "Form " + id + " deleted successfully";
    }

    @Override
    public List<DefaultValuesSetDTO> getDefaultValues(Long id) throws NoSuchElementExistsException {
        if (!formRepository.exists(id)) {
            throw new NoSuchElementExistsException("Form doesn't exist");
        }
        return defaultValuesSetRepository.findAllByForm_Id(id).stream().map(set -> objectMapper.convert(set, DefaultValuesSetDTO.class)).collect(Collectors.toList());
    }

    @Override
    public DefaultValuesSetDTO saveDefaultValues(Long id, DefaultValuesSetDTO defaultValuesSetDTO) throws NoSuchElementExistsException, DataIntegrityException {
        Form form = formRepository.findOne(id);
        if (form == null) {
            throw new NoSuchElementExistsException("Form doesn't exist");
        }
        defaultValuesSetDTO.setId(null);
        defaultValuesSetDTO.setFormId(id);
        DefaultValuesSet defaultValuesSet = objectMapper.convert(defaultValuesSetDTO, DefaultValuesSet.class);

        if (!formDataValidator.isDataValid(defaultValuesSet.getDefaultValues(), form)) {
            throw new DataIntegrityException(formDataValidator.getErrorMessage());
        }

        defaultValuesSet.getDefaultValues().forEach(val -> val.setDefaultValuesSet(defaultValuesSet));

        return objectMapper.convert(defaultValuesSetRepository.save(defaultValuesSet), DefaultValuesSetDTO.class);
    }

    @Override
    public DefaultValuesSetDTO updateDefaultValues(Long id, DefaultValuesSetDTO defaultValuesSetDTO) throws NoSuchElementExistsException, DataIntegrityException {

        DefaultValuesSet defaultValuesSet = defaultValuesSetRepository.findOne(id);
        if (defaultValuesSet == null) {
            throw new NoSuchElementExistsException("Values set doesn't exist");
        }

        if (!defaultValuesSet.getForm().getId().equals(id)) {
            throw new DataIntegrityException("Form id in set and path doesn't match");
        }

        List<FormFieldDefaultValue> formFieldDefaultValues = defaultValuesSetDTO.getDefaultValues().stream()
                .map(dto -> objectMapper.convert(dto, FormFieldDefaultValue.class).builderSetDefaultValuesSet(defaultValuesSet))
                .collect(Collectors.toList());

        if (!formDataValidator.isDataValid(formFieldDefaultValues, defaultValuesSet.getForm())) {
            throw new DataIntegrityException(formDataValidator.getErrorMessage());
        }

        defaultValuesSet.getDefaultValues().clear();
        defaultValuesSet.getDefaultValues().addAll(formFieldDefaultValues);

        return objectMapper.convert(defaultValuesSetRepository.save(defaultValuesSet), DefaultValuesSetDTO.class);
    }

    @Override
    public String deleteDefaultSet(Long id) throws NoSuchElementExistsException {
        DefaultValuesSet defaultValuesSet = defaultValuesSetRepository.findOne(id);
        if (defaultValuesSet == null) {
            throw new NoSuchElementExistsException("Default values set doesn't exist");
        }
        defaultValuesSetRepository.delete(id);
        return "Set " + id + " successfully deleted";
    }

    private Form convertFromDTO(FormDTO formDTO) {
        Form form = objectMapper.convert(formDTO, Form.class);
        form.getFormFields().forEach(field -> {
                    if (field.getFieldAvailableValues() != null) {
                        field.getFieldAvailableValues().forEach(value -> {
                            value.setFormField(field);
                            value.setId(null);
                        });
                    }
                    field.setForm(form);
                    field.setId(null);
                }
        );
        form.setActive(true);

        return form;
    }

    private Boolean doesFormExist(Long id) {
        return Optional.ofNullable(formRepository.findOne(id)).isPresent();
    }
}
