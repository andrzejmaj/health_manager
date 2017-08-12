package engineer.thesis.core.service.Interface;

import engineer.thesis.core.model.dto.PersonalDetailsDTO;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public interface IPersonalDetailsService {

    PersonalDetailsDTO save(PersonalDetailsDTO personalDetailsDTO) throws NoSuchElementException;

}
