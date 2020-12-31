package com.anesuandtatenda.studentlecturerplatform.web;

import com.anesuandtatenda.studentlecturerplatform.local.ResponseMessage;
import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.model.UserAccount;
import com.anesuandtatenda.studentlecturerplatform.service.UserAccountService;
import com.anesuandtatenda.studentlecturerplatform.web.requests.LoginRequest;
import com.anesuandtatenda.studentlecturerplatform.web.requests.UserAccountRequest;
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
@Api(tags = "UserAccounts")
@RequestMapping("v1/userAccounts")
public class UserAccountController {

    private final UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @GetMapping("")
    @ApiOperation("Get UserAccounts")
    public ResponseEntity<?> getAll(@PageableDefault(sort = "name") Pageable pageable,
                                @RequestParam(required = false) String search) {
        try {
            Page<UserAccount> accounts = userAccountService.findAll(pageable, search);
            return new ResponseEntity<Page<UserAccount>>(accounts, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    @ApiOperation("Get All UserAccounts")
    public ResponseEntity<?> getAll() {
        try {
            Collection<UserAccount> userAccounts = userAccountService.findAll();
            return new ResponseEntity<Collection<UserAccount>>(userAccounts, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getLecturers/all")
    @ApiOperation("Get All UserAccounts")
    public ResponseEntity<?> getLecturers() {
        try {
            Collection<UserAccount> userAccounts = userAccountService.findAllLecturers();
            return new ResponseEntity<Collection<UserAccount>>(userAccounts, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userAccountId}")
    @ApiOperation("Get a UserAccount by Id")
    public ResponseEntity<?> getUserAccount(@PathVariable long userAccountId) {
        try {
            UserAccount userAccount = userAccountService.findById(userAccountId);
            return new ResponseEntity<UserAccount>(userAccount, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{userAccountId}")
    @ApiOperation("Delete a UserAccount by Id")
    public ResponseEntity<?> delete(@PathVariable long userAccountId) {
         try {
             userAccountService.delete(userAccountId);
             return new ResponseEntity<>(new ResponseMessage("success"), HttpStatus.OK);
         }
         catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("")
    @ApiOperation("Create UserAccount")
    public ResponseEntity<?> create(@RequestBody UserAccountRequest request) {
        try {
            UserAccount userAccountCreated = userAccountService.create(request);
            return new ResponseEntity<UserAccount>(userAccountCreated, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login/{regNumber}/{password}")
    @ApiOperation("Login to the system")
    public ResponseEntity<?> login(@PathVariable String regNumber,@PathVariable String password){
        try {
            UserAccount userLoggedIn = userAccountService.login(regNumber, password);
            return new ResponseEntity<UserAccount>(userLoggedIn, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/v2/login")
    @ApiOperation("Login to the system")
    public ResponseEntity<?> loginv2(@RequestParam String regNumber,@RequestParam String password){
        try {
            UserAccount userLoggedIn = userAccountService.login(regNumber, password);
            return new ResponseEntity<UserAccount>(userLoggedIn, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/v3/login")
    @ApiOperation("Login to the system")
    public ResponseEntity<?> loginv3(@RequestBody LoginRequest loginRequest){
        try {
            UserAccount userLoggedIn = userAccountService.login(loginRequest);
            return new ResponseEntity<UserAccount>(userLoggedIn, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userAccountId}")
    @ApiOperation("Update userAccount")
    public ResponseEntity<?> update(@RequestBody UserAccount request, @PathVariable long userAccountId) {
        try {
            if(request.getId() != userAccountId){
                throw new InvalidRequestException("You can not delete this record as it might be used by another record");
            }
            UserAccount userAccountUpdated = userAccountService.update(request);
            return new ResponseEntity<UserAccount>(userAccountUpdated, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/findByUsernameOrFirstname")
    @ApiOperation("Find By Username or Firstname")
    public ResponseEntity<?> findByUsernameOrFirstname(@RequestParam(required = false)  String username, @RequestParam(required = false) String firstName){
        try {
            UserAccount user = userAccountService.findByRegNumberOrFirstname(username, firstName);
            return new ResponseEntity<UserAccount>(user, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
