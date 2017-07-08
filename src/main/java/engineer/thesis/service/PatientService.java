package engineer.thesis.service;

import engineer.thesis.exception.AlreadyExistsException;
import engineer.thesis.model.*;
import engineer.thesis.model.dto.*;
import engineer.thesis.repository.CurrentConditionRepository;
import engineer.thesis.repository.CurrentDrugRepository;
import engineer.thesis.repository.PatientRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotSupportedException;
import java.io.NotSerializableException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PatientService implements IPatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AccountService accountService;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public PatientDTO findById(Long id) throws NoSuchElementException {
        Optional<Patient> patient = Optional.ofNullable(patientRepository.findOne(id));
        return convertPatientToDTO(patient.orElseThrow(NoSuchElementException::new));
    }

    @Override
    public PatientDTO findByPesel(String pesel) throws NoSuchElementException {
        Optional<Patient> patient = Optional.ofNullable(patientRepository.findByUser_PersonalDetails_Pesel(pesel));
        return convertPatientToDTO(patient.orElseThrow(NoSuchElementException::new));

    }

    @Override
    public List<PatientDTO> findPatientsByLastName(String lastName) {
        return patientRepository.findByUser_PersonalDetails_LastNameLike(lastName).stream().map(this::convertPatientToDTO).collect(Collectors.toList());
    }

    @Override
    public PatientDTO saveNewPatient(PatientDTO patientDTO, String email) throws AlreadyExistsException {
        if (accountService.doesAccountExist(patientDTO.getAccount().getPersonalDetails().getPesel())) {
            throw new AlreadyExistsException("Account with such pesel number already exists");
        }
        return convertPatientToDTO(patientRepository.save(convertPatientToEntity(patientDTO)));
    }

    private PatientDTO convertPatientToDTO(Patient patient) {
        return modelMapper.map(patient, PatientDTO.class);
    }

    private Patient convertPatientToEntity(PatientDTO patientDTO) {
        return modelMapper.map(patientDTO, Patient.class);
    }

    @Override
    public PatientDTO mapToDTO(Patient data) {
        return null;
    }

    @Override
    public Patient mapFromDTO(PatientDTO patientDTO) {
        return null;
    }
}