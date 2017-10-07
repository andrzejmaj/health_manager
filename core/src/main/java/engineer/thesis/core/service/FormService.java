package engineer.thesis.core.service;

import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.Form;
import engineer.thesis.core.model.dto.FormDTO;
import engineer.thesis.core.repository.FormRepository;
import engineer.thesis.core.utils.CustomObjectMapper;
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

    @Override
    public String deleteForm(Long id) {
        if (!doesFormExist(id)) {
            throw new NoSuchElementException("Form does not exist");
        }
        formRepository.delete(id);
        return "Form " + id + " deleted successfully";
    }

    @Override
    public FormDTO getFormById(Long id) {
        Optional<Form> form = Optional.ofNullable(formRepository.findOne(id));
        if (!form.isPresent()) {
            throw new NoSuchElementException("Form does not exist");
        }
        return objectMapper.convert(form.get(), FormDTO.class);
    }

    @Override
    public List<FormDTO> getAllForms() {
        return formRepository.findAll().stream().map(form -> objectMapper.convert(form, FormDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<FormDTO> getFormsByName(String name) {
        return formRepository.findByName(name).stream().map(form -> objectMapper.convert(form, FormDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<FormDTO> getFormsByOwnerId(Long id) {
        return formRepository.findByOwner_Id(id).stream().map(form -> objectMapper.convert(form, FormDTO.class)).collect(Collectors.toList());
    }

    @Override
    public FormDTO saveForm(FormDTO formDTO) {
        formDTO.getFormFields().forEach(field -> field.setFieldType(field.getFieldType().toUpperCase()));
        Form form = objectMapper.convert(formDTO, Form.class);
        form.getFormFields().forEach(field -> {
            if (field.getFieldAvailableValues() != null) {
                field.getFieldAvailableValues().forEach(option -> option.setFormField(field));
            }
                    field.setForm(form);
                }
        );
        return objectMapper.convert(formRepository.save(form), FormDTO.class);
    }

    @Override
    public FormDTO updateForm(Long id, FormDTO formDTO) throws DataIntegrityException, NoSuchElementExistsException {
        Form form = formRepository.findOne(id);
        if (form == null) {
            throw new NoSuchElementExistsException("Form doesn't exist");
        }

        return null;
    }

    private Boolean doesFormExist(Long id) {
        return Optional.ofNullable(formRepository.findOne(id)).isPresent();
    }
}
