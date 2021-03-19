package com.anesuandtatenda.studentlecturerplatform.web;
import com.anesuandtatenda.studentlecturerplatform.local.ResponseMessage;
import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.local.requests.DepartmentUpdateRequest;
import com.anesuandtatenda.studentlecturerplatform.model.Department;
import com.anesuandtatenda.studentlecturerplatform.service.DepartmentService;
import com.anesuandtatenda.studentlecturerplatform.web.requests.DepartmentRequest;
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
@Api(tags = "Departments")
@RequestMapping("v1/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("")
    @ApiOperation("Get Departments")
    public ResponseEntity<?> getAll(@PageableDefault(sort = "name") Pageable pageable,
                                    @RequestParam(required = false) String search) {
            try {
                Page<Department> departments = departmentService.findAll(pageable, search);
                return new ResponseEntity<Page<Department>>(departments, HttpStatus.OK);
            }
          catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    @ApiOperation("Get All Departments")
    public ResponseEntity<?> getAll() {
        try {
            Collection<Department> departments = departmentService.findAll();
            return new ResponseEntity<Collection<Department>>(departments, HttpStatus.OK);
        }
          catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{departmentId}")
    @ApiOperation("Get a Department by Id")
    public ResponseEntity<?> getDepartment(@PathVariable long departmentId) {
         try {
             Department department = departmentService.findById(departmentId);
             return new ResponseEntity<Department>(department, HttpStatus.OK);
         }
          catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{departmentId}")
    @ApiOperation("Delete a Department by Id")
    public ResponseEntity<?> delete(@PathVariable long departmentId) {
         try {
             departmentService.delete(departmentId);
             return new ResponseEntity<>(new ResponseMessage("success"), HttpStatus.OK);
         }
          catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("")
    @ApiOperation("Create Department")
    public ResponseEntity<?> create(@RequestBody DepartmentRequest request) {
        try {
            Department departmentCreated = departmentService.create(request);
            return new ResponseEntity<Department>(departmentCreated, HttpStatus.OK);
        }
          catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{departmentId}")
    @ApiOperation("Update Department")
    public ResponseEntity<?> update(@RequestBody DepartmentUpdateRequest request, @PathVariable long departmentId) {
        try {
            if(request.getId() != departmentId){
                throw new InvalidRequestException("Department with specified Id not found");
            }
            Department departmentUpdated = departmentService.update(request);
            return new ResponseEntity<Department>(departmentUpdated, HttpStatus.OK);
        }
          catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
