package com.anesuandtatenda.studentlecturerplatform.service;


import com.anesuandtatenda.studentlecturerplatform.model.Account;
import com.anesuandtatenda.studentlecturerplatform.web.requests.AccountRequest;
import com.anesuandtatenda.studentlecturerplatform.web.requests.LoginRequest;

import java.util.Collection;

public interface UserService extends BaseService<Account, AccountRequest, Account> {

    Account login(String username, String pasword);

    Account updatePassword(LoginRequest request);

    Account getAccountByName(String username);

    Account findByUsername(String username);

    Account findByFirstName(String firstName);

    Account findByUsernameOrFirstname(String username, String firstName);

    String logout();

    Collection<Account> findAllLecturers();

    //You can think of other functionalities we need on accounts management. Good nyt man.
}
