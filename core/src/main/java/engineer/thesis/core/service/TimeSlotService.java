package engineer.thesis.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import engineer.thesis.core.model.Doctor;
import engineer.thesis.core.model.TimeSlot;
import engineer.thesis.core.model.dto.TimeSlotDTO;
import engineer.thesis.core.repository.DoctorRepository;
import engineer.thesis.core.repository.TimeSlotRepository;

@Service
public class TimeSlotService implements ITimeSlotService {

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
