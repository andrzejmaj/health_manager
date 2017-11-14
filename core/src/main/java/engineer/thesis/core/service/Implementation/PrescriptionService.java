package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.PrescriptionDTO;
import engineer.thesis.core.model.dto.PrescriptionDrugDTO;
import engineer.thesis.core.model.entity.Appointment;
import engineer.thesis.core.model.entity.Drug;
import engineer.thesis.core.model.entity.Prescription;
import engineer.thesis.core.model.entity.PrescriptionDrug;
import engineer.thesis.core.repository.AppointmentRepository;
import engineer.thesis.core.repository.DrugRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.repository.PrescriptionRepository;
import engineer.thesis.core.service.Interface.BasePatientService;
import engineer.thesis.core.service.Interface.IPrescriptionService;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PrescriptionService implements IPrescriptionService, BasePatientService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private CustomObjectMapper objectMapper;

    @Autowired
    private DrugRepository drugRepository;

    @Override
    public PrescriptionDTO get(Long id) throws NoSuchElementExistsException {
        Prescription prescription = prescriptionRepository.findOne(id);
        if (prescription == null) {
            throw new NoSuchElementExistsException("Prescription doesn't exist");
        }

        return objectMapper.convert(prescription, PrescriptionDTO.class);
    }

    @Override
    public List<PrescriptionDTO> getByPatientId(Long patientId) throws NoSuchElementExistsException {
        checkPatientExistence(patientId, patientRepository);
        return prescriptionRepository.findAllByAppointment_Patient_Id(patientId).stream().map(p -> objectMapper.convert(p, PrescriptionDTO.class)).collect(Collectors.toList());
    }

    @Override
    public PrescriptionDTO save(PrescriptionDTO prescriptionDTO) throws NoSuchElementExistsException {
        Appointment appointment = appointmentRepository.findOne(prescriptionDTO.getAppointmentId());
        if (appointment == null) {
            throw new NoSuchElementExistsException("Appointment doesn't exist");
        }

        Prescription prescription = objectMapper.convert(prescriptionDTO, Prescription.class);

        prescription.setDrugs(convertPrescriptionListToEntity(prescriptionDTO.getDrugs(), prescription));
        prescription.setAppointment(appointment);
        prescription.setId(null);
        prescription.setCreationDate(new Date());

        return objectMapper.convert(prescriptionRepository.save(prescription), PrescriptionDTO.class);
    }

    @Override
    public PrescriptionDTO update(Long id, PrescriptionDTO prescriptionDTO) throws NoSuchElementExistsException {
        Prescription prescription = prescriptionRepository.findOne(id);
        if (prescription == null) {
            throw new NoSuchElementExistsException("Prescription doesn't exist");
        }

        Appointment appointment = appointmentRepository.findOne(prescriptionDTO.getAppointmentId());
        if (appointment == null) {
            throw new NoSuchElementExistsException("Appointment doesn't exist");
        }

        prescription.setAppointment(appointment);
        prescription.setNotes(prescriptionDTO.getNotes());
        prescription.getDrugs().clear();
        prescription.getDrugs().addAll(convertPrescriptionListToEntity(prescriptionDTO.getDrugs(), prescription));

        return objectMapper.convert(prescriptionRepository.save(prescription), PrescriptionDTO.class);
    }

    @Override
    public String delete(Long id) throws NoSuchElementExistsException {
        Prescription prescription = prescriptionRepository.findOne(id);
        if (prescription == null) {
            throw new NoSuchElementExistsException("Prescription doesn't exist");
        }
        prescriptionRepository.delete(id);
        return "Successfully deleted prescription " + id;
    }

    private List<PrescriptionDrug> convertPrescriptionListToEntity(List<PrescriptionDrugDTO> drugsDTO, Prescription prescription) throws NoSuchElementExistsException {
        List<PrescriptionDrug> drugs;
        try {
            drugs = drugsDTO.stream().map(drug -> convertToPrescriptionDrug(drug.getDrugName(), prescription, drug.getSize())).collect(Collectors.toList());
        } catch (NoSuchElementException e) {
            throw new NoSuchElementExistsException("Drug doesn't exist");
        }
        return drugs;
    }

    private PrescriptionDrug convertToPrescriptionDrug(String name, Prescription prescription, String size) {
        PrescriptionDrug prescriptionDrug = new PrescriptionDrug();
        Drug drug = drugRepository.findByName(name);
        if (drug == null) {
            throw new NoSuchElementException("Drug doesn't exist");
        }

        prescriptionDrug.setDrug(drug);
        prescriptionDrug.setPrescription(prescription);
        prescriptionDrug.setSize(size);
        return prescriptionDrug;
    }
}
