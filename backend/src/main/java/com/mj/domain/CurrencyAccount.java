package com.mj.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

import static javax.persistence.GenerationType.AUTO;

@Entity(name = "CURRENCY_ACCOUNT")
@Data
@NoArgsConstructor
public class CurrencyAccount {

    @Id
    @GeneratedValue(strategy = AUTO)
    private long id;

    @Column
    private Currency currency;

    @Column
    private BigDecimal amount;

    public CurrencyAccount(BigDecimal initialAmount, Currency currency) {
        this.amount = initialAmount;
        this.currency = currency;
    }
}
