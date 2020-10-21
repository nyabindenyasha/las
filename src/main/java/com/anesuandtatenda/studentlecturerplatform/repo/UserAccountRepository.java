package com.anesuandtatenda.studentlecturerplatform.repo;


import com.anesuandtatenda.studentlecturerplatform.model.Account;

import java.util.Optional;

public interface UserAccountRepository extends BaseRepository<Account> {

    boolean existsByUsername(String name);

    boolean existsByFirstName(String name);

    Account getByUsername(String username);

    Optional<Account> findByUsername(String username);

    Optional<Account> findByFirstName(String firstName);

}
