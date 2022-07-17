package com.mj.validator;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Component;

@Component
public class PeselValidator {

    public boolean isPeselValid(String pesel) {
        if (pesel == null || pesel.isBlank() || pesel.length() != 11) {
            return false;
        }
        return validateBirthDate(pesel);
    }
    
    private boolean validateBirthDate(String pesel) {
        int[] peselArray = new int[11];
        for (int i = 0; i < 11; i++) {
            peselArray[i] = Character.getNumericValue(pesel.charAt(i));
        }

        int birthDay = 10 * peselArray[4] + peselArray[5];
        int birthYear = 10 * peselArray[0] + peselArray[1];
        int birthMonth = 10 * peselArray[2] + peselArray[3];
        if (birthDay > 31) {
            return false;
        }

        if (birthMonth <= 12) {
            birthYear += 1900;
        } else if (birthMonth <= 32) {
            birthYear += 2000;
            birthMonth -= 20;
        } else if (birthMonth <= 52) {
            birthYear += 2100;
            birthMonth -= 40;
        } else if (birthMonth <= 72) {
            birthYear += 2200;
            birthMonth -= 60;
        } else if (birthMonth <= 92) {
            birthYear += 1800;
            birthMonth -= 80;
        } else {
            return false;
        }

        return isLegalAge(LocalDate.of(birthYear, birthMonth, birthDay));
    }

    private boolean isLegalAge(LocalDate birthDate) {
        return Period.between(birthDate, getCurrentTime()).getYears() >= 18;
    }

    LocalDate getCurrentTime() {
        return LocalDate.now();
    }


}
