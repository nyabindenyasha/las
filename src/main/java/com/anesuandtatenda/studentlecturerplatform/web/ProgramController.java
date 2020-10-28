package com.anesuandtatenda.studentlecturerplatform.web;

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
    public Page<Programs> getAll(@PageableDefault(sort = "name") Pageable pageable,
                                 @RequestParam(required = false) String search) {
        return programService.findAll(pageable, search);
    }

    @GetMapping("/all")
    @ApiOperation("Get All Programs")
    public Collection<Programs> getAll() {
        return programService.findAll();
    }

    @GetMapping("/{programId}")
    @ApiOperation("Get a Program by Id")
    public Programs getProgram(@PathVariable long programId) {
        return programService.findById(programId);
    }

    @DeleteMapping("/{programId}")
    @ApiOperation("Delete a Program by Id")
    public void delete(@PathVariable long programId) {
        programService.delete(programId);
    }


    @PostMapping("")
    @ApiOperation("Create Program")
    public Programs create(@RequestBody ProgramCreateRequest request) {
        return programService.create(request);
    }

    @PutMapping("/{programId}")
    @ApiOperation("Update Program")
    public Programs update(@RequestBody ProgramUpdateRequest request, @PathVariable long programId) {
        if(request.getId() != programId){
            throw new InvalidRequestException("You can not delete this record as it might be used by another record");
        }
        return programService.update(request);
    }
}
