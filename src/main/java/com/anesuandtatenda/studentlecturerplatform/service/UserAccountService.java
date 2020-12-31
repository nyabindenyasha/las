package com.anesuandtatenda.studentlecturerplatform.service;


import com.anesuandtatenda.studentlecturerplatform.model.UserAccount;
import com.anesuandtatenda.studentlecturerplatform.web.requests.LoginRequest;
import com.anesuandtatenda.studentlecturerplatform.web.requests.UserAccountRequest;

import java.util.Collection;

public interface UserAccountService extends BaseService<UserAccount, UserAccountRequest, UserAccount> {

    UserAccount login(String username, String password);

    UserAccount login(LoginRequest request);

    boolean existByRegNumber(String regNumber);

    UserAccount findByFirstName(String firstName);

    String logout();

    Collection<UserAccount> findAllLecturers();

    UserAccount findByRegNumberOrFirstname(String username, String firstName);

    //You can think of other functionalities we need on accounts management. Good nyt man.
}
