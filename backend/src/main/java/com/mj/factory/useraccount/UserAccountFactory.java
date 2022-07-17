package com.mj.factory.useraccount;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.mj.api.dto.in.UserAccountDto;
import com.mj.domain.Currency;
import com.mj.domain.CurrencyAccount;
import com.mj.domain.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAccountFactory {

    public UserAccount createUserAccount(UserAccountDto dto) {
        UserAccount userAccount = new UserAccount();
        userAccount.setInitialAmount(dto.getInitialAmount());
        userAccount.setName(dto.getName());
        userAccount.setSurname(dto.getSurname());
        userAccount.setPesel(dto.getPesel());

        Set<CurrencyAccount> currencyAccounts = new HashSet<>();
        currencyAccounts.add(new CurrencyAccount(dto.getInitialAmount(), Currency.PLN));
        currencyAccounts.add(new CurrencyAccount(new BigDecimal(0), Currency.USD));

        userAccount.setCurrencyAccounts(currencyAccounts);
        return userAccount;
    }
}
