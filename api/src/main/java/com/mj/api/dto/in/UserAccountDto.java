package com.mj.api.dto.in;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class UserAccountDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -8129736676849932677L;

    @NotEmpty(message = "Name is required.")
    private String name;

    @NotEmpty(message = "Surname is required.")
    private String surname;

    @NotEmpty(message = "Pesel is required.")
    @Size(max = 11, min = 11, message = "Pesel has invalid size.")
    private String pesel;

    @DecimalMin(value = "0.1", message = "Amount to exchange must be grate than 0.")
    private BigDecimal initialAmount;


}
