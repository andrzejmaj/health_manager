package engineer.thesis.service;

import engineer.thesis.model.PersonalDetails;
import engineer.thesis.model.dto.PersonalDetailsDTO;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public interface IPersonalDetailsService {

    PersonalDetailsDTO save(PersonalDetailsDTO personalDetailsDTO) throws NoSuchElementException;

}
