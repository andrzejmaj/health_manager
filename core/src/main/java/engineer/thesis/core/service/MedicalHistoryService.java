package engineer.thesis.core.service;

import engineer.thesis.core.model.MedicalHistory;
import engineer.thesis.core.model.Patient;
import engineer.thesis.core.model.dto.MedicalHistoryDTO;
import engineer.thesis.core.repository.MedicalHistoryRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MedicalHistoryService implements IMedicalHistoryService{

    @Autowired
    MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    CustomObjectMapper objectMapper;


    @Override
    public MedicalHistoryDTO save(Long id, MedicalHistoryDTO medicalHistoryDTO) throws NoSuchElementException {
        Patient patient = patientRepository.findOne(id);
        if (patient == null) {
            throw new NoSuchElementException("Patient doesn't not exists");
        }
        medicalHistoryDTO.setId(null);
        return objectMapper.convert(medicalHistoryRepository.save(objectMapper.convert(medicalHistoryDTO, MedicalHistory.class)),MedicalHistoryDTO.class);
    }

    @Override
    public MedicalHistoryDTO update(MedicalHistoryDTO medicalHistoryDTO) throws NoSuchElementException {
        MedicalHistory medicalHistory = medicalHistoryRepository.findOne(medicalHistoryDTO.getId());
        if (medicalHistory == null) {
            throw new NoSuchElementException("History record doesn't exist");
        }
        medicalHistoryDTO.setId(medicalHistory.getId());

        return objectMapper.convert(medicalHistoryRepository.save(objectMapper.convert(medicalHistoryDTO, MedicalHistory.class)),MedicalHistoryDTO.class);
    }

    @Override
    public List<MedicalHistoryDTO> getAllByPatientIdFromPeriod(Long id, Date start, Date end) throws NoSuchElementException {
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
}
