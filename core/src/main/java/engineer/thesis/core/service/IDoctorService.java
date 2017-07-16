package engineer.thesis.core.service;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.model.Doctor;
import engineer.thesis.core.model.dto.DoctorDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public interface IDoctorService {
    DoctorDTO findByID(Long id) throws NoSuchElementException;

    List<DoctorDTO> getAllDoctors();

    DoctorDTO saveDoctor(DoctorDTO doctorDTO) throws AlreadyExistsException;

    DoctorDTO updateDoctor(DoctorDTO doctorDTO) throws NoSuchElementException;
}
