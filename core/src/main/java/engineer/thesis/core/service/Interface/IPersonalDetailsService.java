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

    PersonalDetailsDTO saveOrUpdateMine(PersonalDetailsDTO personalDetailsDTO, boolean save) throws NoSuchElementExistsException, AlreadyExistsException;

    PersonalDetailsDTO get(Long patientId) throws NoSuchElementExistsException, AccessDeniedException;

    PersonalDetailsDTO saveOrUpdate(Long patientId, PersonalDetailsDTO personalDetailsDTO, boolean save) throws NoSuchElementExistsException, AlreadyExistsException, AccessDeniedException;

    PersonalDetailsDTO getDoctor(Long doctorId) throws NoSuchElementExistsException, AccessDeniedException;

    PersonalDetailsDTO saveOrUpdateDoctor(Long doctorId, PersonalDetailsDTO personalDetails, boolean save) throws NoSuchElementExistsException, AlreadyExistsException, AccessDeniedException
            ;
}
