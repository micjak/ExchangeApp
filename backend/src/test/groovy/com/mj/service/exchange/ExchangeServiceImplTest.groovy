package com.mj.service.exchange

import com.mj.api.dto.in.Currency
import com.mj.api.dto.in.ExchangeDto
import com.mj.api.dto.out.ExchangeRatesSeries
import com.mj.api.dto.out.Rate
import com.mj.domain.CurrencyAccount
import com.mj.domain.UserAccount
import com.mj.exception.NotEnoughFundsException
import com.mj.exception.PeselNotValidException
import com.mj.exception.UserAccountNotFoundException
import com.mj.repository.useraccount.UserAccountRepository
import com.mj.validator.PeselValidator
import spock.lang.Specification

class ExchangeServiceImplTest extends Specification {

    private final static String PESEL = "99010111111"
    private final static BigDecimal AMOUNT = new BigDecimal(100)

    PeselValidator peselValidator = new PeselValidator()
    UserAccountRepository repository = Mock()

    ExchangeServiceImpl exchangeServiceImpl = new ExchangeServiceImpl(repository, peselValidator)

    def "Pesel is not valid, exchange failed"() {
        given:
        ExchangeDto exchangeDto = createExchangeDto("9122312", AMOUNT)

        ExchangeRatesSeries exchangeRatesSeries = createExchangeRateSeries()

        when:
        exchangeServiceImpl.exchange(exchangeDto, exchangeRatesSeries)

        then:
        thrown(PeselNotValidException)
    }

    def "User account not found by pesel, exchange failed"() {
        given:
        ExchangeDto exchangeDto = createExchangeDto(PESEL, AMOUNT)
        ExchangeRatesSeries exchangeRatesSeries = createExchangeRateSeries()
        and:
        repository.findByPesel(_) >> Optional.empty()

        when:
        exchangeServiceImpl.exchange(exchangeDto, exchangeRatesSeries)

        then:
        thrown(UserAccountNotFoundException)
    }

    def "User has not enough money to exchange, exchange failed"() {
        given:
        ExchangeDto exchangeDto = createExchangeDto(PESEL, new BigDecimal(150))
        ExchangeRatesSeries exchangeRatesSeries = createExchangeRateSeries()
        and:
        UserAccount userAccount = createUserAccount()
        and:
        repository.findByPesel(_) >> Optional.of(userAccount)

        when:
        exchangeServiceImpl.exchange(exchangeDto, exchangeRatesSeries)

        then:
        thrown(NotEnoughFundsException)
    }

    def "Exchange finished with success"() {
        given:
        ExchangeDto exchangeDto = createExchangeDto(PESEL, AMOUNT)
        ExchangeRatesSeries exchangeRatesSeries = createExchangeRateSeries()
        and:
        UserAccount userAccount = createUserAccount()
        and:
        repository.findByPesel(_) >> Optional.of(userAccount)

        when:
        exchangeServiceImpl.exchange(exchangeDto, exchangeRatesSeries)

        then:
        noExceptionThrown()
        1 * repository.save(_)
    }

    private static ExchangeDto createExchangeDto(String pesel, BigDecimal amount) {
        ExchangeDto exchangeDto = new ExchangeDto()
        exchangeDto.setPesel(pesel)
        exchangeDto.setCurrencyFrom(Currency.PLN)
        exchangeDto.setCurrencyTo(Currency.USD)
        exchangeDto.setExchangeAmount(amount)
        exchangeDto
    }

    private static ExchangeRatesSeries createExchangeRateSeries() {
        ExchangeRatesSeries exchangeRatesSeries = new ExchangeRatesSeries()
        exchangeRatesSeries.setCode("USD")
        exchangeRatesSeries.setCurrency("dolar amerykański")
        exchangeRatesSeries.setTable("A")

        Set<Rate> rates = new HashSet<>()
        Rate rate = new Rate()
        rate.setMid(new BigDecimal(4.7966))
        rate.setNo("136/A/NBP/2022")
        rate.setEffectiveDate("2022-07-15")
        rates.add(rate);
        exchangeRatesSeries.setRates(rates)
        exchangeRatesSeries
    }

    private static UserAccount createUserAccount() {
        UserAccount userAccount = new UserAccount()
        userAccount.setId(1)
        userAccount.setPesel(PESEL)
        userAccount.setInitialAmount(new BigDecimal(AMOUNT))
        userAccount.setName("Name")
        userAccount.setSurname("Surname")
        Set<CurrencyAccount> currencyAccounts = new HashSet<>()
        CurrencyAccount currencyAccountPln = new CurrencyAccount(new BigDecimal(AMOUNT), com.mj.domain.Currency.PLN)
        CurrencyAccount currencyAccountUsd = new CurrencyAccount(new BigDecimal(0), com.mj.domain.Currency.USD)
        currencyAccounts.add(currencyAccountPln)
        currencyAccounts.add(currencyAccountUsd)
        userAccount.setCurrencyAccounts(currencyAccounts)
        userAccount
    }

}

