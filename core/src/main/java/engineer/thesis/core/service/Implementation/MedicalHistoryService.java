package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.Disease;
import engineer.thesis.core.model.MedicalHistory;
import engineer.thesis.core.model.dto.MedicalHistoryDTO;
import engineer.thesis.core.repository.DiseaseRepository;
import engineer.thesis.core.repository.MedicalHistoryRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.service.Interface.BaseService;
import engineer.thesis.core.service.Interface.IMedicalHistoryService;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MedicalHistoryService extends BaseService implements IMedicalHistoryService {

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
    public void delete(Long patientId, Long id) throws NoSuchElementExistsException {

        if (medicalHistoryRepository.exists(id)) {
            throw new NoSuchElementExistsException("History note not doesn't exists");
        }
        medicalHistoryRepository.delete(id);
    }

    @Override
    public MedicalHistoryDTO saveOrUpdate(Long patientId, MedicalHistoryDTO medicalHistoryDTO, boolean b) throws NoSuchElementExistsException, DataIntegrityException {
        // TODO: 20.08.17  add permission to do action check

        if (!patientRepository.exists(medicalHistoryDTO.getPatientId())) {
            throw new NoSuchElementExistsException("Patient doesn't not exists");
        }

        if (medicalHistoryDTO.getDisease() == null) {
            throw new DataIntegrityException("No disease given");
        }

        if (medicalHistoryDTO.getDisease().getName() == null) {
            throw new DataIntegrityException("Invalid disease, name is always required");
        }

        if (medicalHistoryDTO.getId() != null && medicalHistoryRepository.findAllByPatient_Id(patientId).stream().noneMatch(hist -> Objects.equals(hist.getId(), medicalHistoryDTO.getId()))) {
            throw new NoSuchElementExistsException("Medical record doesn't exists for patient");
        }

        MedicalHistory medicalHistory = objectMapper.convert(medicalHistoryDTO, MedicalHistory.class);
        Disease disease = diseaseRepository.findByName(medicalHistoryDTO.getDisease().getName());

        if (disease != null && disease.getId().equals(medicalHistoryDTO.getDisease().getId())) {
            medicalHistory.setDisease(disease);
            return objectMapper.convert(medicalHistoryRepository.save(medicalHistory), MedicalHistoryDTO.class);
        } else if (disease == null) {
            medicalHistoryDTO.getDisease().setId(null);
            return objectMapper.convert(medicalHistoryRepository.save(medicalHistory), MedicalHistoryDTO.class);
        } else {
            throw new DataIntegrityException("Invalid disease");
        }
    }
}
