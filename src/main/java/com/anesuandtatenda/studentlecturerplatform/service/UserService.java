package com.anesuandtatenda.studentlecturerplatform.service;


import com.anesuandtatenda.studentlecturerplatform.model.Account;
import com.anesuandtatenda.studentlecturerplatform.web.requests.AccountRequest;
import com.anesuandtatenda.studentlecturerplatform.web.requests.LoginRequest;

public interface UserService extends BaseService<Account, AccountRequest, Account> {

    Account login(String username, String pasword);

    Account updatePassword(LoginRequest request);

    Account getAccountByName(String username);

    Account findByUsername(String username);

    Account findByFirstName(String firstName);

    void findByUsernameOrFirstname(String username, String firstName);

    String logout();

    //You can think of other functionalities we need on accounts management. Good nyt man.
}
