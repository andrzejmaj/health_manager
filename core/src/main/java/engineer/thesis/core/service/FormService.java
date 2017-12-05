package engineer.thesis.core.service;

import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.ForbiddenContentException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.DefaultValuesSetDTO;
import engineer.thesis.core.model.dto.FormDTO;
import engineer.thesis.core.model.entity.DefaultValuesSet;
import engineer.thesis.core.model.entity.Form;
import engineer.thesis.core.model.entity.UserRole;
import engineer.thesis.core.repository.DefaultValuesSetRepository;
import engineer.thesis.core.repository.FormRepository;
import engineer.thesis.core.repository.UserRepository;
import engineer.thesis.core.security.model.SecurityUser;
import engineer.thesis.core.service.Interface.BaseService;
import engineer.thesis.core.utils.CustomObjectMapper;
import engineer.thesis.core.validator.FormDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FormService implements IFormService, BaseService {

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private CustomObjectMapper objectMapper;

    @Autowired
    private DefaultValuesSetRepository defaultValuesSetRepository;

    @Autowired
    private FormDataValidator formDataValidator;

    @Autowired
    private UserRepository userRepository;

    @Override
    public FormDTO getFormById(Long id) throws NoSuchElementExistsException {
        Optional<Form> form = Optional.ofNullable(formRepository.findByIdAndActiveIsTrue(id));
        if (!form.isPresent()) {
            throw new NoSuchElementExistsException("Form doesn't exist");
        }
        checkOwnership(form.get());
        return objectMapper.convert(form.get(), FormDTO.class);
    }

    @Override
    public List<FormDTO> getAllForms() {
        return formRepository.findAvailableForms(((SecurityUser) SecurityContextHolder.getContext().getAuthentication().getDetails()).getId()).stream().map(form -> objectMapper.convert(form, FormDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<FormDTO> getFormsByName(String name) {
        return formRepository.findByNameContainingIgnoreCaseAndActiveIsTrue(name, ((SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())
                .stream().map(form -> objectMapper.convert(form, FormDTO.class)).collect(Collectors.toList());
    }

    @Override
    public FormDTO saveForm(FormDTO formDTO) throws NoSuchElementExistsException {
        formDTO.setId(null);
        Form form = convertFromDTO(formDTO);
        return objectMapper.convert(formRepository.save(form), FormDTO.class);
    }

    @Override
    public FormDTO updateForm(Long id, FormDTO formDTO) throws DataIntegrityException, NoSuchElementExistsException {
        Form form = formRepository.findByIdAndActiveIsTrue(id);
        if (form == null) {
            throw new NoSuchElementExistsException("Form doesn't exist");
        }
        checkOwnership(form);
        form.setActive(false);
        formRepository.save(form);

        return saveForm(formDTO);
    }

    @Override
    public String deleteForm(Long id) {
        Form form = formRepository.findOne(id);
        if (form == null) {
            throw new NoSuchElementException("Form doesn't exist");
        }
        checkOwnership(form);
        formRepository.delete(id);
        return "Form " + id + " deleted successfully";
    }

    @Override
    public List<DefaultValuesSetDTO> getDefaultValues(Long id) throws NoSuchElementExistsException {
        Form form = formRepository.findOne(id);
        if (form == null) {
            throw new NoSuchElementExistsException("Form doesn't exist");
        }
        checkOwnership(form);
        return defaultValuesSetRepository.findAllByForm_Id(id).stream().map(set -> objectMapper.convert(set, DefaultValuesSetDTO.class)).collect(Collectors.toList());
    }

    @Override
    public DefaultValuesSetDTO saveDefaultValues(Long id, DefaultValuesSetDTO defaultValuesSetDTO) throws NoSuchElementExistsException, DataIntegrityException {
        Form form = formRepository.findOne(id);
        if (form == null) {
            throw new NoSuchElementExistsException("Form doesn't exist");
        }
        checkOwnership(form);

        defaultValuesSetDTO.setId(null);
        defaultValuesSetDTO.setFormId(id);
        DefaultValuesSet defaultValuesSet = objectMapper.convert(defaultValuesSetDTO, DefaultValuesSet.class);

        if (!formDataValidator.isDataValidDefault(defaultValuesSet.getDefaultValues(), form)) {
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

        checkOwnership(defaultValuesSet.getForm());

        defaultValuesSetDTO.setFormId(defaultValuesSet.getForm().getId());
        DefaultValuesSet newDefaultValuesSet = objectMapper.convert(defaultValuesSetDTO, DefaultValuesSet.class);

        if (!formDataValidator.isDataValidDefault(newDefaultValuesSet.getDefaultValues(), defaultValuesSet.getForm())) {
            throw new DataIntegrityException(formDataValidator.getErrorMessage());
        }

        defaultValuesSet.getDefaultValues().clear();
        defaultValuesSet.getDefaultValues().addAll(newDefaultValuesSet.getDefaultValues());
        newDefaultValuesSet.setDefaultValues(defaultValuesSet.getDefaultValues());
        newDefaultValuesSet.setForm(defaultValuesSet.getForm());

        return objectMapper.convert(defaultValuesSetRepository.save(newDefaultValuesSet), DefaultValuesSetDTO.class);
    }

    @Override
    public String deleteDefaultSet(Long id) throws NoSuchElementExistsException {
        DefaultValuesSet defaultValuesSet = defaultValuesSetRepository.findOne(id);
        if (defaultValuesSet == null) {
            throw new NoSuchElementExistsException("Default values set doesn't exist");
        }

        checkOwnership(defaultValuesSet.getForm());

        defaultValuesSetRepository.delete(id);
        return "Set " + id + " successfully deleted";
    }

    private Form convertFromDTO(FormDTO formDTO) {
        Form form = objectMapper.convert(formDTO, Form.class);
        form.getFormFields().forEach(field -> {
            if (field.getOptions() != null) {
                field.getOptions().forEach(value -> {
                            value.setFormField(field);
                            value.setId(null);
                        });
                    }
                    field.setForm(form);
                    field.setId(null);
                }
        );
        form.setActive(true);
        form.setOwner(userRepository.findOne(((SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
        return form;
    }

    private Boolean doesFormExist(Long id) {
        return Optional.ofNullable(formRepository.findOne(id)).isPresent();
    }

    public void checkOwnership(Form form) {
        if (!getCurrentLoggedUser().getId().equals(form.getOwner().getId()) && form.getOwner().getRole().equals(UserRole.ROLE_DOCTOR)) {
            throw new ForbiddenContentException();
        }
    }
}
