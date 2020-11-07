package com.anesuandtatenda.studentlecturerplatform.web;

import com.anesuandtatenda.studentlecturerplatform.local.ResponseMessage;
import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.local.requests.TimeSlotsRequest;
import com.anesuandtatenda.studentlecturerplatform.model.TimeSlots;
import com.anesuandtatenda.studentlecturerplatform.service.TimeSlotService;
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
@Api(tags = "TimeSlots")
@RequestMapping("v1/timeSlots")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @GetMapping("")
    @ApiOperation("Get TimeSlots")
    public ResponseEntity<?> getAll(@PageableDefault(sort = "name") Pageable pageable,
                                    @RequestParam(required = false) String search) {
        try {
            Page<TimeSlots> timeSlots = timeSlotService.findAll(pageable, search);
            return new ResponseEntity<Page<TimeSlots>>(timeSlots, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    @ApiOperation("Get All TimeSlots")
    public ResponseEntity<?> getAll() {
        try {
            Collection<TimeSlots> timeSlots = timeSlotService.findAll();
            return new ResponseEntity<Collection<TimeSlots>>(timeSlots, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{timeSlotId}")
    @ApiOperation("Get a TimeSlot by Id")
    public ResponseEntity<?> getTimeSlot(@PathVariable long timeSlotId) {
        try {
            TimeSlots timeSlot = timeSlotService.findById(timeSlotId);
            return new ResponseEntity<TimeSlots>(timeSlot, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{timeSlotId}")
    @ApiOperation("Delete a TimeSlot by Id")
    public ResponseEntity<?> delete(@PathVariable long timeSlotId) {
        try {
            timeSlotService.delete(timeSlotId);
            return new ResponseEntity<>(new ResponseMessage("success"), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("")
    @ApiOperation("Create TimeSlot")
    public ResponseEntity<?> create(@RequestBody TimeSlotsRequest request) {
        try {
            TimeSlots timeSlotCreated = timeSlotService.create(request);
            return new ResponseEntity<TimeSlots>(timeSlotCreated, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{timeSlotId}")
    @ApiOperation("Update TimeSlot")
    public ResponseEntity<?> update(@RequestBody TimeSlotsRequest request, @PathVariable long timeSlotId) {
        try {
            if(request.getId() != timeSlotId){
                throw new InvalidRequestException("You can not delete this record as it might be used by another record");
            }
            TimeSlots timeSlotUpdated = timeSlotService.update(request);
            return new ResponseEntity<TimeSlots>(timeSlotUpdated, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
