package com.anesuandtatenda.studentlecturerplatform.web;
import com.anesuandtatenda.studentlecturerplatform.local.ResponseMessage;
import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.model.Account;
import com.anesuandtatenda.studentlecturerplatform.service.UserService;
import com.anesuandtatenda.studentlecturerplatform.web.requests.AccountRequest;
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

    private final UserService userAccountService;

    public UserAccountController(UserService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @GetMapping("")
    @ApiOperation("Get UserAccounts")
    public ResponseEntity<?> getAll(@PageableDefault(sort = "name") Pageable pageable,
                                @RequestParam(required = false) String search) {
        try {
            Page<Account> accounts = userAccountService.findAll(pageable, search);
            return new ResponseEntity<Page<Account>>(accounts, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    @ApiOperation("Get All UserAccounts")
    public ResponseEntity<?> getAll() {
        try {
            Collection<Account> accounts = userAccountService.findAll();
            return new ResponseEntity<Collection<Account>>(accounts, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getLecturers/all")
    @ApiOperation("Get All UserAccounts")
    public ResponseEntity<?> getLecturers() {
        try {
            Collection<Account> accounts = userAccountService.findAllLecturers();
            return new ResponseEntity<Collection<Account>>(accounts, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userAccountId}")
    @ApiOperation("Get a UserAccount by Id")
    public ResponseEntity<?> getUserAccount(@PathVariable long userAccountId) {
        try {
            Account account = userAccountService.findById(userAccountId);
            return new ResponseEntity<Account>(account, HttpStatus.OK);
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
    public ResponseEntity<?> create(@RequestBody AccountRequest request) {
        try {
            Account userAccountCreated = userAccountService.create(request);
            return new ResponseEntity<Account>(userAccountCreated, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login/{regNumber}/{password}")
    @ApiOperation("Login to the system")
    public ResponseEntity<?> login(@PathVariable String regNumber,@PathVariable String password){
        try {
            Account userLoggedIn = userAccountService.login(regNumber,password);
            return new ResponseEntity<Account>(userLoggedIn, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/v2/login")
    @ApiOperation("Login to the system")
    public ResponseEntity<?> loginv2(@RequestParam String regNumber,@RequestParam String password){
        try {
            Account userLoggedIn = userAccountService.login(regNumber,password);
            return new ResponseEntity<Account>(userLoggedIn, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userAccountId}")
    @ApiOperation("Update userAccount")
    public ResponseEntity<?> update(@RequestBody Account request, @PathVariable long userAccountId) {
        try {
            if(request.getId() != userAccountId){
                throw new InvalidRequestException("You can not delete this record as it might be used by another record");
            }
            Account userAccountUpdated = userAccountService.update(request);
            return new ResponseEntity<Account>(userAccountUpdated, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/findByUsernameOrFirstname")
    @ApiOperation("Find By Username or Firstname")
    public ResponseEntity<?> findByUsernameOrFirstname(@RequestParam(required = false)  String username, @RequestParam(required = false) String firstName){
        try {
            Account user = userAccountService.findByUsernameOrFirstname(username, firstName);
            return new ResponseEntity<Account>(user, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
