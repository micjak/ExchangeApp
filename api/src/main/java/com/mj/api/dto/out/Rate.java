package com.mj.api.dto.out;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class Rate implements Serializable {

    @Serial
    private static final long serialVersionUID = -2570589055057029606L;

    private String no;
    private String effectiveDate;
    private BigDecimal mid;

}
