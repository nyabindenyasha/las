package com.anesuandtatenda.studentlecturerplatform.web;
import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.model.Account;
import com.anesuandtatenda.studentlecturerplatform.service.UserService;
import com.anesuandtatenda.studentlecturerplatform.web.requests.AccountRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public Page<Account> getAll(@PageableDefault(sort = "name") Pageable pageable,
                                @RequestParam(required = false) String search) {
        return userAccountService.findAll(pageable, search);
    }

    @GetMapping("/all")
    @ApiOperation("Get All UserAccounts")
    public Collection<Account> getAll() {
        return userAccountService.findAll();
    }

    @GetMapping("/{userAccountId}")
    @ApiOperation("Get a UserAccount by Id")
    public Account getUserAccount(@PathVariable long userAccountId) {
        return userAccountService.findById(userAccountId);
    }

    @DeleteMapping("/{userAccountId}")
    @ApiOperation("Delete a UserAccount by Id")
    public void delete(@PathVariable long userAccountId) {
        userAccountService.delete(userAccountId);
    }


    @PostMapping("")
    @ApiOperation("Create UserAccount")
    public Account create(@RequestBody AccountRequest request) {
        return userAccountService.create(request);
    }

    @PostMapping("/login/{username}/{password}")
    @ApiOperation("Login to the system")
    public Account login(@PathVariable String username,@PathVariable String password){
        return userAccountService.login(username,password);
    }

    @PutMapping("/{userAccountId}")
    @ApiOperation("Update userAccount")
    public Account update(@RequestBody Account request, @PathVariable long userAccountId) {
        if(request.getId() != userAccountId){
            throw new InvalidRequestException("You can not delete this record as it might be used by another record");
        }
        return userAccountService.update(request);
    }


    @GetMapping("/findByUsernameOrFirstname")
    @ApiOperation("Find By Username or Firstname")
    public void findByUsernameOrFirstname(@RequestParam(required = false)  String username, @RequestParam(required = false) String firstName){
        userAccountService.findByUsernameOrFirstname(username, firstName);
    }
}
