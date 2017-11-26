package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.RequestMedicalHistoryDTO;
import engineer.thesis.core.model.dto.ResponseMedicalCheckupValueDTO;
import engineer.thesis.core.model.dto.ResponseMedicalHistoryDTO;
import engineer.thesis.core.model.entity.Form;
import engineer.thesis.core.model.entity.MedicalCheckup;
import engineer.thesis.core.model.entity.MedicalCheckupValue;
import engineer.thesis.core.model.entity.MedicalHistory;
import engineer.thesis.core.repository.FormRepository;
import engineer.thesis.core.repository.MedicalCheckupRepository;
import engineer.thesis.core.repository.MedicalHistoryRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.service.Interface.IMedicalHistoryService;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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

    @Autowired
    private FormRepository formRepository;

    @Override
    public List<ResponseMedicalHistoryDTO> getAllByPatientIdFromPeriod(Long id, Date start, Date end) throws NoSuchElementExistsException, DataIntegrityException {
        try {

            if (start == null && end == null) {
                return medicalHistoryRepository.findAllByPatient_Id(id)
                        .stream().map(this::mapToDTO).collect(Collectors.toList());
            } else {
                return medicalHistoryRepository.findAllByPatient_IdAndDetectionDateBetween(id,
                        start != null ? start : new GregorianCalendar(1800, Calendar.JANUARY, 1).getTime(),
                        end != null ? end : new GregorianCalendar().getTime())
                        .stream().map(this::mapToDTO)
                        .collect(Collectors.toList());
            }
        } catch (NoSuchElementException e) {
            throw new DataIntegrityException(e.getMessage());
        }
    }
    @Override
    public RequestMedicalHistoryDTO save(Long patientId, RequestMedicalHistoryDTO requestMedicalHistoryDTO) throws NoSuchElementExistsException, DataIntegrityException {
        requestMedicalHistoryDTO.setId(null);

        if (!patientRepository.exists(patientId)) {
            throw new NoSuchElementExistsException("Patient doesn't exists");
        }
        requestMedicalHistoryDTO.setPatientId(patientId);

        MedicalCheckup medicalCheckup = medicalCheckupRepository.findOne(requestMedicalHistoryDTO.getMedicalCheckupId());
        if (medicalCheckup == null) {
            throw new NoSuchElementExistsException("Medical Checkup doesn't exist");
        }

        MedicalHistory medicalHistory = objectMapper.convert(requestMedicalHistoryDTO, MedicalHistory.class);

        return objectMapper.convert(medicalHistoryRepository.save(medicalHistory), RequestMedicalHistoryDTO.class);
    }


    @Override
    public RequestMedicalHistoryDTO update(RequestMedicalHistoryDTO requestMedicalHistoryDTO, Long id) throws NoSuchElementExistsException, DataIntegrityException {

        MedicalHistory existingMedicalHistory = medicalHistoryRepository.findOne(id);

        if (existingMedicalHistory == null) {
            throw new NoSuchElementExistsException("Medical record doesn't exists");
        }

        requestMedicalHistoryDTO.setId(existingMedicalHistory.getId());
        requestMedicalHistoryDTO.setPatientId(existingMedicalHistory.getPatient().getId());

        MedicalCheckup medicalCheckup = medicalCheckupRepository.findOne(requestMedicalHistoryDTO.getMedicalCheckupId());
        if (medicalCheckup == null) {
            throw new NoSuchElementExistsException("Medical Checkup doesn't exist");
        }

        MedicalHistory medicalHistory = objectMapper.convert(requestMedicalHistoryDTO, MedicalHistory.class);

        return objectMapper.convert(medicalHistoryRepository.save(medicalHistory), RequestMedicalHistoryDTO.class);
    }

    @Override
    public void delete(Long id) throws NoSuchElementExistsException {
        if (!medicalHistoryRepository.exists(id)) {
            throw new NoSuchElementExistsException("History record doesn't exist");
        }
        medicalHistoryRepository.delete(id);
    }

    private List<ResponseMedicalCheckupValueDTO> convertToResponseMedicalCheckupValueDTO(Long id, List<MedicalCheckupValue> values) {
        Form form = formRepository.getOne(id);

        return values.stream().map(val -> new ResponseMedicalCheckupValueDTO(val, form.getFormFields().stream()
                .filter(field -> Objects.equals(field.getId(), val.getFormFieldId())).findFirst().get().getName())).collect(Collectors.toList());
    }

    private ResponseMedicalHistoryDTO mapToDTO(MedicalHistory mh) {
        ResponseMedicalHistoryDTO historyDTO = objectMapper.convert(mh, ResponseMedicalHistoryDTO.class);
        if (historyDTO.getMedicalCheckup() != null)
            historyDTO.getMedicalCheckup().setMedicalCheckupValues(convertToResponseMedicalCheckupValueDTO(mh.getMedicalCheckup().getForm().getId(), mh.getMedicalCheckup().getMedicalCheckupValues()));
        return historyDTO;
    }
}
