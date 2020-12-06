package com.anesuandtatenda.studentlecturerplatform.service;

import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.model.Account;
import com.anesuandtatenda.studentlecturerplatform.model.Programs;
import com.anesuandtatenda.studentlecturerplatform.model.enums.Role;
import com.anesuandtatenda.studentlecturerplatform.repo.ProgramRepository;
import com.anesuandtatenda.studentlecturerplatform.repo.UserAccountRepository;
import com.anesuandtatenda.studentlecturerplatform.web.requests.AccountRequest;
import com.anesuandtatenda.studentlecturerplatform.web.requests.LoginRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

import lombok.val;


@Service
class UserAccountServiceImpl extends BaseServiceImpl<Account, AccountRequest, Account> implements UserService {

    private final UserAccountRepository userAccountRepository;
    private final ProgramRepository programRepository;


    UserAccountServiceImpl(UserAccountRepository userAccountRepository, ProgramRepository programRepository) {
        super(userAccountRepository);
        this.userAccountRepository = userAccountRepository;
        this.programRepository = programRepository;
    }

    @Override
    protected Class<Account> getEntityClass() {
        return Account.class;
    }

    @Override
    public Account create(AccountRequest request) {

        Optional<Programs> program = programRepository.findById(request.getProgramId());

        if(request.getRole() == Role.STUDENT && request.getYear() < 1)
            throw new InvalidRequestException("Year of study is required.");

        Account userAccount = Account.fromCommand(request);
        userAccount.setProgram(program.get());
        String registrationNumber = generateRegistrationNumber();

        boolean detailsExist = userAccountRepository.existsByRegNumber(registrationNumber);

        if(detailsExist){
            throw new InvalidRequestException("UserAccount already exist.");
        }

        userAccount.setRegNumber(registrationNumber);;
        return userAccountRepository.save(userAccount);
    }

    String generateRegistrationNumber(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random=new Random();

        String registrationNumber = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        boolean existByRegNumber=userAccountRepository.existsByRegNumber(registrationNumber);
        if (existByRegNumber) {
            generateRegistrationNumber();
        }
        return registrationNumber;
    }


    @Override
    public Account update(Account request) {

        boolean detailsExists = userAccountRepository.existsByUsername(request.getUsername());

        if (!detailsExists) {
            throw new InvalidRequestException("UserAccount not found");
        }

        Account userAccount = findById(request.getId());

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
    public Account login(String regNumber, String password) {
        Account account;
        if (!userAccountRepository.existsByRegNumber(regNumber)){
            throw new InvalidRequestException("User does not exist");
        }
        account=getAccountByName(regNumber);
        if (!account.getPassword().equals(password)){
            throw new InvalidRequestException("Wrong password");
        }
        return  account;
    }

    @Override
    public Account updatePassword(LoginRequest request) {
        return null;
    }

    @Override
    public Account getAccountByName(String regNumber) {
        boolean exists=userAccountRepository.existsByRegNumber(regNumber);
        if (!exists){
            throw new InvalidRequestException("User not registered");
        }
        return userAccountRepository.getByRegNumber(regNumber);
    }

    @Override
    public Account findByUsername(String username) {
        return userAccountRepository.findByUsername(username).get();
          //      .orElseThrow(() -> new InvalidRequestException("User record was not found for the supplied username"));
    }

    @Override
    public Account findByFirstName(String firstName) {
        return userAccountRepository.findByFirstName(firstName).get();
           //     .orElseThrow(() -> new InvalidRequestException("User record was not found for the supplied firstName"));
    }

    @Override
    public Account findByUsernameOrFirstname(String username, String firstName){

        boolean userByUsernameExists = userAccountRepository.existsByUsername(username);
        boolean userByFirstNameExists = userAccountRepository.existsByFirstName(firstName);

        if(userByUsernameExists) {
          val userByUsername = findByUsername(username);
            System.out.println(userByUsername);
            return userByUsername;
        }

        else if(userByFirstNameExists) {
           val userByFirstName = findByFirstName(firstName);
            System.out.println(userByFirstName);
            return userByFirstName;
        }

        else
            throw new InvalidRequestException("User record was not found for the supplied firstName or username");
    }

    @Override
    public String logout() {
        return null;
    }

    @Override
    public Collection<Account> findAllLecturers() {
        Collection<Account> accounts = userAccountRepository.findAll();
        Collection<Account> lecturers = accounts.parallelStream().filter(account -> account.getRole() == Role.LECTURER).collect(Collectors.toList());
        return lecturers;
    }
}



