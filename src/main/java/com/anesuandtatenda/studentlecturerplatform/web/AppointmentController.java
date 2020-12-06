package com.anesuandtatenda.studentlecturerplatform.web;

import com.anesuandtatenda.studentlecturerplatform.local.ResponseMessage;
import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.local.requests.AppointmentRequest;
import com.anesuandtatenda.studentlecturerplatform.local.requests.AppointmentRequestMobile;
import com.anesuandtatenda.studentlecturerplatform.local.requests.ApproveAppointmentRequest;
import com.anesuandtatenda.studentlecturerplatform.model.Appointments;
import com.anesuandtatenda.studentlecturerplatform.service.AppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin
@RestController
@Api(tags = "Appointments")
@RequestMapping("v1/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("")
    @ApiOperation("Get Appointments")
    public ResponseEntity<?> getAll(@PageableDefault(sort = "name") Pageable pageable,
                                    @RequestParam(required = false) String search) {
        try {
            Page<Appointments> appointments = appointmentService.findAll(pageable, search);
            return new ResponseEntity<Page<Appointments>>(appointments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    @ApiOperation("Get All Appointments")
    public ResponseEntity<?> findAll() {
        try {
            Collection<Appointments> appointments = appointmentService.findAll();
            return new ResponseEntity<Collection<Appointments>>(appointments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{appointmentId}")
    @ApiOperation("Get a Appointment by Id")
    public ResponseEntity<?> getAppointment(@PathVariable long appointmentId) {
        try {
            Appointments appointment = appointmentService.findById(appointmentId);
            return new ResponseEntity<Appointments>(appointment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByLecturerId/{lecturerId}")
    @ApiOperation("Get a Appointments by LecturerId")
    public ResponseEntity<?> getAppointmentByLecturerId(@PathVariable long lecturerId) {
        try {
            Collection<Appointments> appointment = appointmentService.findByAppointmentWithId(lecturerId);
            return new ResponseEntity<Collection<Appointments>>(appointment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByStudentId/{studentId}")
    @ApiOperation("Get a Appointment by StudentId")
    public ResponseEntity<?> getAppointmentByStudentId(@PathVariable long studentId) {
        try {
            Collection<Appointments> appointment = appointmentService.findByAppointmentById(studentId);
            return new ResponseEntity<Collection<Appointments>>(appointment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{appointmentId}")
    @ApiOperation("Delete a Appointment by Id")
    public ResponseEntity<?> delete(@PathVariable long appointmentId) {
        try {
            appointmentService.delete(appointmentId);
            return new ResponseEntity<>(new ResponseMessage("success"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("")
    @ApiOperation("Create Appointment")
    public ResponseEntity<?> create(@RequestBody AppointmentRequest request) {
        try {
            Appointments appointmentCreated = appointmentService.create(request);
            return new ResponseEntity<Appointments>(appointmentCreated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/createFromMobile")
    @ApiOperation("Create Appointment")
    public ResponseEntity<?> createFromMobile(@RequestBody AppointmentRequestMobile request) {
        try {
            Appointments appointmentCreated = appointmentService.createFromMobile(request);
            return new ResponseEntity<Appointments>(appointmentCreated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/approve-appointment/{appointmentId}")
    @ApiOperation("Approve Appointment")
    public ResponseEntity<?> approveAppointment(@RequestBody ApproveAppointmentRequest request, @PathVariable long appointmentId) {
        try {
            if (request.getAppointmentId() != appointmentId) {
                throw new InvalidRequestException("Invalid Request");
            }
            Appointments appointmentApproved = appointmentService.approveBooking(request);
            return new ResponseEntity<Appointments>(appointmentApproved, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
