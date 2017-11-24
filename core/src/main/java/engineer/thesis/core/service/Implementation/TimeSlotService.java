package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.controller.TimeSlotController;
import engineer.thesis.core.model.dto.TimeSlotDTO;
import engineer.thesis.core.model.entity.Doctor;
import engineer.thesis.core.model.entity.TimeSlot;
import engineer.thesis.core.repository.DoctorRepository;
import engineer.thesis.core.repository.TimeSlotRepository;
import engineer.thesis.core.service.Interface.ITimeSlotService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TimeSlotService implements ITimeSlotService {
    private final static Logger logger = Logger.getLogger(TimeSlotController.class);

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    private static TimeSlotDTO mapToDTO(TimeSlot timeSlot) {
        return new TimeSlotDTO(timeSlot.getId(), timeSlot.getStartDateTime(), timeSlot.getEndDateTime(), timeSlot.getDoctor().getId(), timeSlot.isAvailableForSelfSign());
    }

    @Override
    public TimeSlotDTO getById(long id) {
        return mapToDTO(Optional.ofNullable(timeSlotRepository.getOne(id)).orElseThrow(() -> new NoSuchElementException("No timeslot with id " + id)));
    }

    @Override
    public TimeSlotDTO saveTimeSlot(TimeSlotDTO timeSlotDTO, long doctorId) throws IllegalArgumentException {
        if (timeSlotRepository.exists(timeSlotDTO.getId()) && !isOwner(doctorId, timeSlotDTO.getId())) {
            throw new IllegalArgumentException("Bad id (the slot is owned by another doctor)");
        }

        TimeSlot timeSlot = mapFromDTO(timeSlotDTO, doctorId);
        if (isValid(timeSlot)) {
            timeSlot = timeSlotRepository.save(timeSlot);
        } else {
            throw new IllegalArgumentException("The slot is not valid (probably interleaving)");
        }

        return mapToDTO(timeSlot);
    }

    @Override
    public List<TimeSlotDTO> getInIntervalForDoctor(long doctorId, Date startDate, Date endDate) throws IllegalArgumentException {
        logger.info("Getting timeslots between: " + startDate + ", " + endDate);
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Dates went full retard. startDate is after endDate [" + startDate + ", " + endDate + "]");
        }

        return timeSlotRepository.findInInterval(doctorId, startDate, endDate).stream()
                .map(TimeSlotService::mapToDTO).collect(Collectors.toList());
    }

    private boolean isValid(TimeSlot timeSlot) {
        List<TimeSlot> interleavingSlots = timeSlotRepository.findInterleaving(timeSlot.getDoctor(),
                timeSlot.getStartDateTime(), timeSlot.getEndDateTime());

        return interleavingSlots.isEmpty()
                || (interleavingSlots.size() == 1 && interleavingSlots.get(0).getId() == timeSlot.getId());
    }

    private TimeSlot mapFromDTO(TimeSlotDTO timeSlotDTO, long doctorId) {
        Doctor doctor = doctorRepository.findOne(doctorId);
        TimeSlot timeSlot = new TimeSlot(timeSlotDTO.getStartDateTime(), timeSlotDTO.getEndDateTime(), doctor, timeSlotDTO.isAvailableForSelfSign());
        if (timeSlotDTO.getId() != null) {
            timeSlot.setId(timeSlotDTO.getId());
        }

        return timeSlot;
    }

    private boolean isOwner(long doctorId, long timeSlotId) {
        return timeSlotRepository.findOne(timeSlotId).getDoctor().getId() == doctorId;
    }

    @Override
    public TimeSlotDTO moveTimeSlot(long timeSlotId, long doctorId, Date startDate, Date endDate) {
        if (!doctorRepository.exists(doctorId)) {
            throw new NoSuchDoctorException(doctorId);
        }
        List<TimeSlotDTO> interleaving = getInIntervalForDoctor(doctorId, startDate, endDate);
        if (!interleaving.isEmpty()) {
            //it still can work - if we're moving the slot over itself
            boolean itsOkay = interleaving.size() == 1
                    && interleaving.get(0).getId() == timeSlotId;
            if (!itsOkay) {
                throw new IllegalArgumentException("There is another timeslot interleaving!");
            }
        }
        TimeSlot timeSlot = timeSlotRepository.getOne(timeSlotId);
        if (timeSlot == null) {
            throw new NoSuchTimeSlotException(timeSlotId);
        }
        timeSlot.setStartDateTime(startDate);
        timeSlot.setEndDateTime(endDate);
        return mapToDTO(timeSlotRepository.save(timeSlot));
    }

    public static class NoSuchDoctorException extends NoSuchElementException {
        NoSuchDoctorException(long doctorId) {
            super("Doctor with ID " + doctorId + " does not exist!");
        }
    }

    public static class NoSuchTimeSlotException extends NoSuchElementException {
        NoSuchTimeSlotException(long timeSlotId) {
            super("Timeslot with ID " + timeSlotId + " does not exist!");
        }
    }
}
