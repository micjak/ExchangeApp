package com.mj.validator

import spock.lang.Specification

import java.time.LocalDate

class PeselValidatorTest extends Specification {

    def "Pesel validator works fine"() {
        given:
        PeselValidator peselValidator = Spy(PeselValidator)
        peselValidator.getCurrentTime() >> LocalDate.parse("2022-01-01")
        when:
        boolean isValid = peselValidator.isPeselValid(pesel)

        then:
        assert isValid == excpectedValidationResult

        where:
        pesel         | excpectedValidationResult
        "90010112121" | true
        "75112901010" | true
        "08250112121" | false
        "9g01011212a" | false
        null          | false
        ""            | false
        "75133201010" | false
    }
}
