package com.mj.api.dto.in;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class ExchangeDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 711640368981109375L;

    @NotNull(message = "Currency from exchange required.")
    private Currency currencyFrom;
    @NotNull(message = "Currency to exchange required.")
    private Currency currencyTo;
    @DecimalMin(value = "0.1", message = "Amount to exchange must be grate than 0.")
    private BigDecimal exchangeAmount;
    @NotEmpty(message = "Pesel is required.")
    @Size(max = 11, min = 11, message = "Pesel has invalid size.")
    private String pesel;



}
