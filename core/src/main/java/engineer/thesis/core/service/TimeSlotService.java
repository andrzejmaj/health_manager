package engineer.thesis.core.service;

import engineer.thesis.core.controller.TimeSlotController;
import engineer.thesis.core.model.Doctor;
import engineer.thesis.core.model.TimeSlot;
import engineer.thesis.core.model.dto.TimeSlotDTO;
import engineer.thesis.core.repository.DoctorRepository;
import engineer.thesis.core.repository.TimeSlotRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeSlotService implements ITimeSlotService {
	private final static Logger logger = Logger.getLogger(TimeSlotController.class);

	@Autowired
	private TimeSlotRepository timeSlotRepository;

	@Autowired
	private DoctorRepository doctorRepository;

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
		TimeSlot timeSlot = new TimeSlot(timeSlotDTO.getStartDateTime(), timeSlotDTO.getEndDateTime(), doctor);
		if (timeSlotDTO.getId() != null) {
			timeSlot.setId(timeSlotDTO.getId());
		}

		return timeSlot;
	}

	private static TimeSlotDTO mapToDTO(TimeSlot timeSlot) {
		return new TimeSlotDTO(timeSlot.getId(), timeSlot.getStartDateTime(), timeSlot.getEndDateTime());
	}

	private boolean isOwner(long doctorId, long timeSlotId) {
		return timeSlotRepository.findOne(timeSlotId).getDoctor().getId() == doctorId;
	}
}
