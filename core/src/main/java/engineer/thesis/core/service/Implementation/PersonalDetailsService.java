package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.model.PersonalDetails;
import engineer.thesis.core.model.dto.PersonalDetailsDTO;
import engineer.thesis.core.repository.PersonalDetailsRepository;
import engineer.thesis.core.service.Interface.IPersonalDetailsService;
import engineer.thesis.core.utils.CustomObjectMapper;
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

        PersonalDetails personalDetails = personalDetailsRepository.findOne(personalDetailsDTO.getId());
        if (personalDetails == null) {
            throw new NoSuchElementException("Details not found XD");
        }
        return objectMapper.convert(personalDetailsRepository.save(objectMapper.convert(personalDetailsDTO, PersonalDetails.class)), PersonalDetailsDTO.class);
    }

}
