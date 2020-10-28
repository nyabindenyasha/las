package com.anesuandtatenda.studentlecturerplatform.web;

import com.anesuandtatenda.studentlecturerplatform.model.Facaulty;
import com.anesuandtatenda.studentlecturerplatform.model.LecturersBookings;
import com.anesuandtatenda.studentlecturerplatform.service.FacaultyService;
import com.anesuandtatenda.studentlecturerplatform.service.LecturersBookingsService;
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
@Api(tags = "LecturersBookings")
@RequestMapping("v1/lecturers-bookings")
public class LecturersBookingsController {

    private final LecturersBookingsService lecturersBookingsService;

    public LecturersBookingsController(LecturersBookingsService lecturersBookingsService) {
        this.lecturersBookingsService = lecturersBookingsService;
    }

    @GetMapping("")
    @ApiOperation("Get LecturersBookings")
    public Page<LecturersBookings> getAll(@PageableDefault(sort = "name") Pageable pageable,
                                 @RequestParam(required = false) String search) {
        return lecturersBookingsService.findAll(pageable, search);
    }

    @GetMapping("/all")
    @ApiOperation("Get All LecturersBookings")
    public Collection<LecturersBookings> getAll() {
        return lecturersBookingsService.findAll();
    }

    @PostMapping("")
    @ApiOperation("Create LecturersBooking")
    public LecturersBookings create(@RequestBody LecturersBookings request) {
        return lecturersBookingsService.create(request);
    }

    @GetMapping("/findbydate/all")
    @ApiOperation("Get All LecturersBookings by Date")
    public List<LecturersBookings> getAllByDate(String date) {
        return lecturersBookingsService.findByDate(LocalDate.parse(date));}


    @GetMapping("/findbylecturerid/all")
    @ApiOperation("Get All LecturersBookings by LecturerId")
    public List<LecturersBookings> getAllByDate(long id) {
        return lecturersBookingsService.findBylecturerId(id);}


    @GetMapping("/findbylecturerid/date/all")
    @ApiOperation("Get All LecturersBookings by Date and LecturerId")
    public List<LecturersBookings> getAllByDateAndLecturerId(String date, long id) {
        return lecturersBookingsService.findByDateAndLecturerId(LocalDate.parse(date), id);}
}
