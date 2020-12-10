package com.anesuandtatenda.studentlecturerplatform.web;


import com.anesuandtatenda.studentlecturerplatform.local.ResponseMessage;
import com.anesuandtatenda.studentlecturerplatform.local.requests.TimeSlotLecturerRequest;
import com.anesuandtatenda.studentlecturerplatform.model.TimeSlotLecturer;
import com.anesuandtatenda.studentlecturerplatform.service.TimeSlotLecturerService;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;

@CrossOrigin
@RestController
@Api(tags = "TimeSlot-Lecturers Controller")
@RequestMapping("v1/timeslot-lecturer")
public class TimeSlotLecturerController {
    
    private final TimeSlotLecturerService timeSlotLecturerService;

    public TimeSlotLecturerController(TimeSlotLecturerService timeSlotLecturerService) {
        this.timeSlotLecturerService = timeSlotLecturerService;
    }

    @GetMapping("/all")
    @ApiOperation("Get All Lecturers Timeslots")
    public ResponseEntity<?> getAll() {
        try {
            Collection<TimeSlotLecturer> timeSlotLecturers = timeSlotLecturerService.findAll();
            return new ResponseEntity<Collection<TimeSlotLecturer>>(timeSlotLecturers, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("")
    @ApiOperation("Create Lecturer Timeslot")
    public ResponseEntity<?> create(@RequestBody TimeSlotLecturerRequest timeSlotLecturer) {
        try {
            TimeSlotLecturer timeSlotLecturerCreated = timeSlotLecturerService.create(timeSlotLecturer);
            return new ResponseEntity<TimeSlotLecturer>(timeSlotLecturerCreated, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findby-day-of-week/all")
    @ApiOperation("Get All Lecturers Timeslots by Day of Week")
    public ResponseEntity<?> getAllByDayOfWeek(long dayOfWeek) {
        try {
            Collection<TimeSlotLecturer> timeSlotLecturers = timeSlotLecturerService.findByDayOfWeek(dayOfWeek);
            return new ResponseEntity<Collection<TimeSlotLecturer>>(timeSlotLecturers, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/findby-lecturer-id/all")
    @ApiOperation("Get All Lecturers Timeslots by LecturerId")
    public ResponseEntity<?> getAllByLecturerId(long id) {
        try {
            Collection<TimeSlotLecturer> timeSlotLecturers = timeSlotLecturerService.findByAccountId(id);
            return new ResponseEntity<Collection<TimeSlotLecturer>>(timeSlotLecturers, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/findbylecturerid/dayofweek/all")
    @ApiOperation("Get All Lecturers Timeslots by Day Of Week and LecturerId")
    public ResponseEntity<?> getAllByDateAndLecturerId(long dayOfWeek, long id) {
        try {
            Collection<TimeSlotLecturer> timeSlotLecturers = timeSlotLecturerService.findByDayOfWeekAndAccountId(dayOfWeek, id);
            return new ResponseEntity<Collection<TimeSlotLecturer>>(timeSlotLecturers, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findbylecturerid/date/all")
    @ApiOperation("Get All Lecturers Timeslots by Day Of Week and LecturerId")
    public ResponseEntity<?> getAllByDateAndLecturerId(String date, long id) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            long dayOfWeek = localDate.getDayOfWeek().getValue();
            Collection<TimeSlotLecturer> timeSlotLecturers = timeSlotLecturerService.findByDayOfWeekAndAccountId(dayOfWeek, id);
            return new ResponseEntity<Collection<TimeSlotLecturer>>(timeSlotLecturers, HttpStatus.OK);
        }
              catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
