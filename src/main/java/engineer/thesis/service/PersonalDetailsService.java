package engineer.thesis.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import engineer.thesis.model.PersonalDetails;
import engineer.thesis.model.dto.PersonalDetailDTO;
import engineer.thesis.repository.PatientRepository;
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
    public PersonalDetailDTO save(PersonalDetailDTO personalDetailDTO) throws NoSuchElementException {

        PersonalDetails personalDetails = personalDetailsRepository.getOne(personalDetailDTO.getId());
        if (personalDetails == null) {
            throw new NoSuchElementException("Details not found XD");
        }

        return mapToDTO(personalDetailsRepository.save(mapFromDTO(personalDetailDTO)));
    }

    @Override
    public PersonalDetailDTO mapToDTO(PersonalDetails personalDetails) {
        return modelMapper.map(personalDetails, PersonalDetailDTO.class);
    }

    @Override
    public PersonalDetails mapFromDTO(PersonalDetailDTO personalDetailDTO) {
        return modelMapper.map(personalDetailDTO, PersonalDetails.class);
    }
}
