package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.TimeSlotDTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface ITimeSlotService {

    TimeSlotDTO getById(long id);

    TimeSlotDTO delete(long id) throws NoSuchElementExistsException;

    TimeSlotDTO saveTimeSlot(TimeSlotDTO timeSlotDTO, long doctorId) throws IllegalArgumentException;

    List<TimeSlotDTO> getInIntervalForDoctor(long doctorId, Date startDate, Date endDate) throws IllegalArgumentException;

    TimeSlotDTO moveTimeSlot(long timeSlotId, long doctorId, Date startDate, Date endDate);
}
