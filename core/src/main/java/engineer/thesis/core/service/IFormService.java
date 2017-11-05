package engineer.thesis.core.service;

import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.DefaultValuesSetDTO;
import engineer.thesis.core.model.dto.FormDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IFormService {

    FormDTO getFormById(Long id);

    List<FormDTO> getAllForms();

    List<FormDTO> getFormsByName(String name);

    List<FormDTO> getFormsByOwnerId(Long id);

    FormDTO saveForm(FormDTO formDTO) throws NoSuchElementExistsException;

    FormDTO updateForm(Long id, FormDTO form) throws DataIntegrityException, NoSuchElementExistsException;

    String deleteForm(Long id);

    List<DefaultValuesSetDTO> getDefaultValues(Long id) throws NoSuchElementExistsException;

    DefaultValuesSetDTO saveDefaultValues(Long id, DefaultValuesSetDTO defaultValuesSetDTO) throws NoSuchElementExistsException, DataIntegrityException;

    DefaultValuesSetDTO updateDefaultValues(Long id, DefaultValuesSetDTO defaultValuesSetDTO) throws NoSuchElementExistsException, DataIntegrityException;

    String deleteDefaultSet(Long id) throws NoSuchElementExistsException;
}
