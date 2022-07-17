package com.mj.service.useraccount;

import com.mj.api.dto.in.UserAccountDto;
import com.mj.domain.UserAccount;

public interface UserAccountService {

    void createUserAccount(UserAccountDto dto);

    UserAccount getUserByPesel(String pesel);


}
