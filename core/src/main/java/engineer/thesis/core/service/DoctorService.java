package engineer.thesis.core.service;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.model.Doctor;
import engineer.thesis.core.model.dto.DoctorDTO;
import engineer.thesis.core.repository.DoctorRepository;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class DoctorService implements IDoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private CustomObjectMapper objectMapper;

    @Override
    public DoctorDTO findByID(Long id) throws NoSuchElementException {
        Doctor doctor = doctorRepository.findOne(id);
        if (doctor == null) {
            throw new NoSuchElementException("Doctor not found");
        }
        return objectMapper.convert(doctor, DoctorDTO.class);
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream().map(d -> objectMapper.convert(d,DoctorDTO.class)).collect(Collectors.toList());
    }

    @Override
    public DoctorDTO saveDoctor(DoctorDTO doctorDTO) throws AlreadyExistsException {
        Doctor doctor = doctorRepository.getOne(doctorDTO.getId());
        if (doctor != null) {
            throw new AlreadyExistsException("Doctor already exists");
        }
        return objectMapper.convert(doctorRepository.save(objectMapper.convert(doctorDTO, Doctor.class)), DoctorDTO.class);
    }

    @Override
    public DoctorDTO updateDoctor(DoctorDTO doctorDTO) throws NoSuchElementException {
        Doctor doctor = doctorRepository.getOne(doctorDTO.getId());
        if (doctor == null) {
            throw new NoSuchElementException("Doctor does not exists");
        }
        return objectMapper.convert(doctorRepository.save(objectMapper.convert(doctorDTO, Doctor.class)), DoctorDTO.class);
    }

}
