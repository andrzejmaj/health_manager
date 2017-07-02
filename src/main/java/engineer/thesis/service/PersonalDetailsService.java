package engineer.thesis.service;

import engineer.thesis.model.PersonalDetails;
import engineer.thesis.model.dto.PersonalDetailDTO;
import engineer.thesis.repository.PatientRepository;
import engineer.thesis.repository.PersonalDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


@Service
public class PersonalDetailsService implements IPersonalDetailsService {

    private PersonalDetailsRepository personalDetailsRepository;

    @Autowired
    public PersonalDetailsService(PatientRepository patientRepository, PersonalDetailsRepository personalDetailsRepository) {
        this.personalDetailsRepository = personalDetailsRepository;
    }


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
        return new PersonalDetailDTO(
                personalDetails.getId(),
                personalDetails.getFirstName(),
                personalDetails.getLastName(),
                personalDetails.getGender(),
                personalDetails.getPesel(),
                personalDetails.getBirthDate(),
                personalDetails.getPhoneNumber(),
                personalDetails.getCountry(),
                personalDetails.getStreet(),
                personalDetails.getCity(),
                personalDetails.getBuildingNumber(),
                personalDetails.getFlatNumber()
        );
    }

    @Override
    public PersonalDetails mapFromDTO(PersonalDetailDTO personalDetailDTO) {
        return new PersonalDetails(
                personalDetailDTO.getId(),
                personalDetailDTO.getPesel(),
                personalDetailDTO.getFirstName(),
                personalDetailDTO.getLastName(),
                personalDetailDTO.getBirthdate(),
                personalDetailDTO.getGender(),
                personalDetailDTO.getPhoneNumber(),
                personalDetailDTO.getCountry(),
                personalDetailDTO.getStreet(),
                personalDetailDTO.getCity(),
                personalDetailDTO.getBuildingNumber(),
                personalDetailDTO.getFlatNumber()
        );
    }
}
