package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.Disease;
import engineer.thesis.core.model.MedicalHistory;
import engineer.thesis.core.model.dto.MedicalHistoryDTO;
import engineer.thesis.core.repository.DiseaseRepository;
import engineer.thesis.core.repository.MedicalHistoryRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.service.Interface.IMedicalHistoryService;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalHistoryService implements IMedicalHistoryService {

    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Autowired
    private CustomObjectMapper objectMapper;


    @Override
    public List<MedicalHistoryDTO> getAllByPatientIdFromPeriod(Long id, Date start, Date end) throws NoSuchElementExistsException {
        if (start != null && end != null) {
            return medicalHistoryRepository.findAllByPatient_IdAndDetectionDateBetween(id, start, end)
                    .stream().map(mh -> objectMapper.convert(mh, MedicalHistoryDTO.class)).collect(Collectors.toList());
        } else if (start == null && end == null) {
            return medicalHistoryRepository.findAllByPatient_Id(id)
                    .stream().map(mh -> objectMapper.convert(mh, MedicalHistoryDTO.class)).collect(Collectors.toList());
        } else {
            return medicalHistoryRepository.findAllByPatient_IdAndDetectionDateBetween(id,
                    start != null ? start : new GregorianCalendar(1800, Calendar.JANUARY, 1).getTime(),
                    end != null ? end : new GregorianCalendar().getTime())
                    .stream().map(medHist -> objectMapper.convert(medHist, MedicalHistoryDTO.class))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public MedicalHistoryDTO save(Long patientId, MedicalHistoryDTO medicalHistoryDTO) throws NoSuchElementExistsException, DataIntegrityException {

        if (!patientId.equals(medicalHistoryDTO.getPatientId())) {
            throw new DataIntegrityException("Ids in the body and path does not match");
        }

        if (!patientRepository.exists(medicalHistoryDTO.getPatientId())) {
            throw new NoSuchElementExistsException("Patient doesn't not exists");
        }

        Disease disease = diseaseRepository.findByName(medicalHistoryDTO.getDisease().getName());
        if (disease == null) {
            if (medicalHistoryDTO.getId() != null) {
                throw new DataIntegrityException("Invalid disease");
            }
        }

        if (disease != null && !disease.getId().equals(medicalHistoryDTO.getDisease().getId())) {
            throw new DataIntegrityException("Invalid disease");
        }

        medicalHistoryDTO.setId(null);
        return objectMapper.convert(medicalHistoryRepository.save(objectMapper.convert(medicalHistoryDTO, MedicalHistory.class)),MedicalHistoryDTO.class);
    }

    @Override
    public MedicalHistoryDTO update(Long patientId, MedicalHistoryDTO medicalHistoryDTO) throws DataIntegrityException, NoSuchElementExistsException {


        if (!patientId.equals(medicalHistoryDTO.getPatientId())) {
            throw new DataIntegrityException("Ids in path and body doesn't match");
        }

        if (!patientRepository.exists(patientId)) {
            throw new NoSuchElementExistsException("Patient doesn't exists");
        }

        if (!medicalHistoryRepository.exists(medicalHistoryDTO.getId())) {
            throw new NoSuchElementExistsException("History record doesn't exist");
        }

        return objectMapper.convert(medicalHistoryRepository.save(objectMapper.convert(medicalHistoryDTO, MedicalHistory.class)),MedicalHistoryDTO.class);
    }

    @Override
    public void delete(Long id) throws NoSuchElementExistsException {
        if (medicalHistoryRepository.exists(id)) {
            throw new NoSuchElementExistsException("History note not doesn't exists");
        }
        medicalHistoryRepository.delete(id);
    }
}
