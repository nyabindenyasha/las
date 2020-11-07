package com.anesuandtatenda.studentlecturerplatform.web;

import com.anesuandtatenda.studentlecturerplatform.local.ResponseMessage;
import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.local.requests.ProgramCreateRequest;
import com.anesuandtatenda.studentlecturerplatform.local.requests.ProgramUpdateRequest;
import com.anesuandtatenda.studentlecturerplatform.model.Programs;
import com.anesuandtatenda.studentlecturerplatform.service.ProgramService;
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
@Api(tags = "Programs")
@RequestMapping("v1/programs")
public class ProgramController {

    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping("")
    @ApiOperation("Get Programs")
    public ResponseEntity<?> getAll(@PageableDefault(sort = "name") Pageable pageable,
                                    @RequestParam(required = false) String search) {
        try {
            Page<Programs> programs = programService.findAll(pageable, search);
            return new ResponseEntity<Page<Programs>>(programs,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    @ApiOperation("Get All Programs")
    public ResponseEntity<?> getAll() {
        try {
            Collection<Programs> programs = programService.findAll();
            return new ResponseEntity<Collection<Programs>>(programs, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{programId}")
    @ApiOperation("Get a Program by Id")
    public ResponseEntity<?> getProgram(@PathVariable long programId) {
        try {
            Programs program = programService.findById(programId);
            return new ResponseEntity<Programs>(program, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{programId}")
    @ApiOperation("Delete a Program by Id")
    public ResponseEntity<?> delete(@PathVariable long programId) {
        try {
            programService.delete(programId);
            return new ResponseEntity<>(new ResponseMessage("success"), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("")
    @ApiOperation("Create Program")
    public ResponseEntity<?> create(@RequestBody ProgramCreateRequest request) {
        try {
            Programs programCreated = programService.create(request);
            return new ResponseEntity<Programs>(programCreated, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{programId}")
    @ApiOperation("Update Program")
    public ResponseEntity<?> update(@RequestBody ProgramUpdateRequest request, @PathVariable long programId) {
        try {
            if(request.getId() != programId){
                throw new InvalidRequestException("You can not delete this record as it might be used by another record");
            }
            Programs programUpdated = programService.update(request);
            return new ResponseEntity<Programs>(programUpdated, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
