package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.Appointment;
import engineer.thesis.core.model.Patient;
import engineer.thesis.core.model.TimeSlot;
import engineer.thesis.core.model.VisitPriority;
import engineer.thesis.core.model.dto.AppointmentDTO;
import engineer.thesis.core.repository.AppointmentRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.repository.TimeSlotRepository;
import engineer.thesis.core.service.Interface.IAppointmentService;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService implements IAppointmentService {
    private final static Logger logger = Logger.getLogger(AppointmentService.class);

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private CustomObjectMapper objectMapper;

    @Override
    public AppointmentDTO getById(long id) throws NoSuchElementExistsException {
        return Optional.ofNullable(appointmentRepository.findOne(id))
                .map(appointment -> objectMapper.convert(appointment, AppointmentDTO.class))
                .orElseThrow(() -> new NoSuchElementExistsException("No appointment with id " + id));
    }

    @Override
    public AppointmentDTO save(AppointmentDTO appointmentDTO, long patientId) {
        logger.info("Saveing new appointment for " + patientId + ".\n" + appointmentDTO.toString());
        Appointment oldAppointment = appointmentRepository.findByTimeSlotId(appointmentDTO.getTimeSlotId());
        if (oldAppointment != null) {
            appointmentRepository.delete(oldAppointment);
        }

        Patient patient = patientRepository.findOne(patientId);
        if (patient == null) {
            throw new IllegalArgumentException("No patient with id " + patientId);
        }
        TimeSlot timeSlot = timeSlotRepository.findOne(appointmentDTO.getTimeSlotId());
        if (timeSlot == null) {
            throw new IllegalArgumentException("No timeSlot with id " + appointmentDTO.getTimeSlotId());
        }
        Integer officeNumber = appointmentDTO.getOfficeNumber();
        boolean tookPlace = appointmentDTO.getTookPlace();
        String data = appointmentDTO.getData();
        VisitPriority priority = appointmentDTO.getPriority();
        Appointment newAppointment = new Appointment(patient, timeSlot, officeNumber, tookPlace, data, priority);

        return objectMapper.convert(appointmentRepository.save(newAppointment), AppointmentDTO.class);
    }

    @Override
    public boolean exists(AppointmentDTO appointmentDTO) {
        return appointmentRepository.findByTimeSlotId(appointmentDTO.getTimeSlotId()) != null;
    }

    @Override
    public AppointmentDTO find(long appointmentId) {
        return objectMapper.convert(appointmentRepository.findOne(appointmentId), AppointmentDTO.class);
    }

    @Override
    public List<AppointmentDTO> getInIntervalForDoctor(long doctorId, Date startDate, Date endDate) throws IllegalArgumentException {
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Dates went full retard. startDate is after endDate [" + startDate + ", " + endDate + "]");
        }

        return appointmentRepository.findInIntervalForDoctor(doctorId, startDate, endDate).stream()
                .map(appointment -> objectMapper.convert(appointment, AppointmentDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> getInIntervalForPatient(long patientId, Date startDate, Date endDate) throws IllegalArgumentException {
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Dates went full retard. startDate is after endDate [" + startDate + ", " + endDate + "]");
        }

        return appointmentRepository.findInIntervalForPatient(patientId, startDate, endDate).stream()
                .map(appointment -> objectMapper.convert(appointment, AppointmentDTO.class)).collect(Collectors.toList());
    }
}
