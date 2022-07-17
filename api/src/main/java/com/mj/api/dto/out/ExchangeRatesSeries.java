package com.mj.api.dto.out;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

import lombok.Data;

@Data
public class ExchangeRatesSeries implements Serializable {

    @Serial
    private static final long serialVersionUID = 4664750552004548353L;

    private String table;
    private String currency;
    private String code;
    private Set<Rate> rates;

}
