package com.mj.service.useraccount;

import java.util.Optional;

import com.mj.api.dto.in.UserAccountDto;
import com.mj.domain.UserAccount;
import com.mj.exception.AccountForUserAlreadyExistsException;
import com.mj.exception.PeselNotValidException;
import com.mj.exception.UserAccountNotFoundException;
import com.mj.factory.useraccount.UserAccountFactory;
import com.mj.repository.useraccount.UserAccountRepository;
import com.mj.validator.PeselValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final PeselValidator peselValidator;
    private final UserAccountFactory factory;
    private final UserAccountRepository repository;

    @Override
    public void createUserAccount(UserAccountDto dto) {
        validateUserAccountData(dto);
        UserAccount userAccount = factory.createUserAccount(dto);
        repository.save(userAccount);
    }

    private void validateUserAccountData(UserAccountDto dto) {
        if (!peselValidator.isPeselValid(dto.getPesel())) {
            throw new PeselNotValidException("PESEL identifier is not valid. Please provide valid PESEL.");
        }
        boolean userAccountExists = repository.existsByPesel(dto.getPesel());
        if (userAccountExists) {
            throw new AccountForUserAlreadyExistsException("Account already exists!");
        }
    }

    @Override
    public UserAccount getUserByPesel(String pesel) {
        Optional<UserAccount> userAccount = repository.findByPesel(pesel);
        return userAccount.orElseThrow(() -> new UserAccountNotFoundException("User account not found."));
    }
}
