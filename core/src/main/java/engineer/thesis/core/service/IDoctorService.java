package engineer.thesis.core.service;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.model.dto.DoctorDTO;
import org.springframework.stereotype.Service;

@Service
public interface IDoctorService {

    DoctorDTO saveDoctor(DoctorDTO personalDetailDTO) throws AlreadyExistsException;

}
