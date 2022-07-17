package com.mj.factory.useraccount

import com.mj.api.dto.in.UserAccountDto
import com.mj.domain.UserAccount
import spock.lang.Specification

class UserAccountFactoryTest extends Specification {

    private final static String NAME = "NAME"
    private final static String SURNAME = "SURNAME"
    private final static String PESEL = "99010111111"
    private final static BigDecimal AMOUNT = new BigDecimal(100)

    UserAccountFactory factory = new UserAccountFactory()

    def "User account factory creates UserAccount object"() {
        given:
        UserAccountDto dto = new UserAccountDto()
        dto.setInitialAmount(AMOUNT)
        dto.setName(NAME)
        dto.setSurname(SURNAME)
        dto.setPesel(PESEL)

        when:
        UserAccount userAccount = factory.createUserAccount(dto)

        then:
        userAccount.name == NAME
        userAccount.surname == SURNAME
        userAccount.pesel == PESEL
        userAccount.initialAmount == AMOUNT
        userAccount.currencyAccounts.size() == 2
    }


}
