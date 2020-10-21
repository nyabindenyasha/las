package com.anesuandtatenda.studentlecturerplatform.web;
import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.local.requests.AppointmentRequest;
import com.anesuandtatenda.studentlecturerplatform.local.requests.ApproveAppointmentRequest;
import com.anesuandtatenda.studentlecturerplatform.model.Appointments;
import com.anesuandtatenda.studentlecturerplatform.service.AppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
    public Page<Appointments> getAll(@PageableDefault(sort = "name") Pageable pageable,
                                     @RequestParam(required = false) String search) {
        return appointmentService.findAll(pageable, search);
    }

    @GetMapping("/all")
    @ApiOperation("Get All Appointments")
    public Collection<Appointments> getAll() {
        return appointmentService.findAll();
    }

    @GetMapping("/{appointmentId}")
    @ApiOperation("Get a Appointment by Id")
    public Appointments getAppointment(@PathVariable long appointmentId) {
        return appointmentService.findById(appointmentId);
    }

    @DeleteMapping("/{appointmentId}")
    @ApiOperation("Delete a Appointment by Id")
    public void delete(@PathVariable long appointmentId) {
        appointmentService.delete(appointmentId);
    }


    @PostMapping("")
    @ApiOperation("Create Appointment")
    public ResponseEntity<Appointments> create(@RequestBody AppointmentRequest request) {
            return ResponseEntity.ok().body(appointmentService.create(request));
    }


    @PutMapping("/approve-appointment/{appointmentId}")
    @ApiOperation("Approve Appointment")
    public Appointments approveAppointment(@RequestBody ApproveAppointmentRequest request, @PathVariable long appointmentId) {
        if(request.getAppointmentId() != appointmentId){
            throw new InvalidRequestException("Invalid Request");
        }
        return appointmentService.approveBooking(request);
    }
}
