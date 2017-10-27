package engineer.thesis.core.controller;

import engineer.thesis.core.model.dto.TimeSlotDTO;
import engineer.thesis.core.service.Interface.ITimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.NoSuchElementException;

@RestController
public class TimeSlotController {

    @Autowired
    private ITimeSlotService timeSlotService;


    @RequestMapping(method = RequestMethod.GET, path = "/timeSlot/{id}")
    public ResponseEntity<?> getById(
            @PathVariable(name = "id") long id) {
        try {
            return new ResponseEntity<>(timeSlotService.getById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


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


    @RequestMapping(method = RequestMethod.PUT, path = "/timeSlotMove/{timeSlotId}/{doctorId}/{start}/{end}")
    public ResponseEntity<?> moveTimeSlot(
            @PathVariable(name = "timeSlotId") long timeSlotId,
            @PathVariable(name = "doctorId") long doctorId,
            @PathVariable(name = "start") long start,
            @PathVariable(name = "end") long end) {
        try {
            TimeSlotDTO timeSlot = timeSlotService.moveTimeSlot(timeSlotId, doctorId, new Date(start), new Date(end));
            return new ResponseEntity<>(timeSlot, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
