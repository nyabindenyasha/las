package com.anesuandtatenda.studentlecturerplatform.repo;


import com.anesuandtatenda.studentlecturerplatform.model.UserAccount;

import java.util.Optional;

public interface UserAccountRepository extends BaseRepository<UserAccount> {

    boolean existsByUsername(String name);

    boolean existsByFirstName(String name);

    boolean existsByRegNumber(String regNumber);

    UserAccount getByUsername(String username);

    UserAccount getByRegNumber(String username);

    Optional<UserAccount> findByUsername(String username);

    Optional<UserAccount> findByFirstName(String firstName);

}
