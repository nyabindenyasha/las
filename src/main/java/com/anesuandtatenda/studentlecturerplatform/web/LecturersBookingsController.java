package com.anesuandtatenda.studentlecturerplatform.web;

import com.anesuandtatenda.studentlecturerplatform.local.ResponseMessage;
import com.anesuandtatenda.studentlecturerplatform.model.LecturersBookings;
import com.anesuandtatenda.studentlecturerplatform.service.LecturersBookingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;

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
    public ResponseEntity<?> getAll(@PageableDefault(sort = "name") Pageable pageable,
                                    @RequestParam(required = false) String search) {
        try {
            Page<LecturersBookings> lecturersBookings = lecturersBookingsService.findAll(pageable, search);
            return new ResponseEntity<Page<LecturersBookings>>(lecturersBookings, HttpStatus.OK);
        }
          catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    @ApiOperation("Get All LecturersBookings")
    public ResponseEntity<?> getAll() {
         try {
             Collection<LecturersBookings> lecturersBookings = lecturersBookingsService.findAll();
             return new ResponseEntity<Collection<LecturersBookings>>(lecturersBookings, HttpStatus.OK);
         }
          catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("")
    @ApiOperation("Create LecturersBooking")
    public ResponseEntity<?> create(@RequestBody LecturersBookings request) {
         try {
             LecturersBookings lecturersBookingCreated = lecturersBookingsService.create(request);
             return new ResponseEntity<LecturersBookings>(lecturersBookingCreated, HttpStatus.OK);
         }
          catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findbydate/all")
    @ApiOperation("Get All LecturersBookings by Date")
    public ResponseEntity<?> getAllByDate(String date) {
        try {
            Collection<LecturersBookings> lecturersBookings = lecturersBookingsService.findByDate(LocalDate.parse(date));
            return new ResponseEntity<Collection<LecturersBookings>>(lecturersBookings, HttpStatus.OK);
        }
          catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/findbylecturerid/all")
    @ApiOperation("Get All LecturersBookings by LecturerId")
    public ResponseEntity<?> getAllByDate(long id) {
        try {
            Collection<LecturersBookings> lecturersBookings = lecturersBookingsService.findBylecturerId(id);
            return new ResponseEntity<Collection<LecturersBookings>>(lecturersBookings, HttpStatus.OK);
        }
          catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/findbylecturerid/date/all")
    @ApiOperation("Get All LecturersBookings by Date and LecturerId")
    public ResponseEntity<?> getAllByDateAndLecturerId(String date, long id) {
        try {
            Collection<LecturersBookings> lecturersBookings = lecturersBookingsService.findByDateAndLecturerId(LocalDate.parse(date), id);
            return new ResponseEntity<Collection<LecturersBookings>>(lecturersBookings, HttpStatus.OK);
        }
          catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
