package com.mj.service.exchange;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import com.mj.api.dto.in.Currency;
import com.mj.api.dto.in.ExchangeDto;
import com.mj.api.dto.out.ExchangeRatesSeries;
import com.mj.api.dto.out.Rate;
import com.mj.domain.CurrencyAccount;
import com.mj.domain.UserAccount;
import com.mj.exception.*;
import com.mj.repository.useraccount.UserAccountRepository;
import com.mj.validator.PeselValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final UserAccountRepository userAccountRepository;
    private final PeselValidator peselValidator;


    @Override
    public void exchange(ExchangeDto dto, ExchangeRatesSeries exchangeRatesSeries) {
        if (!peselValidator.isPeselValid(dto.getPesel())) {
            throw new PeselNotValidException("PESEL identifier is not valid. Please provide valid PESEL.");
        }

        Optional<UserAccount> userAccountOptional = userAccountRepository.findByPesel(dto.getPesel());
        UserAccount userAccount = userAccountOptional.orElseThrow(() -> new UserAccountNotFoundException("User account not found."));

        CurrencyAccount currencyAccountFrom = getCurrencyAccount(dto.getCurrencyFrom(), userAccount);
        CurrencyAccount currencyAccountTo = getCurrencyAccount(dto.getCurrencyTo(), userAccount);

        validateFunds(currencyAccountFrom.getAmount(), dto.getExchangeAmount());

        BigDecimal amountLeft = currencyAccountFrom.getAmount().subtract(dto.getExchangeAmount());
        currencyAccountFrom.setAmount(amountLeft);

        BigDecimal exchangedAmount = exchange(dto, exchangeRatesSeries, currencyAccountTo);
        currencyAccountTo.setAmount(currencyAccountTo.getAmount().add(exchangedAmount));

        userAccountRepository.save(userAccount);
    }

    private void validateFunds(BigDecimal availableAmount, BigDecimal exchangeAmount) {
        if (availableAmount.compareTo(exchangeAmount) < 0) {
            throw new NotEnoughFundsException("There are not enough funds to exchange.");
        }
    }

    private BigDecimal exchange(ExchangeDto dto, ExchangeRatesSeries exchangeRatesSeries, CurrencyAccount currencyAccountTo) {
        BigDecimal currencyRate = getCurrencyRate(exchangeRatesSeries);
        return Currency.USD.equals(dto.getCurrencyTo()) ? dto.getExchangeAmount().divide(currencyRate, 3, RoundingMode.HALF_UP) : dto.getExchangeAmount().multiply(currencyRate);
    }

    private BigDecimal getCurrencyRate(ExchangeRatesSeries exchangeRatesSeries) {
        Optional<Rate> exchangeRate = exchangeRatesSeries.getRates().stream().findFirst();
        return exchangeRate.map(Rate::getMid).orElseThrow(() -> new CurrencyRateNotFoundException("Currency rate not found."));
    }

    private CurrencyAccount getCurrencyAccount(Currency currency, UserAccount userAccount) {
        Optional<CurrencyAccount> currencyAccountOptional = userAccount.getCurrencyAccounts()
                                                                       .stream()
                                                                       .filter(currencyAccount -> currency.name().equals(currencyAccount.getCurrency().name()))
                                                                       .findFirst();
        return currencyAccountOptional.orElseThrow(() -> new AccountForCurrencyDoesNotExistsException(""));
    }
}
