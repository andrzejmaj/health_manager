package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.MedicalHistoryDTO;
import engineer.thesis.core.model.entity.MedicalCheckup;
import engineer.thesis.core.model.entity.MedicalHistory;
import engineer.thesis.core.repository.MedicalCheckupRepository;
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
    private MedicalCheckupRepository medicalCheckupRepository;

    @Autowired
    private CustomObjectMapper objectMapper;

    @Override
    public List<MedicalHistoryDTO> getAllByPatientIdFromPeriod(Long id, Date start, Date end) throws NoSuchElementExistsException {

        if (start == null && end == null) {
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
        medicalHistoryDTO.setId(null);

        if (!patientRepository.exists(patientId)) {
            throw new NoSuchElementExistsException("Patient doesn't exists");
        }
        medicalHistoryDTO.setPatientId(patientId);

        MedicalCheckup medicalCheckup = medicalCheckupRepository.findOne(medicalHistoryDTO.getMedicalCheckupId());
        if (medicalCheckup == null) {
            throw new NoSuchElementExistsException("Medical Checkup doesn't exist");
        }

        MedicalHistory medicalHistory = objectMapper.convert(medicalHistoryDTO, MedicalHistory.class);

        return objectMapper.convert(medicalHistoryRepository.save(medicalHistory), MedicalHistoryDTO.class);
    }


    @Override
    public MedicalHistoryDTO update(MedicalHistoryDTO medicalHistoryDTO, Long id) throws NoSuchElementExistsException, DataIntegrityException {

        MedicalHistory existingMedicalHistory = medicalHistoryRepository.findOne(id);

        if (existingMedicalHistory == null) {
            throw new NoSuchElementExistsException("Medical record doesn't exists");
        }

        medicalHistoryDTO.setId(existingMedicalHistory.getId());
        medicalHistoryDTO.setPatientId(existingMedicalHistory.getPatient().getId());

        MedicalCheckup medicalCheckup = medicalCheckupRepository.findOne(medicalHistoryDTO.getMedicalCheckupId());
        if (medicalCheckup == null) {
            throw new NoSuchElementExistsException("Medical Checkup doesn't exist");
        }

        MedicalHistory medicalHistory = objectMapper.convert(medicalHistoryDTO, MedicalHistory.class);

        return objectMapper.convert(medicalHistoryRepository.save(medicalHistory), MedicalHistoryDTO.class);
    }

    @Override
    public void delete(Long id) throws NoSuchElementExistsException {
        if (!medicalHistoryRepository.exists(id)) {
            throw new NoSuchElementExistsException("History record doesn't exist");
        }
        medicalHistoryRepository.delete(id);
    }
}
