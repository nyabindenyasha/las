package com.anesuandtatenda.studentlecturerplatform.web;

import com.anesuandtatenda.studentlecturerplatform.local.ResponseMessage;
import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.model.Facaulty;
import com.anesuandtatenda.studentlecturerplatform.service.FacaultyService;
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
@Api(tags = "Facaulty")
@RequestMapping("v1/facaulty")
public class FacaultyController {

    private final FacaultyService facaultyService;

    public FacaultyController(FacaultyService facaultyService) {
        this.facaultyService = facaultyService;
    }

    @GetMapping("")
    @ApiOperation("Get Facaulty")
    public ResponseEntity<?> getAll(@PageableDefault(sort = "name") Pageable pageable,
                                    @RequestParam(required = false) String search) {
        try {
            Page<Facaulty> facaulties = facaultyService.findAll(pageable, search);
            return new ResponseEntity<Page<Facaulty>>(facaulties, HttpStatus.OK);
        }
         catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    @ApiOperation("Get All Facaulty")
    public ResponseEntity<?> getAll() {
        try {
            Collection<Facaulty> facaulties = facaultyService.findAll();
            return new ResponseEntity<Collection<Facaulty>>(facaulties,HttpStatus.OK);
        }
         catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{facaultyId}")
    @ApiOperation("Get a Facaulty by Id")
    public ResponseEntity<?> getFacaulty(@PathVariable long facaultyId) {
        try {
            Facaulty facaulty = facaultyService.findById(facaultyId);
            return new ResponseEntity<Facaulty>(facaulty, HttpStatus.OK);
        }
         catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{facaultyId}")
    @ApiOperation("Delete a Facaulty by Id")
    public ResponseEntity<?> delete(@PathVariable long facaultyId) {
        try {
            facaultyService.delete(facaultyId);
            return new ResponseEntity<>(new ResponseMessage("success"), HttpStatus.OK);
        }
         catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("")
    @ApiOperation("Create Facaulty")
    public ResponseEntity<?> create(@RequestBody Facaulty request) {
        try {
            Facaulty facaultyCreated = facaultyService.create(request);
            return new ResponseEntity<Facaulty>(facaultyCreated, HttpStatus.OK);
        }
         catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{facaultyId}")
    @ApiOperation("Update Facaulty")
    public ResponseEntity<?> update(@RequestBody Facaulty request, @PathVariable long facaultyId) {
        try {
            if(request.getId() != facaultyId){
                throw new InvalidRequestException("You can not delete this record as it might be used by another record");
            }
            Facaulty facaultyUpdated = facaultyService.update(request);
            return new ResponseEntity<Facaulty>(facaultyUpdated, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
