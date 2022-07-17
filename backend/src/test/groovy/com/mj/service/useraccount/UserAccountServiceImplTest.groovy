package com.mj.service.useraccount

import com.mj.api.dto.in.UserAccountDto
import com.mj.domain.UserAccount
import com.mj.exception.AccountForUserAlreadyExistsException
import com.mj.exception.PeselNotValidException
import com.mj.exception.UserAccountNotFoundException
import com.mj.factory.useraccount.UserAccountFactory
import com.mj.repository.useraccount.UserAccountRepository
import com.mj.validator.PeselValidator
import spock.lang.Specification

class UserAccountServiceImplTest extends Specification {

    private final static String NAME = "NAME"
    private final static String SURNAME = "SURNAME"
    private final static String PESEL = "99010111111"
    private final static BigDecimal AMOUNT = new BigDecimal(100)

    PeselValidator peselValidator = new PeselValidator()
    UserAccountFactory factory = new UserAccountFactory()
    UserAccountRepository repository = Mock()

    UserAccountServiceImpl service = new UserAccountServiceImpl(peselValidator, factory, repository)

    def "Create user account failed: invalid pesel"() {
        given: "dto with wrong pesel"
        UserAccountDto dto = createUserAccountDto("")

        when: "create user account"
        service.createUserAccount(dto)

        then: "exception is thrown"
        thrown(PeselNotValidException)
    }

    def "Create user account failed: user already exists"() {
        given: "dto for existing user"
        UserAccountDto dto = createUserAccountDto(PESEL)
        and:
        repository.existsByPesel(_) >> true

        when: "create user account"
        service.createUserAccount(dto)

        then: "exception is thrown"
        thrown(AccountForUserAlreadyExistsException)
    }

    def "Create user account with success"() {
        given: "dto for user"
        UserAccountDto dto = createUserAccountDto(PESEL)
        and:
        repository.existsByPesel(_) >> false

        when: "create user account"
        service.createUserAccount(dto)

        then: "no exception thrown, user creates with success"
        noExceptionThrown()
        1 * repository.save(_)
    }

    def "Get user account by pesel returns user account"() {
        given: "user account"
        UserAccount userAccount = new UserAccount()
        userAccount.setId(1)
        userAccount.setPesel(PESEL)
        and:
        repository.findByPesel(_) >> Optional.of(userAccount)

        when: "find user account by pesel"
        UserAccount foundUserAccount = service.getUserByPesel(PESEL)

        then: "no exception thrown"
        noExceptionThrown()
        foundUserAccount.id == 1
        foundUserAccount.pesel == PESEL
    }

    def "User not found by pesel"() {
        given: "user account"
        UserAccount userAccount = new UserAccount()
        userAccount.setId(1)
        userAccount.setPesel(PESEL)
        and:
        repository.findByPesel(_) >> Optional.empty()

        when: "find user account by pesel"
        service.getUserByPesel(PESEL)

        then: "throw exception"
        thrown(UserAccountNotFoundException)
    }

    private static UserAccountDto createUserAccountDto(String pesel) {
        UserAccountDto dto = new UserAccountDto()
        dto.setName(NAME)
        dto.setSurname(SURNAME)
        dto.setPesel(pesel)
        dto.setInitialAmount(AMOUNT)
        dto
    }


}
