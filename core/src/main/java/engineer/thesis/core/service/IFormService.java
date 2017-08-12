package engineer.thesis.core.service;

import engineer.thesis.core.model.dto.FormDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IFormService {

    String deleteForm(Long id);

    FormDTO getFormById(Long id);

    List<FormDTO> getFormsByName(String name);

    List<FormDTO> getFormsByOwnerId(Long id);

    FormDTO saveForm(FormDTO formDTO);

}
