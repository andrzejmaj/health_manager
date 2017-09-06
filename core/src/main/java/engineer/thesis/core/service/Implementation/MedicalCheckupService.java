package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.repository.MedicalCheckupRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.service.Interface.IMedicalCheckupService;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalCheckupService implements IMedicalCheckupService {

    @Autowired
    private MedicalCheckupRepository medicalCheckupRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private CustomObjectMapper objectMapper;

//    @Override
//    public List<MedicalCheckupDTO> getPatientCheckups(Long id) throws NoSuchElementExistsException {
//        if (!patientRepository.exists(id)) {
//            throw new NoSuchElementExistsException("Patient doesn't exists");
//        }
//        return medicalCheckupRepository.findAllByPatientId_Id(id).stream().map(medicalCheckup -> objectMapper.convert(medicalCheckup, MedicalCheckupDTO.class)).collect(Collectors.toList());
//    }
//
//    @Override
//    public MedicalCheckupDTO saveMedicalCheckup(Long patientId, MedicalCheckupDTO medicalCheckupDTO) throws NoSuchElementExistsException, DataIntegrityException {
//        if (!patientId.equals(medicalCheckupDTO.getPatientId())) {
//            throw new DataIntegrityException("Ids in path and body does not match");
//        }
//        if (!patientRepository.exists(medicalCheckupDTO.getPatientId())) {
//            throw new NoSuchElementExistsException("Patient doesn't exist");
//        }
//        medicalCheckupDTO.setId(null);
//        return objectMapper.convert(medicalCheckupRepository.save(
//                objectMapper.convert(medicalCheckupDTO, MedicalCheckup.class)), MedicalCheckupDTO.class);
//    }
//
//    @Override
//    public MedicalCheckupDTO updateMedicalCheckup(Long patientId, MedicalCheckupDTO medicalCheckupDTO) throws NoSuchElementExistsException, DataIntegrityException {
//
//        if (!patientId.equals(medicalCheckupDTO.getPatientId())) {
//            throw new DataIntegrityException("Ids in path and body does not match");
//        }
//
//        if (!patientRepository.exists(medicalCheckupDTO.getPatientId())) {
//            throw new NoSuchElementExistsException("Patient doesn't exist");
//        }
//
//        if (medicalCheckupRepository.exists(medicalCheckupDTO.getId())) {
//            throw new NoSuchElementExistsException("Medical checkup doesn't exist");
//        }
//
//        return objectMapper.convert(medicalCheckupRepository.save(
//                objectMapper.convert(medicalCheckupDTO, MedicalCheckup.class)), MedicalCheckupDTO.class);
//    }
//
//    @Override
//    public void delete(Long id) throws NoSuchElementExistsException {
//        if (!medicalCheckupRepository.exists(id)) {
//            throw new NoSuchElementExistsException("Checkup doesn't exists");
//        }
//        medicalCheckupRepository.delete(id);
//    }
}
