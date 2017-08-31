package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.model.dto.DoctorDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public interface IDoctorService {
    DoctorDTO findByID(Long id) throws NoSuchElementException;

    List<DoctorDTO> getAllDoctors();

    DoctorDTO updateDoctor(DoctorDTO doctorDTO) throws NoSuchElementException;

    DoctorDTO saveDoctor(DoctorDTO personalDetailDTO) throws AlreadyExistsException;

}
