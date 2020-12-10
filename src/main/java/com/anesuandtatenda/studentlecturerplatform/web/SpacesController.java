package com.anesuandtatenda.studentlecturerplatform.web;

import com.anesuandtatenda.studentlecturerplatform.local.ResponseMessage;
import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.model.Spaces;
import com.anesuandtatenda.studentlecturerplatform.service.SpacesService;
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
@Api(tags = "Spaces")
@RequestMapping("v1/spaces")
public class SpacesController {

    private final SpacesService spacesService;

    public SpacesController(SpacesService spacesService) {
        this.spacesService = spacesService;
    }

    @GetMapping("")
    @ApiOperation("Get Spaces")
    public ResponseEntity<?> getAll(@PageableDefault(sort = "name") Pageable pageable,
                                    @RequestParam(required = false) String search) {
        try {
            Page<Spaces> facaulties = spacesService.findAll(pageable, search);
            return new ResponseEntity<Page<Spaces>>(facaulties, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    @ApiOperation("Get All Spaces")
    public ResponseEntity<?> getAll() {
        try {
            Collection<Spaces> facaulties = spacesService.findAll();
            return new ResponseEntity<Collection<Spaces>>(facaulties, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByLecturerId/{lecturerId}/all")
    @ApiOperation("Get All Spaces")
    public ResponseEntity<?> findByLecturerId(@PathVariable long lecturerId) {
        try {
            Collection<Spaces> facaulties = spacesService.findByLecturerId(lecturerId);
            return new ResponseEntity<Collection<Spaces>>(facaulties, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/evaluateByLecturerId/{lecturerId}/{dayOfWeek}")
    @ApiOperation("Get All Spaces")
    public ResponseEntity<?> evaluateByLecturerId(@PathVariable long lecturerId, int dayOfWeek) {
        try {
            Collection<Spaces> facaulties = spacesService.evaluateByLecturerId(lecturerId, dayOfWeek);
            return new ResponseEntity<Collection<Spaces>>(facaulties, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{spacesId}")
    @ApiOperation("Get a Spaces by Id")
    public ResponseEntity<?> getSpaces(@PathVariable long spacesId) {
        try {
            Spaces spaces = spacesService.findById(spacesId);
            return new ResponseEntity<Spaces>(spaces, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{spacesId}")
    @ApiOperation("Delete a Spaces by Id")
    public ResponseEntity<?> delete(@PathVariable long spacesId) {
        try {
            spacesService.delete(spacesId);
            return new ResponseEntity<>(new ResponseMessage("success"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("")
    @ApiOperation("Create Spaces")
    public ResponseEntity<?> create(@RequestBody Spaces request) {
        try {
            Spaces spacesCreated = spacesService.create(request);
            return new ResponseEntity<Spaces>(spacesCreated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{spacesId}")
    @ApiOperation("Update Spaces")
    public ResponseEntity<?> update(@RequestBody Spaces request, @PathVariable long spacesId) {
        try {
            if (request.getId() != spacesId) {
                throw new InvalidRequestException("Spaces with specified Id not found");
            }
            Spaces spacesUpdated = spacesService.update(request);
            return new ResponseEntity<Spaces>(spacesUpdated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
