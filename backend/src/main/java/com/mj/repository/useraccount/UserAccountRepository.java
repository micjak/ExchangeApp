package com.mj.repository.useraccount;

import java.util.Optional;

import com.mj.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    boolean existsByPesel(String pesel);

    Optional<UserAccount> findByPesel(String pesel);

}
