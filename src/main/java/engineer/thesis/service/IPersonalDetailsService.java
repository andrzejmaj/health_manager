package engineer.thesis.service;

import engineer.thesis.exception.AlreadyExistsException;
import engineer.thesis.model.PersonalDetails;
import engineer.thesis.model.dto.PersonalDetailDTO;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public interface IPersonalDetailsService extends IBasicService<PersonalDetails,PersonalDetailDTO>{

    PersonalDetailDTO save(PersonalDetailDTO personalDetailsDTO) throws NoSuchElementException;

}
