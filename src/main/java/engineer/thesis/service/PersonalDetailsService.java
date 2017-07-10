package engineer.thesis.service;

import engineer.thesis.model.PersonalDetails;
import engineer.thesis.model.dto.PersonalDetailsDTO;
import engineer.thesis.repository.PersonalDetailsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


@Service
public class PersonalDetailsService implements IPersonalDetailsService {

    @Autowired
    private PersonalDetailsRepository personalDetailsRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public PersonalDetailsDTO save(PersonalDetailsDTO personalDetailsDTO) throws NoSuchElementException {

        PersonalDetails personalDetails = personalDetailsRepository.getOne(personalDetailsDTO.getId());
        if (personalDetails == null) {
            throw new NoSuchElementException("Details not found XD");
        }

        return mapToDTO(personalDetailsRepository.save(mapFromDTO(personalDetailsDTO)));
    }

    @Override
    public PersonalDetailsDTO mapToDTO(PersonalDetails personalDetails) {
        return modelMapper.map(personalDetails, PersonalDetailsDTO.class);
    }

    @Override
    public PersonalDetails mapFromDTO(PersonalDetailsDTO personalDetailsDTO) {
        return modelMapper.map(personalDetailsDTO, PersonalDetails.class);
    }
}
