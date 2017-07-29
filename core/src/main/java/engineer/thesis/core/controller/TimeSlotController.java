package engineer.thesis.core.controller;

import engineer.thesis.core.repository.TimeSlotRepository;
import engineer.thesis.core.service.ITimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class TimeSlotController {

    @Autowired
    private ITimeSlotService timeSlotService;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @RequestMapping(method = RequestMethod.GET, path = "/timeSlots/{doctorId}/{start}/{end}")
    public ResponseEntity<?> getDoctor(
//            @PathVariable(name = "startDate") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) Date startDate,
//            @PathVariable(name = "endDate") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) Date endDate) {
//on nie parsuje dat w standardzie ISO 8601 a Typescript-owe toISODate też nie daje poprawnego formatu :/
            @PathVariable(name = "doctorId") long doctorId,
            @PathVariable(name = "start") long start,
            @PathVariable(name = "end") long end) {
        return new ResponseEntity<>(timeSlotService.getInIntervalForDoctor(doctorId, new Date(start), new Date(end)), HttpStatus.OK);
    }
}
