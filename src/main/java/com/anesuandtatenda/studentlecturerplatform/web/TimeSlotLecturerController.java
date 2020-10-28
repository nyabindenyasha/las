package com.anesuandtatenda.studentlecturerplatform.web;


import com.anesuandtatenda.studentlecturerplatform.local.requests.TimeSlotLecturerRequest;
import com.anesuandtatenda.studentlecturerplatform.model.LecturersBookings;
import com.anesuandtatenda.studentlecturerplatform.model.TimeSlotLecturer;
import com.anesuandtatenda.studentlecturerplatform.service.TimeSlotLecturerService;
import com.anesuandtatenda.studentlecturerplatform.service.TimeSlotLecturerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

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
    public Collection<TimeSlotLecturer> getAll() {
        return timeSlotLecturerService.findAll();
    }

    @PostMapping("")
    @ApiOperation("Create Lecturer Timeslot")
    public TimeSlotLecturer create(@RequestBody TimeSlotLecturerRequest timeSlotLecturer) {
        return timeSlotLecturerService.create(timeSlotLecturer);
    }

    @GetMapping("/findby-day-of-week/all")
    @ApiOperation("Get All Lecturers Timeslots by Day of Week")
    public List<TimeSlotLecturer> getAllByDayOfWeek(int dayOfWeek) {
        return timeSlotLecturerService.findByDayOfWeek(dayOfWeek);}


    @GetMapping("/findby-lecturer-id/all")
    @ApiOperation("Get All Lecturers Timeslots by LecturerId")
    public List<TimeSlotLecturer> getAllByLecturerId(long id) {
        return timeSlotLecturerService.findByAccountId(id);}


    @GetMapping("/findbylecturerid/dayofweek/all")
    @ApiOperation("Get All Lecturers Timeslots by Day Of Week and LecturerId")
    public List<TimeSlotLecturer> getAllByDateAndLecturerId(int dayOfWeek, long id) {
        return timeSlotLecturerService.findByDayOfWeekAndAccountId(dayOfWeek, id);}

    @GetMapping("/findbylecturerid/date/all")
    @ApiOperation("Get All Lecturers Timeslots by Day Of Week and LecturerId")
    public List<TimeSlotLecturer> getAllByDateAndLecturerId(String date, long id) {
        LocalDate localDate = LocalDate.parse(date);
        int dayOfWeek = localDate.getDayOfWeek().getValue();
        System.out.println(dayOfWeek);
        return timeSlotLecturerService.findByDayOfWeekAndAccountId(dayOfWeek, id);}

}
