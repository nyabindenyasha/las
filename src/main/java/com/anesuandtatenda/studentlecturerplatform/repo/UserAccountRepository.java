package com.anesuandtatenda.studentlecturerplatform.repo;


import com.anesuandtatenda.studentlecturerplatform.model.UserAccount;

import java.util.Optional;

public interface UserAccountRepository extends BaseRepository<UserAccount> {

    boolean existsByFirstName(String name);

    boolean existsByRegNumber(String regNumber);

    UserAccount getByRegNumber(String username);

    Optional<UserAccount> findByFirstName(String firstName);

}
