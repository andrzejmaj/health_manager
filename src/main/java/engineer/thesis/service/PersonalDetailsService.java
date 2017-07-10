package engineer.thesis.service;

import engineer.thesis.model.PersonalDetails;
import engineer.thesis.model.dto.PersonalDetailsDTO;
import engineer.thesis.repository.PersonalDetailsRepository;
import engineer.thesis.utils.CustomObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


@Service
public class PersonalDetailsService implements IPersonalDetailsService {

    @Autowired
    private PersonalDetailsRepository personalDetailsRepository;

    @Autowired
    private CustomObjectMapper objectMapper;

    @Override
    public PersonalDetailsDTO save(PersonalDetailsDTO personalDetailsDTO) throws NoSuchElementException {

        PersonalDetails personalDetails = personalDetailsRepository.getOne(personalDetailsDTO.getId());
        if (personalDetails == null) {
            throw new NoSuchElementException("Details not found XD");
        }
        return objectMapper.convert(personalDetailsRepository.save(objectMapper.convert(personalDetailsDTO, PersonalDetails.class)), PersonalDetailsDTO.class);
    }

}
