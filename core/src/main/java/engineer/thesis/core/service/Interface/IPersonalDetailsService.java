package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.AccessDeniedException;
import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.PersonalDetailsDTO;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public interface IPersonalDetailsService {

    PersonalDetailsDTO getMine() throws NoSuchElementExistsException;

    PersonalDetailsDTO saveOrUpdateMine(PersonalDetailsDTO personalDetailsDTO);

    PersonalDetailsDTO getPatient(Long patientId) throws NoSuchElementExistsException;

    PersonalDetailsDTO saveOrUpdatePatient(Long patientId, PersonalDetailsDTO personalDetailsDTO) throws NoSuchElementExistsException;

    PersonalDetailsDTO getDoctor(Long doctorId) throws NoSuchElementExistsException;

    PersonalDetailsDTO saveOrUpdateDoctor(Long doctorId, PersonalDetailsDTO personalDetailsDTO) throws NoSuchElementExistsException;
}
