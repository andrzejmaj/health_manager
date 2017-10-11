package engineer.thesis.core.service;

import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.FormDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IFormService {

    String deleteForm(Long id);

    FormDTO getFormById(Long id);

    List<FormDTO> getAllForms();

    List<FormDTO> getFormsByName(String name);

    List<FormDTO> getFormsByOwnerId(Long id);

    FormDTO saveForm(FormDTO formDTO);

    FormDTO updateForm(Long id, FormDTO form) throws DataIntegrityException, NoSuchElementExistsException;
}
