package com.anesuandtatenda.studentlecturerplatform.service;

import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.model.UserAccount;
import com.anesuandtatenda.studentlecturerplatform.model.enums.Role;
import com.anesuandtatenda.studentlecturerplatform.repo.ProgramRepository;
import com.anesuandtatenda.studentlecturerplatform.repo.UserAccountRepository;
import com.anesuandtatenda.studentlecturerplatform.web.requests.LoginRequest;
import com.anesuandtatenda.studentlecturerplatform.web.requests.UserAccountRequest;
import lombok.val;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;


@Service
class UserAccountServiceImpl extends BaseServiceImpl<UserAccount, UserAccountRequest, UserAccount> implements UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final ProgramRepository programRepository;


    UserAccountServiceImpl(UserAccountRepository userAccountRepository, ProgramRepository programRepository) {
        super(userAccountRepository);
        this.userAccountRepository = userAccountRepository;
        this.programRepository = programRepository;
    }

    @Override
    protected Class<UserAccount> getEntityClass() {
        return UserAccount.class;
    }

    @Override
    public UserAccount create(UserAccountRequest request) {

        if (request.getRole() == Role.STUDENT && request.getYear() < 1)
            throw new InvalidRequestException("Year of study is required.");

        if (request.getRole() == Role.STUDENT && request.getProgramId() < 1)
            throw new InvalidRequestException("Program is of study is required.");

        UserAccount userAccount = UserAccount.fromCommand(request);

        boolean detailsExist = userAccountRepository.existsByRegNumber(request.getRegNumber());

        if (detailsExist) {
            throw new InvalidRequestException("UserAccount already exist.");
        }

        return userAccountRepository.save(userAccount);
    }


    @Override
    public UserAccount update(UserAccount request) {

        boolean detailsExists = userAccountRepository.existsByRegNumber(request.getRegNumber());

        if (!detailsExists) {
            throw new InvalidRequestException("UserAccount not found");
        }

        UserAccount userAccount = findById(request.getId());

        userAccount.update(request);

        return userAccountRepository.save(userAccount);
    }

    @Override
    public void delete(Long id) {
        try {
            super.delete(id);
        } catch (ConstraintViolationException var3) {
            throw new InvalidRequestException("You can not delete this record is might be used by another record");
        }
    }


    @Override
    public UserAccount login(String regNumber, String password) {
        UserAccount userAccount;
        if (!userAccountRepository.existsByRegNumber(regNumber)) {
            throw new InvalidRequestException("User does not exist");
        }
        userAccount = getAccountByName(regNumber);
        if (!userAccount.getPassword().equals(password)) {
            throw new InvalidRequestException("Wrong password");
        }
        return userAccount;
    }

    @Override
    public UserAccount login(LoginRequest request) {
        UserAccount userAccount;
        if (!userAccountRepository.existsByRegNumber(request.getRegNumber())) {
            throw new InvalidRequestException("User does not exist");
        }
        userAccount = getAccountByName(request.getRegNumber());
        if (!userAccount.getPassword().equals(request.getPassword())) {
            throw new InvalidRequestException("Wrong password");
        }
        return userAccount;
    }

    @Override
    public boolean existByRegNumber(String regNumber) {
        return userAccountRepository.existsByRegNumber(regNumber);
    }


    public UserAccount getAccountByName(String regNumber) {
        boolean exists = userAccountRepository.existsByRegNumber(regNumber);
        if (!exists) {
            throw new InvalidRequestException("User not registered");
        }
        return userAccountRepository.getByRegNumber(regNumber);
    }


    public UserAccount findByRegNumber(String regNumber) {
        return userAccountRepository.getByRegNumber(regNumber);
        //      .orElseThrow(() -> new InvalidRequestException("User record was not found for the supplied username"));
    }

    @Override
    public UserAccount findByFirstName(String firstName) {
        return userAccountRepository.findByFirstName(firstName).get();
        //     .orElseThrow(() -> new InvalidRequestException("User record was not found for the supplied firstName"));
    }

    @Override
    public UserAccount findByRegNumberOrFirstname(String username, String firstName) {

        boolean userByUsernameExists = userAccountRepository.existsByUsername(username);
        boolean userByFirstNameExists = userAccountRepository.existsByFirstName(firstName);

        if (userByUsernameExists) {
            val userByUsername = findByRegNumber(username);
            System.out.println(userByUsername);
            return userByUsername;
        } else if (userByFirstNameExists) {
            val userByFirstName = findByFirstName(firstName);
            System.out.println(userByFirstName);
            return userByFirstName;
        } else
            throw new InvalidRequestException("User record was not found for the supplied firstName or username");
    }

    @Override
    public String logout() {
        return null;
    }

    @Override
    public Collection<UserAccount> findAllLecturers() {
        Collection<UserAccount> userAccounts = userAccountRepository.findAll();
        Collection<UserAccount> lecturers = userAccounts.parallelStream().filter(account -> account.getRole() == Role.LECTURER).collect(Collectors.toList());
        return lecturers;
    }
}



