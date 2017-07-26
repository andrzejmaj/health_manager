package engineer.thesis.core.service;

import org.springframework.stereotype.Service;

import engineer.thesis.core.model.dto.TimeSlotDTO;

@Service
public interface ITimeSlotService {

	TimeSlotDTO saveTimeSlot(TimeSlotDTO timeSlotDTO, long doctorId) throws IllegalArgumentException;
}
