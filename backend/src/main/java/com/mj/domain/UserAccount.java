package com.mj.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

import lombok.Data;

import static javax.persistence.GenerationType.AUTO;

@Entity(name = "USER_ACCOUNT")
@Data
public class UserAccount {

    @Id
    @GeneratedValue(strategy = AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String pesel;

    @Column(name = "INITIAL_AMOUNT")
    private BigDecimal initialAmount;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "USER_ACCOUNT_ID")
    private Set<CurrencyAccount> currencyAccounts;
}
